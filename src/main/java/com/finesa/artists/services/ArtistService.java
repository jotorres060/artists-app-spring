package com.finesa.artists.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finesa.artists.models.Artist;
import com.finesa.artists.repository.ArtistRepository;

@Service
public class ArtistService {
	
	@Autowired
	private ArtistRepository artistRepo;
	
	public List<Artist> findAll() {
		return (List<Artist>) this.artistRepo.findAll();
	}
	
	public Artist findById(Long id) {
		return this.artistRepo.findById(id).orElse(null);
	}
	
	public Artist save(Artist artist, Artist currentArtist) {
		if (artist.getId() == null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			artist.setCreatedAt(sdf.format(new Date()));
		}
		
		Optional<Artist> optionalArtist = Optional.ofNullable(currentArtist);
		if (optionalArtist.isPresent()) {
			Artist art = optionalArtist.get();
			art.setName(artist.getName());
			art.setLastName(artist.getLastName());
			art.setGender(artist.getGender());
			artist = art;
		}
		
		return this.artistRepo.save(artist);
	}
	
	public void deleteById(Long id) {
		this.artistRepo.deleteById(id);
	}
	
}
