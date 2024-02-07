package family.project.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import family.project.domain.enums.OrderStatus;
import family.project.dto.OrderDtoTest;
import family.project.dto.OrderSearchCondition;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static family.project.domain.QDelivery.delivery;
import static family.project.domain.QMember.member;
import static family.project.domain.QOrder.order;
import static org.springframework.util.StringUtils.hasText;

@Component
public class OrderRepositorySearchCustomImpl implements OrderRepositorySearchCustom {

    private final EntityManager em;
    private final JPAQueryFactory query;

    @Autowired
    public OrderRepositorySearchCustomImpl(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }


    @Override
    public List<OrderDtoTest> search(OrderSearchCondition condition, String email) {
        List<OrderDtoTest> result = query
                .select(Projections.constructor(OrderDtoTest.class,
                        order
                ))
                .from(order)
                .join(order.member, member).fetchJoin()
                .join(order.delivery, delivery).fetchJoin()
                .where(
                        //null 무시
                        memberEmailEq(email),
                        orderStatusEq(condition.getOrderStatus()),
                        yearEq(condition.getYear()),
                        monthEq(condition.getMonth())
                ).fetch();


        //일단 년도는 무조건 설정한 후 해당 item이름을 가져오도록 설정 -> sqld공부 후 itemName을 sql로 불러올 수 있다면 생각해보기
        if (hasText(condition.getOrderItemName())) {
            return orderItemNameEq(condition.getOrderItemName(), result);
        }

        return result;
    }

    private BooleanExpression memberEmailEq(String email) {
        return hasText(email) ? member.email.eq(email) : null;
    }

    private List<OrderDtoTest> orderItemNameEq(String orderItemName, List<OrderDtoTest> result) {
        return result.stream()
                .filter(orderDtoTest -> orderDtoTest.getOrderItems().stream()
                        .anyMatch(orderItemDtoTest -> orderItemDtoTest.getItemName().equals(orderItemName))).collect(Collectors.toList());
    }

    //enum처리
    private BooleanExpression orderStatusEq(OrderStatus orderStatus) {
        return orderStatus != null ? order.status.eq(orderStatus) : null;

    }

    private BooleanExpression yearEq(Integer year) {
        return year != null ? order.orderDate.year().eq(year) : null;
    }

    private BooleanExpression monthEq(Integer month) {
        return month != null ? order.orderDate.month().eq(month) : null;
    }
}
