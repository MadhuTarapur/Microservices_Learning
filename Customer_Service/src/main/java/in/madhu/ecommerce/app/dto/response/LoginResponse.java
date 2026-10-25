package in.madhu.ecommerce.app.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@AllArgsConstructor
public class LoginResponse {
	
	private String token;

    private String tokenType;

    private Long userId;

    private String firstName;

    private String lastName;

    private String email;

    private String role;



}
