package app.virtual_guardian.service;

import app.virtual_guardian.entity.Treatment;
import app.virtual_guardian.repository.TreatmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TreatmentService {
    private final TreatmentRepository treatmentRepository;

    @Autowired
    public TreatmentService(TreatmentRepository treatmentRepository) {
        this.treatmentRepository = treatmentRepository;
    }

    public void insertTreatment(Treatment treatment){
        treatmentRepository.save(treatment);
    }

    public Treatment getTreatment(Integer treatmentId){
        Optional<Treatment> treatment = treatmentRepository.findById(treatmentId);
        return treatment.orElse(null);
    }

    public void deleteTreatment(Treatment treatment){
        treatmentRepository.delete(treatment);
    }
}
