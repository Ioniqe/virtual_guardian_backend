package app.virtual_guardian.service;

import app.virtual_guardian.entity.MedicationList;
import app.virtual_guardian.repository.MedicationListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicationListService {
    private final MedicationListRepository medicationListRepository;

    @Autowired
    public MedicationListService(MedicationListRepository medicationListRepository) {
        this.medicationListRepository = medicationListRepository;
    }

    public void insertMedicationList(MedicationList medicationList){
        medicationListRepository.save(medicationList);
    }

}
