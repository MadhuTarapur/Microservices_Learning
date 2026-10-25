package in.madhu.ecommerce.app.Service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import in.madhu.ecommerce.app.Entity.User;
import in.madhu.ecommerce.app.Repository.UserRepository;

@Service
public class UserService implements UserDetailsService {
	
	@Autowired
	UserRepository userrepo;
	
	@Autowired
	private BCryptPasswordEncoder pwdEncoder;
	
	public boolean saveUser(User user) {
		String encodedpwd = pwdEncoder.encode(user.getPassword());
		user.setPassword(encodedpwd);
		 User savedUser = userrepo.save(user);
		 return savedUser.getId()!=null;
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
		
		User user =userrepo.findByEmail(email);
		
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), Collections.emptyList());
	}
	
}
