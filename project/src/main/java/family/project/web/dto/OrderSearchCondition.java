package family.project.web.dto;

import family.project.domain.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
public class OrderSearchCondition {
    //이름 검색시 작동
    private String orderItemName;
    //주문상태{주문, 취소} 클릭 시
    private OrderStatus orderStatus;
    //년도 클릭 시 Integer을 통해 null처리
    private Integer year;
    //월 클릭 시 Integer을 통해 null처리
    private Integer month;




}
