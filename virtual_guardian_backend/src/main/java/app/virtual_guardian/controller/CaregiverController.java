package app.virtual_guardian.controller;

import app.virtual_guardian.dto.UserDTO;
import app.virtual_guardian.entity.Caregiver;
import app.virtual_guardian.entity.Patient;
import app.virtual_guardian.entity.User;
import app.virtual_guardian.service.CaregiverService;
import app.virtual_guardian.service.PatientService;
import app.virtual_guardian.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;


@RestController
//@CrossOrigin(origins = "http://localhost:8080", allowedHeaders = "*", exposedHeaders = "Authorization")
@CrossOrigin(origins = "*")
public class CaregiverController {

    private final UserService userService;
    private final CaregiverService caregiverService;
    private final PatientService patientService;

    @Autowired
    public CaregiverController(UserService userService, CaregiverService caregiverService, PatientService patientService) {
        this.userService = userService;
        this.caregiverService = caregiverService;
        this.patientService = patientService;
    }

    private UserDTO verifyCaregiverExistence(String userId) {
        UserDTO dto = userService.getUserDTOById(userId);
        if(dto == null)
            return null;
        if (!dto.getType().equals("caregiver")) {
            System.out.println("Caregiver was not found.");
            return null;
        }
        return dto;
    }

    //----------------------------------CREATE-----------------------------
    @RequestMapping(value = "/caregiver/new", method = RequestMethod.POST)
    public ResponseEntity saveCaregiver(@RequestBody UserDTO userDTO) {
        User user = userService.getInsertedUser(userDTO);
        caregiverService.insertCaregiver(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //----------------------------------READ----------------------------------
    @RequestMapping(value = "/caregiver/{userId}", method = RequestMethod.GET)
    public ResponseEntity<UserDTO> readDoctor(@PathVariable("userId") String userId) {
        UserDTO dto = verifyCaregiverExistence(userId);
        if(dto == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    //----------------------------------DELETE-----------------------------
    @RequestMapping(value = "/caregiver/delete/{userId}", method = { RequestMethod.GET, RequestMethod.DELETE })
    public ResponseEntity deleteCaregiver(@PathVariable("userId") String userId) {
        //update patient with caregiverId: null
        Caregiver caregiver = caregiverService.getCaregiver(userId);

        if(caregiver == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        try {
            Set<Patient> patients = caregiver.getListOfPatients();
            patients.forEach(patient -> {
                patient.setCaregiver(null);
                patientService.saveUpdatedPatient(patient);
            });
        } catch (Exception e) {
            System.out.println("Caregiver has no patients");
        }

        caregiver.setListOfPatients(new HashSet<>());
        caregiverService.saveCaregiver(caregiver);


        userService.deleteUser(userId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
