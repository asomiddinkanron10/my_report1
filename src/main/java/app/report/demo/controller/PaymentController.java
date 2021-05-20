package app.report.demo.controller;

import app.report.demo.entity.User;
import app.report.demo.payload.ApiResponse;
import app.report.demo.payload.PaymentDto;
import app.report.demo.security.CurrentUser;
import app.report.demo.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.UUID;

@Controller
    @RequestMapping("/api/payment")
public class PaymentController {
    @Autowired
    PaymentService paymentService;

    @PostMapping
    public HttpEntity<?> saveOrEditPayment(@RequestBody PaymentDto paymentDto, @CurrentUser User user) {
        ApiResponse apiResponse = paymentService.saveOrEditPayment(paymentDto, user);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/getOnePayment")
    public HttpEntity<?> getOnePayment(@RequestParam(value = "page", defaultValue = "0") int page,
                                       @RequestParam(value = "size", defaultValue = "1") int size) {
        ApiResponse apiResponse = paymentService.getOnePayment(page, size);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/getAllPayment")
    public HttpEntity<?> getAllPayment(@RequestParam(value = "page", defaultValue = "0") int page,
                                       @RequestParam(value = "size", defaultValue = "10") int size, @CurrentUser User user) {
        ApiResponse apiResponse = paymentService.getAllPayment(page, size, user);
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/remove/{id}")
    public HttpEntity<?> deletePayment(@PathVariable UUID id) {
        ApiResponse apiResponse = paymentService.deletePayment(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

}
