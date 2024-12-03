package com.marciorodrigues.user_service_api.service;

import com.marciorodrigues.user_service_api.entity.User;
import com.marciorodrigues.user_service_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findById(final String id){
        System.out.println("Service" + id);
        return userRepository.findById(id).orElseThrow(null);
    }

}
