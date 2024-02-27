package family.project.web.dto.myInfo;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyInfoPasswordEditDto {
    private Long id;
    @Length(min = 8, max = 15)
    private String currentPassword;
    @Length(min = 8, max = 15)
    private String newPassword;
    @Length(min = 8, max = 15)
    private String reNewPassword;

    public MyInfoPasswordEditDto(Long id) {
        this.id = id;
    }
}
