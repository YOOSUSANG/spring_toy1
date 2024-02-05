package family.project.service;

import family.project.domain.*;
import family.project.domain.enums.DeliveryStatus;
import family.project.domain.enums.OrderStatus;
import family.project.repository.ItemRepository;
import family.project.repository.MemberRepository;
import family.project.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Long Order(Long memberId, Long itemId, int count){
        Member member = memberRepository.findById(memberId).orElse(null);
        Item item = itemRepository.findById(itemId).orElse(null);

        //배송정보 생성
        Delivery delivery = new Delivery(DeliveryStatus.READY, member.getAddress());
        //주문상품 생성(상품, 상품가격, 상품개수)
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);
        Order order = Order.createOrder(member, delivery, orderItem);
        orderRepository.save(order);
        return order.getId();
    }

    public void cancelOrder(Long orderId) {
        Order findOrder = orderRepository.findById(orderId).orElse(null);
        findOrder.cancel();
    }
    // 주문 검색은 quesysql로 만들기



}
