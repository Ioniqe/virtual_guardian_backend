package app.virtual_guardian.repository;

import app.virtual_guardian.entity.Day;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DayRepository extends JpaRepository<Day, Integer> {
    List<Day> findAllByResult(String result);
}
