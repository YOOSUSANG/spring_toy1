package family.project.web.dto.myInfo;

import family.project.domain.Member;
import family.project.domain.enums.MemberType;
import family.project.domain.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyInfoDto {
    private Long id;
    private String username;
    private String nickname;
    private MemberType memberType;
    private String email;
    private String password;
    private Integer boardCount;


    public MyInfoDto(Member member) {
        this.id = member.getId();
        this.username = member.getUsername();
        this.nickname = member.getNickname();
        this.memberType = member.getMemberType();
        this.email = member.getEmail();
        this.password = member.getPassword();
        this.boardCount = member.getBoards().size();
    }
}
