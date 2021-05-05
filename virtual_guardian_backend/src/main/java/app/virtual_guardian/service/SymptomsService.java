package app.virtual_guardian.service;

import app.virtual_guardian.entity.Symptom;
import app.virtual_guardian.repository.SymptomsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SymptomsService {
    private final SymptomsRepository symptomsRepository;

    @Autowired
    public SymptomsService(SymptomsRepository symptomsRepository) {
        this.symptomsRepository = symptomsRepository;
    }

    public List<Symptom> getAllSymptoms(){
        return symptomsRepository.findAll();
    }
}
