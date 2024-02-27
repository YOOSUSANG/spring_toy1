package family.project.web.dto.join;


import family.project.domain.enums.MemberType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
public class MemberJoinDto {

    @NotBlank
    @Length(min = 2, max = 5)
    private String username;

    @NotBlank
    @Length(max = 8)
    private String nickname;

    @NotBlank
    @Length(max = 20)
    private String email;

    @NotBlank
    @Length(min = 8, max = 15)
    private String password;

    @NotBlank
    private String address;// 공백 기준으로 나눠서 넣기

    @NotNull
    private MemberType memberType;

    public MemberJoinDto() {

    }
}
