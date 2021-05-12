package app.virtual_guardian.controller;

import app.virtual_guardian.dto.SpecialUserDTO;
import app.virtual_guardian.dto.UserDTO;
import app.virtual_guardian.dto.builder.UserBuilder;
import app.virtual_guardian.entity.User;
import app.virtual_guardian.service.AdminService;
import app.virtual_guardian.service.SpecialCredentialsService;
import app.virtual_guardian.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class AdminController {
    private final AdminService adminService;
    private final UserService userService;
    private final SpecialCredentialsService specialCredentialsService;

    @Autowired
    public AdminController(AdminService adminService, UserService userService, SpecialCredentialsService specialCredentialsService) {
        this.adminService = adminService;
        this.userService = userService;
        this.specialCredentialsService = specialCredentialsService;
    }

    private UserDTO verifyAdminExistence(String userId) {
        UserDTO dto = userService.getUserDTOById(userId);
        if(dto == null)
            return null;
        if (!dto.getType().equals("admin")) {
            System.out.println("Admin was not found.");
            return null;
        }
        return dto;
    }

    //----------------------------------CREATE-----------------------------
    @RequestMapping(value = "/admin/new", method = RequestMethod.POST)
    public ResponseEntity saveAdmin(@RequestBody SpecialUserDTO specialUserDTO) {
        if(specialCredentialsService.getBySpecialCredential(specialUserDTO.getSecretCredential()) == null)
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);

        UserDTO userDTO = UserBuilder.toUserDTOFromSpecialUserDTO(specialUserDTO);
        User user = userService.getInsertedUser(userDTO);
        adminService.insertAdmin(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //----------------------------------READ---------------------------------- TODO
    @RequestMapping(value = "/admin/{userId}", method = RequestMethod.GET)
    public ResponseEntity<UserDTO> readAdmin(@PathVariable("userId") String userId) {
        UserDTO dto = verifyAdminExistence(userId);
        if(dto == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    //----------------------------------READ ALL----------------------------------
    @RequestMapping(value = "/admin/all", method = RequestMethod.GET)
    public ResponseEntity<List<UserDTO>> readAllAdmins() {
        List<User> admins = userService.getAllUsersOfAType("admin");
        List<UserDTO> adminsDTO = new ArrayList<>();
        admins.forEach(admin -> adminsDTO.add(UserBuilder.toUserDTOWithDetails(admin)));
        return new ResponseEntity<>(adminsDTO, HttpStatus.OK);
    }

//    //----------------------------------UPDATE-----------------------------
//    @RequestMapping(value = "/admin/update", method = RequestMethod.PUT)
//    public ResponseEntity updateAdmin(@RequestBody UserDTO newUserDTO) {
//        UserDTO dto = verifyAdminExistence(newUserDTO.getId());
//        if(dto == null)
//            return new ResponseEntity(HttpStatus.NOT_FOUND);
//        userService.updateUser(newUserDTO);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

//    //----------------------------------DELETE-----------------------------
//    @RequestMapping(value = "/admin/delete/{userId}", method = RequestMethod.DELETE)
//    public ResponseEntity deleteAdmin(@PathVariable("userId") String userId) {
//        User user = userService.getUserById(userId);
//        if(user == null)
//            return new ResponseEntity(HttpStatus.NOT_FOUND);
//        userService.deleteUser(userId);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

    //----------------------------------DELETE ADMINS-----------------------------
    @RequestMapping(value = "/admin/delete/bulk", method = RequestMethod.DELETE)
    public ResponseEntity deleteAdmins(@RequestBody List<String> adminsToBeDeleted) {
        adminsToBeDeleted.forEach(userService::deleteUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
