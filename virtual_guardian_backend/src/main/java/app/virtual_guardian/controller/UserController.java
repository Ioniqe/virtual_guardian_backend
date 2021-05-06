package app.virtual_guardian.controller;

import app.virtual_guardian.dto.LoginDTO;
import app.virtual_guardian.dto.PatientDTO;
import app.virtual_guardian.dto.UserDTO;
import app.virtual_guardian.dto.builder.PatientBuilder;
import app.virtual_guardian.entity.Patient;
import app.virtual_guardian.service.PatientService;
import app.virtual_guardian.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;
    private final PatientService patientService;

    @Autowired
    public UserController(UserService userService, PatientService patientService) {
        this.userService = userService;
        this.patientService = patientService;
    }

    //----------------------------------LOGIN USER-----------------------------
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity verifyDoctorLogin(@RequestBody LoginDTO loginDTO) {

        UserDTO userDTO = userService.getUserFromLogin(loginDTO);
        if(userDTO == null){
            System.out.println("User was not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if(userDTO.getType().equals("patient")){
            Patient patient = patientService.getPatient(userDTO.getId());
            PatientDTO patientDTO = PatientBuilder.toPatientDTOFromUserDTO(userDTO, patient);
            return new ResponseEntity<>(patientDTO, HttpStatus.OK);
        }

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }


}
