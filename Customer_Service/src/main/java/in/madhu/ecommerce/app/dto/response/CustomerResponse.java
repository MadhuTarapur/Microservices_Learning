package in.madhu.ecommerce.app.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CustomerResponse {
	
	private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String role;

    private Boolean enabled;

    private Boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    
}
