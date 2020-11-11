package projet.ynov.dizifymusicapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import projet.ynov.dizifymusicapi.entity.Favorite;


//@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {}
