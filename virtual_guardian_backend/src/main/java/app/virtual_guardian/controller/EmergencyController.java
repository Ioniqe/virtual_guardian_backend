package app.virtual_guardian.controller;

import app.virtual_guardian.dto.EmergencyDTO;
import app.virtual_guardian.dto.builder.EmergencyBuilder;
import app.virtual_guardian.entity.Emergency;
import app.virtual_guardian.entity.Patient;
import app.virtual_guardian.service.EmergencyService;
import app.virtual_guardian.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class EmergencyController {
    private final EmergencyService emergencyService;
    private final PatientService patientService;

    @Autowired
    public EmergencyController(EmergencyService emergencyService, PatientService patientService) {
        this.emergencyService = emergencyService;
        this.patientService = patientService;
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
    @RequestMapping(value = "/emergency/get_all/{patientId}", method = RequestMethod.GET)
    public ResponseEntity<List<EmergencyDTO>> getEmergencies(@PathVariable("patientId") String patientId) {
        Patient patient = patientService.getPatient(patientId);

        if(patient == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<EmergencyDTO> emergenciesList = new ArrayList<>();

        patient.getListOfEmergencies().forEach(emergency -> emergenciesList.add(EmergencyBuilder.toDTO(emergency)));

        return new ResponseEntity<>(emergenciesList, HttpStatus.OK);
    }
}
