package in.madhu.ecommerce.app.security.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import in.madhu.ecommerce.app.entity.Customer;

public class CustomUserDetails implements UserDetails {
	
	private final Customer customer;

    public CustomUserDetails(Customer customer) {
        this.customer = customer;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return List.of(
                new SimpleGrantedAuthority(
                		customer.getRole().name()));
    }
    
    @Override
    public String getPassword() {
        return customer.getPassword();
    }
    
    @Override
    public String getUsername() {
        return customer.getEmail();
    }

    public boolean isEnabled() {
        return customer.getEnabled();
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    
    public Customer getUser() {
        return customer;
    }

}
