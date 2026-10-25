package in.madhu.ecommerce.app.security.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
	
	private String secret;

    private long expiration;

}
