package projet.ynov.dizifymusicapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import projet.ynov.dizifymusicapi.entity.User;


//@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	  User findByUsername(String username);
}
