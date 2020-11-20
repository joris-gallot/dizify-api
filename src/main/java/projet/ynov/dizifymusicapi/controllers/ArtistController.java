package projet.ynov.dizifymusicapi.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import projet.ynov.dizifymusicapi.entity.Album;
import projet.ynov.dizifymusicapi.entity.Artist;
import projet.ynov.dizifymusicapi.entity.Favorite;
import projet.ynov.dizifymusicapi.entity.Title;
import projet.ynov.dizifymusicapi.entity.User;
import projet.ynov.dizifymusicapi.entity.params.ArtistParams;
import projet.ynov.dizifymusicapi.exceptions.GlobalHttpException;
import projet.ynov.dizifymusicapi.repositories.ArtistRepository;
import projet.ynov.dizifymusicapi.repositories.FavoriteRepository;
import projet.ynov.dizifymusicapi.repositories.TitleRepository;
import projet.ynov.dizifymusicapi.repositories.UserRepository;

@RestController
@RequestMapping("/api")
public class ArtistController {

	@Autowired
	private ArtistRepository artistRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private FavoriteRepository favoriteRepository;
	
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
	 * Get all Artist list.
	 *
	 * @return the list
	 */
	@GetMapping("/artists")
	public List<Artist> getAllArtists() {
		List<Artist> artists = artistRepository.findAll();
		User userLogged = getUserLogged();
		
		
		for (Artist artist: artists) {
			if(userLogged == null) {
				artist.setFavoriteId(0L);
			} else {
				Favorite artistFavorite = favoriteRepository.findByUserAndAlbum(userLogged.getId(), artist.getId());
				
				if (artistFavorite == null) {
					artist.setFavoriteId(0L);
				} else {
					artist.setFavoriteId(artistFavorite.getId());
				}
				
				for (Title title : artist.getTitles()) {
					Favorite titleFavorite = favoriteRepository.findByUserAndTitle(userLogged.getId(), title.getId());
					
					if (titleFavorite == null) {
						title.setFavoriteId(0L);
					} else {
						title.setFavoriteId(titleFavorite.getId());
					}
				}
			}
		}
		return artistRepository.findAll();
    }

	/**
	 * Gets Artists by id.
	 *
	 * @param artistId the Artist id
	 * @return the Artists by id
	 * @throws GlobalHttpException the resource not found exception
	 */
	@GetMapping("/artists/{id}")
	public ResponseEntity<Artist> getArtistsById(@PathVariable(value = "id") Long artistId) throws GlobalHttpException {
		
		Artist artist = artistRepository
			  				.findById(artistId)
	  						.orElseThrow(() -> new GlobalHttpException(HttpStatus.NOT_FOUND, "Artist not found with id : " + artistId));
		
		User userLogged = getUserLogged();
		if(userLogged == null) {
			artist.setFavoriteId(0L);
		} else {
			Favorite artistFavorite = favoriteRepository.findByUserAndArtist(userLogged.getId(), artist.getId());
			
			if (artistFavorite == null) {
				artist.setFavoriteId(0L);
			} else {
				artist.setFavoriteId(artistFavorite.getId());
			}
			
			for (Title title : artist.getTitles()) {
				Favorite titleFavorite = favoriteRepository.findByUserAndTitle(userLogged.getId(), title.getId());
				
				if (titleFavorite == null) {
					title.setFavoriteId(0L);
				} else {
					title.setFavoriteId(titleFavorite.getId());
				}
			}
		}
	  
		return ResponseEntity.ok().body(artist);
	}

	/**
	 * Create Artist Artist.
	 *
	 * @param artist the Artist
	 * @return the Artist
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/artists")
	public Artist createArtist(@Validated @RequestBody ArtistParams params) {
		
		params.setCreatedAt(new Date());
		params.setUpdatedAt(new Date());
		
		if (params.getImage() == null || params.getImage() == "") {
			params.setImage("https://i.pravatar.cc/200");
		}
		
		Artist artist = new Artist(params);
		
		try {
			return artistRepository.save(artist);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Name already taken.");
		}
	}

	/**
	 * Update Artist response entity.
	 *
	 * @param artistId the Artist id
	 * @param artistDetails the Artist details
	 * @return the response entity
	 * @throws GlobalHttpException the resource not found exception
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("/artists/{id}")
	public ResponseEntity<Artist> updateArtist(@PathVariable(value = "id") Long artistId, @Validated @RequestBody ArtistParams artistDetails) throws GlobalHttpException {
	    Artist artist = artistRepository
	            			.findById(artistId)
	            			.orElseThrow(() -> new GlobalHttpException(HttpStatus.NOT_FOUND, "Artist not found with id : " + artistId));
	    
	    if (artistDetails.getName() != null) {
	    	artist.setName(artistDetails.getName());	    	
	    }
	    
	    if (artistDetails.getImage() != null) {
	    	artist.setImage(artistDetails.getImage());	    	
	    }
	    
	    if (artistDetails.getDescription() != null) {
	    	artist.setDescription(artistDetails.getDescription());	    	
	    }

	    artist.setUpdatedAt(new Date());
	    final Artist updatedArtist = artistRepository.save(artist);
	    return ResponseEntity.ok(updatedArtist);
	}

	/**
	 * Delete Artist map.
	 *
	 * @param artistId the Artist id
	 * @return the map
	 * @throws Exception the exception
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/artists/{id}")
	public Map<String, Boolean> deleteArtist(@PathVariable(value = "id") Long artistId) throws Exception {
	    Artist artist = artistRepository
	            			.findById(artistId)
	            			.orElseThrow(() -> new GlobalHttpException(HttpStatus.NOT_FOUND, "Artist not found with id : " + artistId));

	    artistRepository.delete(artist);
	    Map<String, Boolean> response = new HashMap<>();
	    response.put("deleted", Boolean.TRUE);
	    return response;
	}
}
