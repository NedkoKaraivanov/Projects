package bg.softuni.mywarehouse.services.impl;

import bg.softuni.mywarehouse.domain.entities.ProductEntity;
import bg.softuni.mywarehouse.repositories.ProductRepository;
import bg.softuni.mywarehouse.services.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductEntity> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public ProductEntity createProduct(ProductEntity product) {
        return productRepository.save(product);
    }

    @Override
    public void updateProduct(ProductEntity product) {
        productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
