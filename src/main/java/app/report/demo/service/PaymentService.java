package app.report.demo.service;

import app.report.demo.entity.Payment;
import app.report.demo.entity.User;
import app.report.demo.exception.utils.CommonUtils;
import app.report.demo.payload.ApiResponse;
import app.report.demo.payload.CategoryDto;
import app.report.demo.payload.PaymentDto;
import app.report.demo.payload.UserDto;
import app.report.demo.repository.CategoryRepository;
import app.report.demo.repository.PaymentRepository;
import app.report.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PaymentService {
    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    UserRepository userRepository;

    public ApiResponse saveOrEditPayment(PaymentDto paymentDto, User user) {
        try {
            Payment payment = new Payment();
            if (paymentDto.getId() != null)
                payment = paymentRepository.findById(paymentDto.getId()).orElseThrow(() -> new ResourceNotFoundException("getPayment"));
            if (paymentDto.getCategoryId() != null)
                payment.setCategory(categoryRepository.findById(paymentDto.getCategoryId() == null ? payment.getCategory().getId() : paymentDto.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("getCategory")));
            payment.setUser(user);
            payment.setDescription(paymentDto.getDescription() == null ? payment.getDescription() : paymentDto.getDescription());
            payment.setPrice(paymentDto.getPrice() == null ? payment.getPrice() : paymentDto.getPrice());
            paymentRepository.save(payment);
            return new ApiResponse("Payment added", true);
        } catch (Exception e) {
            return new ApiResponse("Error", false);
        }
    }

//    public ApiResponse getPayment(UUID id) {
//        try {
//            Payment payment = paymentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("getPayment"));
//            return new ApiResponse("Success", true);
//        } catch (Exception e) {
//            return new ApiResponse("Error", false);
//        }
//    }

    public ApiResponse getOnePayment(int page, int size) {
        try {
            Page<Payment> onePayment = paymentRepository.findAll(PageRequest.of(page, size));
            return new ApiResponse("Success", true, onePayment.getContent().stream().map(this::getOnePaymentDto).collect(Collectors.toList()));
        } catch (Exception e) {
            return new ApiResponse("Error", false);
        }
    }

    public PaymentDto getOnePaymentDto(Payment payment) {
        return new PaymentDto(
                payment.getId(),
                payment.getCategory().getId(),
                new CategoryDto(payment.getCategory().getId(), payment.getCategory().getUser().getId(), payment.getCategory().getName(), payment.getCategory().getDescription()),
                payment.getUser().getId(),
                new UserDto(payment.getUser().getId(), payment.getUser().getFirstName(), payment.getUser().getLastName(), null, null, true, null, null, null, null),
                payment.getPrice(),
                payment.getDescription()
        );
    }

    public ApiResponse getAllPayment(int page, int size, User user) {
        try {
            List<Payment> allPayment = paymentRepository.findAllByUser(user, CommonUtils.getPageableById(page, size));
            return new ApiResponse("Success full", true, allPayment);
        } catch (Exception e) {
            return new ApiResponse("Error", false);
        }
    }

//    public PaymentDto getAllPaymentDto(Payment payment) {
//        return new PaymentDto(
//                payment.getId(),
//                payment.getCategory().getId(),
//                payment.getUser().getId(),
//                payment.getPrice(),
//                payment.getDescription()
//        );
//    }

    public ApiResponse deletePayment(UUID id) {
        try {
            paymentRepository.deleteById(id);
            return new ApiResponse("Success full delete", true);
        } catch (Exception e) {
            return new ApiResponse("Error", false);
        }
    }

}
