package bg.softuni.mywarehouse.services;

import bg.softuni.mywarehouse.domain.entities.ProductEntity;

import java.util.List;

public interface ProductService {

    List<ProductEntity> getAllProducts();

    ProductEntity createProduct(ProductEntity product);

    void updateProduct(ProductEntity product);

    void deleteProduct(Long id);
}
