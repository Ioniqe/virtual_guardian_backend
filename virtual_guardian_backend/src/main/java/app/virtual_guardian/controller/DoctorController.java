package app.virtual_guardian.controller;

import app.virtual_guardian.dto.SpecialUserDTO;
import app.virtual_guardian.dto.UserDTO;
import app.virtual_guardian.dto.builder.UserBuilder;
import app.virtual_guardian.entity.SpecialCredentials;
import app.virtual_guardian.entity.User;
import app.virtual_guardian.service.SpecialCredentialsService;
import app.virtual_guardian.service.DoctorService;
import app.virtual_guardian.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class DoctorController {
    private final UserService userService;
    private final DoctorService doctorService;
    private final SpecialCredentialsService specialCredentialsService;

    @Autowired
    public DoctorController(UserService userService, DoctorService doctorService, SpecialCredentialsService specialCredentialsService) {
        this.userService = userService;
        this.doctorService = doctorService;
        this.specialCredentialsService = specialCredentialsService;
    }

    private UserDTO verifyDoctorExistence(String userId) {
        UserDTO dto = userService.getUserDTOById(userId);
        if(dto == null)
            return null;
        if (!dto.getType().equals("doctor")) {
            System.out.println("Doctor was not found.");
            return null;
        }
        return dto;
    }

    //---------------------------------CREATE---------------------------------
    @RequestMapping(value = "/doctor/new", method = RequestMethod.POST)
    public ResponseEntity createDoctor(@RequestBody SpecialUserDTO specialUserDTO) {
        SpecialCredentials specialCredentials = specialCredentialsService.getBySpecialCredential(specialUserDTO.getSecretCredential());

        if(specialCredentials == null)
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);

        UserDTO userDTO = UserBuilder.toUserDTOFromSpecialUserDTO(specialUserDTO);
        User user = userService.getInsertedUser(userDTO);
        doctorService.insertDoctor(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //----------------------------------READ----------------------------------
    @RequestMapping(value = "/doctor/{userId}", method = RequestMethod.GET)
    public ResponseEntity<UserDTO> readDoctor(@PathVariable("userId") String userId) {
        UserDTO dto = verifyDoctorExistence(userId);
        if(dto == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
