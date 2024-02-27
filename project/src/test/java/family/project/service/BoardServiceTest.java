package family.project.service;

import family.project.domain.Address;
import family.project.domain.Member;
import family.project.domain.board.Board;
import family.project.domain.enums.BoardTag;
import family.project.domain.enums.MemberType;
import family.project.domain.enums.RoleType;
import family.project.domain.service.BoardService;
import family.project.domain.service.MemberService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @Autowired
    private MemberService memberService;

    @BeforeEach
    public void 생성() {
        Member member1 = Member.craeteMember("dbtntkd456@naver.com", "yoosusang", null, RoleType.ADMIN, MemberType.BROTHER, new Address("서울", "압구정", "111-111"));
        Member member2 = Member.craeteMember("poii9927@ajou.ac.kr", "yoojusang", null, RoleType.USER, MemberType.BROTHER, new Address("서울", "강남", "111-112"));
        memberService.register(member1);
        memberService.register(member2);
        Board board1Member1 = Board.createBoard(member1, "김피탕 굿", "김피탕 한번 먹고보세요.!!!", BoardTag.FOOD_RECOMMENDATIONS_REVIEWS, true, (String) null);
        for (int i = 0; i < 100; i++) {
            board1Member1.addLikesCount();
        }
        for (int i = 0; i < 20; i++) {
            board1Member1.addCommentsCount();
        }
        Board board2Member1 = Board.createBoard(member1, "감자탕 노맛", "ㅋㅋㅋㅋㅋ 이거 누가 만든거냐", BoardTag.FOOD_RECOMMENDATIONS_REVIEWS, true, (String) null);
        for (int i = 0; i < 50; i++) {
            board2Member1.addLikesCount();
        }
        for (int i = 0; i < 50; i++) {
            board2Member1.addCommentsCount();
        }
        Board board1Member2 = Board.createBoard(member2, "성수동 맛집..", "추천좀요.", BoardTag.DOMESTIC, true, (String) null);
        for (int i = 0; i < 110; i++) {
            board1Member2.addLikesCount();
        }
        for (int i = 0; i < 70; i++) {
            board1Member2.addCommentsCount();
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
        List<Board> boards = boardService.viewAll();
        List<Board> user1Boards = boardService.viewMyBoard(userEmail1);
        List<Board> user2Boards = boardService.viewMyBoard(userEmail2);
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
    @DisplayName("게시판 좋아요 내림차순으로")
    @Test
    public void 게시판_좋아요() throws Exception {
        //given
        String userEmail1 = "dbtntkd456@naver.com";
        String userEmail2 = "poii9927@ajou.ac.kr";
        //when
        List<Board> boards = boardService.orderByLikeCountDesc();
        //then
        for (Board board : boards) {
            System.out.println("boardTitle = " + board.getTitle());
        }
    }

    @DisplayName("게시판 댓글 내림차순으로")
    @Test
    public void 게시판_댓글() throws Exception {
        //given
        String userEmail1 = "dbtntkd456@naver.com";
        String userEmail2 = "poii9927@ajou.ac.kr";
        //when
        List<Board> boards = boardService.orderByCommentCountDesc();
        //then
        for (Board board : boards) {
            System.out.println("board.getTitle() = " + board.getTitle());
        }

    }


}