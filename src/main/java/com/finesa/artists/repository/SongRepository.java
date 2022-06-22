package com.finesa.artists.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.finesa.artists.models.Song;

@Repository
public interface SongRepository extends CrudRepository<Song, Long> {

}
