package bg.softuni.mywarehouse.services;

import bg.softuni.mywarehouse.domain.dtos.ProductDTO;
import bg.softuni.mywarehouse.domain.entities.ProductEntity;

import java.util.List;

public interface ProductService {

    List<ProductEntity> getAllProducts();

    ProductEntity createProduct(ProductEntity product);

    ProductEntity updateProduct(ProductEntity existingProduct, ProductDTO updatedProduct);

    void deleteProduct(Long id);

    ProductEntity getProductById(Long id);
}
