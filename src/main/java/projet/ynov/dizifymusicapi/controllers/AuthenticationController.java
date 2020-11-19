package projet.ynov.dizifymusicapi.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import projet.ynov.dizifymusicapi.entity.Admin;
import projet.ynov.dizifymusicapi.entity.User;
import projet.ynov.dizifymusicapi.entity.params.UserParams;
import projet.ynov.dizifymusicapi.enums.Role;
import projet.ynov.dizifymusicapi.exceptions.CustomException;
import projet.ynov.dizifymusicapi.repositories.AdminRepository;
import projet.ynov.dizifymusicapi.repositories.UserRepository;
import projet.ynov.dizifymusicapi.security.JwtTokenProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@RestController
@RequestMapping("/api")
public class AuthenticationController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AdminRepository adminRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	/**
	 * Create User.
	 *
	 * @param params the UserParams
	 * @return the User
	 */
	@PostMapping("/auth/signup")
	public User signupUser(@Validated @RequestBody UserParams params) {

		params.setPassword(passwordEncoder.encode(params.getPassword()));
		params.setCreatedAt(new Date());
		params.setUpdatedAt(new Date());
		
		if (params.getImage() == null || params.getImage() == "") {
			params.setImage("https://i.pravatar.cc/200");
		}
		
		User user = new User(params);
		
		try {
			User userCreated = userRepository.save(user);

			String token = jwtTokenProvider.createToken(user.getUsername(), Role.ROLE_USER);
			userCreated.setToken(token);
			
			return userCreated;
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Email or username already taken.");
		}
	}
	
	/**
	 * Create Admin.
	 *
	 * @param params the UserParams
	 * @return the Admin
	 */
	@PostMapping("/auth/admin/signup")
	public Admin signupAdmin(@Validated @RequestBody UserParams params) {

		params.setPassword(passwordEncoder.encode(params.getPassword()));
		params.setCreatedAt(new Date());
		params.setUpdatedAt(new Date());
		
		if (params.getImage() == null || params.getImage() == "") {
			params.setImage("https://i.pravatar.cc/200");
		}
		
		Admin admin = new Admin(params);
		
		try {
			Admin adminCreated = adminRepository.save(admin);

			String token = jwtTokenProvider.createToken(admin.getUsername(), Role.ROLE_ADMIN);
			adminCreated.setToken(token);
			
			return adminCreated;
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Email or username already taken.");
		}
	}
	

	
	/**
	 * Login Admin.
	 *
	 * @param params the UserParams
	 * @return the Admin
	 */
	@PostMapping("/auth/admin/signin")
	public Admin signinAdmin(@RequestBody UserParams params) {
		try {
		  List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		  authorities.add(new SimpleGrantedAuthority(Role.ROLE_ADMIN.getAuthority()));
		  
	      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(params.getUsername(), params.getPassword(), authorities));
	      String token = jwtTokenProvider.createToken(params.getUsername(), Role.ROLE_ADMIN);
	      Admin adminFinded = adminRepository.findByUsername(params.getUsername());
	      
	      adminFinded.setToken(token);
	      
	      return adminFinded;
	      
	    } catch (AuthenticationException e) {
	      throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
	    }
	}
	
	/**
	 * Login User.
	 *
	 * @param params the UserParams
	 * @return the User
	 */
	@PostMapping("/auth/signin")
	public User signin(@RequestBody UserParams params) {
		try {
		  System.out.println("ubhijnko,lm");

		  List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		  authorities.add(new SimpleGrantedAuthority(Role.ROLE_USER.getAuthority()));
		  
	      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(params.getUsername(), params.getPassword(), authorities));

		  System.out.println("ubhijnko,lm");
	      String token = jwtTokenProvider.createToken(params.getUsername(), Role.ROLE_USER);
	      User userFinded = userRepository.findByUsername(params.getUsername());
	      
	      userFinded.setToken(token);
	      
	      return userFinded;
	      
	    } catch (AuthenticationException e) {
	      throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
	    }
	}
}
