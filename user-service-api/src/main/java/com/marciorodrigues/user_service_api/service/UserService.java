package com.marciorodrigues.user_service_api.service;

import com.marciorodrigues.user_service_api.mapper.UserMapper;
import com.marciorodrigues.user_service_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import models.exceptions.ResourceNotFoundExceptions;
import models.requests.CreateUserRequest;
import models.responses.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private  UserMapper userMapper;



    public UserResponse findById(final String id){
        return userMapper.fromEntity(userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptions(
                        "Object not found! ID: " + id + "Type: " + UserResponse.class.getSimpleName()
                )));

    }

    public void save(CreateUserRequest createUserRequest) {
        userRepository.save(userMapper.fromRequest(createUserRequest));
    }
}
