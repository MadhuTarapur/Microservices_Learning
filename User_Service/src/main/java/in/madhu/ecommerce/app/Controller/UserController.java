package in.madhu.ecommerce.app.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.madhu.ecommerce.app.Entity.User;
import in.madhu.ecommerce.app.Repository.UserRepository;
import in.madhu.ecommerce.app.Service.JwtService;
import in.madhu.ecommerce.app.Service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService userservice;
	
	@Autowired
	private AuthenticationManager authmanager;
	
	@Autowired
	private UserRepository crepo;

	@Autowired
	private PasswordEncoder pwdEncoder;
	
	@Autowired
	private JwtService jwt;
	
//	@PostMapping("/register")
//	public ResponseEntity<String> userReg(@RequestBody User user) {
//		
//		boolean status=userservice.saveUser(user);
//		if(status) {
//			return new ResponseEntity<>("Registration Success", HttpStatus.CREATED);
//		}
//		else {
//		
//		return new ResponseEntity<>("Registration failed", HttpStatus.INTERNAL_SERVER_ERROR);
//	}
//}
	
	@PostMapping("/register")
	public String registerUser(@RequestBody User user) {
		
		// duplicate check

		String encodedPwd = pwdEncoder.encode(user.getPassword());
		user.setPassword(encodedPwd);

		crepo.save(user);

		return "User registered";
	}

	
//	@PostMapping("/login")
//	public ResponseEntity<String> Login(@RequestBody User user) {
//		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
//		Authentication authenticate= authmanager.authenticate(token);
//		boolean status =authenticate.isAuthenticated();
//		if(status) {
//			return new ResponseEntity<>("Login success", HttpStatus.OK);
//		}
//		else {
//			return new ResponseEntity<>("Failed", HttpStatus.BAD_REQUEST);
//			
//		}
//		
//	}
	
	@PostMapping("/login")
	public ResponseEntity<String> loginCheck(@RequestBody User user) {
		
		UsernamePasswordAuthenticationToken token = 
				new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());

		try {
			Authentication authenticate = authmanager.authenticate(token);

			if (authenticate.isAuthenticated()) {
				String jwtToken = jwt.generateToken(user.getEmail());
				return new ResponseEntity<>(jwtToken, HttpStatus.OK);
			}

		} catch (Exception e) {
			//logger
		}

		return new ResponseEntity<String>("Invalid Credentials", HttpStatus.BAD_REQUEST);
	}
}


