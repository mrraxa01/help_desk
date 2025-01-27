package com.marciorodrigues.user_service_api.service;

import com.marciorodrigues.user_service_api.entity.User;
import com.marciorodrigues.user_service_api.mapper.UserMapper;
import com.marciorodrigues.user_service_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import models.exceptions.ResourceNotFoundExceptions;
import models.requests.CreateUserRequest;
import models.requests.UpdateUserRequest;
import models.responses.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private  UserMapper userMapper;


    public UserResponse findById(final String id){
        return userMapper.fromEntity(find(id));
    }

    public List<UserResponse> findByAll(){
        return userRepository.findAll()
                .stream().map(userMapper::fromEntity)
                .toList();
    }

    public void save(CreateUserRequest createUserRequest) {
        verifyIfEmailAlreadyExists(createUserRequest.email(), null);
        userRepository.save(userMapper.fromRequest(createUserRequest));
    }

    public UserResponse update(final String id, UpdateUserRequest updateUserRequest){
        User entity = find(id);
        verifyIfEmailAlreadyExists(updateUserRequest.email(), id);
        return userMapper.fromEntity(userRepository.save(userMapper.update(updateUserRequest, entity)));
    }

    private User find(final String id){
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptions(
                        "Object not found! ID: " + id + "Type: " + UserResponse.class.getSimpleName()
                ));
    }
    private void verifyIfEmailAlreadyExists (final String email, final String id){
        userRepository.findByEmail(email)
                .filter(user -> !user.getId().equals(id))
                .ifPresent(user ->{throw new DataIntegrityViolationException("Email [" +email + "] Already exists!");}
                    );
    }
}
