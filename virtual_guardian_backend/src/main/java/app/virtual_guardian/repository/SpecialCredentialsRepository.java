package app.virtual_guardian.repository;

import app.virtual_guardian.entity.SpecialCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialCredentialsRepository extends JpaRepository<SpecialCredentials, Integer> {
    SpecialCredentials findByUserCredentials(String secretDoctorId);
}
