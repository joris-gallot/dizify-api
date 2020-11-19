package projet.ynov.dizifymusicapi.security;

import projet.ynov.dizifymusicapi.entity.Admin;
import projet.ynov.dizifymusicapi.enums.Role;
import projet.ynov.dizifymusicapi.repositories.AdminRepository;

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
public class MyAdminDetails implements UserDetailsService {

  @Autowired
  private AdminRepository adminRepository;
  
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	Admin admin = adminRepository.findByUsername(username);

	if (admin == null) {
		throw new UsernameNotFoundException("Admin '" + username + "' not found");
	}
	
	List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
	authorities.add(new SimpleGrantedAuthority(Role.ROLE_ADMIN.getAuthority()));
        
	return org.springframework.security.core.userdetails.User//
			.withUsername(username)//
			.password(admin.getPassword())//
			.authorities(authorities)//
	        .accountExpired(false)//
	        .accountLocked(false)//
	        .credentialsExpired(false)//
	        .disabled(false)//
	        .build();
  }

}