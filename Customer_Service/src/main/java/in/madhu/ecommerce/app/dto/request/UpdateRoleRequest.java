package in.madhu.ecommerce.app.dto.request;

import in.madhu.ecommerce.app.enums.Role;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateRoleRequest {
	
	@NotNull(message = "Role is required")
    private Role role;

}
