package family.project.domain;


import family.project.domain.enums.DeliveryStatus;
import family.project.domain.enums.OrderStatus;
import family.project.domain.mapped.BasicEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;


//주문 관리
@Entity
@Table(name = "orders")
@Getter
public class Order extends BasicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;
    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    //order persist시 자동으로 orderItems도 persist
    @OneToMany(mappedBy = "order", cascade = ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = LAZY, cascade = ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;




    //***** 연관관계 메소드 *****//
    public void addMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addDelivery(Delivery delivery) {
        Delivery newDelivery = new Delivery(this, delivery.getStatus(), delivery.getAddress()); //cascade all이라서 사용 가능
        this.delivery = newDelivery;
    }
    public void addOrderItem(OrderItem orderItem) {
        //새로운 Item을 만드는데 이때 기존 orderItem에 대한 item에 연관관계도 넘겨줘야 한다.
        OrderItem newOrderItem = new OrderItem(orderItem.getItem(),this, orderItem.getOrderPrice(), orderItem.getCount());
        this.orderItems.add(newOrderItem);
    }

    public void addStatus(OrderStatus orderStatus) {
        this.status = orderStatus;
    }

    public void addDate() {
        this.orderDate = LocalDateTime.now();
    }

    //***** 생성 메소드 *****//
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order();
        order.addMember(member);
        order.addDelivery(delivery);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.addStatus(OrderStatus.ORDER);
        order.addDate();
        return order;
    }

    //***** 비지니스 로직 *****//
    public void cancel(){
        if (delivery.getStatus() == DeliveryStatus.SHIP) {
            throw new IllegalStateException("이미 배송중인 상품은 취소가 불가능 합니다.");
        }
        if (delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송이 완료된 상품은 취소가 불가능 합니다.");
        }
        this.addStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }
    //***** 조회 로직 *****//

    //주문 전체 가격
    public int getTotalPrice(){
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }



}
