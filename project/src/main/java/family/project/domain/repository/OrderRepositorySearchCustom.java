package family.project.domain.repository;

import family.project.web.dto.OrderDto;
import family.project.web.dto.OrderSearchCondition;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepositorySearchCustom {

    List<OrderDto> search(OrderSearchCondition condition, String email); // 단순 search -> page 설정 안함 -> 후에 타임리프로 서버사이드렌더링할 떄 변경



}
