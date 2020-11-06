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

import projet.ynov.dizifymusicapi.entity.Title;
import projet.ynov.dizifymusicapi.exceptions.ResourceNotFoundException;
import projet.ynov.dizifymusicapi.repositories.TitleRepository;

@RestController
@RequestMapping("/api")
public class TitleController {

	@Autowired
	private TitleRepository titleRepository;
	
	/**
	 * Get all Title list.
	 *
	 * @return the list
	 */
	@GetMapping("/titles")
	public List<Title> getAllTitles() {
		return titleRepository.findAll();
    }

	/**
	 * Gets Titles by id.
	 *
	 * @param TitleId the Title id
	 * @return the Titles by id
	 * @throws ResourceNotFoundException the resource not found exception
	 */
	@GetMapping("/titles/{id}")
	public ResponseEntity<Title> getTitlesById(@PathVariable(value = "id") Long titleId) throws ResourceNotFoundException {
		Title Title = titleRepository
			  				.findById(titleId)
	  						.orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Title not found on :: " + titleId));
	  
		return ResponseEntity.ok().body(Title);
	}

	/**
	 * Create Title Title.
	 *
	 * @param Title the Title
	 * @return the Title
	 */
	@PostMapping("/titles")
	public Title createTitle(@Validated @RequestBody Title title) {
		title.setCreatedAt(new Date());
		title.setUpdatedAt(new Date());
		return titleRepository.save(title);
	}

	/**
	 * Update Title response entity.
	 *
	 * @param TitleId the Title id
	 * @param TitleDetails the Title details
	 * @return the response entity
	 * @throws ResourceNotFoundException the resource not found exception
	 */
	@PutMapping("/Titles/{id}")
	public ResponseEntity<Title> updateTitle(@PathVariable(value = "id") Long titleId, @Validated @RequestBody Title titleDetails) throws ResourceNotFoundException {
	    Title Title = titleRepository
	            			.findById(titleId)
	            			.orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Title not found on :: " + titleId));

	    Title.setName(titleDetails.getName());
	    Title.setDuration(titleDetails.getDuration());
	    Title.setCreatedAt(titleDetails.getCreatedAt());
	    Title.setUpdatedAt(new Date());
	    final Title updatedTitle = titleRepository.save(Title);
	    return ResponseEntity.ok(updatedTitle);
	}

	/**
	 * Delete Title map.
	 *
	 * @param TitleId the Title id
	 * @return the map
	 * @throws Exception the exception
	 */
	@DeleteMapping("/Title/{id}")
	public Map<String, Boolean> deleteTitle(@PathVariable(value = "id") Long titleId) throws Exception {
	    Title Title = titleRepository
	            			.findById(titleId)
	            			.orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Title not found on :: " + titleId));

	    titleRepository.delete(Title);
	    Map<String, Boolean> response = new HashMap<>();
	    response.put("deleted", Boolean.TRUE);
	    return response;
	}
}
