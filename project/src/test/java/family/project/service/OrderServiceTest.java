package family.project.service;

import family.project.domain.*;
import family.project.domain.enums.MemberType;
import family.project.domain.enums.OrderStatus;
import family.project.domain.enums.RoleType;
import family.project.domain.food.Food;
import family.project.domain.groceries.Vitamin;
import family.project.domain.service.ItemService;
import family.project.domain.service.MemberService;
import family.project.domain.service.OrderService;
import family.project.web.dto.OrderDto;
import family.project.web.dto.OrderSearchCondition;
import family.project.domain.exception.NotEnoughStockException;
import family.project.domain.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    private MemberService memberService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;

    @DisplayName("상품주문입니다.")
    @Test
    public void 상품주문() throws Exception {
        //given
        Member member = Member.craeteMember("yoosusang", null, RoleType.ADMIN, MemberType.BROTHER, new Address("서울", "압구정", "111-111"));
        memberService.register(member);
        Item item = createHealthGroceries("루테인", 10000, 100, "circle", "Immunity");
        itemService.register(item);
        int itemOrderCount = 2;
        //when
        Long orderId = orderService.Order(member.getId(), item.getId(), itemOrderCount);
        //then
        //여기서 검색조건을 설정해서 찾아주는 걸로 바꿔야 함 -> 아직 미구현
        Order getOrder = orderRepository.findById(orderId).orElse(null);

        assertEquals(OrderStatus.ORDER, getOrder.getStatus(), "상품 주문시 상태는 ORDER");
        assertEquals(1, getOrder.getOrderItems().size(), "주문한 상품 종류 수가 정확해야 한다.");
        assertEquals(10000 * 2, getOrder.getTotalPrice(), "주문한 가격은 가격 * 수량이다.");
        assertEquals(98, item.getStockQuantity(), "주문 수량만큼 재고가 줄어야 한다");
    }

    @DisplayName("재고수량초과")
    @Test
    public void 상품주문_재고수량초과() throws Exception {
        //given
        Member member = Member.craeteMember("yoosusang", null, RoleType.ADMIN, MemberType.BROTHER, new Address("서울", "압구정", "111-111"));
        memberService.register(member);
        Item item = createHealthGroceries("루테인", 10000, 10, "circle", "Immunity");
        itemService.register(item);
        //when
        int itemOrderCount = 11;
        //then
        assertThrows(NotEnoughStockException.class, () -> orderService.Order(member.getId(), item.getId(), itemOrderCount));
    }

    @DisplayName("주문취소")
    @Test
    public void 주문취소() throws Exception {
        //given
        Member member = Member.craeteMember("yoosusang", null, RoleType.ADMIN, MemberType.BROTHER, new Address("서울", "압구정", "111-111"));
        memberService.register(member);
        Item item = createHealthGroceries("루테인", 10000, 10, "circle", "Immunity");
        itemService.register(item);
        int itemOrderCount = 2;

        Long orderId = orderService.Order(member.getId(), item.getId(), itemOrderCount);
        //when
        orderService.cancelOrder(orderId); // 변경 감지 작동
        //then
        Order order = orderRepository.findById(orderId).orElse(null);
        assertEquals(OrderStatus.CANCEL, order.getStatus(), "주문취소상태");
        assertEquals(10, item.getStockQuantity(), "주문하기 전 수량과 같아야 한다.");
    }

    @DisplayName("주문확인")
    @Test
    public void 내주문확인() throws Exception {
        //given
        //로그인
        Member member1 = Member.craeteMember("dbtntkd456@naver.com", "yoosusang", null, RoleType.ADMIN, MemberType.BROTHER, new Address("서울", "압구정", "111-111"));
        Member member2 = Member.craeteMember("poii9927@ajou.ac.kr", "yoojusang", null, RoleType.USER, MemberType.BROTHER, new Address("서울", "강남", "111-112"));
        memberService.register(member1);
        memberService.register(member2);

        //아이템 등록
        Item item1 = createHealthGroceries("루테인", 10000, 10, "circle", "Immunity");
        Item item2 = createFood("BBQ 황금 올리브", 22000, 10, 2200, "chicken", "촉촉한 치킨");
        itemService.register(item1);
        itemService.register(item2);

        //주문
        Long orderId1 = orderService.Order(member1.getId(), item1.getId(), 2);
        Long orderId2 = orderService.Order(member1.getId(), item2.getId(), 1);
        Long orderId3 = orderService.Order(member2.getId(), item2.getId(), 3);
        //when
        List<Order> orders = orderService.searchMyOrder("dbtntkd456@naver.com");
        //then
        assertEquals(2, orders.size(), "내 주문은 총 두건이다.");
        assertThat(orders).extracting(order -> order.getMember().getEmail()).containsExactly("dbtntkd456@naver.com", "dbtntkd456@naver.com");
        assertEquals(6, item2.getStockQuantity(), "남은 수량은 6개 입니다.");

    }

    @DisplayName("검색조건")
    @Test
    public void 검색조건확인() throws Exception {
        //given
        //로그인
        Member member1 = Member.craeteMember("dbtntkd456@naver.com", "yoosusang", null, RoleType.ADMIN, MemberType.BROTHER, new Address("서울", "압구정", "111-111"));
        Member member2 = Member.craeteMember("poii9927@ajou.ac.kr", "yoojusang", null, RoleType.USER, MemberType.BROTHER, new Address("서울", "강남", "111-112"));
        memberService.register(member1);
        memberService.register(member2);

        //아이템 등록
        Item item1 = createHealthGroceries("루테인", 10000, 10, "circle", "Immunity");
        Item item2 = createFood("BBQ 황금 올리브", 22000, 10, 2200, "chicken", "촉촉한 치킨");
        itemService.register(item1);
        itemService.register(item2);

        //주문
        Long orderId1 = orderService.Order(member1.getId(), item1.getId(), 2); // 루테인
        Long orderId2 = orderService.Order(member1.getId(), item1.getId(), 2); // 루테인
        Long orderId3 = orderService.Order(member1.getId(), item2.getId(), 1); // BBQ

        Long orderId4 = orderService.Order(member2.getId(), item2.getId(), 3); // BBQ
        //when

        //아무 조건 없이 전체 출력해보기
        String userEmail1 = "dbtntkd456@naver.com";
        String userEmail2 = "poii9927@ajou.ac.kr";
        OrderSearchCondition notSearchCondition = new OrderSearchCondition(null, null, null, null);
        List<OrderDto> orderDtoTests1 = orderService.searchCondition(notSearchCondition, userEmail1);
        //주문 상태
        OrderSearchCondition orderStatusSearchCondition1 = new OrderSearchCondition(null, OrderStatus.ORDER, null, null);
        List<OrderDto> orderDtoTests2 = orderService.searchCondition(orderStatusSearchCondition1, userEmail1);
        OrderSearchCondition orderStatusSearchCondition2 = new OrderSearchCondition(null, OrderStatus.CANCEL, null, null);
        List<OrderDto> orderDtoTests3 = orderService.searchCondition(orderStatusSearchCondition2, userEmail1);
        //년도 체크
        OrderSearchCondition orderYearSearchCondition3 = new OrderSearchCondition(null, null, 2024, null);
        List<OrderDto> orderDtoTests4 = orderService.searchCondition(orderYearSearchCondition3, userEmail1);
        //월 체크
        OrderSearchCondition orderMonthSearchCondition4 = new OrderSearchCondition(null, null, null, 2);
        List<OrderDto> orderDtoTests5 = orderService.searchCondition(orderMonthSearchCondition4, userEmail1);
        //년 월 체크
        OrderSearchCondition orderYearMonthSearchCondition5 = new OrderSearchCondition(null, null, 2024, 2);
        List<OrderDto> orderDtoTests6 = orderService.searchCondition(orderYearMonthSearchCondition5, userEmail1);

        //이름체크
        OrderSearchCondition orderNameSearchCondition6 = new OrderSearchCondition("루테인", null, null, null);
        List<OrderDto> orderDtoTests7 = orderService.searchCondition(orderNameSearchCondition6, userEmail1);
        OrderSearchCondition orderNameSearchCondition7 = new OrderSearchCondition("BBQ 황금 올리브", null, null, null);
        List<OrderDto> orderDtoTests8 = orderService.searchCondition(orderNameSearchCondition7, userEmail1);
        List<OrderDto> orderDtoTests9 = orderService.searchCondition(orderNameSearchCondition6, userEmail2);
        List<OrderDto> orderDtoTests10 = orderService.searchCondition(orderNameSearchCondition7, userEmail2);


        //년 이름체크
        OrderSearchCondition orderNameSearchCondition9 = new OrderSearchCondition("루테인", null, 2024, null);
        List<OrderDto> orderDtoTests11 = orderService.searchCondition(orderNameSearchCondition9, userEmail1);


        //then
        assertEquals(3, orderDtoTests1.size(), "dbtntkd456@naver.com: 주문한 것은 3개");
        assertEquals(3, orderDtoTests2.size(), "dbtntkd456@naver.com: 주문 상태는 3개");
        assertEquals(0, orderDtoTests3.size(), "dbtntkd456@naver.com: 취소 상태는 0개");
        assertEquals(3, orderDtoTests4.size(), "dbtntkd456@naver.com: 2024년도에 주문한 총 개수는 3개");
        assertEquals(3, orderDtoTests5.size(), "dbtntkd456@naver.com: 2월에 주문한 총 개수는 3개");
        assertEquals(3, orderDtoTests6.size(), "dbtntkd456@naver.com: 2024년 2월에 주문한 총 개수는 3개");
        assertEquals(2, orderDtoTests7.size(), "dbtntkd456@naver.com: 전체 주문에서 루테인 주문 2개");
        assertEquals(1, orderDtoTests8.size(), "dbtntkd456@naver.com: 전체 주문에서 bbq 주문 1개");
        assertEquals(0, orderDtoTests9.size(), "poii9927@ajou.ac.kr: 전체 주문에서 루테인 주문 0개");
        assertEquals(1, orderDtoTests10.size(), "poii9927@ajou.ac.kr: 전체 주문에서 bbq 주문 1개");
        assertEquals(2, orderDtoTests11.size(), "dbtntkd456@naver.com: 전체 주문에서 2024년도에 루테인 주문 2개");


    }


    private Item createFood(String name, int price, int stockQuantity, int calories, String foodType, String information) {
        return new Food(name, price, stockQuantity, calories, foodType, information);
    }


    private Vitamin createHealthGroceries(String name, Integer price, Integer stockQuantity, String shape, String supplementFunction) {
        return new Vitamin(name, price, stockQuantity, shape, supplementFunction);
    }

}