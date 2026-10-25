package in.madhu.ecommerce.app.security.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import in.madhu.ecommerce.app.entity.Customer;
import in.madhu.ecommerce.app.exception.ResourceNotFoundException;
import in.madhu.ecommerce.app.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CurrentUserService {
	
	private final CustomerRepository userRepository;

    public Customer getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext()
                        .getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated()) {

            throw new ResourceNotFoundException(
                    "Authenticated user not found");
        }

        String email = authentication.getName();

        return userRepository
                .findByEmailIgnoreCaseAndActiveTrue(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"));
    }

}
