package family.project.web.controller;

import family.project.domain.Member;
import family.project.domain.security.PrincipalDetails;
import family.project.web.dto.myInfo.MyInfoToHomeDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class HomeController {



    @GetMapping
    public String index(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {

        //로그인 성공
        extractPrincipalAndModel(principalDetails, model);
        return "index";
    }

    private void extractPrincipalAndModel(PrincipalDetails principalDetails, Model model) {
        if (principalDetails != null) {
            Member member = principalDetails.getMember();
            Long id = member.getId();
            String nickname = member.getNickname();
            MyInfoToHomeDto MyInfoToHomeDto = new MyInfoToHomeDto(id, nickname);
            model.addAttribute("nickname", nickname);
            model.addAttribute("member", MyInfoToHomeDto);
        }
        if (principalDetails==null) {
            model.addAttribute("member", new MyInfoToHomeDto());
        }
        model.addAttribute("principal", principalDetails);
    }
}
