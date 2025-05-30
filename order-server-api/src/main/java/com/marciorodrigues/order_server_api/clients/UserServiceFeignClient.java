package com.marciorodrigues.order_server_api.clients;

import models.responses.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service-api",
        url = "${user.service.url:http://localhost:8080/api/users}")
public interface UserServiceFeignClient {

    @GetMapping("/{id}")
    ResponseEntity<UserResponse> findById(
            @PathVariable(name = "id") final String id);
}
