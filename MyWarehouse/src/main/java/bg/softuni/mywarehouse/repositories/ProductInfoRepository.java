package bg.softuni.mywarehouse.repositories;

import bg.softuni.mywarehouse.domain.entities.ProductInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductInfoRepository extends JpaRepository<ProductInfoEntity, Long> {
}
