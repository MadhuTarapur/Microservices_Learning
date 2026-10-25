package in.madhu.ecommerce.app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import in.madhu.ecommerce.app.DTO.ProductRequest;
import in.madhu.ecommerce.app.DTO.ProductResponse;
import in.madhu.ecommerce.app.Entity.Product;
import in.madhu.ecommerce.app.Repository.ProductRepository;
import in.madhu.ecommerce.app.exception.DuplicateProductException;
import in.madhu.ecommerce.app.exception.ResourceNotFoundException;
import in.madhu.ecommerce.app.mapper.ProductMapper;

@Service
public class ProductServiceImpl implements ProductService {
	
	private final ProductRepository productRepository;
	
    private final ProductMapper productMapper;
	
	public ProductServiceImpl(ProductRepository productRepository,
            ProductMapper productMapper) {
		this.productRepository = productRepository;
		this.productMapper = productMapper;
}

	@Override
	public ProductResponse addProduct(ProductRequest request) {
		if (productRepository.existsByNameIgnoreCase(request.getName())) {
            throw new DuplicateProductException(
                    "Product already exists with name : " + request.getName());
        }

        Product product = productMapper.toEntity(request);

        Product savedProduct = productRepository.save(product);

        return productMapper.toResponse(savedProduct);
	}

	@Override
	public ProductResponse updateProduct(Long id, ProductRequest request) {
		Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found with id : " + id));

        Optional<Product> existing =
                productRepository.findByNameIgnoreCase(request.getName());

        if (existing.isPresent() 
        		&& !existing.get().getId().equals(id)) {

            throw new DuplicateProductException(
                    "Product already exists with name : "
                            + request.getName());
        }

        productMapper.updateEntity(request, product);

        Product updatedProduct = productRepository.save(product);

        return productMapper.toResponse(updatedProduct);
    }
	

	@Override
	public void deleteProduct(Long id) {
		Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found with id : " + id));

        productRepository.delete(product);
		
	}

	@Override
	public ProductResponse getProductById(Long id) {
		Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found with id : " + id));

        return productMapper.toResponse(product);
	}

	@Override
	public Page<ProductResponse> getAllProducts(int page, int size, String sortBy, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return productRepository.findAll(pageable)
                .map(productMapper::toResponse);
	}

	@Override
	public Page<ProductResponse> searchProducts(
	        String keyword,
	        int page,
	        int size,
	        String sortBy,
	        String sortDirection) {

	    Sort sort = sortDirection.equalsIgnoreCase("desc")
	            ? Sort.by(sortBy).descending()
	            : Sort.by(sortBy).ascending();

	    Pageable pageable = PageRequest.of(page, size, sort);

	    return productRepository
	            .findByNameContainingIgnoreCaseOrCategoryContainingIgnoreCase(
	                    keyword,
	                    keyword,
	                    pageable)
	            .map(productMapper::toResponse);
	}

}
