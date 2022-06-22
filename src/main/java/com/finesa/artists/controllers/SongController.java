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

import com.finesa.artists.models.Song;
import com.finesa.artists.services.SongService;

@RestController
@RequestMapping("/api/songs")
public class SongController {
	
	@Autowired
	private SongService songService;
	
	@GetMapping
	public List<Song> index() {
		return this.songService.findAll();
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> show(@PathVariable(required = true) Long id) {
		Map<String, Object> response = new HashMap<>();
		Song song = null;
		
		try {
			song = this.songService.findById(id);
		} catch (DataAccessException e) {
			response.put("message", "Ocurrió un error al consultar las canciones.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if (song == null) {
			response.put("message", "No se encontró la canción con id: ".concat(id.toString()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		response.put("song", song);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<?> store(@RequestBody() Song song) {
		Map<String, Object> response = new HashMap<>();
		Song newSong = null;
		
		try {
			newSong = this.songService.save(song, null);
		} catch (DataAccessException e) {
			response.put("message", "Ocurrió un error al crear la canción.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("message", "Canción creada exitosamente.");
		response.put("song", newSong);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<?> update(@RequestBody() Song song, @PathVariable(required = true) Long id) {
		Map<String, Object> response = new HashMap<>();
		Song currentSong = this.songService.findById(id);
		
		try {
			currentSong = this.songService.findById(id);
			this.songService.save(song, currentSong);
		} catch (DataAccessException e) {
			response.put("message", "Ocurrió un error al actualizar la canción.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("message", "Canción actualizada exitosamente.");
		response.put("artist", currentSong);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> destroy(@PathVariable() Long id) {
		Map<String, Object> response = new HashMap<>();
		
		try {
			this.songService.deleteById(id);
		} catch (DataAccessException e) {
			response.put("message", "Ocurrió un error al eliminar la canción.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("message", "Canción eliminada exitosamente.");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

}
