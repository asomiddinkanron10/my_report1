package app.report.demo.controller;

import app.report.demo.entity.User;
import app.report.demo.payload.ApiResponse;
import app.report.demo.payload.UserDto;
import app.report.demo.security.CurrentUser;
import app.report.demo.security.JwtTokenProvider;
import app.report.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    UserService userService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtTokenProvider jwtTokenProvider;


    @GetMapping("/allUser")
    public HttpEntity<?> getUserMe(@CurrentUser User user) {
        if (user != null) {
            UserDto userDto = userService.getUserDto(user);
            return ResponseEntity.status(200).body(userDto);
        }
        return ResponseEntity.status(409).body("User not found");
    }

    @GetMapping("/getAllUser")
    public HttpEntity<?> getUserPage(@RequestParam(value = "page", defaultValue = "0") int page,
                                     @RequestParam(value = "size", defaultValue = "10") int size) {
        ApiResponse apiResponse = userService.getUserPage(page, size);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


}
