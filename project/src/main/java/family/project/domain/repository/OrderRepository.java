package family.project.domain.repository;

import family.project.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositorySearchCustom {


    @Override
    Optional<Order> findById(Long id);

    @Override
    List<Order> findAll();


    @Query("select o from Order o" +
            " join fetch o.member m" +
            " join fetch o.delivery d" +
            " where m.email = :email")
    List<Order> findByMyOrders(@Param("email") String email);


}
