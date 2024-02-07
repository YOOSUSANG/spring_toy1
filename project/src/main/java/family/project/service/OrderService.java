package family.project.service;

import family.project.domain.*;
import family.project.domain.enums.DeliveryStatus;
import family.project.domain.enums.OrderStatus;
import family.project.dto.OrderDtoTest;
import family.project.dto.OrderSearchCondition;
import family.project.repository.ItemRepository;
import family.project.repository.MemberRepository;
import family.project.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
public class OrderService {


    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, MemberRepository memberRepository, ItemRepository itemRepository) {
        this.orderRepository = orderRepository;
        this.memberRepository = memberRepository;
        this.itemRepository = itemRepository;
    }

    @Transactional
    public Long Order(Long memberId, Long itemId, int count) {
        Member member = memberRepository.findById(memberId).orElse(null);
        Item item = itemRepository.findById(itemId).orElse(null);

        //배송정보 생성
        Delivery delivery = new Delivery(DeliveryStatus.READY, member.getAddress());
        //주문상품 생성(상품, 상품가격, 상품개수)
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);
        Order order = Order.createOrder(member, delivery, orderItem);
        System.out.println("order = " + order.getOrderItems().get(0).getItem());
        orderRepository.save(order);
        return order.getId();
    }

    //조회를 제외하고 즉 변경감지에도 Transactional을 붙여야 한다.
    @Transactional
    public void cancelOrder(Long orderId) {
        Order findOrder = orderRepository.findById(orderId).orElse(null);
//        System.out.println("findOrder = " + findOrder.getOrderItems().get(0).getItem());
        findOrder.cancel();
    }

    public List<Order> searchMyOrder(String email){
        return orderRepository.findByMyOrders(email);
    }

    public List<OrderDtoTest> searchCondition(OrderSearchCondition orderSearchCondition, String email){
        return orderRepository.search(orderSearchCondition, email);
    }



}
