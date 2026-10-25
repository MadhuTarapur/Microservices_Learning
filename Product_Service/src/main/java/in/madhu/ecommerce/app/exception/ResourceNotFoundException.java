package in.madhu.ecommerce.app.exception;

public class ResourceNotFoundException extends RuntimeException{
	
	public ResourceNotFoundException(String message) {
        super(message);
    }
	
//	GET /products/100
//	If product doesn't exist
//	throw new ResourceNotFoundException("Product not found with id : " + id);

}
