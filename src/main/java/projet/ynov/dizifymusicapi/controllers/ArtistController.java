package projet.ynov.dizifymusicapi.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
	 * @param ArtistId the Artist id
	 * @return the Artists by id
	 * @throws ResourceNotFoundException the resource not found exception
	 */
	@GetMapping("/artists/{id}")
	public ResponseEntity<Artist> getArtistsById(@PathVariable(value = "id") Long ArtistId) throws ResourceNotFoundException {
		Artist Artist = artistRepository
			  				.findById(ArtistId)
	  						.orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Artist not found on :: " + ArtistId));
	  
		return ResponseEntity.ok().body(Artist);
	}

	/**
	 * Create Artist Artist.
	 *
	 * @param Artist the Artist
	 * @return the Artist
	 */
	@PostMapping("/artists")
	public Artist createArtist(@Validated @RequestBody Artist Artist) {
		Artist.setCreatedAt(new Date());
		Artist.setUpdatedAt(new Date());
		return artistRepository.save(Artist);
	}

	/**
	 * Update Artist response entity.
	 *
	 * @param ArtistId the Artist id
	 * @param ArtistDetails the Artist details
	 * @return the response entity
	 * @throws ResourceNotFoundException the resource not found exception
	 */
	@PutMapping("/artists/{id}")
	public ResponseEntity<Artist> updateArtist(@PathVariable(value = "id") Long ArtistId, @Validated @RequestBody Artist ArtistDetails) throws ResourceNotFoundException {
	    Artist Artist = artistRepository
	            			.findById(ArtistId)
	            			.orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Artist not found on :: " + ArtistId));

	    Artist.setName(ArtistDetails.getName());
	    Artist.setImage(ArtistDetails.getImage());
	    Artist.setUpdatedAt(new Date());
	    final Artist updatedArtist = artistRepository.save(Artist);
	    return ResponseEntity.ok(updatedArtist);
	}

	/**
	 * Delete Artist map.
	 *
	 * @param ArtistId the Artist id
	 * @return the map
	 * @throws Exception the exception
	 */
	@DeleteMapping("/artist/{id}")
	public Map<String, Boolean> deleteArtist(@PathVariable(value = "id") Long ArtistId) throws Exception {
	    Artist Artist = artistRepository
	            			.findById(ArtistId)
	            			.orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Artist not found on :: " + ArtistId));

	    artistRepository.delete(Artist);
	    Map<String, Boolean> response = new HashMap<>();
	    response.put("deleted", Boolean.TRUE);
	    return response;
	}
}
