package family.project.service;

import family.project.domain.*;
import family.project.domain.enums.MemberType;
import family.project.domain.enums.RoleType;
import family.project.domain.enums.item.ItemTag;
import family.project.domain.food.Food;
import family.project.domain.service.ItemService;
import family.project.domain.service.MemberService;
import family.project.domain.service.TradeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest()
@Transactional
public class tradeServiceTest {

    @Autowired
    private MemberService memberService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private TradeService tradeService;

    @DisplayName("구매중입니다.")
    @Test
    public void 구매중() throws Exception{
        //given
        Member member1 = Member.craeteMember("유수상", "이러쿵저러쿵", "dbtntkd456@naver.com", "123123123",
                null, RoleType.USER, MemberType.BROTHER, new Address("경기도", "의정부시", "329"));
        Member member2 = Member.craeteMember("유주상", "저러쿵", "dbwntkd456@naver.com", "123123123",
                null, RoleType.USER, MemberType.OlD_BROTHER, new Address("경기도", "수원시", "154"));
        memberService.register(member1);
        memberService.register(member2);
        Food koreaFood1 = Food.createFood("김치찌개", 7000, 50, 600, ItemTag.KOREA,"국내산 김치를 활용했습니다.","가능동", null);
        koreaFood1.addMember(member2);
        Food koreaFood2 = Food.createFood("청국장", 6000, 50, 500,  ItemTag.KOREA,"국내산 김치를 활용했습니다.","가능동", null);
        koreaFood2.addMember(member2);
        itemService.register(koreaFood1);
        itemService.register(koreaFood2);
        //when
        Member member = memberService.search(1L);
        Item item1 = itemService.search(1L);
        Item item2 = itemService.search(2L);
        tradeService.PurchasingRegister(item1.getId(), member.getId());
        tradeService.PurchasingRegister(item2.getId(), member.getId());
        List<PurchasingItem> purchasingItems = tradeService.searchMyPurchasing(member.getId());
        //then
        assertEquals(2, purchasingItems.size(), "member가 구매 중인 상품은 총 2개입니다.");

    }

    @DisplayName("구매중취소입니다.")
    @Test
    public void 구매중취소() throws Exception{
        //given
        Member member1 = Member.craeteMember("유수상", "이러쿵저러쿵", "dbtntkd456@naver.com", "123123123",
                null, RoleType.USER, MemberType.BROTHER, new Address("경기도", "의정부시", "329"));
        Member member2 = Member.craeteMember("유주상", "저러쿵", "dbwntkd456@naver.com", "123123123",
                null, RoleType.USER, MemberType.OlD_BROTHER, new Address("경기도", "수원시", "154"));
        memberService.register(member1);
        memberService.register(member2);
        Food koreaFood1 = Food.createFood("김치찌개", 7000, 50, 600, ItemTag.KOREA,"국내산 김치를 활용했습니다.","가능동", null);
        koreaFood1.addMember(member2);
        Food koreaFood2 = Food.createFood("청국장", 6000, 50, 500,  ItemTag.KOREA,"국내산 김치를 활용했습니다.","가능동", null);
        koreaFood2.addMember(member2);
        itemService.register(koreaFood1);
        itemService.register(koreaFood2);
        //when
        Member member = memberService.search(1L);
        Item item1 = itemService.search(1L);
        Item item2 = itemService.search(2L);
        tradeService.PurchasingRegister(item1.getId(), member.getId());
        tradeService.PurchasingRegister(item2.getId(), member.getId());
        tradeService.removePurchasing(item1.getId());
        List<PurchasingItem> purchasingItems = tradeService.searchMyPurchasing(member.getId());
        int size1 = purchasingItems.size();
        tradeService.removePurchasing(item2.getId());
        List<PurchasingItem> purchasingItem1 = tradeService.searchMyPurchasing(member.getId());
        int size2 = purchasingItem1.size();
        //then
        assertEquals(size1, 1, "현재 상품 ID가 1인 것을 삭제하였습니다. 남은 구매 중 상품은 1개입니다.");
        assertEquals(size2,0,"현재 상품 ID가 2인 것을 삭제하였습니다. 남은 구매 중 상품은 없습니다.");
    }

    @DisplayName("구매중 전체 취소입니다.")
    @Test
    public void 구매중_전체취소() throws Exception{
        //given
        Member member1 = Member.craeteMember("유수상", "이러쿵저러쿵", "dbtntkd456@naver.com", "123123123",
                null, RoleType.USER, MemberType.BROTHER, new Address("경기도", "의정부시", "329"));
        Member member2 = Member.craeteMember("유주상", "저러쿵", "dbwntkd456@naver.com", "123123123",
                null, RoleType.USER, MemberType.OlD_BROTHER, new Address("경기도", "수원시", "154"));
        memberService.register(member1);
        memberService.register(member2);
        Food koreaFood1 = Food.createFood("김치찌개", 7000, 50, 600, ItemTag.KOREA,"국내산 김치를 활용했습니다.","가능동", null);
        koreaFood1.addMember(member2);
        Food koreaFood2 = Food.createFood("청국장", 6000, 50, 500,  ItemTag.KOREA,"국내산 김치를 활용했습니다.","가능동", null);
        koreaFood2.addMember(member2);
        itemService.register(koreaFood1);
        itemService.register(koreaFood2);
        //when
        Member member = memberService.search(1L);
        Item item1 = itemService.search(1L);
        Item item2 = itemService.search(2L);
        tradeService.PurchasingRegister(item1.getId(), member.getId());
        tradeService.PurchasingRegister(item2.getId(), member.getId());
        tradeService.removeAllPurchasing(member.getId());
        List<PurchaseItem> purchaseItems = tradeService.searchMyPurchase(member.getId());
        int size = purchaseItems.size();

        //then
        assertEquals(size, 0, "멤버가 자신이 구매 중인 상품을 다 삭제하였습니다.");
    }

    @DisplayName("구매중 전송 확인입니다.")
    @Test
    public void 구매중_전송확인() throws Exception{
        //given
        Member member1 = Member.craeteMember("유수상", "이러쿵저러쿵", "dbtntkd456@naver.com", "123123123",
                null, RoleType.USER, MemberType.BROTHER, new Address("경기도", "의정부시", "329"));
        Member member2 = Member.craeteMember("유주상", "저러쿵", "dbwntkd456@naver.com", "123123123",
                null, RoleType.USER, MemberType.OlD_BROTHER, new Address("경기도", "수원시", "154"));
        memberService.register(member1);
        memberService.register(member2);

        Food koreaFood1 = Food.createFood("김치찌개", 7000, 50, 600, ItemTag.KOREA,"국내산 김치를 활용했습니다.","가능동", null);
        koreaFood1.addMember(member2);
        Food koreaFood2 = Food.createFood("청국장", 6000, 50, 500,  ItemTag.KOREA,"국내산 김치를 활용했습니다.","가능동", null);
        koreaFood2.addMember(member2);
        itemService.register(koreaFood1);
        itemService.register(koreaFood2);
        //when
        Member findMember1 = memberService.search(1L);
        Member findMember2 = memberService.search(2L);
        Item item1 = itemService.search(1L);
        Item item2 = itemService.search(2L);
        tradeService.PurchasingRegister(item1.getId(), findMember1.getId());
        tradeService.PurchasingRegister(item2.getId(), findMember1.getId());
        List<PurchasingItem> purchasingItems = tradeService.confirmPurchasing(findMember2.getId());
        int size = purchasingItems.size();
        //then
        assertEquals(2, size, " 내 상품에 구매 요청이 온 것은 총 2개입니다");
    }










































}
