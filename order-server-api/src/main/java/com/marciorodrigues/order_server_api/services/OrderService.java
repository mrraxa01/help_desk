package com.marciorodrigues.order_server_api.services;

import com.marciorodrigues.order_server_api.entities.Order;
import models.requests.CreateOrderRequest;
import models.requests.UpdateOrderRequest;
import models.responses.OrderResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderService {


    Order findById(final Long orderId);
    List<Order> findAll();
    void save(CreateOrderRequest createOrderRequest);

    OrderResponse update(Long id, UpdateOrderRequest updateOrderRequest);

    void delete(Long id);


    Page<Order> findAllPaginated(Integer page, Integer size, String direction, String orderBy);
}
