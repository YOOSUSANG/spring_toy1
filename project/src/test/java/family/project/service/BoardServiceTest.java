package family.project.service;

import family.project.domain.Address;
import family.project.domain.Member;
import family.project.domain.board.Board;
import family.project.domain.enums.BoardCheckTag;
import family.project.domain.enums.BoardTag;
import family.project.domain.enums.MemberType;
import family.project.domain.enums.RoleType;
import family.project.domain.service.BoardService;
import family.project.domain.service.MemberService;
import family.project.web.dto.community.BoardFilterDto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest()
@Transactional
class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @Autowired
    private MemberService memberService;

    @BeforeEach
    public void 생성() {
        Member member1 = Member.craeteMember("yoosusang", "이러쿵저러쿵", "dbtntkd456@naver.com", "123123123",
                null, RoleType.USER, MemberType.BROTHER, new Address("경기도", "의정부시", "329"));
        Member member2 = Member.craeteMember("yoojusang", "저러쿵", "poii9927@ajou.ac.kr", "123123123",
                null, RoleType.USER, MemberType.OlD_BROTHER, new Address("경기도", "수원시", "154"));
        memberService.register(member1);
        memberService.register(member2);
        Board board1Member1 = Board.createBoard(member1, "김피탕 굿", "김피탕 한번 먹고보세요.!!!", BoardTag.FOOD_RECOMMENDATIONS_REVIEWS, true, (String) null);
        for (int i = 0; i < 100; i++) {
            board1Member1.addLikesCount();
        }
        for (int i = 0; i < 20; i++) {
            board1Member1.addCommentsCount();
        }
        for (int i = 0; i < 40; i++) {
            board1Member1.addViews();
        }
        Board board2Member1 = Board.createBoard(member1, "감자탕 노맛", "ㅋㅋㅋㅋㅋ 이거 누가 만든거냐", BoardTag.FOOD_RECOMMENDATIONS_REVIEWS, true, (String) null);
        for (int i = 0; i < 50; i++) {
            board2Member1.addLikesCount();
        }
        for (int i = 0; i < 50; i++) {
            board2Member1.addCommentsCount();
        }
        for (int i = 0; i < 70; i++) {
            board2Member1.addViews();
        }
        Board board1Member2 = Board.createBoard(member2, "성수동 맛집..", "추천좀요.", BoardTag.DOMESTIC, true, (String) null);
        for (int i = 0; i < 110; i++) {
            board1Member2.addLikesCount();
        }
        for (int i = 0; i < 70; i++) {
            board1Member2.addCommentsCount();
        }
        for (int i = 0; i < 10; i++) {
            board1Member2.addViews();
        }
        boardService.register(board1Member1);
        boardService.register(board2Member1);
        boardService.register(board1Member2);

    }

    @DisplayName("게시판등록 테스트입니다.")
    @Test
    public void 게시판등록() throws Exception {
        //given
        String userEmail1 = "dbtntkd456@naver.com";
        String userEmail2 = "poii9927@ajou.ac.kr";
        //when
        List<Board> boards = boardService.postAll();
        List<Board> user1Boards = boardService.MyPostAll(userEmail1);
        List<Board> user2Boards = boardService.MyPostAll(userEmail2);
        //then
        assertEquals(3, boards.size(), "총 게시판 등록 개수는 3개입니다.");
        assertEquals(2, user1Boards.size(), "dbtntkd456@naver.com : 총 게시판 등록 개수는 2개입니다.");
        assertEquals(1, user2Boards.size(), "poii9927@ajou.ac.kr : 총 게시판 등록 개수는 1개입니다.");
    }

    @DisplayName("게시판 제목 찾기")
    @Test
    public void 게시판_제목찾기() throws Exception {
        //given
        String userEmail1 = "dbtntkd456@naver.com";
        String userEmail2 = "poii9927@ajou.ac.kr";
        //when
        List<Board> findBoard1 = boardService.findTitle("김피탕");
        List<Board> findBoard2 = boardService.findTitle("탕");
        List<Board> findBoard3 = boardService.findTitle("성수동 맛집..");
        //then
        assertEquals(1, findBoard1.size(), "김피탕 제목은 한개");
        assertEquals(2, findBoard2.size(), "탕이 들어간 제목은 두개");
        assertEquals(1, findBoard3.size(), "성수동 맛집.. 은 한개");
    }

    @DisplayName("게시판 모두삭제 테스트")
    @Test
    public void 게시판_모두삭제() throws Exception {
        //given
//        Member member1 = Member.craeteMember("dbtntkd456@naver.com", "yoosusang", null, RoleType.ADMIN, MemberType.BROTHER, new Address("서울", "압구정", "111-111"));
//        Member member2 = Member.craeteMember("poii9927@ajou.ac.kr", "yoojusang", null, RoleType.USER, MemberType.BROTHER, new Address("서울", "강남", "111-112"));
//        memberService.register(member1);
//        memberService.register(member2);
//        Board board1Member1 = Board.createBoard(member1, "김피탕 굿", "김피탕 한번 먹고보세요.!!!", BoardTag.FOOD_RECOMMENDATIONS_REVIEWS, true, (String) null);
//        for (int i = 0; i < 100; i++) {
//            board1Member1.addLikesCount();
//        }
//        for (int i = 0; i < 20; i++) {
//            board1Member1.addCommentsCount();
//        }
//        Board board2Member1 = Board.createBoard(member1, "감자탕 노맛", "ㅋㅋㅋㅋㅋ 이거 누가 만든거냐", BoardTag.FOOD_RECOMMENDATIONS_REVIEWS, true, (String) null);
//        for (int i = 0; i < 50; i++) {
//            board2Member1.addLikesCount();
//        }
//        for (int i = 0; i < 50; i++) {
//            board2Member1.addCommentsCount();
//        }
//        Board board1Member2 = Board.createBoard(member2, "성수동 맛집..", "추천좀요.", BoardTag.DOMESTIC, true, (String) null);
//        for (int i = 0; i < 110; i++) {
//            board1Member2.addLikesCount();
//        }
//        for (int i = 0; i < 70; i++) {
//            board1Member2.addCommentsCount();
//        }
//        boardService.register(board1Member1);
//        boardService.register(board2Member1);
//        boardService.register(board1Member2);
//        //when
//        board1Member1.removeMember(member1);
//        board2Member1.removeMember(member1);
//        board1Member2.removeMember(member2);
//        boardService.clear();
//        List<Board> boards = boardService.viewAll();
//        //then
//        assertEquals(0, boards.size(), "다 삭제");
    }

    @DisplayName("게시판 선택 삭제")
    @Test
    public void 게시판_지정삭제() throws Exception {
        //given
//        Member member1 = Member.craeteMember("dbtntkd456@naver.com", "yoosusang", null, RoleType.ADMIN, MemberType.BROTHER, new Address("서울", "압구정", "111-111"));
//        Member member2 = Member.craeteMember("poii9927@ajou.ac.kr", "yoojusang", null, RoleType.USER, MemberType.BROTHER, new Address("서울", "강남", "111-112"));
//        memberService.register(member1);
//        memberService.register(member2);
//        Board board1Member1 = Board.createBoard(member1, "김피탕 굿", "김피탕 한번 먹고보세요.!!!", BoardTag.FOOD_RECOMMENDATIONS_REVIEWS, true, (String) null);
//        for (int i = 0; i < 100; i++) {
//            board1Member1.addLikesCount();
//        }
//        for (int i = 0; i < 20; i++) {
//            board1Member1.addCommentsCount();
//        }
//        Board board2Member1 = Board.createBoard(member1, "감자탕 노맛", "ㅋㅋㅋㅋㅋ 이거 누가 만든거냐", BoardTag.FOOD_RECOMMENDATIONS_REVIEWS, true, (String) null);
//        for (int i = 0; i < 50; i++) {
//            board2Member1.addLikesCount();
//        }
//        for (int i = 0; i < 50; i++) {
//            board2Member1.addCommentsCount();
//        }
//        Board board1Member2 = Board.createBoard(member2, "성수동 맛집..", "추천좀요.", BoardTag.DOMESTIC, true, (String) null);
//        for (int i = 0; i < 110; i++) {
//            board1Member2.addLikesCount();
//        }
//        for (int i = 0; i < 70; i++) {
//            board1Member2.addCommentsCount();
//        }
//        boardService.register(board1Member1);
//        boardService.register(board2Member1);
//        boardService.register(board1Member2);
//        //when
//        board1Member1.removeMember(member1);
//        boardService.removeBoard(board1Member1.getId());
//        List<Board> boards = boardService.viewAll();
//        //then
//        assertEquals(2,boards.size(),"김피탕 title 삭제했으므로 2개");
//        for (Board board : boards) {
//            System.out.println("board.getTitle() = " + board.getTitle());
//
//        }
    }





    @DisplayName("전체 게시판 좋아요 동적으로 내림차순")
    @Test
    public void 게시판_동적_좋아요() throws Exception{
        //given
        String userEmail1 = "dbtntkd456@naver.com";
        String userEmail2 = "poii9927@ajou.ac.kr";
        //전체 DTO
        BoardFilterDto bfDto1 = new BoardFilterDto(null, null, BoardCheckTag.LIKE);
        PageRequest result = PageRequest.of(0, 3);

        //when
        Page<Board> findBoards = boardService.postSearch_dynamic(bfDto1, result);
        assertEquals(3, findBoards.getSize(), "촣 3건을 불러옵니다");
        List<Board> content = findBoards.getContent();
        for (Board board : content) {
            System.out.println("board = " + board.getLikesCount());
        }
        //then
    }

    @DisplayName("전체 게시판 댓글 내림차순으로")
    @Test
    public void 게시판_동적_댓글() throws Exception{
        //given
        String userEmail1 = "dbtntkd456@naver.com";
        String userEmail2 = "poii9927@ajou.ac.kr";
        //전체 DTO
        BoardFilterDto bfDto1 = new BoardFilterDto(null, null, BoardCheckTag.COMMENT);
        PageRequest result = PageRequest.of(0, 3);

        //when
        Page<Board> findBoards = boardService.postSearch_dynamic(bfDto1, result);
        assertEquals(3, findBoards.getSize(), "촣 3건을 불러옵니다");
        List<Board> content = findBoards.getContent();
        for (Board board : content) {
            System.out.println("board = " + board.getCommentsCount());
        }
        //then
    }
    @DisplayName("전체 게시판 조회수 내림차순으로")
    @Test
    public void 게시판_동적_조회수() throws Exception{
        //given
        String userEmail1 = "dbtntkd456@naver.com";
        String userEmail2 = "poii9927@ajou.ac.kr";
        //전체 DTO
        BoardFilterDto bfDto1 = new BoardFilterDto(null, null, BoardCheckTag.VIEW);
        PageRequest result = PageRequest.of(0, 3);

        //when
        Page<Board> findBoards = boardService.postSearch_dynamic(bfDto1, result);
        assertEquals(3, findBoards.getSize(), "촣 3건을 불러옵니다");
        List<Board> content = findBoards.getContent();
        for (Board board : content) {
            System.out.println("board = " + board.getViews());
        }
        //then
    }

    @DisplayName("동적 게시판 조회")
    @Test
    public void 게시판_동적_조회() throws Exception{
        //given
        String userEmail1 = "dbtntkd456@naver.com";
        String userEmail2 = "poii9927@ajou.ac.kr";
        //전체 DTO
        BoardFilterDto bfDto1 = new BoardFilterDto(null, BoardTag.DOMESTIC, BoardCheckTag.RECENT);
        BoardFilterDto bfDto2 = new BoardFilterDto(null, BoardTag.FOOD_RECOMMENDATIONS_REVIEWS, BoardCheckTag.RECENT);
        PageRequest result = PageRequest.of(0, 3);

        //when
        Page<Board> findBoards = boardService.postSearch_dynamic(bfDto1, result);
        Page<Board> findBoards2 = boardService.postSearch_dynamic(bfDto2, result);
        List<Board> content = findBoards.getContent();
        List<Board> content2 = findBoards2.getContent();
        assertEquals(1, content.size(), "국내게시판은 1건을 불러옵니다");
        assertEquals(2, findBoards2.getTotalElements(), "레스토랑 리뷰 게시판은 2건을 불러옵니다");
        for (Board board : content) {
            System.out.println("board = " + board.getBoardTag());
        }
        for (Board board : content2) {
            System.out.println("board.getBoardTag() = " + board.getBoardTag());
        }
        //then
    }


    @DisplayName("게시판을 동적으로 제목을 검색합니다.")
    @Test
    public void 게시판_동적_제목조회() throws Exception{
        //given
        BoardFilterDto bfDto1 = new BoardFilterDto("감자탕",null, BoardCheckTag.RECENT);
        BoardFilterDto bfDto2 = new BoardFilterDto("탕",null, BoardCheckTag.RECENT);
        BoardFilterDto boardFilterDto = new BoardFilterDto(null, null, null);
        PageRequest result = PageRequest.of(0, 3);
        //when
        Page<Board> findBoards1 = boardService.postSearch_dynamic(bfDto1, result);
        Page<Board> findBoards2 = boardService.postSearch_dynamic(bfDto2, result);
        Page<Board> findBoard3 = boardService.postSearch_dynamic(boardFilterDto, result);
        List<Board> content1 = findBoards1.getContent();
        List<Board> content2 = findBoards2.getContent();
        List<Board> content3 = findBoard3.getContent();
        //then
        assertEquals(1,content1.size(),"감자탕 제목은 1개입니다.");
        assertEquals(2, content2.size(), "탕이 들어간 제목은 2개입니다.");
        assertEquals(3, content3.size(), "아무 조건이 없는 게시판은 3개입니다.");
        for (Board title : content2) {
            System.out.println("content = " + title.getTitle());
        }
    }







}