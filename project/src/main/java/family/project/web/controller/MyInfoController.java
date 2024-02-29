package family.project.web.controller;

import family.project.domain.Member;
import family.project.domain.security.PrincipalDetails;
import family.project.domain.service.MemberService;
import family.project.web.dto.myInfo.MemberNickNameEditDto;
import family.project.web.dto.myInfo.MyInfoDto;
import family.project.web.dto.myInfo.MyInfoPasswordEditDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Objects;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/myinfo")
public class MyInfoController {

    private final MemberService memberService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    //해당 controller에 nickname 모두 attribute
    @ModelAttribute("nickname")
    public String nickname(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        Member member = principalDetails.getMember();
        String nickname = member.getNickname();
        return nickname;
    }


    @GetMapping("/{id}")
    public String myInfo(@PathVariable("id") Long id, Model model) {
        Member findMember = memberService.search(id);

        String nickname = findMember.getNickname();
        MyInfoDto myInfoDto = new MyInfoDto(findMember.getId(),findMember.getUsername(), findMember.getNickname(), findMember.getMemberType(), findMember.getEmail(), findMember.getPassword());
        model.addAttribute("member", myInfoDto);

        return "myInfo";
    }

    @GetMapping("/nicknameEdit/{id}")
    public String myInfoNicknameEdit(@PathVariable("id") Long id, Model model) {
        Member findMember = memberService.search(id);
        String nickname = findMember.getNickname();
        MemberNickNameEditDto memberNickNameEditDto = new MemberNickNameEditDto(findMember.getId());
        model.addAttribute("member", memberNickNameEditDto);
        return "myInfoNickname";
    }

    @GetMapping("/passwordEdit/{id}")
    public String myInfoPasswordEdit(@PathVariable("id")Long id, Model model) {
        Member findMember = memberService.search(id);
        String nickname = findMember.getNickname();
        model.addAttribute("member", new MyInfoPasswordEditDto(id));
        return "myInfoPassword";

    }


    @PostMapping("/passwordEdit/{id}")
    public String myInfoPasswordEdit_post(@PathVariable("id") Long id, @Validated @ModelAttribute("member") MyInfoPasswordEditDto myInfoPasswordEditDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "myInfoPassword";
        }
        Member findMember = memberService.search(id);
        String password = findMember.getPassword();
        String currentPassword = myInfoPasswordEditDto.getCurrentPassword();
        String newPassword = myInfoPasswordEditDto.getNewPassword();
        String reNewPassword = myInfoPasswordEditDto.getReNewPassword();

        //bindingResult.addError(new ObjectError()) -> global Error
        if (!bCryptPasswordEncoder.matches(currentPassword, password)) {
            bindingResult.addError(new ObjectError("member","현재 비밀번호를 잘못 입력하였습니다. 다시 입력해주세요."));
            return "myInfoPassword";
        }
        if (!Objects.equals(newPassword, reNewPassword)) {
            log.info("newPassword = {}", newPassword);
            log.info("reNewPassword = {}", reNewPassword);
            bindingResult.addError(new ObjectError("member","신규 비밀번호가 맞지 않습니다. 다시 입력해주세요. "));
            return "myInfoPassword";
        }
        memberService.editPassword(id, newPassword);
        return "redirect:/";
    }

    @PostMapping("/nicknameEdit/{id}")
    public String myInfoNicknameEdit_post(@PathVariable("id") Long id, @Validated @ModelAttribute("member") MemberNickNameEditDto memberNickNameEditDto, BindingResult bindingResult,
                                          RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "myInfoNickname";
        }
        Member editMember = memberService.editNickName(id, memberNickNameEditDto.getNickname());
        redirectAttributes.addAttribute("id", editMember.getId());
        return "redirect:/myInfo/nicknameEdit/{id}";
    }

}
