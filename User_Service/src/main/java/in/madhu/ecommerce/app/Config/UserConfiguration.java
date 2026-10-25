package in.madhu.ecommerce.app.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import in.madhu.ecommerce.app.Service.UserService;
import in.madhu.ecommerce.app.filter.AppFilter;
//import org.springframework.security.core.userdetails.*;
import lombok.SneakyThrows;

@Configuration
@EnableWebSecurity
public class UserConfiguration {
	
	@Autowired
	private UserService userservice;
	
	@Autowired
	private AppFilter filter;
	
//	@Autowired
//	private UserService userDtlsSvc;
	
//	@Bean
//	public DaoAuthenticationProvider authprovider() {
//		
//		DaoAuthenticationProvider authprovider = new DaoAuthenticationProvider(userservice);
//		authprovider.setPasswordEncoder(pwdencoder());
//		//authprovider.setUserDetailsPasswordService(userservice);
//		return authprovider;
//		
//	}
	
	
	@Bean
	public DaoAuthenticationProvider authprovider() {
		
		DaoAuthenticationProvider authprovider = new DaoAuthenticationProvider(userservice);
		authprovider.setPasswordEncoder(pwdencoder());
		return authprovider;
		
	}
	
	@SneakyThrows
	@Bean
	public AuthenticationManager authmanager(AuthenticationConfiguration config)throws Exception {
	
		return config.getAuthenticationManager();
	}
	
	@Bean
	public BCryptPasswordEncoder pwdencoder() {
		
		return new BCryptPasswordEncoder();
		
	}
	
	
	@SneakyThrows
//	@Bean
//	public SecurityFilterChain security(HttpSecurity http) throws Exception {
//		
//		http.authorizeHttpRequests((req) ->{
//			
//			req.requestMatchers("/register", "/api/login")
//			.permitAll()
//			.anyRequest()
//			.authenticated();
//		});
//		
//			return http.csrf(csrf -> csrf.disable()).build();
//		
//		
//	}
	
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/users/login","/api/users/register").permitAll()
                .and()
                .authorizeHttpRequests().requestMatchers("/api/**")
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authprovider())
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class).build();

	}
	
}


