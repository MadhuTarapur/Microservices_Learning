package in.madhu.ecommerce.app.mapper;

import org.springframework.stereotype.Component;

import in.madhu.ecommerce.app.dto.request.RegisterRequest;
import in.madhu.ecommerce.app.dto.request.UpdateProfileRequest;
import in.madhu.ecommerce.app.dto.response.CustomerResponse;
import in.madhu.ecommerce.app.entity.Customer;

@Component
public class CustomerMapper {
	
	public Customer toEntity(RegisterRequest request) {

        Customer customer = Customer.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .build();

        return customer;
    }
	
	public CustomerResponse toResponse(Customer customer) {

        return CustomerResponse.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .role(customer.getRole().name())
                .enabled(customer.getEnabled())
                .active(customer.getActive())
                .createdAt(customer.getCreatedAt())
                .updatedAt(customer.getUpdatedAt())
                .build();
    }
	
	public void updateProfile(UpdateProfileRequest request,
			Customer customer) {

		customer.setFirstName(request.getFirstName());
		customer.setLastName(request.getLastName());
}

}
