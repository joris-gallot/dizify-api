package projet.ynov.dizifymusicapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import projet.ynov.dizifymusicapi.entity.Album;


//@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {}
