package com.music.api.servic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.music.api.entity.Playlist;
import com.music.api.entity.Song;
import com.music.api.entity.User;
import com.music.api.repository.PlaylistRepository;
import com.music.api.repository.SongRepository;
import com.music.api.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MusicService {

  private UserRepository userRepository;

  private SongRepository songRepository;

  private PlaylistRepository playlistRepository;

  private final Map<Long, Song> playbackStatus = new HashMap<>();

  // --- User Management ---
  public User registerUser(User user) {
    user.setRole("USER");
    return userRepository.save(user);
  }

  public Optional<User> getUserById(Long id) {
    return userRepository.findById(id);
  }

  // --- Song Management ---
  public Song addSong(Song song) {
    return songRepository.save(song);
  }

  public List<Song> searchSongs(String title, String artist, String genre) {
    if (title != null)
      return songRepository.findByTitleContainingIgnoreCase(title);
    if (artist != null)
      return songRepository.findByArtistContainingIgnoreCase(artist);
    if (genre != null)
      return songRepository.findByGenreContainingIgnoreCase(genre);
    return songRepository.findAll();
  }

  // --- Playlist Management ---
  public Playlist createPlaylist(Long userId, String name) {
    User user = userRepository.findById(userId).orElseThrow();
    Playlist playlist = new Playlist();
    playlist.setName(name);
    playlist.setUser(user);
    return playlistRepository.save(playlist);
  }

  public Playlist addSongToPlaylist(Long playlistId, Long songId) {
    Playlist playlist = playlistRepository.findById(playlistId).orElseThrow();
    Song song = songRepository.findById(songId).orElseThrow();
    playlist.getSongs().add(song);
    return playlistRepository.save(playlist);
  }

  public Playlist removeSongFromPlaylist(Long playlistId, Long songId) {
    Playlist playlist = playlistRepository.findById(playlistId).orElseThrow();
    playlist.getSongs().removeIf(song -> song.getId().equals(songId));
    return playlistRepository.save(playlist);
  }

  public List<Playlist> getUserPlaylists(Long userId) {
    return playlistRepository.findByUserId(userId);
  }

  // --- Playback Simulation ---
  public Song playSong(Long userId, Long songId) {
    Song song = songRepository.findById(songId).orElseThrow();
    playbackStatus.put(userId, song);
    return song;
  }

  public Song getCurrentPlaying(Long userId) {
    return playbackStatus.get(userId);
  }

  public void stopPlayback(Long userId) {
    playbackStatus.remove(userId);
  }

}
