package in.madhu.ecommerce.app.service;

import org.springframework.data.domain.Page;

import in.madhu.ecommerce.app.DTO.ProductRequest;
import in.madhu.ecommerce.app.DTO.ProductResponse;

public interface ProductService {
	
	ProductResponse addProduct(ProductRequest request);

    ProductResponse updateProduct(Long id, ProductRequest request);

    void deleteProduct(Long id);

    ProductResponse getProductById(Long id);

    Page<ProductResponse> getAllProducts(int page,
                                         int size,
                                         String sortBy,
                                         String sortDirection);

    Page<ProductResponse> searchProducts(
            String keyword,
            int page,
            int size,
            String sortBy,
            String sortDirection);

}
	

