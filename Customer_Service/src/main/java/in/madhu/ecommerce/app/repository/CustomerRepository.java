package in.madhu.ecommerce.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import in.madhu.ecommerce.app.entity.Customer;
import in.madhu.ecommerce.app.enums.Role;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	
	Optional<Customer> findByEmailIgnoreCase(String email);
	
	Optional<Customer> findByEmailIgnoreCaseAndActiveTrue(String email);

    boolean existsByEmailIgnoreCase(String email);

    long countByRole(Role role);


}
