package in.madhu.ecommerce.app.service;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.madhu.ecommerce.app.dto.request.LoginRequest;
import in.madhu.ecommerce.app.dto.request.RegisterRequest;
import in.madhu.ecommerce.app.dto.request.UpdateProfileRequest;
import in.madhu.ecommerce.app.dto.request.UpdateRoleRequest;
import in.madhu.ecommerce.app.dto.response.CustomerResponse;
import in.madhu.ecommerce.app.dto.response.LoginResponse;
import in.madhu.ecommerce.app.entity.Customer;
import in.madhu.ecommerce.app.enums.Role;
import in.madhu.ecommerce.app.exception.ResourceNotFoundException;
import in.madhu.ecommerce.app.exception.UserAlreadyExistsException;
import in.madhu.ecommerce.app.mapper.CustomerMapper;
import in.madhu.ecommerce.app.repository.CustomerRepository;
import in.madhu.ecommerce.app.security.jwt.JwtService;
import in.madhu.ecommerce.app.security.model.CustomUserDetails;
import in.madhu.ecommerce.app.security.service.CurrentUserService;
//import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
	
	private final CustomerRepository repository;

    private final CustomerMapper mapper;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final CurrentUserService currentUserService;
    
    @Override
    public CustomerResponse register(RegisterRequest request) {

        if (repository.existsByEmailIgnoreCase(request.getEmail())) {
            throw new UserAlreadyExistsException(
                    "Email already registered.");
        }

        Customer customer = mapper.toEntity(request);

        customer.setPassword(
                passwordEncoder.encode(request.getPassword()));

        customer.setRole(Role.ROLE_USER);

        customer.setEnabled(true);

        customer.setActive(true);

        Customer savedUser = repository.save(customer);

        return mapper.toResponse(savedUser);
    }
    
    /**
     * Login User
     */
    @Override
    public LoginResponse login(LoginRequest request) {

        Authentication authentication =
                authenticationManager.authenticate(

                        new UsernamePasswordAuthenticationToken(

                                request.getEmail(),

                                request.getPassword()));

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        Customer user = userDetails.getUser();

        String token = jwtService.generateToken(user);

        return LoginResponse.builder()
                .token(token)
                .tokenType("Bearer")
                .userId(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }
    
    /**
     * Get Logged-in User Profile
     */
    @Override
    @Transactional(readOnly = true)
    public CustomerResponse getProfile() {

        Customer currentUser = currentUserService.getCurrentUser();

        return mapper.toResponse(currentUser);
    }
    
    /**
     * Update Logged-in User Profile
     */
    @Override
    public CustomerResponse updateProfile(UpdateProfileRequest request) {

        Customer currentUser = currentUserService.getCurrentUser();

        mapper.updateProfile(request, currentUser);

        Customer updatedUser = repository.save(currentUser);

        return mapper.toResponse(updatedUser);
    }
    
    /**
     * Get All Users (Admin Only)
     */
    @Override
    @Transactional(readOnly = true)
    public List<CustomerResponse> getAllUsers() {

        return repository.findAll()
                .stream()
                .filter(Customer::getActive)
                .map(mapper::toResponse)
                .toList();
    }
    
    /**
     * Update User Role (Admin Only)
     */
    @Override
    public CustomerResponse updateRole(Long userId, UpdateRoleRequest request) {

        Customer user = findUserById(userId);

        // Prevent changing the role of the last admin
        if (user.getRole() == Role.ROLE_ADMIN
                && request.getRole() == Role.ROLE_USER
                && repository.countByRole(Role.ROLE_ADMIN) == 1) {

            throw new IllegalStateException(
                    "Cannot change the role of the last administrator.");
        }

        user.setRole(request.getRole());

        Customer updatedUser = repository.save(user);

        return mapper.toResponse(updatedUser);
    }
    
    /**
     * Soft Delete User (Admin Only)
     */
    @Override
    public void deleteUser(Long userId) {

        Customer user = findUserById(userId);

        // Prevent deleting the last admin
        if (user.getRole() == Role.ROLE_ADMIN
                && repository.countByRole(Role.ROLE_ADMIN) == 1) {

            throw new IllegalStateException(
                    "Cannot delete the last administrator.");
        }

        user.setActive(false);

        repository.save(user);
    }
    
    /**
     * Find User By Id
     */
    private Customer findUserById(Long userId) {

        return repository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with id : " + userId));
    }

}



