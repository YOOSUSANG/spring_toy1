package family.project.dto;

import family.project.domain.OrderItem;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderItemDtoTest {

    private Long orderItemId;
    private String itemName;
    private String img;
    private int orderPrice;
    private int count;
    public OrderItemDtoTest(OrderItem orderItem) {
        this.orderItemId = orderItem.getId();
        this.itemName = orderItem.getItem().getName(); // LAZY -> Batch size 없을 시 각 호출
        this.img = orderItem.getItem().getImg();// LAZY -> Batch size 없을 시 각 호출
        this.orderPrice = orderItem.getOrderPrice();
        this.count = orderItem.getCount();
    }

}
