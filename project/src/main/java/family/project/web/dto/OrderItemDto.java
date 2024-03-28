package family.project.web.dto;

import family.project.domain.OrderItem;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderItemDto {

    private Long orderItemId;
    private String itemName;
    private int orderPrice;
    private int count;
    public OrderItemDto(OrderItem orderItem) {
        this.orderItemId = orderItem.getId();
        this.itemName = orderItem.getItem().getName(); // LAZY -> Batch size 없을 시 각 호출
        this.orderPrice = orderItem.getOrderPrice();
        this.count = orderItem.getCount();
    }

}
