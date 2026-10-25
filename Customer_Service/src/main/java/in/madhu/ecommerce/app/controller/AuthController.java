package in.madhu.ecommerce.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.madhu.ecommerce.app.dto.request.LoginRequest;
import in.madhu.ecommerce.app.dto.request.RegisterRequest;
import in.madhu.ecommerce.app.dto.response.CustomerResponse;
import in.madhu.ecommerce.app.dto.response.LoginResponse;
import in.madhu.ecommerce.app.service.UserService;
import in.madhu.ecommerce.app.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {
	
	private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<CustomerResponse>> register(
            @Valid @RequestBody RegisterRequest request) {

    	CustomerResponse response = userService.register(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(
                        true,
                        "User registered successfully",
                        response));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @Valid @RequestBody LoginRequest request) {

        LoginResponse response = userService.login(request);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Login successful",
                        response));
    }

}
