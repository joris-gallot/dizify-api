package projet.ynov.dizifymusicapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import projet.ynov.dizifymusicapi.entity.Admin;


//@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
	  Admin findByUsername(String username);
}
