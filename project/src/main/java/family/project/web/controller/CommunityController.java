package family.project.web.controller;

import family.project.PageCustom;
import family.project.domain.Member;
import family.project.domain.board.Board;
import family.project.domain.enums.BoardCheckTag;
import family.project.domain.enums.BoardTag;
import family.project.domain.file.FileStore;
import family.project.domain.file.UploadFile;
import family.project.domain.security.PrincipalDetails;
import family.project.domain.service.BoardService;
import family.project.domain.service.MemberService;
import family.project.domain.statics.PageRange;
import family.project.web.dto.MemberFormDto;
import family.project.web.dto.community.*;
import family.project.web.dto.page.PageFormDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/community")
public class CommunityController {

    private final BoardService boardService;
    private final MemberService memberService;
    private final FileStore fileStore;

    // enum으로 된 것들을 view에 넘기기. -> 해당 ENUM의모든 정보를배열로 반환한다.
    @ModelAttribute("boardTags")
    private BoardTag[] boarTags() {
        return BoardTag.values();
    }

    @ModelAttribute("checkTags")
    private BoardCheckTag[] checkTags() {
        return BoardCheckTag.values();
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

    @GetMapping("/post/new")
    public String newPost(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        Member member = principalDetails.getMember();
        Long id = member.getId();
        model.addAttribute("principal", principalDetails);
        model.addAttribute("post", new NewPostFormDto(id));
        return "community/post/newPost";
    }


    @PostMapping("/post/new")
    public String newPost_post(@Validated @ModelAttribute("post") NewPostFormDto npfDto, BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) {
            if (npfDto.getBoardTag() == null) {
                bindingResult.addError(new ObjectError("post", "태그를 선택해주세요"));
                return "community/post/newPost";

            }
            if (npfDto.getTitle().isEmpty()) {
                bindingResult.addError(new ObjectError("post", "제목을 작성해주세요."));
                return "community/post/newPost";
            }
            if (npfDto.getContent().isEmpty()) {
                bindingResult.addError(new ObjectError("post", "내용을 작성해주세요"));
                return "community/post/newPost";
            }
        }
        Long memberId = npfDto.getId();
        Member findMember = memberService.search(memberId);
        UploadFile attachFile = fileStore.storeFile(npfDto.getAttachFile());
        List<UploadFile> storeImageFiles = fileStore.storeFiles(npfDto.getImageFiles());
        Board newBoard = Board.createBoard_real(findMember, npfDto.getTitle(), npfDto.getContent(), npfDto.getBoardTag(), true, attachFile, storeImageFiles);
        log.info("checkPostNew = {}", npfDto);
        boardService.register(newBoard);
        return "redirect:/";
    }

    @GetMapping("/post/{id}")
    public String Post(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable("id") Long boardId, Model model) {
        Board board = boardService.selectPost(boardId);

        List<Board> postAll = boardService.MyPostAll(board.getMember().getEmail());
        List<Board> currentPost = new ArrayList<>();
        currentPost.add(board);// 현재 post
        extractPostAllDto(model, currentPost);
        model.addAttribute("myAll", postAll); //현재 내가 작성한 post
        extractCommunityPrincipalAndModel(principalDetails, model);
        return "community/post/post";
    }

    //{categoryTag}가 BoardFilterDto에 있는 필드와 매칭이 된다.
    @GetMapping({"/posts/{categoryTag}", "/posts"})
    public String index(@AuthenticationPrincipal PrincipalDetails principalDetails, BoardFilterDto boardFilterDto, Model model, PageCustom pageable) {

        log.info("boarFilterDto = {}", boardFilterDto);
        //초기
        if (boardFilterDto.getOrder() == null) {
            boardFilterDto.setOrder(BoardCheckTag.RECENT);
        }
        //초기
        if (boardFilterDto.getCategoryTag() == null) {
            model.addAttribute("selectedCategory", false);
        } else {
            model.addAttribute("selectedCategory", true);
        }
        model.addAttribute("filterDto", boardFilterDto);

        log.info("Community pageable null ={}", pageable);
        int size = 2;
        Page<Board> pageBoards = boardService.postSearch_dynamic(boardFilterDto, new PageCustom(size, pageable.getPage()).getPageRequest());
        int start, end;

        int currentPage = pageBoards.getNumber() + 1;
        int nextPage = currentPage + 1;
        int previousPage = currentPage - 1;
        int totalPage = pageBoards.getTotalPages();
        log.info("pageTest = {}", pageable);
        log.info("PageGetTotalElement() = {}", pageBoards.getTotalElements());
        log.info("PageGetTotalPages = {}", totalPage);
        log.info("PageGetSize = {}", pageBoards.getSize());
        log.info("PageGetNumber = {}", currentPage);
        log.info("PageGetNumberOfElement = {}", pageBoards.getNumberOfElements());

        //10 단위로 start, end 로직 %연산자 응용
        start = PageRange.startCul(currentPage);
        end = PageRange.endCul(currentPage,totalPage);
        log.info("startPage= {}", start);
        log.info("endPage = {}", end);
        List<Board> boards = pageBoards.getContent();
        postCheckAndViewFormBordTags(boards, model, boardFilterDto.getOrder(), principalDetails);
        PageFormDto pageFormDto = new PageFormDto(start, end, totalPage, currentPage, nextPage, previousPage);
        model.addAttribute("page", pageFormDto);
        return "community/communityIndex";
    }


    private void postCheckAndViewFormBordTags(List<Board> boardService, Model model, BoardCheckTag boardCheckTag, PrincipalDetails principalDetails) {
        List<Board> boards = boardService;
        BoardCheckTagDto boardCheckTagDto = new BoardCheckTagDto(boardCheckTag);
        extractPostAllDto(model, boards);
        extractCommunityPrincipalAndModel(principalDetails, model);
        model.addAttribute("principal", principalDetails);
        //fragment를 사용했으니까 직접적으로 호출해서 전달해야한다.
        model.addAttribute("order", boardCheckTagDto.getOrder());
    }

    @ResponseBody
    @GetMapping("/post/images/{filename}")
    public Resource downloadImage(@PathVariable("filename") String filename) throws MalformedURLException {
        log.info("fileName={}", filename);
        //저장된 파일을 불러온다.
        return new UrlResource("file:" + fileStore.getFullPath(filename));
    }

    @GetMapping("/post/attach/{boardId}")
    public ResponseEntity<Resource> downloadAttach(@AuthenticationPrincipal PrincipalDetails principalDetails,@PathVariable("boardId") Long boardId) throws MalformedURLException {
        Board board = boardService.selectPost(boardId);
        String uploadFileName = board.getAttachFile().getUploadFileName();
        String storeFileName = board.getAttachFile().getStoreFileName();
        UrlResource resource = new UrlResource("file:" + fileStore.getFullPath(storeFileName)); //파일 내용 담기
        String encodeUploadFileName = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8); //업로드 파일 명을 UTF_8로 encoding 하기.(깨짐 방지)
        String contentDisposition = "attachment; filename=\"" + encodeUploadFileName + "\""; //업로드 한 파일 명 설정

        //업로드한 파일명 + 정보을 담아 response하기
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition).body(resource);

    }

    private void extractCommunityPrincipalAndModel(PrincipalDetails principalDetails, Model model) {

        if (principalDetails != null) {
            Member findMember = principalDetails.getMember();
            MemberFormDto memberFormDto = new MemberFormDto(findMember.getId(), findMember.getNickname());
            model.addAttribute("member", memberFormDto);
        }
        model.addAttribute("principal", principalDetails);
    }

    private void extractPostAllDto(Model model, List<Board> boards) {
        List<PostAllFormDto> boardDtos = boards.stream().map(board -> new PostAllFormDto(board)).collect(Collectors.toList());
        model.addAttribute("product", boardDtos);
    }

}
