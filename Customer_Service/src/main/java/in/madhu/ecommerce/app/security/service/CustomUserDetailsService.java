package in.madhu.ecommerce.app.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import in.madhu.ecommerce.app.entity.Customer;
import in.madhu.ecommerce.app.repository.CustomerRepository;
import in.madhu.ecommerce.app.security.model.CustomUserDetails;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
	
	private final CustomerRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        return repository.findByEmailIgnoreCase(email)

                .map(CustomUserDetails::new)

                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found"));
    }

}
