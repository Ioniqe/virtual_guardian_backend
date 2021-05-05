package app.virtual_guardian.service;

import app.virtual_guardian.entity.Disease;
import app.virtual_guardian.repository.DiseaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DiseaseService {
    private final DiseaseRepository diseaseRepository;

    @Autowired
    public DiseaseService(DiseaseRepository diseaseRepository) {
        this.diseaseRepository = diseaseRepository;
    }

    public Disease getDisease(Integer diseaseId){
        Optional<Disease> disease = diseaseRepository.findById(diseaseId);
        return disease.orElse(null);
    }

    public void insertDisease(Disease disease){
        diseaseRepository.save(disease);
    }

    public List<Disease> getDiseases(){
        return diseaseRepository.findAll();
    }
}
