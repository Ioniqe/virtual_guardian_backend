package app.virtual_guardian.dto.builder;

import app.virtual_guardian.dto.SpecialUserDTO;
import app.virtual_guardian.dto.PatientDTO;
import app.virtual_guardian.dto.UserDTO;
import app.virtual_guardian.entity.User;

public class UserBuilder {
    public UserBuilder() {
    }

    public static User toEntity(UserDTO userDTO){
        return new User(userDTO.getUsername(), userDTO.getPassword(), userDTO.getFirstname(),
                userDTO.getLastname(), userDTO.getBirthday());
    }

    public static UserDTO toUserDTOWithDetails(User user){
        return new UserDTO(user.getId(), user.getUsername(), user.getPassword(), user.getFirstname(),
                user.getLastname(), user.getBirthday(), user.getType().getType(), user.getGender().getGender());
    }

    public static UserDTO toUserDTOFromPatientDTO(PatientDTO patientDTO){
        return new UserDTO(patientDTO.getId(), patientDTO.getUsername(), patientDTO.getPassword(),
                patientDTO.getFirstname(), patientDTO.getLastname(), patientDTO.getBirthday(), patientDTO.getType(), patientDTO.getGender());

    }

    public static UserDTO toUserDTOFromSpecialUserDTO(SpecialUserDTO specialUserDTO){
        return new UserDTO(specialUserDTO.getId(), specialUserDTO.getUsername(), specialUserDTO.getPassword(),
                specialUserDTO.getFirstname(), specialUserDTO.getLastname(), specialUserDTO.getBirthday(), specialUserDTO.getType(), specialUserDTO.getGender());

    }
}
