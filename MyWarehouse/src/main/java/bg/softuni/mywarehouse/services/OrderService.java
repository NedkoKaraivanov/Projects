package bg.softuni.mywarehouse.services;

import bg.softuni.mywarehouse.domain.dtos.OrderDTO;
import bg.softuni.mywarehouse.domain.entities.OrderEntity;

import java.util.List;

public interface OrderService {

    List<OrderEntity> getAllOrders();

    List<OrderEntity> getOrderAllOrdersForUser(Long userId);

    List<OrderEntity> getAllLoadedOrders(boolean loaded);

    List<OrderEntity> getAllPaidOrders(boolean paid);

    List<OrderEntity> getAllOrdersForUserWithTotalPriceHigherOrEqual(Long userId, double totalPrice);

    OrderEntity getOrderById(Long id);

    OrderEntity createOrder(OrderEntity order);

    void updateOrder(OrderEntity order);

    void deleteOrder(Long id);

    OrderDTO createOrderDTO(OrderEntity orderEntity);
}
