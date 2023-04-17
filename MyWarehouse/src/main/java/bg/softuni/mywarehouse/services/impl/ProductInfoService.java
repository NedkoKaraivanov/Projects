package bg.softuni.mywarehouse.services.impl;

import bg.softuni.mywarehouse.repositories.ProductInfoRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductInfoService {

    private final ProductInfoRepository productInfoRepository;

    public ProductInfoService(ProductInfoRepository productInfoRepository) {
        this.productInfoRepository = productInfoRepository;
    }
}
