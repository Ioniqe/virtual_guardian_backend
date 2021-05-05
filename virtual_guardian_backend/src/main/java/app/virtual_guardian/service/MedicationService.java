package app.virtual_guardian.service;

import app.virtual_guardian.entity.Medication;
import app.virtual_guardian.repository.MedicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MedicationService {
    private final MedicationRepository medicationRepository;

    @Autowired
    public MedicationService(MedicationRepository medicationRepository) {
        this.medicationRepository = medicationRepository;
    }

    public void insertMedication(Medication medication){
        medicationRepository.save(medication);
    }

    public Medication getMedication(String medicationId){
        Optional<Medication> medication = medicationRepository.findById(medicationId);
        return medication.orElse(null);
    }
}
