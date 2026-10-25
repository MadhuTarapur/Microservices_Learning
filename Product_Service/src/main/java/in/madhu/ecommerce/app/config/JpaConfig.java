package in.madhu.ecommerce.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class JpaConfig {
	
	//This enables Hibernate to automatically populate

//createdAt
//updatedAt
//without writing any code.

}
