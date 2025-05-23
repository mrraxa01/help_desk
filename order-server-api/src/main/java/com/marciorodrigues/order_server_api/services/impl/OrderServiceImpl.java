package com.marciorodrigues.order_server_api.services.impl;

import com.marciorodrigues.order_server_api.entities.Order;
import com.marciorodrigues.order_server_api.mapper.OrderMapper;
import com.marciorodrigues.order_server_api.repositories.OrderRepository;
import com.marciorodrigues.order_server_api.services.OrderService;
import lombok.RequiredArgsConstructor;
import models.enums.OrderStatusEnum;
import models.exceptions.ResourceNotFoundExceptions;
import models.requests.CreateOrderRequest;
import models.requests.UpdateOrderRequest;
import models.responses.OrderResponse;
import org.springframework.stereotype.Service;

import static java.time.LocalDateTime.now;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public Order findById(final Long orderId) {
        return orderRepository
                .findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundExceptions(
                        "Object not found. Id : " + orderId + ", Type: " + Order.class.getSimpleName()
                ));
    }

    @Override
    public void save(CreateOrderRequest createOrderRequest) {
        orderRepository.save(orderMapper.fromRequest(createOrderRequest));
    }

    @Override
    public OrderResponse update(Long orderId, UpdateOrderRequest updateOrderRequest) {
        Order entity = findById(orderId);
        entity = orderMapper.fromRequest(entity, updateOrderRequest);

        if (updateOrderRequest.status() != null &&
                updateOrderRequest.status().equals(OrderStatusEnum.CLOSED.getDescription()))
            entity.setClosedAt(now());


        return orderMapper.fromEntity(orderRepository.save(entity));
    }

    @Override
    public void delete(final Long id) {
        orderRepository.delete(findById(id));
    }


}
