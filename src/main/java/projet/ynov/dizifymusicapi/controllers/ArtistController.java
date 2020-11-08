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

import projet.ynov.dizifymusicapi.entity.Artist;
import projet.ynov.dizifymusicapi.exceptions.ResourceNotFoundException;
import projet.ynov.dizifymusicapi.repositories.ArtistRepository;

@RestController
@RequestMapping("/api")
public class ArtistController {

	@Autowired
	private ArtistRepository artistRepository;
	
	/**
	 * Get all Artist list.
	 *
	 * @return the list
	 */
	@GetMapping("/artists")
	public List<Artist> getAllArtists() {
		return artistRepository.findAll();
    }

	/**
	 * Gets Artists by id.
	 *
	 * @param artistId the Artist id
	 * @return the Artists by id
	 * @throws ResourceNotFoundException the resource not found exception
	 */
	@GetMapping("/artists/{id}")
	public ResponseEntity<Artist> getArtistsById(@PathVariable(value = "id") Long artistId) throws ResourceNotFoundException {
		Artist artist = artistRepository
			  				.findById(artistId)
	  						.orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Artist not found with id : " + artistId));
	  
		return ResponseEntity.ok().body(artist);
	}

	/**
	 * Create Artist Artist.
	 *
	 * @param artist the Artist
	 * @return the Artist
	 */
	@PostMapping("/artists")
	public Artist createArtist(@Validated @RequestBody Artist artist) {
		artist.setCreatedAt(new Date());
		artist.setUpdatedAt(new Date());
		
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
	 * @throws ResourceNotFoundException the resource not found exception
	 */
	@PutMapping("/artists/{id}")
	public ResponseEntity<Artist> updateArtist(@PathVariable(value = "id") Long artistId, @Validated @RequestBody Artist artistDetails) throws ResourceNotFoundException {
	    Artist artist = artistRepository
	            			.findById(artistId)
	            			.orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Artist not found with id : " + artistId));

	    artist.setName(artistDetails.getName());
	    artist.setImage(artistDetails.getImage());
	    artist.setCreatedAt(artistDetails.getCreatedAt());
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
	@DeleteMapping("/artist/{id}")
	public Map<String, Boolean> deleteArtist(@PathVariable(value = "id") Long artistId) throws Exception {
	    Artist artist = artistRepository
	            			.findById(artistId)
	            			.orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Artist not found with id : " + artistId));

	    artistRepository.delete(artist);
	    Map<String, Boolean> response = new HashMap<>();
	    response.put("deleted", Boolean.TRUE);
	    return response;
	}
}
