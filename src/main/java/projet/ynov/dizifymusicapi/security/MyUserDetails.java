package projet.ynov.dizifymusicapi.security;

import projet.ynov.dizifymusicapi.entity.Admin;
import projet.ynov.dizifymusicapi.entity.User;
import projet.ynov.dizifymusicapi.enums.Role;
import projet.ynov.dizifymusicapi.repositories.AdminRepository;
import projet.ynov.dizifymusicapi.repositories.UserRepository;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
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
	
    if (role == Role.ROLE_ADMIN) {
    	admin = adminRepository.findByUsername(username);

    	if (admin == null) {
    		throw new UsernameNotFoundException("User '" + username + "' not found");
    	}
    } else {
    	user = userRepository.findByUsername(username);

    	if (user == null) {
    		throw new UsernameNotFoundException("User '" + username + "' not found");
    	}
    }    


    return org.springframework.security.core.userdetails.User//
        .withUsername(username)//
        .password(user.getPassword())//
        .authorities(new ArrayList<GrantedAuthority>())//
        .accountExpired(false)//
        .accountLocked(false)//
        .credentialsExpired(false)//
        .disabled(false)//
        .build();
  }

}