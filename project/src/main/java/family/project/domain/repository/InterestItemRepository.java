package family.project.domain.repository;

import family.project.domain.InterestItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterestItemRepository extends JpaRepository<InterestItem, Long> {
    List<InterestItem> findByMemberId(Long memberId);

    void deleteAllByMemberId(Long memberId);

}
