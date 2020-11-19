package projet.ynov.dizifymusicapi.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import projet.ynov.dizifymusicapi.entity.Favorite;
import projet.ynov.dizifymusicapi.entity.User;


//@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
	List<Favorite> findAllByUser(User user, Pageable pageable);
	List<Favorite> findAllByUser(User user);

	@Query(value = "SELECT * FROM favorites WHERE user_id = ?1 and album_id = ?2", nativeQuery = true)
	Favorite findByUserAndAlbum(long userId, long albumId);
}
