package app.virtual_guardian.controller;

import app.virtual_guardian.dto.PatientDTO;
import app.virtual_guardian.dto.TreatmentDTO;
import app.virtual_guardian.dto.UserDTO;
import app.virtual_guardian.dto.builder.PatientBuilder;
import app.virtual_guardian.dto.builder.TreatmentBuilder;
import app.virtual_guardian.dto.builder.UserBuilder;
import app.virtual_guardian.entity.*;
import app.virtual_guardian.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin(origins = "*")
public class PatientController {

    private final UserService userService;
    private final DoctorService doctorService;
    private final PatientService patientService;
    private final CaregiverService caregiverService;
    private final TreatmentService treatmentService;

    @Autowired
    public PatientController(UserService userService, DoctorService doctorService, PatientService patientService, CaregiverService caregiverService, TreatmentService treatmentService) {
        this.userService = userService;
        this.doctorService = doctorService;
        this.patientService = patientService;
        this.caregiverService = caregiverService;
        this.treatmentService = treatmentService;
    }

    //---------------------------------CREATE--------------------------------- TODO
    @RequestMapping(value = "/patient/new/{doctorUserId}", method = { RequestMethod.GET, RequestMethod.POST })
    public ResponseEntity savePatient(@RequestBody PatientDTO patientDTO, @PathVariable("doctorUserId") String doctorUserId) {
        UserDTO userDTO = UserBuilder.toUserDTOFromPatientDTO(patientDTO);
        User user = userService.getInsertedUser(userDTO);
        Doctor doctor = doctorService.getDoctorById(doctorUserId);
        if(doctor == null){
            System.out.println("Doctor was not found when performing /patient/new/{doctorUserId}");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        patientService.insertPatient(user, doctor, patientDTO.getAddress());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    private UserDTO verifyPatientExistence(String userId) {
        UserDTO dto = userService.getUserDTOById(userId);
        if(dto == null)
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
        if(userDTO == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Patient patient = patientService.getPatient(userId);
        PatientDTO dto = PatientBuilder.toPatientDTOFromUserDTO(userDTO, patient);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    //-----------------------READ ALL PATIENTS OF DOCTOR-------------------------
    @RequestMapping(value = "/patient/all/{doctorId}", method = RequestMethod.GET)
    public ResponseEntity<List<PatientDTO>> readAllPatients(@PathVariable("doctorId") String doctorId) {
        Doctor doctor = doctorService.getDoctorById(doctorId);
        Set<Patient> patientsOfDoctor = doctor.getListOfPatients();
        List<PatientDTO> patientsDTO = PatientBuilder.toPatientDTOListFromPatientSet(patientsOfDoctor);
        return new ResponseEntity<>(patientsDTO, HttpStatus.OK);
    }

    //----------------------------------UPDATE----------------------------- TODO
    @RequestMapping(value = "/patient/update", method = RequestMethod.PUT)
    public ResponseEntity updatePatient(@RequestBody PatientDTO newPatientDTO) {
        UserDTO userDTO = verifyPatientExistence(newPatientDTO.getId());
        if(userDTO == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        patientService.updatePatient(newPatientDTO);
        UserDTO newUserDTO = UserBuilder.toUserDTOFromPatientDTO(newPatientDTO);
        userService.updateUser(newUserDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    //----------------------------------DELETE----------------------------- TODO
//    @RequestMapping(value = "/patient/delete/{userId}", method = RequestMethod.DELETE)
//    public ResponseEntity deletePatient(@PathVariable("userId") String userId) {
//        Patient patient = patientService.getPatient(userId);
//        if(patient == null)
//            return new ResponseEntity(HttpStatus.NOT_FOUND);
//
//        userService.deleteUser(userId);
//        return new ResponseEntity(HttpStatus.OK);
//    }

    //----------------------------------DELETE PATIENTS-----------------------------
    @RequestMapping(value = "/patient/delete/bulk", method = RequestMethod.DELETE)
    public ResponseEntity deletePatients(@RequestBody List<String> patientsToBeDeleted) {
        patientsToBeDeleted.forEach(userService::deleteUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //---------------------------------ASSIGN CAREGIVER--------------------------------- TODO
    @RequestMapping(value = "/patient/set_caregiver/{patientUserId}/{caregiverUserId}", method = RequestMethod.POST)
    public ResponseEntity setCaregiverToPatientByPeopleId(@PathVariable("patientUserId") String patientUserId, @PathVariable("caregiverUserId") String caregiverUserId) {
        UserDTO userDTO = verifyPatientExistence(patientUserId);
        if(userDTO == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Patient patient = patientService.getPatient(patientUserId);
        Caregiver caregiver = caregiverService.getCaregiver(caregiverUserId);

        if(caregiver == null){
            System.out.println("Caregiver was not found when trying to assign it to patient");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        patientService.setCaregiverToPatient(patient, caregiver);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private ResponseEntity<List<PatientDTO>> getListResponseEntity(boolean b, Set<Patient> listOfPatients) {
        if (b)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        List<PatientDTO> patients = PatientBuilder.toPatientDTOListFromPatientSet(listOfPatients);
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    //----------------------------------GET LIST OF PATIENTS OF DOCTOR---------------------------------- TODO
    @RequestMapping(value = "/get_patients/doctor/{userId}", method = RequestMethod.GET) //TODO verify that this works
    public ResponseEntity<List<PatientDTO>> getListOfPatientsOfDoctor(@PathVariable("userId") String userId) {
        Doctor doctor = doctorService.getDoctorById(userId);

        return getListResponseEntity(doctor == null, doctor.getListOfPatients());
    }

    //----------------------------------GET LIST OF PATIENTS OF CAREGIVER---------------------------------- TODO
    @RequestMapping(value = "/get_patients/caregiver/{userId}", method = RequestMethod.GET) //TODO verify that this works
    public ResponseEntity<List<PatientDTO>> getListOfPatientsOfCaregiver(@PathVariable("userId") String userId) {
        Caregiver caregiver = caregiverService.getCaregiver(userId);

        return getListResponseEntity(caregiver == null, caregiver.getListOfPatients());
    }

    //---------------------------------GET TREATMENTS OF PATIENT--------------------------------- TODO
    @RequestMapping(value = "/treatments/{userId}", method = RequestMethod.GET)
    public ResponseEntity<List<TreatmentDTO>> getTreatmentsOfPatient(@PathVariable("userId") String userId) {
        UserDTO userDTO = verifyPatientExistence(userId);
        if(userDTO == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Patient patient = patientService.getPatient(userId);
        Set<Treatment> treatments = patient.getListOfTreatments();

        List<TreatmentDTO> treatmentDTOS = TreatmentBuilder.toTreatmentDTOListFromTreatmentSet(treatments);
        return new ResponseEntity<>(treatmentDTOS, HttpStatus.OK);
    }
}
