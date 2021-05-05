package app.virtual_guardian.service;

import app.virtual_guardian.dto.builder.CaregiverBuilder;
import app.virtual_guardian.entity.Caregiver;
import app.virtual_guardian.entity.User;
import app.virtual_guardian.repository.CaregiverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CaregiverService {

    private final CaregiverRepository caregiverRepository;

    @Autowired
    public CaregiverService(CaregiverRepository caregiverRepository) {
        this.caregiverRepository = caregiverRepository;
    }

    public void insertCaregiver(User user){
        Caregiver caregiver = CaregiverBuilder.toEntity(user);
        caregiverRepository.save(caregiver);
    }

    public Caregiver getCaregiver(String id){
        return caregiverRepository.findCaregiverByUser_Id(id);
    }

    public void saveCaregiver(Caregiver caregiver){
        caregiverRepository.save(caregiver);
    }
}
