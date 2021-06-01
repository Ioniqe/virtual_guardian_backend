package app.virtual_guardian.dto.builder;

import app.virtual_guardian.dto.EmergencyDTO;
import app.virtual_guardian.entity.Emergency;
import app.virtual_guardian.entity.Patient;

public class EmergencyBuilder {
    public static Emergency toEntity(EmergencyDTO emergencyDTO, Patient patient){
        return new Emergency(emergencyDTO.getDate(), patient);
    }

    public static EmergencyDTO toDTO(Emergency emergency){
        return new EmergencyDTO(emergency.getId(), emergency.getDate(), emergency.getPatient().getUser().getFirstname() + " " + emergency.getPatient().getUser().getLastname());
    }
}
