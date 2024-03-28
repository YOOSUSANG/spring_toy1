package family.project;

import family.project.domain.Address;
import family.project.domain.Member;
import family.project.domain.board.Board;
import family.project.domain.enums.BoardTag;
import family.project.domain.enums.MemberType;
import family.project.domain.enums.RoleType;
import family.project.domain.enums.item.ItemTag;
import family.project.domain.file.FileStore;
import family.project.domain.food.Food;
import family.project.domain.groceries.Vitamin;
import family.project.domain.product.KeyBoard;
import family.project.domain.product.Tablet;
import family.project.domain.service.BoardService;
import family.project.domain.service.ItemService;
import family.project.domain.service.MemberService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Profile("local")
@Component
@Slf4j
//final시 작동
@RequiredArgsConstructor
public class testDataInit {

    private final InitTest initTest;

    @PostConstruct
    public void init() {
        initTest.initBoard();
    }


    @Component
    @RequiredArgsConstructor
    static class InitTest {
        private final MemberService memberService;
        private final BoardService boardService;
        private final ItemService itemService;
        private final FileStore fileStore;

        @Transactional
        public void initBoard() {
            Member member1 = Member.craeteMember("유수상", "이러쿵저러쿵", "dbtntkd456@naver.com", "123123123",
                    null, RoleType.USER, MemberType.BROTHER, new Address("경기도", "의정부시", "329"));
            Member member2 = Member.craeteMember("유주상", "저러쿵", "dbwntkd456@naver.com", "123123123",
                    null, RoleType.USER, MemberType.OlD_BROTHER, new Address("경기도", "수원시", "154"));

            Member member3 = Member.craeteMember("유모상", "쿵쿵덕", "poii9927@ajou.ac.kr", "123123123",
                    null, RoleType.USER, MemberType.OlD_BROTHER, new Address("경기도", "수원시", "154"));

            memberService.register(member1);
            memberService.register(member2);
            memberService.register(member3);

            Board board1 = Board.createBoard(member1, "유수상이 국내 게시판에 작성함", "경주감", BoardTag.DOMESTIC, true, (String) null);
            Board board2 = Board.createBoard(member1, "유수상이 음식 리뷰 게시판에 작성함 ", "오늘은 12시까지 할려나?...", BoardTag.FOOD_RECOMMENDATIONS_REVIEWS, true, (String) null);
            Board board3 = Board.createBoard(member2, "유주상이 국내 게시판에 작성함 ", "나도 경주감ㅋㅋ", BoardTag.DOMESTIC, true, (String) null);
            for (int i = 0; i < 10; i++) {
                board1.addLikesCount();
            }
            for (int i = 0; i < 5; i++) {
                board2.addLikesCount();
            }
            for (int i = 0; i < 3; i++) {
                board3.addLikesCount();
            }

            Board board4 = Board.createBoard(member1, "국내 게시판에 작성함", "경주감", BoardTag.DOMESTIC, true, (String) null);
            Board board5 = Board.createBoard(member1, "음식 리뷰 게시판에 작성함 ", "오늘은 12시까지 할려나?...", BoardTag.FOOD_RECOMMENDATIONS_REVIEWS, true, (String) null);
            Board board6 = Board.createBoard(member2, "게시판에 작성함 ", "나도 경주감ㅋㅋ", BoardTag.DOMESTIC, true, (String) null);
            for (int i = 0; i < 10; i++) {
                board4.addCommentsCount();
            }
            for (int i = 0; i < 5; i++) {
                board6.addCommentsCount();
            }

            Board board7 = Board.createBoard(member1, "작성함", "경주감", BoardTag.DOMESTIC, true, (String) null);
            Board board8 = Board.createBoard(member1, "음식 리뷰 게시판에 작성함 ", "오늘은 12시까지 할려나?...", BoardTag.FOOD_RECOMMENDATIONS_REVIEWS, true, (String) null);
            Board board9 = Board.createBoard(member2, "게시판에 작성함 ", "나도 경주감ㅋㅋ", BoardTag.DOMESTIC, true, (String) null);
            for (int i = 0; i < 10; i++) {
                board7.addViews();
            }
            for (int i = 0; i < 5; i++) {
                board8.addViews();
            }
            for (int i = 0; i < 3; i++) {
                board9.addViews();
            }
            boardService.register(board1);
            boardService.register(board2);
            boardService.register(board3);
            boardService.register(board4);
            boardService.register(board5);
            boardService.register(board6);
            boardService.register(board7);
            boardService.register(board8);
            boardService.register(board9);
            for (int i = 0; i < 50; i++) {
                Board board = Board.createBoard(member2, "게시판에 작성함" + i, "나도 경주감ㅋㅋ", BoardTag.DOMESTIC, true, (String) null);
                boardService.register(board);
            }
            Member findMember1 = memberService.search(1L);
            Member findMember2 = memberService.search(2L);
            Food koreaFood1 = Food.createFood("김치찌개", 7000, 50, 600, ItemTag.KOREA,"국내산 김치를 활용했습니다.","가능동", null);
            koreaFood1.addMember(member1);
            for (int i = 0; i < 10; i++) {
                koreaFood1.addLikeCount();
            }
            Food koreaFood2 = Food.createFood("청국장", 6000, 50, 500,  ItemTag.KOREA,"국내산 김치를 활용했습니다.","가능동", null);
            koreaFood2.addMember(member1);
            for (int i = 0; i < 20; i++) {
                koreaFood2.addLikeCount();
            }
            Food koreaFood3 = Food.createFood("된장찌개", 6000, 50, 500, ItemTag.KOREA,"40/50대 저격하는 구수한 된장찌개","가능동", null);
            koreaFood3.addMember(member2);
            for (int i = 0; i < 15; i++) {
                koreaFood3.addLikeCount();
            }
            Food koreaFood4 = Food.createFood("고등어", 13000, 30, 250, ItemTag.KOREA,"국내산 고등어입니다.","가능동", null);
            Food japanFood1 = Food.createFood("연어", 13000, 30, 350, ItemTag.JAPAN,"노르웨이산 연어입니다.","의정부동", null);
            koreaFood4.addMember(member1);
            japanFood1.addMember(member2);
            Vitamin vitamin1 = Vitamin.createVitamin("루테인", 3000, 50, ItemTag.VITAMIN,"비타민 가격", "서울",null);
            vitamin1.addMember(member1);
            for (int i = 0; i < 50; i++) {
                vitamin1.addLikeCount();
            }
            KeyBoard keyBoard1 = KeyBoard.createKeyBoard("mstone", 130000, 10,ItemTag.KEYBOARD,"키보드 정보","서울",null);
            Tablet tablet1 = Tablet.createTablet("갤럭시탭s9", 810000, 20, ItemTag.TABLET,"정보", "가능동",null);
            keyBoard1.addMember(member2);
            tablet1.addMember(member1);
            for (int i = 0; i < 22; i++) {
                tablet1.addLikeCount();
            }
            Tablet tablet2 = Tablet.createTablet("갤럭시탭s10", 1010000, 10, ItemTag.TABLET,"정보","가능동", null);
            tablet2.addMember(member2);
            itemService.register(koreaFood1);
            itemService.register(koreaFood2);
            itemService.register(koreaFood3);
            itemService.register(koreaFood4);
            itemService.register(japanFood1);
            itemService.register(vitamin1);
            itemService.register(keyBoard1);
            itemService.register(tablet1);
            itemService.register(tablet2);

        }

    }

}
