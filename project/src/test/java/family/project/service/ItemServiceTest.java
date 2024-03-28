package family.project.service;

import family.project.domain.Address;
import family.project.domain.Item;
import family.project.domain.ItemSaveMember;
import family.project.domain.Member;
import family.project.domain.enums.ItemCheckTag;
import family.project.domain.enums.MemberType;
import family.project.domain.enums.RoleType;
import family.project.domain.enums.item.ItemTag;
import family.project.domain.food.Food;
import family.project.domain.groceries.Vitamin;
import family.project.domain.product.KeyBoard;
import family.project.domain.product.Tablet;
import family.project.domain.service.ItemService;
import family.project.domain.service.MemberService;
import family.project.web.dto.item.ItemFilterDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest()
@Transactional
public class ItemServiceTest {

    @Autowired
    private ItemService itemService;
    @Autowired
    private MemberService memberService;


    @BeforeEach
    public void setting() {
//        Food koreaFood1 = Food.createFood("김치찌개", 7000, 50, 600, "국내산 김치를 활용했습니다.", ItemTag.KOREA,"가능동", null);
//        for (int i = 0; i < 10; i++) {
//            koreaFood1.addLikeCount();
//        }
//        Food koreaFood2 = Food.createFood("청국장", 6000, 50, 500, "40/50대 저격하는 구수한 청국장", ItemTag.KOREA,"가능동", null);
//        for (int i = 0; i < 20; i++) {
//            koreaFood2.addLikeCount();
//        }
//        Food koreaFood3 = Food.createFood("된장찌개", 6000, 50, 500, "40/50대 저격하는 구수한 된장찌개", ItemTag.KOREA,"가능동", null);
//        for (int i = 0; i < 15; i++) {
//            koreaFood3.addLikeCount();
//        }
//        Food koreaFood4 = Food.createFood("고등어", 13000, 30, 250, "국내산 고등어입니다.", ItemTag.KOREA,"가능동", null);
//        Food japanFood1 = Food.createFood("연어", 13000, 30, 350, "노르웨이산 연어입니다.", ItemTag.JAPAN,"가능동", null);
//
//        Vitamin vitamin1 = Vitamin.createVitamin("루테인", 3000, 50, ItemTag.VITAMIN,"가능동",null);
//        for (int i = 0; i < 50; i++) {
//            vitamin1.addLikeCount();
//        }
//        KeyBoard keyBoard1 = KeyBoard.createKeyBoard("mstone", 130000, 10, ItemTag.KEYBOARD, "가능동",null);
//
//        Tablet tablet1 = Tablet.createTablet("갤럭시탭s9", 810000, 20,  ItemTag.TABLET, "가능동",null);
//        for (int i = 0; i < 22; i++) {
//            tablet1.addLikeCount();
//        }
//        Tablet tablet2 = Tablet.createTablet("갤럭시탭s10", 1010000, 10,  ItemTag.TABLET,"가능동", null);
//
//        itemService.register(koreaFood1);
//        itemService.register(koreaFood2);
//        itemService.register(koreaFood3);
//        itemService.register(koreaFood4);
//        itemService.register(japanFood1);
//        itemService.register(vitamin1);
//        itemService.register(keyBoard1);
//        itemService.register(tablet1);
//        itemService.register(tablet2);
    }

    @DisplayName("아이템 삭제")
    @Test()
    public void 등록_아이템삭제() throws Exception {
        //given
        Long removeId = 1L;
        //when
        List<Item> itemsBefore = itemService.itemsAll();
        int sizeBefore = itemsBefore.size();
        itemService.removeItem(removeId);
        List<Item> findAll = itemService.itemsAll();
        //then
        assertEquals(sizeBefore - 1, findAll.size(), "id가 1인 상품이 삭제됐습니다");
    }


    @DisplayName("전체조회")
    @Test
    public void 아이템전체조회() throws Exception {
        //given
        //when
        List<Item> findItemAll = itemService.itemsAll();
        //then
        assertEquals(5, findItemAll.size(), "모든 상품이 조회됩니다");
    }

    @DisplayName("개별태그")
    @Test
    public void 개별태그조회() throws Exception {
        //given
        ItemFilterDto koreaTag = new ItemFilterDto(null, ItemTag.KOREA, null);
        ItemFilterDto japanTag = new ItemFilterDto(null, ItemTag.JAPAN, null);
        ItemFilterDto vitaminTag = new ItemFilterDto(null, ItemTag.VITAMIN, null);
        ItemFilterDto keyBoardTag = new ItemFilterDto(null, ItemTag.KEYBOARD, null);
        ItemFilterDto tabletTag = new ItemFilterDto(null, ItemTag.TABLET, null);
        PageRequest pageRequest = PageRequest.of(0, 5);
        //when
        Page<Item> pageKoreaItems = itemService.search_direct(koreaTag, pageRequest);
        Page<Item> pageJapanItems = itemService.search_direct(japanTag, pageRequest);
        Page<Item> vitaminItems = itemService.search_direct(vitaminTag, pageRequest);
        Page<Item> keyBoardItems = itemService.search_direct(keyBoardTag, pageRequest);
        Page<Item> tabletItems = itemService.search_direct(tabletTag, pageRequest);
        //then
        assertEquals(4, pageKoreaItems.getTotalElements(), "총 한식 상품 개수는 2개입니다.");
        assertEquals(0, pageKoreaItems.getNumber(), "현재 상품들은 첫번 쨰 페이지에 존재한다");
        assertEquals(1, pageJapanItems.getTotalElements(), "총 일식 상품 개수는 1개입니다");
        assertEquals(0, pageJapanItems.getNumber(), "총 일식 상품들이 0번쨰에 존재한다");
        assertEquals(1, vitaminItems.getTotalElements(), "총 비타민 상품 개수는 1개이다.");
        assertEquals(1, keyBoardItems.getTotalElements(), "총 키보드 상품 개수는 1개이다.");
        assertEquals(1, tabletItems.getTotalElements(), "총 태블릿 상품 개수는 1개이다.");
    }

    @DisplayName("동적으로 상품 이름 필터링")
    @Test
    public void 상품이름검색() throws Exception {
        //given
        ItemFilterDto titleDto1 = new ItemFilterDto("탭", null, null);
        ItemFilterDto titleDto2 = new ItemFilterDto("찌개", null, null);
        ItemFilterDto titleDto3 = new ItemFilterDto("갤럭시", null, null);
        ItemFilterDto titleDto4 = new ItemFilterDto("어", null, null);
        ItemFilterDto titleDto5 = new ItemFilterDto("김치찌개", null, null);
        ItemFilterDto titleDto6 = new ItemFilterDto("ㅁㄴㅇㅁㅇㅇㅇㅁㅇㄴㅁㅁㅇㄴㅁ", null, null);
        ItemFilterDto titleDto7 = new ItemFilterDto("갤럭시", ItemTag.VITAMIN, null);


        PageRequest pageRequest = PageRequest.of(0, 5);

        //when
        Page<Item> findTitleItem1 = itemService.search_direct(titleDto1, pageRequest);
        Page<Item> findTitleItem2 = itemService.search_direct(titleDto2, pageRequest);
        Page<Item> findTitleItem3 = itemService.search_direct(titleDto3, pageRequest);
        Page<Item> findTitleItem4 = itemService.search_direct(titleDto4, pageRequest);
        Page<Item> findTitleItem5 = itemService.search_direct(titleDto5, pageRequest);
        Page<Item> findTitleItem6 = itemService.search_direct(titleDto6, pageRequest);
        Page<Item> findTitleItem7 = itemService.search_direct(titleDto7, pageRequest);
        //then
        assertEquals(2, findTitleItem1.getTotalElements(), "탭이 들어간 상품은 총 2개입니다");
        assertEquals(2, findTitleItem2.getTotalElements(), "찌개가 들어간 상품은 총 2개입니다");
        assertEquals(2, findTitleItem3.getTotalElements(), "갤럭시가 들어간 상품은 총 2개입니다");
        assertEquals(2, findTitleItem4.getTotalElements(), "어가 들어간 상품은 총 2개입니다");
        assertEquals(1, findTitleItem5.getTotalElements(), "김치찌개가 들어간 상품은 총 1개입니다");
        assertEquals(0, findTitleItem6.getTotalElements(), "ㅁㄴㅇㅁㅇㅇㅇㅁㅇㄴㅁㅁㅇㄴㅁ 들어간 상품은 총 0개입니다");
        assertEquals(0, findTitleItem7.getTotalElements(), "이름이 갤럭시이고 상품이 비타민인 상품은 총 0개입니다");
    }

    @DisplayName("Order로 조회")
    @Test
    public void order() throws Exception {
        //given
        ItemFilterDto titleDto1 = new ItemFilterDto(null, null, ItemCheckTag.RECENT);
        ItemFilterDto titleDto2 = new ItemFilterDto(null, null, ItemCheckTag.LIKE);
        ItemFilterDto titleDto3 = new ItemFilterDto(null, null, ItemCheckTag.LOW_PRICE);
        ItemFilterDto titleDto4 = new ItemFilterDto(null, null, ItemCheckTag.HIGH_PRICE);
        ItemFilterDto titleDto5 = new ItemFilterDto(null, ItemTag.KOREA, ItemCheckTag.LIKE);
        ItemFilterDto titleDto6 = new ItemFilterDto("찌개", ItemTag.KOREA, ItemCheckTag.LIKE);
        ItemFilterDto titleDto7 = new ItemFilterDto("갤럭시", ItemTag.TABLET, ItemCheckTag.HIGH_PRICE);
        PageRequest pageRequest = PageRequest.of(0, 15);
        //when
        Page<Item> findTitleItem1 = itemService.search_direct(titleDto1, pageRequest);
        List<Item> content1 = findTitleItem1.getContent();
        Page<Item> findTitleItem2 = itemService.search_direct(titleDto2, pageRequest);
        List<Item> content2 = findTitleItem2.getContent();
        Page<Item> findTitleItem3 = itemService.search_direct(titleDto3, pageRequest);
        List<Item> content3 = findTitleItem3.getContent();
        Page<Item> findTitleItem4 = itemService.search_direct(titleDto4, pageRequest);
        List<Item> content4 = findTitleItem4.getContent();
        Page<Item> findTitleItem5 = itemService.search_direct(titleDto5, pageRequest);
        List<Item> content5 = findTitleItem5.getContent();
        Page<Item> findTitleItem6 = itemService.search_direct(titleDto6, pageRequest);
        List<Item> content6 = findTitleItem6.getContent();
        Page<Item> findTitleItem7 = itemService.search_direct(titleDto7, pageRequest);
        List<Item> content7 = findTitleItem7.getContent();
        //then
        for (Item con : content1) {
            System.out.println("con.getName() = " + con.getName());
        }
        System.out.println("-------------------------------------");
        for (Item con : content2) {
            System.out.println("con.Name = " + con.getName() + "con.LikeCount = " + con.getLikesCount());
        }
        System.out.println("-------------------------------------");
        for (Item con : content3) {
            System.out.println("con.Name = " + con.getName() + "con.LowPrice = " + con.getPrice());
        }
        System.out.println("-------------------------------------");
        for (Item con : content4) {
            System.out.println("con.Name = " + con.getName() + "con.HighPrice = " + con.getPrice());
        }
        System.out.println("-------------------------------------");
        for (Item con : content5) {
            System.out.println("con.Name = " + con.getName() + "con.LikeCount = " + con.getLikesCount());
        }
        System.out.println("-------------------------------------");
        for (Item con : content6) {
            System.out.println("con.Name = " + con.getName() + "con.LikeCount = " + con.getLikesCount());
        }
        System.out.println("-------------------------------------");
        for (Item con : content7) {
            System.out.println("con.Name = " + con.getName() + "con.LowPrice = " + con.getPrice());
        }
    }

    @DisplayName("찜버튼")
    @Test
    public void 아이템찜버튼() throws Exception {
        //given
        Member member = Member.craeteMember("유수상", "이러쿵저러쿵", "dbtntkd456@naver.com", "123123123",
                null, RoleType.USER, MemberType.BROTHER, new Address("경기도", "의정부시", "329"));

        memberService.register(member);
        //when
        itemService.interestedItem(member.getId(), 1L);
        itemService.interestedItem(member.getId(), 5L);
        Member findMember = memberService.search(member.getId());
        List<ItemSaveMember> itemSaveMembers = findMember.getItemSaveMembers();
        for (ItemSaveMember itemSaveMember : itemSaveMembers) {
            System.out.println("itemSaveMember.getItem() = " + itemSaveMember.getItem().getName());
        }
        //then
        assertEquals(2, itemSaveMembers.size(), "현재 찜은 두개입니다.");
    }

    @DisplayName("찜버튼 취소")
    @Test
    public void 아이템찜버튼취소() throws Exception {
        //given
        Member member = Member.craeteMember("유수상", "이러쿵저러쿵", "dbtntkd456@naver.com", "123123123",
                null, RoleType.USER, MemberType.BROTHER, new Address("경기도", "의정부시", "329"));
        memberService.register(member);
        //when
        itemService.interestedItem(member.getId(), 1L);
        itemService.interestedItem(member.getId(), 5L);
        Member findMember = memberService.search(member.getId());
        List<ItemSaveMember> itemSaveMembersBefore = findMember.getItemSaveMembers();
        int size = itemSaveMembersBefore.size();
        for (ItemSaveMember itemSaveMember : itemSaveMembersBefore) {
            System.out.println("itemSaveMember.getItem().getName() = " + itemSaveMember.getItem().getName());
        }
        member.ItemCancel(5L);
        List<ItemSaveMember> itemSaveMembersAfter = findMember.getItemSaveMembers();
        System.out.println("-----------------------------------------------------------------------------");
        for (ItemSaveMember itemSaveMember : itemSaveMembersAfter) {
            System.out.println("itemSaveMember.getItem().getName() = " + itemSaveMember.getItem().getName());
        }
        //then
        assertEquals(2, size, "취소하기 전 찜한 거는 2개입니다");
        assertEquals(1, itemSaveMembersAfter.size(), "연어 찜을 취소하면 찜한 거는 1개입니다");
    }

    @DisplayName("아이템 등록해보기")
    @Test
    public void 등록_new() throws Exception{
        //given
        Member member = Member.craeteMember("유수상", "이러쿵저러쿵", "dbtntkd456@naver.com", "123123123",
                null, RoleType.USER, MemberType.BROTHER, new Address("경기도", "의정부시", "329"));
        memberService.register(member);

        itemService.register_new(member.getId(), "김치찌개", ItemTag.KOREA, 6000, "맛있는 김치를 사용한 김치찌개입니다.", "의정부 중학교 앞에서 봅시다.", null);
        itemService.register_new(member.getId(), "부대찌개", ItemTag.KOREA, 7000, "최고급 스팸을 사용한 부대찌개입니다.", "의정부 공고 앞에서 봅시다.", null);
        itemService.register_new(member.getId(), "갤럭시탭s9", ItemTag.TABLET, 810000, "한달 전에 구매한 갤럭시탭s9 팝니다.", "의정부 광동고등학교 앞에서 봅시다.", null);

        //when
        Member findMember = memberService.search(1L);
        int size = findMember.getItems().size();
        int koreaSize = findMember.getItems().stream().filter(item -> item.getItemTag() == ItemTag.KOREA).collect(Collectors.toList()).size();
        int tabletSize = findMember.getItems().stream().filter(item -> item.getItemTag() == ItemTag.TABLET).collect(Collectors.toList()).size();
        //then
        assertEquals(3, size, "member가 등록한 아이템은 3개입니다.");
        assertEquals(2, koreaSize,"member가 등록한 아이템 태그 중 한식은 2개입니다.");
        assertEquals(1,tabletSize,"member가 등록한 아이템 태그 중 테블릿은 1개입니다.");
    }




}
