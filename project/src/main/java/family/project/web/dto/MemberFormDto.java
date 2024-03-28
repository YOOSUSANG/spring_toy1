package family.project.web.dto;

import family.project.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberFormDto {

    private Long id;
    private String nickname;

    public MemberFormDto(Member member) {
        this.id = member.getId();
        this.nickname = member.getNickname();
    }
}
