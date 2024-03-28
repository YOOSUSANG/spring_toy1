package family.project.domain.repository;
import family.project.domain.PurchaseItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PurchaseRepository extends JpaRepository<PurchaseItem, Long> {

    @Override
    Optional<PurchaseItem> findById(Long id);

    List<PurchaseItem> findAllByMemberId(Long memberId);

    @Override
    List<PurchaseItem> findAll();

    void deleteAllByMemberId(Long id);

    @Override
    void deleteById(Long id);

    @Override
    void deleteAll();

}
