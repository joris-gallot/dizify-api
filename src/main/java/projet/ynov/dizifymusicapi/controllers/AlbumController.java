package projet.ynov.dizifymusicapi.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import projet.ynov.dizifymusicapi.entity.params.AlbumParams;
import projet.ynov.dizifymusicapi.exceptions.ResourceNotFoundException;
import projet.ynov.dizifymusicapi.repositories.AlbumRepository;
import projet.ynov.dizifymusicapi.repositories.ArtistRepository;

@RestController
@RequestMapping("/api")
public class AlbumController {

	@Autowired
	private AlbumRepository albumRepository;
	@Autowired
	private ArtistRepository artistRepository;
	
	/**
	 * Get all Album list.
	 *
	 * @return the list
	 */
	@GetMapping("/albums")
	public List<Album> getAllAlbums() {
		return albumRepository.findAll();
    }

	/**
	 * Gets album by id.
	 *
	 * @param albumId the Album id
	 * @return the Albums by id
	 * @throws ResourceNotFoundException the resource not found exception
	 */
	@GetMapping("/albums/{id}")
	public ResponseEntity<Album> getAlbumsById(@PathVariable(value = "id") Long albumId) throws ResourceNotFoundException {
		Album album = albumRepository
			  				.findById(albumId)
	  						.orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Album not found with id : " + albumId));
	  
		return ResponseEntity.ok().body(album);
	}

	/**
	 * Create Album.
	 *
	 * @param params the AlbumParams
	 * @return the Album
	 */
	@PostMapping("/albums")
	public Album createAlbum(@Validated @RequestBody AlbumParams params) {
		Artist artist = artistRepository
			  				.findById(params.getAuthor_id())
	  						.orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Artist not found with id : " + params.getAuthor_id()));
		

		params.setCreatedAt(new Date());
		params.setUpdatedAt(new Date());
		
		if (params.getImage() == null || params.getImage() == "") {
			params.setImage("https://picsum.photos/700/500");
		}
		
		Album album = new Album(params);
		album.setAuthor(artist);
		
		try {
			return albumRepository.save(album);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Name already taken.");
		}
	}

	/**
	 * Update Album response entity.
	 *
	 * @param albumId the Album id
	 * @param albumDetails the Album details
	 * @return the response entity
	 * @throws ResourceNotFoundException the resource not found exception
	 */
	@PutMapping("/albums/{id}")
	public ResponseEntity<Album> updateAlbum(@PathVariable(value = "id") Long albumId, @Validated @RequestBody Album albumDetails) throws ResourceNotFoundException {
	    Album album = albumRepository
	            			.findById(albumId)
	            			.orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Album not found with id : " + albumId));

	    album.setName(albumDetails.getName());
	    album.setImage(albumDetails.getImage());
	    album.setUpdatedAt(new Date());
	    final Album updatedAlbum = albumRepository.save(album);
	    return ResponseEntity.ok(updatedAlbum);
	}

	/**
	 * Delete Album map.
	 *
	 * @param albumId the Album id
	 * @return the map
	 * @throws Exception the exception
	 */
	@DeleteMapping("/album/{id}")
	public Map<String, Boolean> deleteAlbum(@PathVariable(value = "id") Long albumId) throws Exception {
	    Album album = albumRepository
	            			.findById(albumId)
	            			.orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Album not found with id : " + albumId));

	    albumRepository.delete(album);
	    Map<String, Boolean> response = new HashMap<>();
	    response.put("deleted", Boolean.TRUE);
	    return response;
	}
}
