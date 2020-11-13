package projet.ynov.dizifymusicapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import projet.ynov.dizifymusicapi.entity.Playlist;
import projet.ynov.dizifymusicapi.entity.User;


//@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
	List<Playlist> findAllByUser(User user);
}
