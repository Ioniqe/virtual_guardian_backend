package app.virtual_guardian.service;

import app.virtual_guardian.dto.LoginDTO;
import app.virtual_guardian.dto.UserDTO;
import app.virtual_guardian.dto.builder.UserBuilder;
import app.virtual_guardian.entity.Admin;
import app.virtual_guardian.entity.Gender;
import app.virtual_guardian.entity.User;
import app.virtual_guardian.entity.UserType;
import app.virtual_guardian.repository.GenderRepository;
import app.virtual_guardian.repository.UserRepository;
import app.virtual_guardian.repository.UserTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final GenderRepository genderRepository;
    private final UserTypeRepository userTypeRepository;

    @Autowired
    public UserService(UserRepository userRepository, GenderRepository genderRepository, UserTypeRepository userTypeRepository) {
        this.userRepository = userRepository;
        this.genderRepository = genderRepository;
        this.userTypeRepository = userTypeRepository;
    }

    public User getInsertedUser(UserDTO userDTO) {
        User user = UserBuilder.toEntity(userDTO);
        Gender gender = genderRepository.findGenderByGender(userDTO.getGender());
        UserType type = userTypeRepository.findUserTypeByType(userDTO.getType());

        user.setId(UUID.randomUUID().toString());
        user.setGender(gender);
        user.setType(type);
        user.setCaregiver(null);
        user.setDoctor(null);
        user.setPatient(null);

        user = userRepository.save(user);

        System.out.println("User with id {} was inserted in db "+ user.getId());

        return user;
    }

    private Optional<User> verifyUserExistence(String id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
            System.out.println("User with id {} was not found in the db" + id);
            return Optional.empty();
        }
        return userOptional;
    }

    public UserDTO getUserDTOById(String id) {
        Optional<User> userOptional = verifyUserExistence(id);
        return userOptional.map(UserBuilder::toUserDTOWithDetails).orElse(null);
    }

    public void deleteUser(String id){
        Optional<User> oldUser = verifyUserExistence(id);

        if(oldUser.isPresent()){
            userRepository.deleteById(id);
        }
    }

    public void updateUser(UserDTO userDTO){
        User newUser = userRepository.findUserById(userDTO.getId());

        newUser.setUsername(userDTO.getUsername());
        newUser.setPassword(userDTO.getPassword());
        newUser.setFirstname(userDTO.getFirstname());
        newUser.setLastname(userDTO.getLastname());
        newUser.setBirthday(userDTO.getBirthday());

        userRepository.save(newUser);
    }

    public User getUserById(String userId){
        Optional<User> user = userRepository.findById(userId);
        return user.orElse(null);
    }

    public UserDTO getUserFromLogin(LoginDTO loginDTO){
        User user = userRepository.findUserByUsernameAndPassword(loginDTO.getUsername(), loginDTO.getPassword());
        if(user != null)
            return UserBuilder.toUserDTOWithDetails(user);
        return null;
    }

    public List<User> getAllUsersOfAType(String user_type){
        UserType userType = userTypeRepository.findUserTypeByType(user_type);
        return userRepository.findAllByTypeIs(userType);
    }
}
