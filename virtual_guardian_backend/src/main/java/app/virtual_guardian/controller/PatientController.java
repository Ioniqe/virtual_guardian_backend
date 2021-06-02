package app.virtual_guardian.controller;

import app.virtual_guardian.dto.PatientDTO;
import app.virtual_guardian.dto.TreatmentDTO;
import app.virtual_guardian.dto.UserDTO;
import app.virtual_guardian.dto.builder.PatientBuilder;
import app.virtual_guardian.dto.builder.TreatmentBuilder;
import app.virtual_guardian.dto.builder.UserBuilder;
import app.virtual_guardian.entity.*;
import app.virtual_guardian.service.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin(origins = "*")
public class PatientController {

    private final UserService userService;
    private final DoctorService doctorService;
    private final PatientService patientService;
    private final CaregiverService caregiverService;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final EmergencyService emergencyService;


    @Autowired
    public PatientController(UserService userService, DoctorService doctorService, PatientService patientService, CaregiverService caregiverService, SimpMessagingTemplate simpMessagingTemplate, EmergencyService emergencyService) {
        this.userService = userService;
        this.doctorService = doctorService;
        this.patientService = patientService;
        this.caregiverService = caregiverService;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.emergencyService = emergencyService;
    }

    //---------------------------------CREATE--------------------------------- TODO
    @RequestMapping(value = "/patient/new/{doctorUserId}", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity savePatient(@RequestBody PatientDTO patientDTO, @PathVariable("doctorUserId") String doctorUserId) {
        UserDTO userDTO = UserBuilder.toUserDTOFromPatientDTO(patientDTO);
        User user = userService.getInsertedUser(userDTO);
        Doctor doctor = doctorService.getDoctorById(doctorUserId);
        if (doctor == null) {
            System.out.println("Doctor was not found when performing /patient/new/{doctorUserId}");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        patientService.insertPatient(user, doctor, patientDTO.getAddress());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    private UserDTO verifyPatientExistence(String userId) {
        UserDTO dto = userService.getUserDTOById(userId);
        if (dto == null)
            return null;
        if (!dto.getType().equals("patient")) {
            System.out.println("Patient was not found.");
            return null;
        }
        return dto;
    }

    //-----------------------------------READ------------------------------ TODO
    @RequestMapping(value = "/patient/{userId}", method = RequestMethod.GET)
    public ResponseEntity<PatientDTO> readPatient(@PathVariable("userId") String userId) {
        UserDTO userDTO = verifyPatientExistence(userId);
        if (userDTO == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Patient patient = patientService.getPatient(userId);
        PatientDTO dto = PatientBuilder.toPatientDTOFromUserDTO(userDTO, patient);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    //----------------------------------UPDATE-----------------------------
    @RequestMapping(value = "/patient/update", method = RequestMethod.PUT)
    public ResponseEntity updatePatient(@RequestBody PatientDTO newPatientDTO) {
        UserDTO userDTO = verifyPatientExistence(newPatientDTO.getId());
        if (userDTO == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        patientService.updatePatient(newPatientDTO);
        UserDTO newUserDTO = UserBuilder.toUserDTOFromPatientDTO(newPatientDTO);
        userService.updateUser(newUserDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //----------------------------------DELETE PATIENTS-----------------------------
    @RequestMapping(value = "/patient/delete/bulk", method = RequestMethod.DELETE)
    public ResponseEntity deletePatients(@RequestBody List<String> patientsToBeDeleted) {
        patientsToBeDeleted.forEach(userService::deleteUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //---------------------------------ASSIGN CAREGIVER---------------------------------
    @RequestMapping(value = "/patient/set_caregiver/{patientUserId}/{caregiverUserId}", method = RequestMethod.POST)
    //TODO test thoroughly
    public ResponseEntity setCaregiverToPatientByPeopleId(@PathVariable("patientUserId") String patientUserId, @PathVariable("caregiverUserId") String caregiverUserId) {
        UserDTO userDTO = verifyPatientExistence(patientUserId);
        if (userDTO == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Patient patient = patientService.getPatient(patientUserId);
        Caregiver oldCaregiver = patient.getCaregiver();

        if (caregiverUserId.equals("-")) {
            if (oldCaregiver != null) {
                oldCaregiver.getListOfPatients().remove(patient);
                caregiverService.saveCaregiver(oldCaregiver);

                patient.setCaregiver(null);
                patientService.saveUpdatedPatient(patient);
            }
            return new ResponseEntity<>(HttpStatus.OK);

        }

        Caregiver newCaregiver = caregiverService.getCaregiver(caregiverUserId);
        if(newCaregiver == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (oldCaregiver == null) {
            patient.setCaregiver(newCaregiver);
            patientService.saveUpdatedPatient(patient);
        } else { // oldCaregiver != null && newCareg != null
            oldCaregiver.getListOfPatients().remove(patient);
            caregiverService.saveCaregiver(oldCaregiver);

            patient.setCaregiver(newCaregiver);
            patientService.saveUpdatedPatient(patient);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    //-----------------------READ ALL PATIENTS OF DOCTOR/CAREGIVER-------------------------
    @RequestMapping(value = "/patient/all/{userId}", method = RequestMethod.GET)
    public ResponseEntity<List<PatientDTO>> readAllPatients(@PathVariable("userId") String userId) {
        Doctor doctor = doctorService.getDoctorById(userId);
        Caregiver caregiver = caregiverService.getCaregiver(userId);

        if(doctor == null && caregiver == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<PatientDTO> patientsList;
        if(doctor != null){
            Set<Patient> patientsOfDoctor = doctor.getListOfPatients();
            patientsList = PatientBuilder.toPatientDTOListFromPatientSet(patientsOfDoctor);
        }else {
            Set<Patient> patientsOfCaregiver = caregiver.getListOfPatients();
            patientsList = PatientBuilder.toPatientDTOListFromPatientSet(patientsOfCaregiver);
        }


        return new ResponseEntity<>(patientsList, HttpStatus.OK);
    }

    //---------------------------------GET TREATMENTS OF PATIENT--------------------------------- TODO
    @RequestMapping(value = "/treatments/{userId}", method = RequestMethod.GET)
    public ResponseEntity<List<TreatmentDTO>> getTreatmentsOfPatient(@PathVariable("userId") String userId) {
        UserDTO userDTO = verifyPatientExistence(userId);
        if (userDTO == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Patient patient = patientService.getPatient(userId);
        Set<Treatment> treatments = patient.getListOfTreatments();

        List<TreatmentDTO> treatmentDTOS = TreatmentBuilder.toTreatmentDTOListFromTreatmentSet(treatments);
        return new ResponseEntity<>(treatmentDTOS, HttpStatus.OK);
    }

    @RequestMapping(value = "/sendEmergency/{patientUserId}", method = RequestMethod.GET)
    public ResponseEntity sendEmergency(@PathVariable("patientUserId") String userId) {
        UserDTO userDTO = verifyPatientExistence(userId);
        if (userDTO == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Patient patient = patientService.getPatient(userId);

        String doctorUserId = patient.getDoctor().getUserId();

        JSONObject item = new JSONObject();
        item.put("userId", doctorUserId);
        item.put("patientName", patient.getUser().getFirstname() + " " + patient.getUser().getLastname());
        item.put("message", "emergency");

        simpMessagingTemplate.convertAndSend("/topic/patient_emergency", item.toString());

        if (patient.getCaregiver() != null) {
            String caregiverUserId = patient.getCaregiver().getUserId();
            item.remove("userId");
            item.put("userId", caregiverUserId);
            simpMessagingTemplate.convertAndSend("/topic/patient_emergency", item.toString());
        }

        //save emergency
        Date date = new Date();
        long time = date.getTime();
        Timestamp ts = new Timestamp(time);
        Emergency emergency = new Emergency(ts, patient);
        emergencyService.saveEmergency(emergency);

        //send emergency object
        item = new JSONObject();
        item.put("id", emergency.getId());
        item.put("patientName", patient.getUser().getFirstname()+ " " + patient.getUser().getLastname());
        item.put("date", emergency.getDate());
        item.put("userId", patient.getDoctor().getUserId());
        simpMessagingTemplate.convertAndSend("/topic/emergency_object", item.toString());

        if(emergency.getPatient().getCaregiver() != null){
            String caregiverUserId = patient.getCaregiver().getUserId();
            item.remove("userId");
            item.put("userId", caregiverUserId);
            simpMessagingTemplate.convertAndSend("/topic/emergency_object", item.toString());
        }

        return new ResponseEntity(HttpStatus.OK);
    }
}
