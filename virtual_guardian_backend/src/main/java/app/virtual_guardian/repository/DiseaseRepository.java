package app.virtual_guardian.repository;

import app.virtual_guardian.entity.Disease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiseaseRepository extends JpaRepository<Disease, Integer> {
}
