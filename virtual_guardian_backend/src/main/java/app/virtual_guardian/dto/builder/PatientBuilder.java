package app.virtual_guardian.dto.builder;

import app.virtual_guardian.dto.PatientDTO;
import app.virtual_guardian.dto.UserDTO;
import app.virtual_guardian.entity.Doctor;
import app.virtual_guardian.entity.Patient;
import app.virtual_guardian.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PatientBuilder {
    public static PatientDTO toPatientDTO(Patient patient){
        if(patient.getCaregiver() != null)
            return new PatientDTO(patient.getUserId(),
                patient.getDoctor().getUserId(),
                patient.getCaregiver().getUserId(),
                patient.getAddress(), patient.getUser().getUsername(), patient.getUser().getPassword(),
                patient.getUser().getFirstname(), patient.getUser().getLastname(), patient.getUser().getBirthday(),
                patient.getUser().getType().getType(), patient.getUser().getGender().getGender());
        return new PatientDTO(patient.getUserId(), patient.getDoctor().getUserId(), null,
                patient.getAddress(), patient.getUser().getUsername(), patient.getUser().getPassword(),
                patient.getUser().getFirstname(), patient.getUser().getLastname(), patient.getUser().getBirthday(),
                patient.getUser().getType().getType(), patient.getUser().getGender().getGender());
    }

    public static Patient toEntity(User user, Doctor doctor, String address){
        return new Patient(user, address, doctor);
    }

    public static PatientDTO toPatientDTOFromUserDTO(UserDTO userDTO, Patient patient){
        if(patient.getCaregiver() != null)
            return new PatientDTO(userDTO.getId(), patient.getDoctor().getUserId(), patient.getCaregiver().getUserId(),
                patient.getAddress(), userDTO.getUsername(), userDTO.getPassword(), userDTO.getFirstname(),
                userDTO.getLastname(), userDTO.getBirthday(), userDTO.getType(), userDTO.getGender());
        return new PatientDTO(userDTO.getId(), patient.getDoctor().getUserId(), null,
                patient.getAddress(), userDTO.getUsername(), userDTO.getPassword(), userDTO.getFirstname(),
                userDTO.getLastname(), userDTO.getBirthday(), userDTO.getType(), userDTO.getGender());
    }

    public static List<PatientDTO> toPatientDTOListFromPatientSet(Set<Patient> patients){
        List<PatientDTO> patientDTOs = new ArrayList<>();
        patients.forEach(patient -> patientDTOs.add(toPatientDTO(patient)));
        return patientDTOs;
    }
}
