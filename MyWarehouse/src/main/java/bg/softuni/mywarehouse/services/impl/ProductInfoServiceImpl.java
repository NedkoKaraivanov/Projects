package bg.softuni.mywarehouse.services.impl;

import bg.softuni.mywarehouse.repositories.ProductInfoRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductInfoServiceImpl {

    private final ProductInfoRepository productInfoRepository;

    public ProductInfoServiceImpl(ProductInfoRepository productInfoRepository) {
        this.productInfoRepository = productInfoRepository;
    }
}
