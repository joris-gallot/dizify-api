package projet.ynov.dizifymusicapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import projet.ynov.dizifymusicapi.entity.Playlist;


//@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {}
