package projet.ynov.dizifymusicapi.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import projet.ynov.dizifymusicapi.entity.Favorite;
import projet.ynov.dizifymusicapi.entity.User;


//@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
	List<Favorite> findAllByUser(User user, Pageable pageable);
}
