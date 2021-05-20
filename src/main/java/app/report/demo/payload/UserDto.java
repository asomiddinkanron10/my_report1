package app.report.demo.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.lang.annotation.DeclareAnnotation;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private UUID id;
    private String firstName;
    private String lastName;
    private String userName;
    private String role;
    private boolean enabled;
    private String password;
    private String prePassword;
    private String oldPassword;
    private String newPassword;

    public UserDto(UUID id, String firstName, String lastName, String userName,String role,boolean enabled) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.role = role;
        this.enabled = enabled;
    }
}
