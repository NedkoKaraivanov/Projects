package bg.softuni.mywarehouse.web;

import bg.softuni.mywarehouse.domain.dtos.ProductDTO;
import bg.softuni.mywarehouse.domain.entities.ProductEntity;
import bg.softuni.mywarehouse.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/admin/products")
public class ProductController {

    private final ProductService productService;

    private final ModelMapper modelMapper;

    public ProductController(ProductService productService, ModelMapper modelMapper) {
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts().stream().map(productService::createProductDTO).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id) {
        ProductEntity productEntity = productService.getProductById(id);

        if (productEntity != null) {
            return ResponseEntity.ok(productService.createProductDTO(productEntity));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
        ProductEntity productEntity = modelMapper.map(productDTO, ProductEntity.class);
        productService.createProduct(productEntity);

        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProductDTO(productEntity));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        ProductEntity existingProduct = productService.getProductById(id);

        if (existingProduct != null) {
            productService.updateProduct(existingProduct, productDTO);
            return ResponseEntity.ok(productService.createProductDTO(existingProduct));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        ProductEntity existingProduct = productService.getProductById(id);
        if (existingProduct != null) {
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
