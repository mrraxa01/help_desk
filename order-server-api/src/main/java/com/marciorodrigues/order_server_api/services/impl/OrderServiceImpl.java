package com.marciorodrigues.order_server_api.services.impl;

import com.marciorodrigues.order_server_api.mapper.OrderMapper;
import com.marciorodrigues.order_server_api.repositories.OrderRepository;
import com.marciorodrigues.order_server_api.services.OrderService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import models.requests.CreateOrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public void save(CreateOrderRequest createOrderRequest) {
        orderRepository.save(orderMapper.fromRequest(createOrderRequest));
    }
}
