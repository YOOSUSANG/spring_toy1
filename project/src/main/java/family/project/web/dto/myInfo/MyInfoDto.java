package family.project.web.dto.myInfo;

import family.project.domain.enums.MemberType;
import family.project.domain.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MyInfoDto {
    private Long id;
    private String username;
    private String nickname;
    private MemberType memberType;
    private String email;
    private String password;





}
