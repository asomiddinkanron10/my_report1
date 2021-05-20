package app.report.demo.repository;

import app.report.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUserName(String userName);

    boolean existsByUserName(String userName);

    @Query(nativeQuery = true, value = "SELECT * FROM users WHERE user_name= :userName AND password= :password")
    User findByUserNameAndPassword(String userName, String password);
}
