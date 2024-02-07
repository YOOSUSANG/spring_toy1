package family.project.repository;

import family.project.dto.OrderDtoTest;
import family.project.dto.OrderSearchCondition;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepositorySearchCustom {

    List<OrderDtoTest> search(OrderSearchCondition condition, String email); // 단순 search -> page 설정 안함 -> 후에 타임리프로 서버사이드렌더링할 떄 변경



}
