package app.virtual_guardian.repository;

import app.virtual_guardian.entity.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenderRepository extends JpaRepository<Gender, Integer> {
    Gender findGenderById(Integer id);
    Gender findGenderByGender(String gender);
}
