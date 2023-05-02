package bg.softuni.mywarehouse.web;

import bg.softuni.mywarehouse.domain.dtos.OrderDTO;
import bg.softuni.mywarehouse.domain.dtos.ProductDTO;
import bg.softuni.mywarehouse.domain.dtos.UserDTO;
import bg.softuni.mywarehouse.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value ="/api/client")
public class ClientController {

    private final ProductService productService;

    public ClientController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts().stream().map(productService::createProductDTO).collect(Collectors.toList()));
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderDTO>> getOwnOrders() {
        //TODO
        return null;
    }

    @PostMapping("/orders")
    public ResponseEntity<OrderDTO> createOrder() {
        //TODO
        return null;
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDTO> viewProfile() {
        //TODO
        return null;
    }

    @PutMapping("/profile")
    public ResponseEntity<UserDTO> updateProfile() {
        //TODO
        return null;
    }
}
