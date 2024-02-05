package family.project.repository;

import family.project.domain.Order;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {



    @Override
    Optional<Order> findById(Long id);

    @Override
    List<Order> findAll(Sort sort);
}
