package family.project.web.controller;

import family.project.domain.Member;
import family.project.domain.board.Board;
import family.project.domain.enums.BoardTag;
import family.project.domain.file.FileStore;
import family.project.domain.file.UploadFile;
import family.project.domain.security.PrincipalDetails;
import family.project.domain.service.BoardService;
import family.project.domain.service.MemberService;
import family.project.web.dto.community.MyPostsDto;
import family.project.web.dto.community.NewPostFormDto;
import family.project.web.dto.myInfo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/myinfo")
public class MyInfoController {

    private final MemberService memberService;
    private final BoardService boardService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @ModelAttribute("boardTags")
    private BoardTag[] boarTags() {
        return BoardTag.values();
    }

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
        MyInfoDto myInfoDto = new MyInfoDto(findMember);
        model.addAttribute("member", myInfoDto);

        return "info/myInfo";
    }

    @GetMapping("/nicknameEdit/{id}")
    public String myInfoNicknameEdit(@PathVariable("id") Long id, Model model) {
        Member findMember = memberService.search(id);
        String nickname = findMember.getNickname();
        MemberNickNameEditDto memberNickNameEditDto = new MemberNickNameEditDto(findMember.getId());
        model.addAttribute("member", memberNickNameEditDto);
        return "info/myInfoNickname";
    }

    @GetMapping("/passwordEdit/{id}")
    public String myInfoPasswordEdit(@PathVariable("id") Long id, Model model) {
        Member findMember = memberService.search(id);
        model.addAttribute("member", new MyInfoPasswordEditDto(id));
        return "info/myInfoPassword";

    }


    @PostMapping("/passwordEdit/{id}")
    public String myInfoPasswordEdit_post(@PathVariable("id") Long id, @Validated @ModelAttribute("member") MyInfoPasswordEditDto myInfoPasswordEditDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "info/myInfoPassword";
        }
        Member findMember = memberService.search(id);
        String password = findMember.getPassword();
        String currentPassword = myInfoPasswordEditDto.getCurrentPassword();
        String newPassword = myInfoPasswordEditDto.getNewPassword();
        String reNewPassword = myInfoPasswordEditDto.getReNewPassword();

        //bindingResult.addError(new ObjectError()) -> global Error
        if (!bCryptPasswordEncoder.matches(currentPassword, password)) {
            bindingResult.addError(new ObjectError("member", "현재 비밀번호를 잘못 입력하였습니다. 다시 입력해주세요."));
            return "info/myInfoPassword";
        }
        if (!Objects.equals(newPassword, reNewPassword)) {
            log.info("newPassword = {}", newPassword);
            log.info("reNewPassword = {}", reNewPassword);
            bindingResult.addError(new ObjectError("member", "신규 비밀번호가 맞지 않습니다. 다시 입력해주세요. "));
            return "info/myInfoPassword";
        }
        memberService.editPassword(id, newPassword);
        return "redirect:/";
    }

    @PostMapping("/nicknameEdit/{id}")
    public String myInfoNicknameEdit_post(@PathVariable("id") Long id, @Validated @ModelAttribute("member") MemberNickNameEditDto memberNickNameEditDto, BindingResult bindingResult,
                                          RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "info/myInfoNickname";
        }
        Member editMember = memberService.editNickName(id, memberNickNameEditDto.getNickname());
        redirectAttributes.addAttribute("id", editMember.getId());
        return "redirect:/myInfo/nicknameEdit/{id}";
    }

    @GetMapping("/myposts")
    public String myPosts(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        Member member = principalDetails.getMember();
        Long id = member.getId();
        //영속성 컨텍스트의 도움을 받을 수 없는 준영속 상태일 때, 프록시를 초기화(get으로 필드 접근)하면 문제 발생 따라서 영속 상태에서 가져와야 하므로 memberService.search를 사용하여 영속성 컨텍스트에서 다룬다.
        Member findMember = memberService.search(id); //현재 영속성 컨텍스트에서 가져옴 member.getBoard는 준영속 상태이다.
        List<Board> findBoards = findMember.getBoards();
        List<MyPostsDto> boards = findBoards.stream().map(board -> new MyPostsDto(board)).collect(Collectors.toList());
        MyInfoToHomeDto myInfoToHomeDto = new MyInfoToHomeDto(member.getId(), member.getNickname());
        model.addAttribute("member", myInfoToHomeDto);
        model.addAttribute("boards", boards);
        model.addAttribute("deletePost", new MyPostDeleteDto());
        model.addAttribute("principal", principalDetails);
        return "community/post/mypost";
    }

    @PostMapping("/myposts/delete")
    public String myPostsDelete(MyPostDeleteDto myPostDeleteDto) {
        List<Long> boardDeleteId = myPostDeleteDto.getBoardDeleteId();
        boardDeleteId.forEach(id -> boardService.removePost(id));
        return "redirect:/myinfo/myposts";
    }

    @GetMapping("/post/edit/{id}")
    public String myPostEdit(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable("id") Long boardId, Model model) {
        //교체 필요
        Long memberId = principalDetails.getMember().getId();
        //만약 board가 null이면 오류페이지 보여주기 -> 웹 브라우저에 HTML 화면을 제공할 떄는 오류가 발생하면 BasicErrorController 를 사용하는게 편하다.
        //이때는 단순히 5xx, 4xx 관련된 오류 화면을 보여 주면 된다. BasicErrorController는 이런 메커니즘을 모두 구현해두었다.
        Board board = boardService.editPost(boardId, memberId);
        NewPostFormDto post = new NewPostFormDto(board);
        model.addAttribute("post", post);
        return "/info/MyInfoEditPost";
    }

    @PostMapping("/post/edit")
    public String MyPostEdit_post(@Validated @ModelAttribute("post") NewPostFormDto npfDto, BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) {
            if (npfDto.getBoardTag() == null) {
                bindingResult.addError(new ObjectError("post", "태그를 선택해주세요"));
                return "/info/MyInfoEditPost";
            }
            if (npfDto.getTitle().isEmpty()) {
                bindingResult.addError(new ObjectError("post", "제목을 작성해주세요."));
                return "/info/MyInfoEditPost";
            }
            if (npfDto.getContent().isEmpty()) {
                bindingResult.addError(new ObjectError("post", "내용을 작성해주세요"));
                return "/info/MyInfoEditPost";
            }
        }
        log.info("npfDto = {}", npfDto);
        Long id = npfDto.getId();
        boardService.editPost(id, npfDto.getTitle(), npfDto.getContent(), npfDto.getBoardTag(), npfDto.getAttachFile(), npfDto.getImageFiles());
        return "redirect:/myinfo/myposts";

    }
}
