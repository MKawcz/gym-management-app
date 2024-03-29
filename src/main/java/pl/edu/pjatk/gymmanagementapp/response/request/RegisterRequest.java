package pl.edu.pjatk.gymmanagementapp.response.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.pjatk.gymmanagementapp.anntotation.EnumNamePattern;
import pl.edu.pjatk.gymmanagementapp.model.enums.Role;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "Field username cannot be blank and cannot be null")
    @Size(max=50, message = "Field username should be max 50 characters long")
    private String username;

    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Provided email has an invalid format")
    private String email;

    @Pattern(regexp = "^(?=.*\\d)(?=.*[A-Z]).{6,16}$", message = "Password should be between 6 and 16 " +
            "characters long, contain at least 1 number and at least 1 uppercase letter")
    private String password;

//    @EnumNamePattern(regexp = "ROLE_ADMIN|ROLE_USER")
    private Role role;

}
