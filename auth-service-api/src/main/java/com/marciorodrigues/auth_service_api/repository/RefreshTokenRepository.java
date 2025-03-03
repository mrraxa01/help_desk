package com.marciorodrigues.auth_service_api.repository;

import com.marciorodrigues.auth_service_api.models.RefreshToken;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends MongoRepository<RefreshToken, String> {

}
