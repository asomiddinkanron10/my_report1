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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public HttpEntity<?> login(@RequestBody UserDto userDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    userDto.getUserName(),
                    userDto.getPassword()
            ));
            String token = jwtTokenProvider.generateToken(authentication);
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return ResponseEntity.status(409).body("password or user name entered incorrectly!");
        }
    }

    @GetMapping("/me")
    public HttpEntity<?> getUserMe(@CurrentUser User user) {
        if (user != null) {
            UserDto userDto = userService.getUserDto(user);
            return ResponseEntity.status(200).body(userDto);
        }
        return ResponseEntity.status(409).body("User not found");
    }

    @PostMapping("/register")
    public HttpEntity<?> register(@RequestBody UserDto userDto) {
        ApiResponse apiResponse = userService.register(userDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    @PutMapping("/editUserRole")
    public HttpEntity<?> editManagerByAdmin(@RequestBody UserDto userDto) {
        ApiResponse apiResponse = userService.editUserAndAdmin(userDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/editPassword")
    public HttpEntity<?> editPassword(@RequestBody UserDto userDto, @CurrentUser User user) {
        ApiResponse apiResponse = userService.editPassword(userDto, user);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/remove/{id}")
    public HttpEntity<?> deleteUser(@PathVariable UUID id) {
        ApiResponse apiResponse = userService.deleteUser(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/enabled/{id}")
    public HttpEntity<?> enabledUser(@PathVariable UUID id) {
        ApiResponse apiResponse = userService.enabledUser(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
}

//Userni get qilish
//    @GetMapping("/{id}")
//    public HttpEntity<?> getUser(@PathVariable UUID id) {
//        ApiResponse apiResponse = userService.getUser(id);
////        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
//        return ResponseEntity.ok(new ApiResponse("ok", true, userService.getUser(id)));
//    }
