package com.marciorodrigues.order_server_api.repositories;

import com.marciorodrigues.order_server_api.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
