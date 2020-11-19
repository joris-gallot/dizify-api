package projet.ynov.dizifymusicapi.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import projet.ynov.dizifymusicapi.entity.Album;
import projet.ynov.dizifymusicapi.entity.Artist;
import projet.ynov.dizifymusicapi.entity.Favorite;
import projet.ynov.dizifymusicapi.entity.Title;
import projet.ynov.dizifymusicapi.entity.User;
import projet.ynov.dizifymusicapi.entity.params.FavoriteParams;
import projet.ynov.dizifymusicapi.exceptions.GlobalHttpException;
import projet.ynov.dizifymusicapi.repositories.AlbumRepository;
import projet.ynov.dizifymusicapi.repositories.ArtistRepository;
import projet.ynov.dizifymusicapi.repositories.FavoriteRepository;
import projet.ynov.dizifymusicapi.repositories.TitleRepository;
import projet.ynov.dizifymusicapi.repositories.UserRepository;

@RestController
@RequestMapping("/api")
public class FavoriteController {

	@Autowired
	private FavoriteRepository favoriteRepository;
	@Autowired
	private ArtistRepository artistRepository;
	@Autowired
	private AlbumRepository albumRepository;
	@Autowired
	private TitleRepository titleRepository;
	@Autowired
	private UserRepository userRepository;
	
	private User getUserLogged() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		
		if (principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
		} else {
			username = principal.toString();
		}
		
		return userRepository.findByUsername(username);
	}
	
	/**
	 * Get paginate Favorite list.
	 *
	 * @return the list
	 */
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("/favorites")
	public List<Favorite> getAllPaginateFavorites(@RequestParam("page") int page, @RequestParam("per") int per) {		
		Pageable sortedDesc = PageRequest.of(page, per, Sort.by("createdAt").descending());
		
		return favoriteRepository.findAllByUser(getUserLogged(), sortedDesc);
    }
	
	/**
	 * Get all Favorite list.
	 *
	 * @return the list
	 */
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("/all-favorites")
	public List<Favorite> getAllFavorites() {			
		return favoriteRepository.findAllByUser(getUserLogged());
    }

	/**
	 * Gets Favorite by id.
	 *
	 * @param FavoriteId the Favorite id
	 * @return the Favorites by id
	 * @throws GlobalHttpException the resource not found exception
	 */
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("/favorites/{id}")
	public ResponseEntity<Favorite> getFavoritesById(@PathVariable(value = "id") Long favoriteId) throws GlobalHttpException {
		
		User userLogged = getUserLogged();

		Favorite favorite = favoriteRepository
			  				.findById(favoriteId)
	  						.orElseThrow(() -> new GlobalHttpException(HttpStatus.NOT_FOUND, "Favorite not found with id : " + favoriteId));
		
		if (favorite.getUser().getId() == userLogged.getId()) {			
			return ResponseEntity.ok().body(favorite);
		}
		
		throw new GlobalHttpException(HttpStatus.FORBIDDEN, "Not authorized");
	}

	/**
	 * Create Favorite.
	 *
	 * @param params the FavoriteParams
	 * @return the Favorite
	 * @throws Exception 
	 */
	@PreAuthorize("hasRole('ROLE_USER')")
	@PostMapping("/favorites")
	public Favorite createFavorite(@Validated @RequestBody FavoriteParams params) throws Exception {
		User userLogged = getUserLogged();
		
		params.setCreatedAt(new Date());
		params.setUpdatedAt(new Date());

		Favorite favorite = new Favorite(params);
		favorite.setUser(userLogged);
		
		if (params.getAlbum_id() != 0L) {
			Album album = albumRepository
			  					.findById(params.getAlbum_id())
								.orElseThrow(() -> new GlobalHttpException(HttpStatus.NOT_FOUND, "Album not found with id : " + params.getAlbum_id()));
			
			favorite.setAlbum(album);
		} else if (params.getArtist_id() != 0L) {
			Artist artist = artistRepository
			  					.findById(params.getArtist_id())
								.orElseThrow(() -> new GlobalHttpException(HttpStatus.NOT_FOUND, "Artist not found with id : " + params.getArtist_id()));

			favorite.setArtist(artist);
		} else if (params.getTitle_id() != 0L) {
			Title title = titleRepository
			  					.findById(params.getTitle_id())
								.orElseThrow(() -> new GlobalHttpException(HttpStatus.NOT_FOUND, "Title not found with id : " + params.getTitle_id()));

			favorite.setTitle(title);
		} else {
			throw new Exception("album_id or title_id or artist_id must be not null");
		}
		
		
		return favoriteRepository.save(favorite);
	}

	/**
	 * Delete Favorite map.
	 *
	 * @param FavoriteId the Favorite id
	 * @return the map
	 * @throws Exception the exception
	 */
	@PreAuthorize("hasRole('ROLE_USER')")
	@DeleteMapping("/favorites/{id}")
	public Map<String, Boolean> deleteFavorite(@PathVariable(value = "id") Long favoriteId) throws Exception {

		User userLogged = getUserLogged();
		
	    Favorite favorite = favoriteRepository
    			.findById(favoriteId)
    			.orElseThrow(() -> new GlobalHttpException(HttpStatus.NOT_FOUND, "Favorite not found with id : " + favoriteId));
		
		if (favorite.getUser().getId() == userLogged.getId()) {
		    favoriteRepository.delete(favorite);
		    Map<String, Boolean> response = new HashMap<>();
		    response.put("deleted", Boolean.TRUE);
		    return response;
		}
		
		throw new GlobalHttpException(HttpStatus.FORBIDDEN, "Not authorized");
	}
}
