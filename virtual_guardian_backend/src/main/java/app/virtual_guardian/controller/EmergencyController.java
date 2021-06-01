package app.virtual_guardian.controller;

import app.virtual_guardian.dto.EmergencyDTO;
import app.virtual_guardian.dto.builder.EmergencyBuilder;
import app.virtual_guardian.entity.Caregiver;
import app.virtual_guardian.entity.Doctor;
import app.virtual_guardian.entity.Emergency;
import app.virtual_guardian.entity.Patient;
import app.virtual_guardian.service.CaregiverService;
import app.virtual_guardian.service.DoctorService;
import app.virtual_guardian.service.EmergencyService;
import app.virtual_guardian.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin(origins = "*")
public class EmergencyController {
    private final EmergencyService emergencyService;
    private final PatientService patientService;
    private final CaregiverService caregiverService;
    private final DoctorService doctorService;

    @Autowired
    public EmergencyController(EmergencyService emergencyService, PatientService patientService, CaregiverService caregiverService, DoctorService doctorService) {
        this.emergencyService = emergencyService;
        this.patientService = patientService;
        this.caregiverService = caregiverService;
        this.doctorService = doctorService;
    }

    //----------------SAVE EMERGENCY OF PATIENT-------------
    @RequestMapping(value = "/emergency/save/{patientId}", method = RequestMethod.POST)
    public ResponseEntity saveEmergency(@PathVariable("patientId") String patientId, @RequestBody EmergencyDTO emergencyDTO) {
        Patient patient = patientService.getPatient(patientId);

        if(patient == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Emergency emergency = EmergencyBuilder.toEntity(emergencyDTO, patient);
        emergencyService.saveEmergency(emergency);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //----------------GET EMERGENCIES OF PATIENT-------------
    @RequestMapping(value = "/emergency/get_all/patient/{patientId}", method = RequestMethod.GET)
    public ResponseEntity<List<EmergencyDTO>> getEmergenciesOfPatient(@PathVariable("patientId") String patientId) {
        Patient patient = patientService.getPatient(patientId);

        if(patient == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<EmergencyDTO> emergenciesList = new ArrayList<>();

        patient.getListOfEmergencies().forEach(emergency -> emergenciesList.add(EmergencyBuilder.toDTO(emergency)));

        return new ResponseEntity<>(emergenciesList, HttpStatus.OK);
    }

    //----------------GET EMERGENCIES OF PATIENTS OF CAREGIVER-------------
    @RequestMapping(value = "/emergency/get_all/caregiver/{caregiverId}", method = RequestMethod.GET)
    public ResponseEntity<List<EmergencyDTO>> getEmergenciesOfPatientsOfCaregiver(@PathVariable("caregiverId") String caregiverId) {
        Caregiver caregiver = caregiverService.getCaregiver(caregiverId);

        return getListResponseEntity(caregiver == null, caregiver.getListOfPatients());
    }

    //----------------GET EMERGENCIES OF PATIENTS OF DOCTOR-------------
    @RequestMapping(value = "/emergency/get_all/doctor/{doctorId}", method = RequestMethod.GET)
    public ResponseEntity<List<EmergencyDTO>> getEmergenciesOfPatientsOfDoctor(@PathVariable("doctorId") String doctorId) {
        Doctor doctor = doctorService.getDoctorById(doctorId);

        return getListResponseEntity(doctor == null, doctor.getListOfPatients());
    }

    private ResponseEntity<List<EmergencyDTO>> getListResponseEntity(boolean b, Set<Patient> listOfPatients) {
        if (b) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<EmergencyDTO> emergenciesList = new ArrayList<>();

        listOfPatients.forEach(patient -> {
            patient.getListOfEmergencies().forEach(emergency -> emergenciesList.add(EmergencyBuilder.toDTO(emergency)));
        });


        return new ResponseEntity<>(emergenciesList, HttpStatus.OK);
    }
}
