package com.marciorodrigues.auth_service_api.services;

import com.marciorodrigues.auth_service_api.repository.UserRepository;
import com.marciorodrigues.auth_service_api.security.dtos.UserDetailsDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;


@Log4j2
@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        log.info("Loading user by email: {}", email);
        final var entity = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
        log.info("User {} found in database", email);
        return UserDetailsDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .username(entity.getEmail())
                .password(entity.getPassword())
                .authorities(
                        entity.getProfiles().stream()
                        .map(profile -> new SimpleGrantedAuthority(profile.getDescription()))
                .collect(Collectors.toSet()))
                .build();
    }
}
