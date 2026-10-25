package in.madhu.ecommerce.app.util;

import java.time.LocalDateTime;

public class ApiResponse<T> {
	
	private boolean success;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    public ApiResponse() {
        this.timestamp = LocalDateTime.now();
    }

    public ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
    
    

}

//Sample Response
//
//Instead of returning
//
//{
//  "id": 1,
//  "name": "Laptop",
//  "price": 65000
//}
//
//We'll return
//
//{
//    "success": true,
//    "message": "Product created successfully",
//    "data": {
//        "id": 1,
//        "name": "Laptop",
//        "description": "Dell i7",
//        "price": 65000,
//        "category": "Electronics"
//    },
//    "timestamp": "2026-07-07T10:45:15"
//}
