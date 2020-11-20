package projet.ynov.dizifymusicapi.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import projet.ynov.dizifymusicapi.entity.params.TitleParams;
import projet.ynov.dizifymusicapi.exceptions.GlobalHttpException;
import projet.ynov.dizifymusicapi.repositories.AlbumRepository;
import projet.ynov.dizifymusicapi.repositories.ArtistRepository;
import projet.ynov.dizifymusicapi.repositories.FavoriteRepository;
import projet.ynov.dizifymusicapi.repositories.TitleRepository;
import projet.ynov.dizifymusicapi.repositories.UserRepository;

@RestController
@RequestMapping("/api")
public class TitleController {

	@Autowired
	private TitleRepository titleRepository;
	@Autowired
	private ArtistRepository artistRepository;
	@Autowired
	private AlbumRepository albumRepository;
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
	 * Get all Title list.
	 *
	 * @return the list
	 */
	@GetMapping("/titles")
	public List<Title> getAllTitles() {
		List<Title> titles = titleRepository.findAll();
		User userLogged = getUserLogged();
		
		for (Title title : titles) {
			if(userLogged == null) {
				title.setFavoriteId(0L);
			} else {
				Favorite titlefavorite = favoriteRepository.findByUserAndAlbum(userLogged.getId(), title.getId());
				
				if (titlefavorite == null) {
					title.setFavoriteId(0L);
				} else {
					title.setFavoriteId(titlefavorite.getId());
				}
			}
		}
		
		return titles;
    }

	/**
	 * Gets Titles by id.
	 *
	 * @param titleId the Title id
	 * @return the Titles by id
	 * @throws GlobalHttpException the resource not found exception
	 */
	@GetMapping("/titles/{id}")
	public ResponseEntity<Title> getTitlesById(@PathVariable(value = "id") Long titleId) throws GlobalHttpException {
		Title title = titleRepository
			  				.findById(titleId)
	  						.orElseThrow(() -> new GlobalHttpException(HttpStatus.NOT_FOUND, "Title not found with id : " + titleId));
		
		User userLogged = getUserLogged();
		if(userLogged == null) {
			title.setFavoriteId(0L);
		} else {
			Favorite titlefavorite = favoriteRepository.findByUserAndAlbum(userLogged.getId(), title.getId());
			
			if (titlefavorite == null) {
				title.setFavoriteId(0L);
			} else {
				title.setFavoriteId(titlefavorite.getId());
			}
		}
	  
		return ResponseEntity.ok().body(title);
	}

	/**
	 * Create Title Title.
	 *
	 * @param paramd TitleParams
	 * @return the Title
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/titles")
	public Title createTitle(@Validated @RequestBody TitleParams params) {
		Artist artist = artistRepository
  					.findById(params.getAuthor_id())
					.orElseThrow(() -> new GlobalHttpException(HttpStatus.NOT_FOUND, "Artist not found with id : " + params.getAuthor_id()));
		Album album;

		try {
			Optional<Album> optionalAlbum = albumRepository.findById(params.getAlbum_id());
			album = optionalAlbum.get();
        } catch (Exception e) {
        	album = null;
        }			

		params.setCreatedAt(new Date());
		params.setUpdatedAt(new Date());
		
		Title title = new Title(params);
		title.setAuthor(artist);
		title.setAlbum(album);
		
		try {
			return titleRepository.save(title);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Name already taken.");
		}
	}

	/**
	 * Update Title response entity.
	 *
	 * @param TitleId the Title id
	 * @param TitleDetails the Title details
	 * @return the response entity
	 * @throws GlobalHttpException the resource not found exception
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("/titles/{id}")
	public ResponseEntity<Title> updateTitle(@PathVariable(value = "id") Long titleId, @Validated @RequestBody TitleParams titleDetails) throws GlobalHttpException {
	    Title title = titleRepository
	            			.findById(titleId)
	            			.orElseThrow(() -> new GlobalHttpException(HttpStatus.NOT_FOUND, "Title not found with id : " + titleId));
	    
	    if (titleDetails.getName() != null) {
	    	titleDetails.setName(titleDetails.getName());
	    }
	    
	    if (titleDetails.getDuration() != null) {
	    	titleDetails.setDuration(titleDetails.getDuration());
	    }

	    title.setUpdatedAt(new Date());
	    final Title updatedTitle = titleRepository.save(title);
	    return ResponseEntity.ok(updatedTitle);
	}

	/**
	 * Delete Title map.
	 *
	 * @param TitleId the Title id
	 * @return the map
	 * @throws Exception the exception
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/titles/{id}")
	public Map<String, Boolean> deleteTitle(@PathVariable(value = "id") Long titleId) throws Exception {
	    Title title = titleRepository
	            			.findById(titleId)
	            			.orElseThrow(() -> new GlobalHttpException(HttpStatus.NOT_FOUND, "Title not found with id : " + titleId));

	    titleRepository.delete(title);
	    Map<String, Boolean> response = new HashMap<>();
	    response.put("deleted", Boolean.TRUE);
	    return response;
	}
}
