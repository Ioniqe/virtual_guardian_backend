package app.virtual_guardian.repository;

import app.virtual_guardian.entity.Emergency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmergencyRepository extends JpaRepository<Emergency, Integer> {
    List<Emergency> findAllByPatient_UserId(String patientId);
}
