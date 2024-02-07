package family.project.domain;


import jakarta.persistence.*;
import lombok.Getter;

import static jakarta.persistence.FetchType.LAZY;


//주문 상품
@Entity
@Table(name = "order_item")
@Getter
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    private Integer orderPrice;
    //아이템 개수
    private Integer count;
    //여러 개의 주문 상품이 하나의 주문에 속한다.
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    //여러 개의 주문 상품이 하나의 상품에 들어간다. -> 하나의 상품이 여러 개의 주문 상품에 속할 수 있다.
    //만약 OneToOne으로 하면 하나의 주문 상품에 하나의 상품이 속하므로 유연하지 않다.
    //ManyToMany는 oneToMany <-- xxx --> ManyToOne
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    protected OrderItem() {

    }

    public OrderItem(Integer orderPrice, Integer count) {
        this.orderPrice = orderPrice;
        this.count = count;
    }

    public OrderItem(Item item, Order order, Integer orderPrice, Integer count) {
        this.item = item;
        this.order = order;
        this.orderPrice = orderPrice;
        this.count = count;
    }

    public void changeItem(Item item) {
        this.item = item;
    }


    //***** 생성 메소드 *****//
    public static OrderItem createOrderItem(Item item, Integer orderPrice, Integer count) {
        OrderItem orderItem = new OrderItem(orderPrice, count);
        orderItem.changeItem(item);
        item.removeStock(count);
        return orderItem;
    }

    //***** 비지니스 로직 *****//
    //취소시 기존 아이템 수량 복구
    public void cancel() {
        getItem().addStock(count); // 변경 감지
    }

    //***** 조회 로직 *****//
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
