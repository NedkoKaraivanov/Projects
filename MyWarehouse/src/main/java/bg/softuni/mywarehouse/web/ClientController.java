package bg.softuni.mywarehouse.web;

import bg.softuni.mywarehouse.domain.dtos.OrderDTO;
import bg.softuni.mywarehouse.domain.dtos.ProductDTO;
import bg.softuni.mywarehouse.domain.dtos.UserDTO;
import bg.softuni.mywarehouse.domain.entities.OrderEntity;
import bg.softuni.mywarehouse.domain.entities.UserEntity;
import bg.softuni.mywarehouse.domain.request.UserRequest;
import bg.softuni.mywarehouse.services.OrderService;
import bg.softuni.mywarehouse.services.ProductService;
import bg.softuni.mywarehouse.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/client")
@RequiredArgsConstructor
public class ClientController {

    private final ProductService productService;

    private final OrderService orderService;

    private final UserService userService;

    private final ModelMapper modelMapper;


    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts()
                .stream()
                .map(productService::createProductDTO)
                .collect(Collectors.toList()));
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderDTO>> getOwnOrders(Principal principal) {
        Long id = getUserId(principal);
        List<OrderEntity> allOrdersForUser = orderService.getOrderAllOrdersForUser(id);
        if (allOrdersForUser != null) {
            return ResponseEntity.ok(allOrdersForUser
                    .stream()
                    .map(orderService::createOrderDTO)
                    .collect(Collectors.toList()));
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping("/orders")
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO) {
        OrderEntity orderEntity = modelMapper.map(orderDTO, OrderEntity.class);
        orderEntity.setOrderDate(LocalDateTime.now());
        orderService.createOrder(orderEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrderDTO(orderEntity));
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDTO> viewProfile(Principal principal) {
        Long id = getUserId(principal);
        UserEntity user = userService.getUserById(id);
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        return ResponseEntity.ok(userDTO);
    }

    @PutMapping("/profile")
    public ResponseEntity<UserDTO> updateProfile(@RequestBody UserRequest userRequest) {
        UserEntity existingUser = userService.getUserById(userRequest.getId());
        UserEntity updatedUser = userService.updateUser(existingUser, userRequest);
        return ResponseEntity.ok(userService.createUserDTO(updatedUser));
    }

    private long getUserId(Principal principal) {
        String email = principal.getName();
        UserEntity user = userService.getUserByEmail(email);

        return user.getId();
    }
}
