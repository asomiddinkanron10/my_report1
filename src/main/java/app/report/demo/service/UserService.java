package app.report.demo.service;

import app.report.demo.entity.Role;
import app.report.demo.entity.User;
import app.report.demo.entity.enums.RoleName;
import app.report.demo.payload.ApiResponse;
import app.report.demo.payload.UserDto;
import app.report.demo.repository.RoleRepository;
import app.report.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByUserName(s).orElseThrow(() -> new UsernameNotFoundException(s));
    }

    public ApiResponse register(UserDto userDto) {
        try {
            if (!userRepository.existsByUserName(userDto.getUserName())) {
                User user = new User();
                Role byRoleName = roleRepository.findByRoleName(RoleName.ROLE_USER);
                Set<Role> role = new HashSet<>(Arrays.asList(byRoleName));
                user.setRole(role);
                user.setFirstName(userDto.getFirstName());
                user.setLastName(userDto.getLastName());
                user.setUserName(userDto.getUserName());
                if (userDto.getPassword() != null && userDto.getPassword().length() > 0) {
                    user.setPassword(passwordEncoder.encode(userDto.getPassword()));
                } else {
                    return new ApiResponse("Wrong password", false);
                }
                userRepository.save(user);
                return new ApiResponse("User added", true);
            } else {
                return new ApiResponse("User name already exist", false);
            }
        } catch (Exception e) {
            return new ApiResponse("Error", false);
        }
    }

    public ApiResponse editUserAndAdmin(UserDto userDto) {
        try {
            Optional<User> optionalUser = userRepository.findById(userDto.getId());
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                user.setFirstName(userDto.getFirstName() == null ? user.getFirstName() : userDto.getFirstName());
                user.setLastName(userDto.getLastName() == null ? user.getLastName() : userDto.getLastName());
                if (userRepository.existsByUserName(userDto.getUserName()))
                    return new ApiResponse("this number is in the database", true);
                user.setUserName(userDto.getUserName() == null ? user.getUsername() : userDto.getUserName());
                userRepository.save(user);
                return new ApiResponse("User edit", true);
            } else {
                return new ApiResponse("Not found", false);
            }
        } catch (Exception e) {
            return new ApiResponse("Error", false);
        }
    }

    public ApiResponse editPassword(UserDto userDto, User user) {
        if (userDto.getNewPassword().equals(userDto.getPrePassword())) {
            if (user != null) {
                if (passwordEncoder.matches(userDto.getOldPassword(), user.getPassword())) {
                    user.setPassword(passwordEncoder.encode(userDto.getNewPassword()));
                    userRepository.save(user);
                    return new ApiResponse("Password edit", true);
                }
                return new ApiResponse("Error", false);
            }
            return new ApiResponse("Error", false);
        }
        return new ApiResponse("Error", false);
    }

    public String getRoleUser(User user) {
        Set<Role> role = user.getRole();
        for (Role role1 : role) {
            return role1.getRoleName().toString();
        }
        return null;
    }

    public ApiResponse getUserPage(int page, int size) {
        try {
            Page<User> allUser = userRepository.findAll(PageRequest.of(page, size));
            return new ApiResponse("Success", true, allUser.getContent().stream().map(this::getUserDto).collect(Collectors.toList()));
        } catch (Exception e) {
            return new ApiResponse("Error", false);
        }
    }

    public UserDto getUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                getRoleUser(user),
                user.isEnabled()
        );
    }

    public ApiResponse deleteUser(UUID id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setEnabled(false);
            user.setDeleted(true);
            userRepository.save(user);
            return new ApiResponse("User delete", true);
        } else {
            return new ApiResponse("Error", false);
        }
    }

    public ApiResponse enabledUser(UUID id) {
        try {
            Optional<User> userOptional = userRepository.findById(id);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                boolean enabled = !user.isEnabled();
                user.setEnabled(enabled);

                userRepository.save(user);
                return new ApiResponse((enabled ? "active" : "deactive"), true);
            }
            return new ApiResponse("User not found", false);

        } catch (Exception e) {
            return new ApiResponse("Server error", false);
        }
    }

}

//    public ApiResponse getUser(UUID id) {
//        try {
//            User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("getUser"));
//            return new ApiResponse("Success", true);
//        } catch (Exception e) {
//            return new ApiResponse("Error", false);
//        }
//    }

//    public String getRole(User user) {
//        Set<Role> roles = user.getRole();
//        for (Role role : roles) {
//            switch (role.getRoleName().toString()) {
//                case "ROLE_ADMIN":
//                    return "ROLE_ADMIN";
//                case "ROLE_USER":
//                    return "ROLE_USER";
//            }
//        }
//        return null;
//    }