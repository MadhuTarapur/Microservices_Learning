package in.madhu.ecommerce.app.Repository;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.madhu.ecommerce.app.Entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	
	Optional<Product> findByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);
    
    Page<Product> findByNameContainingIgnoreCaseOrCategoryContainingIgnoreCase(
    	    String name,
    	    String category,
    	    Pageable pageable
    	);
	

}
