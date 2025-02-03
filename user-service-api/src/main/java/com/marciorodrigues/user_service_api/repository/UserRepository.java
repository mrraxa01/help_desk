package com.marciorodrigues.user_service_api.repository;

import com.marciorodrigues.user_service_api.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail (final String email);

    void deleteByEmail(final String validEmail);
}
