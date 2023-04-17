package bg.softuni.mywarehouse.web;

import bg.softuni.mywarehouse.domain.dtos.OrderDTO;
import bg.softuni.mywarehouse.domain.dtos.ProductDTO;
import bg.softuni.mywarehouse.domain.dtos.UserDTO;
import bg.softuni.mywarehouse.domain.dtos.UserRoleDTO;
import bg.softuni.mywarehouse.domain.entities.UserEntity;
import bg.softuni.mywarehouse.domain.request.UserRequest;
import bg.softuni.mywarehouse.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final UserService userService;

    private final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    @ResponseBody
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers().stream().map(this::createUserDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ResponseBody
    public UserDTO getUserById(@PathVariable Long id) {
        return createUserDTO(userService.getUserById(id));
    }

    @PostMapping
    @ResponseBody
    public UserDTO createUser(@RequestBody UserRequest user) {
        UserEntity userEntity = userService.createUser(user);

        return UserDTO.builder()
                .id(userEntity.getId())
                .email(userEntity.getEmail())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .address(userEntity.getAddress())
                .phoneNumber(userEntity.getPhoneNumber())
                .isActive(userEntity.getIsActive())
                .roles(userEntity.getRoles().stream().map(role -> UserRoleDTO.builder()
                        .userRole(role.getRole()).id(role.getId()).build()).collect(Collectors.toList()))
                .build();

    }

    private UserDTO createUserDTO(UserEntity user) {
        return UserDTO.builder()
                .email(user.getEmail())
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .isActive(user.getIsActive())
                .address(user.getAddress())
                .phoneNumber(user.getPhoneNumber())
                .orders(user.getOrders().stream().map(order -> OrderDTO.builder().orderDate(order.getOrderDate())
                        .isPaid(order.isPaid())
                        .isLoaded(order.isLoaded())
                        .totalPrice(order.getTotalPrice())
                        .id(order.getId())
                        .products(order.getProducts().stream().map(product -> ProductDTO.builder().brand(product.getBrand())
                                .price(product.getPrice()).size(product.getSize()).quantity(product.getQuantity()).name(product.getName())
                                .name(product.getName()).id(product.getId())
                                .build()).collect(Collectors.toList()))
                        .build()).collect(Collectors.toList()))
                .roles(user.getRoles().stream().map(role -> UserRoleDTO.builder()
                        .userRole(role.getRole()).id(role.getId()).build()).collect(Collectors.toList()))
                .build();
    }
}
