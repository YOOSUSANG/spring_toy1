package family.project.web.dto;

import family.project.domain.Order;
import family.project.domain.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class OrderDto {

    private Long orderId;
    private OrderStatus orderStatus;
    private List<OrderItemDto> orderItems;


    //dto에 생성자로 엔티티를 넣어도 상관없지만 필드는 엔티티가 존재하면 절대 안된다.
    public OrderDto(Order order) {
        this.orderId = order.getId();
        this.orderStatus = order.getStatus();
        this.orderItems = order.getOrderItems().stream().map(OrderItemDto::new).collect(Collectors.toList());

    }

}
