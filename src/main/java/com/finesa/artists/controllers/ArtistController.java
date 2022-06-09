package com.finesa.artists.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finesa.artists.models.Artist;
import com.finesa.artists.services.ArtistService;

@RestController
@RequestMapping("/api/artists")
public class ArtistController {
	
	@Autowired
	private ArtistService artistService;

	@GetMapping
	public List<Artist> index() {
		return this.artistService.findAll();
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> show(@PathVariable(required = true) Long id) {
		Map<String, Object> response = new HashMap<>();
		Artist artist = null;
		
		try {
			artist = this.artistService.findById(id);
		} catch (DataAccessException e) {
			response.put("message", "Ocurrió un error al consultar el artista.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if (artist == null) {
			response.put("message", "No se encontró el artista con id: ".concat(id.toString()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		response.put("artist", artist);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<?> store(@RequestBody() Artist artist) {
		Map<String, Object> response = new HashMap<>();
		Artist newArtist = null;
		
		try {
			newArtist = this.artistService.save(artist, null);
		} catch (DataAccessException e) {
			response.put("message", "Ocurrió un error al crear el artista.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("message", "Artista creado exitosamente.");
		response.put("artist", newArtist);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<?> update(@RequestBody() Artist artist, @PathVariable(required = true) Long id) {
		Map<String, Object> response = new HashMap<>();
		Artist currentArtist = this.artistService.findById(id);
		
		try {
			currentArtist = this.artistService.findById(id);
			this.artistService.save(artist, currentArtist);
		} catch (DataAccessException e) {
			response.put("message", "Ocurrió un error al actualizar el artista.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("message", "Artista actualizado exitosamente.");
		response.put("artist", currentArtist);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> destroy(@PathVariable() Long id) {
		Map<String, Object> response = new HashMap<>();
		
		try {
			this.artistService.deleteById(id);
		} catch (DataAccessException e) {
			response.put("message", "Ocurrió un error al eliminar el artista.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("message", "Artista eliminado exitosamente.");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
}
