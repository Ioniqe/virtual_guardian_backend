package app.virtual_guardian.repository;

import app.virtual_guardian.entity.MedicationList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicationListRepository extends JpaRepository<MedicationList, Integer> {
}
