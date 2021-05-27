package app.virtual_guardian.repository;

import app.virtual_guardian.entity.LabeledDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LabeledDayRepository extends JpaRepository<LabeledDay, Integer> {
    List<LabeledDay> findAllByLabel(String label);
}
