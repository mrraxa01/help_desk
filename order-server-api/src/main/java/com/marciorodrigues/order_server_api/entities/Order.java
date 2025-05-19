package com.marciorodrigues.order_server_api.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import models.enums.OrderStatusEnum;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;
import static models.enums.OrderStatusEnum.OPEN;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tb_order")
public class Order implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 45)
    private String requesterId;

    @Column(nullable = false, length = 45)
    private String customerId;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false, length = 3000)
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatusEnum status = OPEN;

    private LocalDateTime createdAt = now();
    private LocalDateTime closedAt;


}
