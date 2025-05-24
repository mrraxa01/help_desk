package com.marciorodrigues.order_server_api.controllers.impl;

import com.marciorodrigues.order_server_api.controllers.OrderController;
import com.marciorodrigues.order_server_api.mapper.OrderMapper;
import com.marciorodrigues.order_server_api.services.OrderService;
import lombok.RequiredArgsConstructor;
import models.requests.CreateOrderRequest;
import models.requests.UpdateOrderRequest;
import models.responses.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;


@RestController
@RequiredArgsConstructor
public class OrderControllerImpl implements OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @Override
    public ResponseEntity<OrderResponse> findById(Long orderId) {
        return ResponseEntity.ok().body(orderMapper.fromEntity(orderService.findById(orderId)));
    }

    @Override
    public ResponseEntity<List<OrderResponse>> findAll() {
        return ResponseEntity.ok().body(orderMapper.fromEntities(orderService.findAll()));
    }

    @Override
    public ResponseEntity<Void> save(CreateOrderRequest createOrderRequest) {
        orderService.save(createOrderRequest);
        return ResponseEntity.status(CREATED).build();
    }

    @Override
    public ResponseEntity<OrderResponse> update(final Long orderId, UpdateOrderRequest updateOrderRequest) {
        return ResponseEntity.ok().body(orderService.update(orderId, updateOrderRequest));
    }

    @Override
    public ResponseEntity<Void> delete(Long orderId) {
        orderService.delete(orderId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Page<OrderResponse>> findAllPaginated(Integer page, Integer linesPerPage, String direction, String orderBy) {
        return ResponseEntity.ok().body(
                orderService.findAllPaginated(page, linesPerPage, direction, orderBy).map(orderMapper::fromEntity));
    }
}
