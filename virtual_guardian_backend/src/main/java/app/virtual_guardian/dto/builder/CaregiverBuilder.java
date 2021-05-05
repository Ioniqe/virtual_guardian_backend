package app.virtual_guardian.dto.builder;

import app.virtual_guardian.entity.Caregiver;
import app.virtual_guardian.entity.Patient;
import app.virtual_guardian.entity.User;

import java.util.HashSet;
import java.util.Set;

public class CaregiverBuilder {
    public static Caregiver toEntity(User user){
        Set<Patient> listOfPatients = new HashSet<>();
        return new Caregiver(listOfPatients, user);
    }
}
