package com.marciorodrigues.user_service_api.service;

import com.marciorodrigues.user_service_api.entity.User;
import com.marciorodrigues.user_service_api.mapper.UserMapper;
import com.marciorodrigues.user_service_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import models.responses.UserResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    public UserResponse findById(final String id){
        return userMapper.fromEntity(userRepository.findById(id).orElse(null));

    }

}
