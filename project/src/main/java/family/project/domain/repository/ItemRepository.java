package family.project.domain.repository;

import family.project.domain.Item;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long>, ItemRepositorySearchCustom{

    @Override
    Optional<Item> findById(Long id);


    List<Item> findAllByMemberId(Long id);

    Item findByIdAndMemberId(Long itemId, Long memberId);


    @Override
    void deleteAll();

    void deleteById(Long id);

    @Override
    List<Item> findAll(Sort sort);
}
