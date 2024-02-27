package family.project.web.controller;

import family.project.domain.Address;
import family.project.domain.Member;
import family.project.domain.enums.MemberType;
import family.project.domain.enums.RoleType;
import family.project.domain.service.MemberService;
import family.project.web.dto.join.MemberJoinDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class JoinController {

    private final MemberService memberService;


    @ModelAttribute("memberTypes")
    public MemberType[] memberTypes() {
        return MemberType.values(); // enum에 대한 모든 정보들 반환
    }


    @GetMapping("/userJoin")
    public String join(Model model) {
        model.addAttribute("member", new MemberJoinDto());
        return "joinForm";
    }

    @PostMapping("/userJoin")
    public String join_post(@Validated @ModelAttribute("member") MemberJoinDto memberJoinDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            return "joinForm";
        }
        String[] split = memberJoinDto.getAddress().split(" ");
        Member newMember = Member.craeteMember(memberJoinDto.getUsername(), memberJoinDto.getNickname(), memberJoinDto.getEmail(),
                memberJoinDto.getPassword(), null, RoleType.USER, memberJoinDto.getMemberType(), new Address(split[0], split[1], split[2]));
        memberService.register(newMember);
        return "redirect:/";
    }
}
