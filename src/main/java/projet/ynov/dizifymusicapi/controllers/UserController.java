package projet.ynov.dizifymusicapi.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import projet.ynov.dizifymusicapi.entity.User;
import projet.ynov.dizifymusicapi.entity.params.UserParams;
import projet.ynov.dizifymusicapi.exceptions.ResourceNotFoundException;
import projet.ynov.dizifymusicapi.repositories.UserRepository;

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	/**
	 * Get all User list.
	 *
	 * @return the list
	 */
	@GetMapping("/users")
	public List<User> getAllUsers() {
		return userRepository.findAll();
    }

	/**
	 * Gets User by id.
	 *
	 * @param UserId the User id
	 * @return the Users by id
	 * @throws ResourceNotFoundException the resource not found exception
	 */
	@GetMapping("/users/{id}")
	public ResponseEntity<User> getUsersById(@PathVariable(value = "id") Long userId) throws ResourceNotFoundException {
		User user = userRepository
			  				.findById(userId)
	  						.orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND, "User not found with id : " + userId));
	  
		return ResponseEntity.ok().body(user);
	}

	/**
	 * Create User.
	 *
	 * @param params the UserParams
	 * @return the User
	 */
	@PostMapping("/users")
	public User createUser(@Validated @RequestBody UserParams params) {	
		params.setCreatedAt(new Date());
		params.setUpdatedAt(new Date());
		
		if (params.getImage() == null || params.getImage() == "") {
			params.setImage("https://i.pravatar.cc/300");
		}
		
		System.out.println(params);
		
		User user = new User(params);
		
		try {
			return userRepository.save(user);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Email already taken.");
		}
	}

	/**
	 * Update User response entity.
	 *
	 * @param UserId the User id
	 * @param UserDetails the User details
	 * @return the response entity
	 * @throws ResourceNotFoundException the resource not found exception
	 */
	@PutMapping("/users/{id}")
	public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long userId, @RequestBody UserParams userDetails) throws ResourceNotFoundException {
	    User user = userRepository
	            			.findById(userId)
	            			.orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND, "User not found with id : " + userId));
	    	    
	    if(userDetails.getUsername() != null) {	    	
	    	user.setUsername(userDetails.getUsername());
	    }
	    
	    if(userDetails.getImage() != null) {	    	
	    	user.setImage(userDetails.getImage());
	    }
	    
	    if(userDetails.getEmail() != null) {	    	
	    	user.setEmail(userDetails.getEmail());
	    }
	    
	    user.setUpdatedAt(new Date());
	    
	    try {
		    final User updatedUser = userRepository.save(user);
		    return ResponseEntity.ok(updatedUser);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Email already taken.");
		}
	}

	/**
	 * Delete User map.
	 *
	 * @param UserId the User id
	 * @return the map
	 * @throws Exception the exception
	 */
	@DeleteMapping("/users/{id}")
	public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long userId) throws Exception {
	    User user = userRepository
	            			.findById(userId)
	            			.orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND, "User not found with id : " + userId));

	    userRepository.delete(user);
	    Map<String, Boolean> response = new HashMap<>();
	    response.put("deleted", Boolean.TRUE);
	    return response;
	}
}
