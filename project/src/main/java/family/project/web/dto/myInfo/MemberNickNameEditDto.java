package family.project.web.dto.myInfo;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberNickNameEditDto {
    private Long id;
    @NotBlank
    @Length(max = 8)
    private String nickname;


    public MemberNickNameEditDto(Long id) {
        this.id = id;
    }
}
