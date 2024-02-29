package family.project.web.controller;

import family.project.domain.Member;
import family.project.domain.enums.BoardTag;
import family.project.domain.security.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/community")
public class CommunityController {


    // enum으로 된 것들을 view에 넘기기. -> 해당 ENUM의모든 정보를배열로 반환한다.
    @ModelAttribute("boardTags")
    private BoardTag[] boarTags() {
        return BoardTag.values();
    }

    @ModelAttribute("nickname")
    public String nickname(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        if (principalDetails == null) {
            return null;
        }
        Member member = principalDetails.getMember();
        String nickname = member.getNickname();
        return nickname;
    }

    @GetMapping
    public String index(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        if (principalDetails == null) {
            return "communityIndexNotLogin";
        }
        Member findMember = principalDetails.getMember();
        model.addAttribute("member", findMember);
        return "communityIndex";

    }

}
