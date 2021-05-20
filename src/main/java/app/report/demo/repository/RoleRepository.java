package app.report.demo.repository;

import app.report.demo.entity.Role;
import app.report.demo.entity.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    Role findByRoleName(RoleName roleName);
}
