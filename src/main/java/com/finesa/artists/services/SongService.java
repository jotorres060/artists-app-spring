package com.finesa.artists.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finesa.artists.models.Song;
import com.finesa.artists.repository.SongRepository;

@Service
public class SongService {

	@Autowired
	private SongRepository songRepo;
	
	public List<Song> findAll() {
		return (List<Song>) this.songRepo.findAll();
	}
	
	public Song findById(Long id) {
		return this.songRepo.findById(id).orElse(null);
	}
	
	public Song save(Song song, Song currentSong) {
		if (song.getId() == null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			song.setCreatedAt(sdf.format(new Date()));
		}
		
		Optional<Song> optionalSong = Optional.ofNullable(currentSong);
		if (optionalSong.isPresent()) {
			Song s = optionalSong.get();
			s.setName(song.getName());
			s.setReleaseDate(song.getReleaseDate());
			s.setAlbum(song.getAlbum());
			s.setLikes(song.getLikes());
			song = s;
		}
		
		return this.songRepo.save(song);
	}
	
	public void deleteById(Long id) {
		this.songRepo.deleteById(id);
	}
	
}
