package in.madhu.ecommerce.app.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import in.madhu.ecommerce.app.dto.request.UpdateProfileRequest;
import in.madhu.ecommerce.app.dto.request.UpdateRoleRequest;
import in.madhu.ecommerce.app.dto.response.CustomerResponse;
import in.madhu.ecommerce.app.service.UserService;
import in.madhu.ecommerce.app.util.ApiResponse;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Validated
public class UserController {
	
	private final UserService userService;

    /**
     * Logged-in User APIs
     */

    @GetMapping("/api/v1/users/profile")
    public ResponseEntity<ApiResponse<CustomerResponse>> getProfile() {

    	CustomerResponse response = userService.getProfile();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Profile fetched successfully",
                        response));
    }

    @PutMapping("/api/v1/users/profile")
    public ResponseEntity<ApiResponse<CustomerResponse>> updateProfile(
            @Valid @RequestBody UpdateProfileRequest request) {

    	CustomerResponse response =
                userService.updateProfile(request);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Profile updated successfully",
                        response));
    }

    /**
     * Admin APIs
     */

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/api/v1/admin/users")
    public ResponseEntity<ApiResponse<List<CustomerResponse>>> getAllUsers() {

        List<CustomerResponse> response =
                userService.getAllUsers();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Users fetched successfully",
                        response));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/api/v1/admin/users/{id}/role")
    public ResponseEntity<ApiResponse<CustomerResponse>> updateRole(
            @PathVariable Long id,
            @Valid @RequestBody UpdateRoleRequest request) {

    	CustomerResponse response =
                userService.updateRole(id, request);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "User role updated successfully",
                        response));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/api/v1/admin/users/{id}")
    public ResponseEntity<ApiResponse<String>> deleteUser(
            @PathVariable Long id) {

        userService.deleteUser(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "User deleted successfully",
                        "SUCCESS"));
    }

}
