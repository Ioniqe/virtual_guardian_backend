package app.virtual_guardian.service;

import app.virtual_guardian.entity.Emergency;
import app.virtual_guardian.repository.EmergencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmergencyService {
    private final EmergencyRepository emergencyRepository;

    @Autowired
    public EmergencyService(EmergencyRepository emergencyRepository) {
        this.emergencyRepository = emergencyRepository;
    }

    public List<Emergency> getAllOfPatientId(String patientId){
        return emergencyRepository.findAllByPatient_UserId(patientId);
    }

    public void saveEmergency(Emergency emergency){
        emergencyRepository.save(emergency);
    }
}
