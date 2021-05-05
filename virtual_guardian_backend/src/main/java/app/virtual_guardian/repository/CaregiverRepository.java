package app.virtual_guardian.repository;

import app.virtual_guardian.entity.Caregiver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaregiverRepository extends JpaRepository<Caregiver, String> {
    Caregiver findCaregiverByUser_Id(String id);
}
