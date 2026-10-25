package in.madhu.ecommerce.app.service;

import java.util.List;

import in.madhu.ecommerce.app.dto.request.LoginRequest;
import in.madhu.ecommerce.app.dto.request.RegisterRequest;
import in.madhu.ecommerce.app.dto.request.UpdateProfileRequest;
import in.madhu.ecommerce.app.dto.request.UpdateRoleRequest;
import in.madhu.ecommerce.app.dto.response.CustomerResponse;
import in.madhu.ecommerce.app.dto.response.LoginResponse;

public interface UserService {
	
	CustomerResponse register(RegisterRequest request);

    LoginResponse login(LoginRequest request);

    CustomerResponse getProfile();

    CustomerResponse updateProfile(UpdateProfileRequest request);

    List<CustomerResponse> getAllUsers();

    CustomerResponse updateRole(Long userId,
                            UpdateRoleRequest request);

    void deleteUser(Long userId);

}
