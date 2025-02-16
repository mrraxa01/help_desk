package services;

import com.marciorodrigues.auth_service_api.repository.UserRepository;
import com.marciorodrigues.auth_service_api.security.dtos.UserDetailsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final var entity = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
        return UserDetailsDTO.builder()
                .id(entity.getId())
                .username(entity.getEmail())
                .password(entity.getPassword())
                .authorities(
                        entity.getProfiles().stream()
                        .map(profile -> new SimpleGrantedAuthority(profile.getDescription()))
                .collect(Collectors.toSet()))
                .build();
    }
}
