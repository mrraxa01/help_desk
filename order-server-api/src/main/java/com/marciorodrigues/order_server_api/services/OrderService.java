package com.marciorodrigues.order_server_api.services;

import models.requests.CreateOrderRequest;

public interface OrderService {

    void save(CreateOrderRequest createOrderRequest);

}
