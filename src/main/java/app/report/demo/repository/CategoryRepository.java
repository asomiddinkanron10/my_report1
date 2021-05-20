package app.report.demo.repository;

import app.report.demo.entity.Category;
import app.report.demo.entity.Payment;
import app.report.demo.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    List<Category> findAllByUser(User user, Pageable pageable);

    List<Category> findAllByIdAndUser(UUID id, User user);
}
