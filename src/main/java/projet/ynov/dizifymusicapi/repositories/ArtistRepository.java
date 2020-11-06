package projet.ynov.dizifymusicapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import projet.ynov.dizifymusicapi.entity.Artist;

//@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {}
