package bg.softuni.mywarehouse.repositories;

import bg.softuni.mywarehouse.domain.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    List<OrderEntity> findAllByUserId(Long userId);

    List<OrderEntity> findAllByIsPaid(boolean paid);

    List<OrderEntity> findAllByIsLoaded(boolean loaded);

    List<OrderEntity> findAllByUserIdAndTotalPriceGreaterThanEqual(Long userId, double price);
}
