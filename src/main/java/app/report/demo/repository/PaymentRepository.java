package app.report.demo.repository;

import app.report.demo.entity.Category;
import app.report.demo.entity.Payment;
import app.report.demo.entity.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {

    List<Payment> findAllByCategoryIdAndCreatedAtBetweenAndUser(UUID category_id, Timestamp createdAt, Timestamp createdAt2, User user);

    List<Payment> findAllByUser(User user,Pageable pageable);

//    @Query(value = "SELECT u FROM User u WHERE u.status = :status and u.name = :name",nativeQuery = true)
//    List<Payment> findAllBymyCousdnsundu(@Param("user") User user);

}
