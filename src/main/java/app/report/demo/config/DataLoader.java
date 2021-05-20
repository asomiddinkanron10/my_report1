package app.report.demo.config;

import app.report.demo.entity.Role;
import app.report.demo.entity.User;
import app.report.demo.entity.enums.RoleName;
import app.report.demo.repository.RoleRepository;
import app.report.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RoleRepository roleRepository;

    @Value("${spring.datasource.initialization-mode}")
    private String addAdmin;

    @Override
    public void run(String... args) throws Exception {

//        Role larni turlariga qarab ochish
        if (addAdmin.equals("always")) {
            Role roleAdmin = new Role(RoleName.ROLE_ADMIN);
            roleRepository.save(roleAdmin);

            Role roleUser = new Role(RoleName.ROLE_USER);
            roleRepository.save(roleUser);

            Role byRoleName = roleRepository.findByRoleName(RoleName.ROLE_ADMIN);
            Set<Role> role = new HashSet<>(Arrays.asList(byRoleName));

            User user = new User(
                    "Asomiddin",
                    "Raxmiddinov",
                    "+998999596877",
                    passwordEncoder.encode("root123"),
                    false,
                    role,
                    true,
                    true,
                    true,
                    true

            );
            userRepository.save(user);
        }
    }
}


