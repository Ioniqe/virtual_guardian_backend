package app.virtual_guardian.dto.builder;

import app.virtual_guardian.entity.Doctor;
import app.virtual_guardian.entity.Patient;
import app.virtual_guardian.entity.User;

import java.util.HashSet;
import java.util.Set;

public class DoctorBuilder {
    public static Doctor toEntity(User user){
        Set<Patient> listOfPatients = new HashSet<>();
        return new Doctor(listOfPatients, user);
    }
}
