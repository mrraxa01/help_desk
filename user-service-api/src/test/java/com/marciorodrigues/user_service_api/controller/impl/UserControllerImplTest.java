package com.marciorodrigues.user_service_api.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marciorodrigues.user_service_api.entity.User;
import com.marciorodrigues.user_service_api.repository.UserRepository;
import models.requests.CreateUserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.marciorodrigues.user_service_api.service.creator.CreatorUtils.generatedMock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserControllerImplTest {

    public static final String BASE_URI = "/api/users";
    public static final String VALID_EMAIL = "validemailtest@mail.com";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository repository;

    @Test
    void testFindByIdWithValidId() throws Exception {


        final var entity= generatedMock(User.class);
        final var userID = repository.save(entity).getId();

        mockMvc.perform(get("/api/users/{id}", userID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userID))
                .andExpect(jsonPath("$.name").value(entity.getName()))
                .andExpect(jsonPath("$.email").value(entity.getEmail()))
                .andExpect(jsonPath("$.password").value(entity.getPassword()))
                .andExpect(jsonPath("$.profiles").isArray());

        repository.deleteById(userID);
    }

    @Test
    void testFindByIdWithNotFoundException() throws Exception {
        mockMvc.perform(get("/api/users/{id}", "123"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Object not found! ID: 123 Type: UserResponse"))
                .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.error").value(HttpStatus.NOT_FOUND.getReasonPhrase()))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.path").value("/api/users/123"));
    }

    @Test
    void testFindAllWithSuccess() throws Exception {
        final var entity1= generatedMock(User.class);
        final var entity2= generatedMock(User.class);
        repository.saveAll(List.of(entity1, entity2));

        mockMvc.perform(get(BASE_URI))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0]").isNotEmpty())
                .andExpect(jsonPath("$[1]").isNotEmpty());

        repository.deleteAll(List.of(entity1, entity2));
    }

    @Test
    void testSaveWithSuccess() throws Exception {

        final var request = generatedMock(CreateUserRequest.class).withEmail(VALID_EMAIL);

        mockMvc.perform(post(BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(request)))
                .andExpect(status().isCreated());

        repository.deleteByEmail(VALID_EMAIL);
    }

    @Test
    void testSaveUserWithConflict() throws Exception {

        final var entity = generatedMock(User.class).withEmail(VALID_EMAIL);
        repository.save(entity);

        final var request = generatedMock(CreateUserRequest.class).withEmail(entity.getEmail());

        mockMvc.perform(post(BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Email [" +request.email() + "] Already exists!"))
                .andExpect(jsonPath("$.status").value(HttpStatus.CONFLICT.value()))
                .andExpect(jsonPath("$.error").value(HttpStatus.CONFLICT.getReasonPhrase()))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.path").value(BASE_URI));

        repository.deleteByEmail(VALID_EMAIL);
    }

    @Test
    void testSaveUserWithNameEmptyThenThrowBadRequest() throws Exception {

        final var request = generatedMock(CreateUserRequest.class).withName("").withEmail(VALID_EMAIL);

        mockMvc.perform(post(BASE_URI)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJson(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Exception in validation attributes"))
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.error").value("Validation Exception"))
                .andExpect(jsonPath("$.errors[?(@.fieldName == 'name' && @.message == 'Name cannot be empty')]").exists())
                .andExpect(jsonPath("$.errors[?(@.fieldName == 'name' && @.message == 'Name must contain between 3 and 50 characters')]").exists())
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.path").value(BASE_URI));

    }

    private String toJson(final Object object) throws Exception {
        try
        {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new Exception("Error converting object to JSON", e);
        }
    }

}