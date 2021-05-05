package app.virtual_guardian.repository;

import app.virtual_guardian.entity.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTypeRepository extends JpaRepository<UserType, Integer> {
    UserType findUserTypeById(Integer id);
    UserType findUserTypeByType(String type);
}
