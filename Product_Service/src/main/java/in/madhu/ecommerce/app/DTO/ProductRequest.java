package in.madhu.ecommerce.app.DTO;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

//Receives data from the client/ Used for Create/Update APIs
public class ProductRequest { 

	@NotBlank(message = "Product name is required")
	@Size(min = 3, max = 100,
    message = "Product name must be between 3 and 100 characters")
	private String name;
	
	@Size(max = 500,
	          message = "Description cannot exceed 500 characters")
	private String description;
	
	@NotNull(message = "Price is required")
	@DecimalMin(value = "0.01",
    message = "Price should be greater than zero")
	private BigDecimal price;
	
	@NotBlank(message = "Category is required")
	@Size(max = 100)
    private String category;
	
	public ProductRequest() {
		
    }

    public ProductRequest(String name, String description, BigDecimal price, String category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
    
    
}
