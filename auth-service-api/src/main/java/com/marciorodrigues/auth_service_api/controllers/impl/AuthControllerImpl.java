package com.marciorodrigues.auth_service_api.controllers.impl;
import com.marciorodrigues.auth_service_api.controllers.AuthController;
import com.marciorodrigues.auth_service_api.repository.UserRepository;
import com.marciorodrigues.auth_service_api.security.JWTAuthenticationImpl;
import com.marciorodrigues.auth_service_api.utils.JWTUtils;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import models.requests.AuthenticateRequest;
import models.responses.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

@RestController
@Log4j2
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final JWTUtils jwtUtils;
//    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JWTAuthenticationImpl jwtAuthentication;
    private final AuthenticationConfiguration  authenticationConfiguration;
//    @PostConstruct
//    public void init() {
//        log.info("authenticationManager após inicialização: {}", authenticationManager);
//    }
    @Override
    public ResponseEntity<AuthenticationResponse> authenticate(final AuthenticateRequest request) throws Exception {
        log.info("Recebendo requisição de login para: {}", request.email());
 //       log.info("authenticationManager no AuthController: {}", authenticationManager);

//        return ResponseEntity.ok().body(
//                jwtAuthentication
//                        .authenticate(request, authenticationManager));
        return ResponseEntity.ok().body(
                new JWTAuthenticationImpl(authenticationConfiguration.getAuthenticationManager(),jwtUtils).authenticate(request) );
    }


}
