package in.madhu.ecommerce.app.mapper;

import org.springframework.stereotype.Component;

import in.madhu.ecommerce.app.DTO.ProductRequest;
import in.madhu.ecommerce.app.DTO.ProductResponse;
import in.madhu.ecommerce.app.Entity.Product;

@Component
//converts DTO to entity

//Why create a Mapper?
//Without a mapper, our service becomes cluttered with repetitive conversion code.
//Instead of this inside every service method:

public class ProductMapper {
	
	public Product toEntity(ProductRequest request) {

        Product product = new Product();

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setCategory(request.getCategory());

        return product;
    }
	
	public ProductResponse toResponse(Product product) {

        ProductResponse response = new ProductResponse();

        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setCategory(product.getCategory());
        response.setCreatedAt(product.getCreatedAt());
        response.setUpdatedAt(product.getUpdatedAt());

        return response;
    }
	
	public void updateEntity(ProductRequest request, Product product) {

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setCategory(request.getCategory());
    }

}
