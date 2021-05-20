package app.report.demo.controller;

import app.report.demo.entity.User;
import app.report.demo.payload.ApiResponse;
import app.report.demo.security.CurrentUser;
import app.report.demo.service.ProceduresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.UUID;

@RestController
@RequestMapping("/api/procedures")
public class ProceduresController {
    @Autowired
    ProceduresService proceduresService;

    @GetMapping("/getByDateAndType")
    public HttpEntity<?> getByDateAndType(@RequestParam(value = "start") Timestamp start,
                                          @RequestParam(value = "end") Timestamp end,
                                          @RequestParam(value = "type") UUID type,
                                          @CurrentUser User user
    ) {
        ApiResponse apiResponse = proceduresService.getByDateAndType(start, end, type, user);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/getCategoryByPayments")
    public HttpEntity<?> getCategoryByPayments(@RequestParam(value = "type") UUID type,
                                               @CurrentUser User user) {
        ApiResponse apiResponse = proceduresService.getCategoryByPayments(type, user);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
}
