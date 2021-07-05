package app.virtual_guardian.controller;

import app.virtual_guardian.dto.UserDTO;
import app.virtual_guardian.dto.builder.UserBuilder;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@RestController
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

    //----------------------------------READ ALL----------------------------------
    @RequestMapping(value = "/caregiver/all", method = RequestMethod.GET)
    public ResponseEntity<List<UserDTO>> readAllCaregivers() {
        List<User> caregivers = userService.getAllUsersOfAType("caregiver");
        List<UserDTO> caregiversDTO = new ArrayList<>();
        caregivers.forEach(caregiver -> caregiversDTO.add(UserBuilder.toUserDTOWithDetails(caregiver)));
        return new ResponseEntity<>(caregiversDTO, HttpStatus.OK);
    }

    //----------------------------------UPDATE-----------------------------
    @RequestMapping(value = "/caregiver/update", method = RequestMethod.PUT)
    public ResponseEntity updateCaregiver(@RequestBody UserDTO editedCaregiverDTO) {
        UserDTO userDTO = verifyCaregiverExistence(editedCaregiverDTO.getId());

        if(userDTO == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        userService.updateUser(editedCaregiverDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //----------------------------------DELETE CAREGIVERS-----------------------------
    @RequestMapping(value = "/caregiver/delete/bulk", method = RequestMethod.DELETE)
    public ResponseEntity deleteCaregivers(@RequestBody List<String> caregiversToBeDeleted) {
//        caregiversToBeDeleted.forEach(userService::deleteUser);
        caregiversToBeDeleted.forEach(caregiverId ->{
            Caregiver caregiver = caregiverService.getCaregiver(caregiverId);

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


            userService.deleteUser(caregiverId);
        });
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
