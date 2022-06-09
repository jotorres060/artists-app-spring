package com.finesa.artists.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.finesa.artists.models.Artist;

@Repository
public interface ArtistRepository extends CrudRepository<Artist, Long> {
	
}
