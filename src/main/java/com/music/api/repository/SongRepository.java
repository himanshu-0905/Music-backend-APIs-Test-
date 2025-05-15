package com.music.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.music.api.entity.Song;

public interface SongRepository extends JpaRepository<Song, Long> {
  List<Song> findByTitleContainingIgnoreCase(String title);

  List<Song> findByArtistContainingIgnoreCase(String artist);

  List<Song> findByGenreContainingIgnoreCase(String genre);

}
