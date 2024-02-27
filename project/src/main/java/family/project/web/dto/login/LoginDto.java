package family.project.web.dto.login;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginDto {
    @NotEmpty
    private String email;
    @NotEmpty
    private String password;

    public LoginDto() {

    }
}
