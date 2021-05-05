package app.virtual_guardian.repository;

import app.virtual_guardian.entity.Symptom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SymptomsRepository extends JpaRepository<Symptom, Integer> {
}
