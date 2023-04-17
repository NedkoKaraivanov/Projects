package bg.softuni.mywarehouse.services.impl;

import bg.softuni.mywarehouse.domain.entities.OrderEntity;
import bg.softuni.mywarehouse.repositories.OrderRepository;
import bg.softuni.mywarehouse.services.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<OrderEntity> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public List<OrderEntity> getOrderAllOrdersForUser(Long userId) {
        return orderRepository.findAllByUserId(userId);
    }

    @Override
    public List<OrderEntity> getAllLoadedOrders(boolean loaded) {
        return orderRepository.findAllByIsLoaded(loaded);
    }

    @Override
    public List<OrderEntity> getAllPaidOrders(boolean isPaid) {
        return orderRepository.findAllByIsPaid(isPaid);
    }

    @Override
    public List<OrderEntity> getAllOrdersForUserWithTotalPriceHigherOrEqual(Long userId, double totalPrice) {
        return orderRepository.findAllByUserIdAndTotalPriceGreaterThanEqual(userId, totalPrice);
    }

    @Override
    public OrderEntity getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public OrderEntity createOrder(OrderEntity order) {
        return orderRepository.save(order);
    }

    @Override
    public void updateOrder(OrderEntity order) {
        orderRepository.save(order);
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
