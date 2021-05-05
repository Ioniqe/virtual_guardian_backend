package app.virtual_guardian.service;

import app.virtual_guardian.entity.SpecialCredentials;
import app.virtual_guardian.repository.SpecialCredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecialCredentialsService {
    private final SpecialCredentialsRepository specialCredentialsRepository;

    @Autowired
    public SpecialCredentialsService(SpecialCredentialsRepository specialCredentialsRepository) {
        this.specialCredentialsRepository = specialCredentialsRepository;
    }

    public void insertDoctor(SpecialCredentials specialCredentials){
        specialCredentialsRepository.save(specialCredentials);
    }

    public List<SpecialCredentials> getAllDoctorIds(){
        return specialCredentialsRepository.findAll();
    }

    public SpecialCredentials getBySpecialCredential(String specialCredential){
        return specialCredentialsRepository.findByUserCredentials(specialCredential);
    }
}
