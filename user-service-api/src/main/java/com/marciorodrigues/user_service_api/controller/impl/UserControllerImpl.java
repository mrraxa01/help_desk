package com.marciorodrigues.user_service_api.controller.impl;

import com.marciorodrigues.user_service_api.controller.UserController;
import com.marciorodrigues.user_service_api.entity.User;
import com.marciorodrigues.user_service_api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserService userService;
    @Override
    public ResponseEntity<User> findById(String id) {
        return ResponseEntity.ok().body(userService.findById(id));
    }
}
