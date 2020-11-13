package projet.ynov.dizifymusicapi.controllers;

import java.util.Date;

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

import projet.ynov.dizifymusicapi.entity.User;
import projet.ynov.dizifymusicapi.entity.params.UserParams;
import projet.ynov.dizifymusicapi.exceptions.CustomException;
import projet.ynov.dizifymusicapi.repositories.UserRepository;
import projet.ynov.dizifymusicapi.security.JwtTokenProvider;
import org.springframework.security.core.AuthenticationException;

@RestController
@RequestMapping("/api")
public class AuthenticationController {
	@Autowired
	private UserRepository userRepository;
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
	public User signup(@Validated @RequestBody UserParams params) {

		params.setPassword(passwordEncoder.encode(params.getPassword()));
		params.setCreatedAt(new Date());
		params.setUpdatedAt(new Date());
		
		if (params.getImage() == null || params.getImage() == "") {
			params.setImage("https://i.pravatar.cc/200");
		}
		
		User user = new User(params);
		
		try {
			User userCreated = userRepository.save(user);

			String token = jwtTokenProvider.createToken(user.getUsername());
			userCreated.setToken(token);
			
			return userCreated;
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Email or username already taken.");
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
	      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(params.getUsername(), params.getPassword()));
	      String token = jwtTokenProvider.createToken(params.getUsername());
	      User userFinded = userRepository.findByUsername(params.getUsername());
	      
	      userFinded.setToken(token);
	      
	      return userFinded;
	      
	    } catch (AuthenticationException e) {
	      throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
	    }
	}
}
