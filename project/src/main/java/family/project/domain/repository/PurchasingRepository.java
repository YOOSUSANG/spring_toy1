package family.project.domain.repository;
import family.project.domain.PurchasingItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PurchasingRepository extends JpaRepository<PurchasingItem, Long> {

    @Override
    Optional<PurchasingItem> findById(Long id);

    List<PurchasingItem> findAllByMemberId(Long memberId);

    @Override
    List<PurchasingItem> findAll();

    void deleteByItemId(Long itemId);

    @Override
    void deleteAll();

    void deleteAllByMemberId(Long id);

    @Override
    void deleteById(Long id);
}
