package com.marciorodrigues.user_service_api.service;

import com.marciorodrigues.user_service_api.entity.User;
import com.marciorodrigues.user_service_api.mapper.UserMapper;
import com.marciorodrigues.user_service_api.repository.UserRepository;
import models.exceptions.ResourceNotFoundExceptions;
import models.requests.CreateUserRequest;
import models.responses.UserResponse;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

import static com.marciorodrigues.user_service_api.service.creator.CreatorUtils.generatedMock;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {
    @InjectMocks
    private UserService service;
    @Mock
    private UserRepository repository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private BCryptPasswordEncoder encoder;

    @Test
    void whenCallFindByIdWithValidIdThenReturnUserResponse(){
        Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Optional.of(new User()));
        Mockito.when(userMapper.fromEntity(ArgumentMatchers.any(User.class))).thenReturn(generatedMock(UserResponse.class));
        final var response = service.findById("1");
        assertNotNull(response);
        assertEquals(UserResponse.class, response.getClass());
        Mockito.verify(repository, Mockito.times(1)).findById(Mockito.anyString());
        Mockito.verify(userMapper, Mockito.times(1)).fromEntity(ArgumentMatchers.any());
    }

    @Test
    void whenCallByFindByIdWithInvalidIdThenThrowResourceNotFoundException(){
        Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Optional.empty());

        try {
            service.findById("1");
        }catch (Exception e){
            assertEquals(ResourceNotFoundExceptions.class, e.getClass());
            assertEquals("Object not found! ID: 1Type: UserResponse", e.getMessage());
        }

        Mockito.verify(repository, Mockito.times(1)).findById(Mockito.anyString());
        Mockito.verify(userMapper, Mockito.times(0)).fromEntity(ArgumentMatchers.any(User.class));
    }

    @Test
    void whenCallFindAllThenReturnListOfUserResponse(){
        Mockito.when(repository.findAll()).thenReturn(List.of(new User(), new User()));
        Mockito.when(userMapper.fromEntity(ArgumentMatchers.any(User.class))).thenReturn(generatedMock(UserResponse.class));
        final var response = service.findByAll();
        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals(UserResponse.class, response.get(0).getClass());
        Mockito.verify(repository, Mockito.times(1)).findAll();
        Mockito.verify(userMapper, Mockito.times(2)).fromEntity(ArgumentMatchers.any(User.class));
    }

    @Test
    void whenCallSaveThenSuccess(){
        final var request = generatedMock(CreateUserRequest.class);

        Mockito.when(userMapper.fromRequest(ArgumentMatchers.any())).thenReturn(new User());
        Mockito.when(encoder.encode(Mockito.anyString())).thenReturn("encoded");
        Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(new User());
        Mockito.when(repository.findByEmail(request.email())).thenReturn(Optional.empty());

        service.save(request);

        Mockito.verify(userMapper).fromRequest(request);
        Mockito.verify(encoder).encode(request.password());
        Mockito.verify(repository).save(ArgumentMatchers.any(User.class));
        Mockito.verify(repository).findByEmail(request.email());
    }

    @Test
    void whenCallSaveWithExistingEmailThenThrowDataIntegrityViolationException() {
        final var request = generatedMock(CreateUserRequest.class);
        final var entity = generatedMock(User.class);
        Mockito.when(repository.findByEmail(ArgumentMatchers.anyString())).thenReturn(Optional.of(entity));

        try {
            service.save(request);
        } catch (Exception e) {
            assertEquals(DataIntegrityViolationException.class, e.getClass());
            assertEquals("Email [" +request.email() + "] Already exists!", e.getMessage());
        }
        Mockito.verify(repository).findByEmail(request.email());
        Mockito.verify(userMapper, Mockito.times(0)).fromRequest(request);
        Mockito.verify(encoder, Mockito.times(0)).encode(request.password());
        Mockito.verify(repository, Mockito.times(0)).save(ArgumentMatchers.any(User.class));
    }}
