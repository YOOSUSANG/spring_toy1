package family.project.domain.repository;

import family.project.domain.PurchaseItem;
import family.project.domain.TransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionHisRepository extends JpaRepository<TransactionHistory,Long> {


    List<TransactionHistory> findAllByMemberId(Long memberId);


}
