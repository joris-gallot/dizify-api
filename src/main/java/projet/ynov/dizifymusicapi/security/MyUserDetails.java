package projet.ynov.dizifymusicapi.security;

import projet.ynov.dizifymusicapi.entity.Admin;
import projet.ynov.dizifymusicapi.entity.User;
import projet.ynov.dizifymusicapi.enums.Role;
import projet.ynov.dizifymusicapi.repositories.AdminRepository;
import projet.ynov.dizifymusicapi.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetails {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private AdminRepository adminRepository;

  public UserDetails loadUserByUsername(String username, Role role) throws UsernameNotFoundException {
	User user = null;
	Admin admin = null;
	UserBuilder builtedUser = null;
	
    if (role == Role.ROLE_ADMIN) {
    	admin = adminRepository.findByUsername(username);

    	if (admin == null) {
    		throw new UsernameNotFoundException("Admin '" + username + "' not found");
    	}
    	
    	List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
    	authorities.add(new SimpleGrantedAuthority(Role.ROLE_ADMIN.getAuthority()));

    	builtedUser = org.springframework.security.core.userdetails.User//
    			.withUsername(username)//
    			.password(admin.getPassword())//
        		.authorities(authorities);//
    } else {
    	user = userRepository.findByUsername(username);

    	if (user == null) {
    		throw new UsernameNotFoundException("User '" + username + "' not found");
    	}
    	

    	List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
    	authorities.add(new SimpleGrantedAuthority(Role.ROLE_USER.getAuthority()));
    	
    	builtedUser = org.springframework.security.core.userdetails.User//
    			.withUsername(username)//
    			.password(user.getPassword())//
        		.authorities(authorities);//
    }   
        
    return builtedUser
        .accountExpired(false)//
        .accountLocked(false)//
        .credentialsExpired(false)//
        .disabled(false)//
        .build();
  }

}