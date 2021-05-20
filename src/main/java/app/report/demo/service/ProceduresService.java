package app.report.demo.service;

import app.report.demo.entity.User;
import app.report.demo.payload.ApiResponse;
import app.report.demo.repository.CategoryRepository;
import app.report.demo.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.UUID;

@Service
public class ProceduresService {

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    CategoryRepository categoryRepository;

    public ApiResponse getByDateAndType(Timestamp start, Timestamp end, UUID type, User user) {
        return new ApiResponse("Success", true, paymentRepository.findAllByCategoryIdAndCreatedAtBetweenAndUser(type, end, start, user));
    }

    public ApiResponse getCategoryByPayments(UUID type, User user) {
        return new ApiResponse("Success", true, categoryRepository.findAllByIdAndUser(type, user));
    }

}
