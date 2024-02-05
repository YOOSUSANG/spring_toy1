package family.project.repository;

import family.project.domain.Item;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    Optional<Item> findByName(String name);
    @Override
    Optional<Item> findById(Long id);

    @Override
    void deleteAll();

    @Override
    List<Item> findAll(Sort sort);
}
