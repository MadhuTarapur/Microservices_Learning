package in.madhu.ecommerce.app.exception;

public class DuplicateProductException extends RuntimeException {
	
	public DuplicateProductException(String message) {
		super(message);
		
	}
	
//	Trying to create
//	Laptop
//	Again
//	↓
//	Throw
//	DuplicateProductException
//	instead of allowing duplicate products.

}
