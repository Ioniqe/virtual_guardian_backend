package app.virtual_guardian.repository;

import app.virtual_guardian.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, String> {
    Patient findPatientByUserId(String id);
}
