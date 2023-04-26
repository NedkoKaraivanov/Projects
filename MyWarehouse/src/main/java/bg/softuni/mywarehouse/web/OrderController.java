package bg.softuni.mywarehouse.web;

import bg.softuni.mywarehouse.domain.dtos.OrderDTO;
import bg.softuni.mywarehouse.domain.dtos.ProductDTO;
import bg.softuni.mywarehouse.domain.entities.OrderEntity;
import bg.softuni.mywarehouse.services.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/orders")
public class OrderController {

    private final OrderService orderService;

    private final ModelMapper modelMapper;

    public OrderController(OrderService orderService, ModelMapper modelMapper) {
        this.orderService = orderService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders().stream().map(this::createOrderDTO).collect(Collectors.toList()));
    }



    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO) {
        OrderEntity orderEntity = modelMapper.map(orderDTO, OrderEntity.class);
        orderEntity.setOrderDate(LocalDateTime.now());
        orderService.createOrder(orderEntity);

        return ResponseEntity.status(HttpStatus.CREATED).body(createOrderDTO(orderEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        OrderEntity existingOrder = orderService.getOrderById(id);
        if (existingOrder != null) {
            orderService.deleteOrder(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    private OrderDTO createOrderDTO(OrderEntity orderEntity) {
        return OrderDTO.builder()
                .id(orderEntity.getId())
                .orderTime(orderEntity.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm")))
                .isLoaded(orderEntity.isLoaded())
                .isPaid(orderEntity.isPaid())
                .products(orderEntity.getProducts().stream().map(p -> modelMapper.map(p, ProductDTO.class)).collect(Collectors.toList()))
                .build();
    }
}
