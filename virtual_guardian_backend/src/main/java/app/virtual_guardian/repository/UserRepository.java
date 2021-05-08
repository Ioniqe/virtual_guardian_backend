package app.virtual_guardian.repository;

import app.virtual_guardian.entity.User;
import app.virtual_guardian.entity.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findUserById(String id);
    User findUserByUsernameAndPassword(String username, String password);
    List<User> findAllByTypeIs(UserType number);
}
