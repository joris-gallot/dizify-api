package projet.ynov.dizifymusicapi.security;

import projet.ynov.dizifymusicapi.entity.User;
import projet.ynov.dizifymusicapi.enums.Role;
import projet.ynov.dizifymusicapi.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetails implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;
  
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	User user = userRepository.findByUsername(username);

	if (user == null) {
		throw new UsernameNotFoundException("User '" + username + "' not found");
	}
	
	List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
	authorities.add(new SimpleGrantedAuthority(Role.ROLE_USER.getAuthority()));
        
	return org.springframework.security.core.userdetails.User//
		.withUsername(username)//
		.password(user.getPassword())//
		.authorities(authorities)//
        .accountExpired(false)//
        .accountLocked(false)//
        .credentialsExpired(false)//
        .disabled(false)//
        .build();
  }

}