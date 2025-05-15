package com.music.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.music.api.entity.Playlist;
import com.music.api.entity.Song;
import com.music.api.entity.User;
import com.music.api.servic.MusicService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class MusicController {

  private MusicService musicService;



  // --- User Management ---
  @PostMapping("/register")
  public User register(@RequestBody User user) {
    return musicService.registerUser(user);
  }

  @GetMapping("/users/{id}")
  public Optional<User> getUser(@PathVariable Long id) {
    return musicService.getUserById(id);
  }

  // --- Song Management ---
  @PostMapping("/songs")
  public Song addSong(@RequestBody Song song) {
    return  musicService.addSong(song);
  }

  @GetMapping("/songs")
  public List<Song> searchSongs(@RequestParam(required = false) String title,
      @RequestParam(required = false) String artist,
      @RequestParam(required = false) String genre) {
    return musicService.searchSongs(title, artist, genre);
  }

  // --- Playlist Management ---
  @PostMapping("/playlists/{userId}")
  public Playlist createPlaylist(@PathVariable Long userId, @RequestParam String name) {
    return musicService.createPlaylist(userId, name);
  }

  @PostMapping("/playlists/{playlistId}/add/{songId}")
  public Playlist addToPlaylist(@PathVariable Long playlistId, @PathVariable Long songId) {
    return musicService.addSongToPlaylist(playlistId, songId);
  }

  @DeleteMapping("/playlists/{playlistId}/remove/{songId}")
  public Playlist removeFromPlaylist(@PathVariable Long playlistId, @PathVariable Long songId) {
    return musicService.removeSongFromPlaylist(playlistId, songId);
  }

  @GetMapping("/playlists/user/{userId}")
  public List<Playlist> getUserPlaylists(@PathVariable Long userId) {
    return musicService.getUserPlaylists(userId);
  }

  // --- Playback Simulation ---
  @PostMapping("/play/{userId}/{songId}")
  public Song play(@PathVariable Long userId, @PathVariable Long songId) {
    return musicService.playSong(userId, songId);
  }

  @GetMapping("/play/current/{userId}")
  public Song getCurrent(@PathVariable Long userId) {
    return musicService.getCurrentPlaying(userId);
  }

  @DeleteMapping("/play/stop/{userId}")
  public void stop(@PathVariable Long userId) {
    musicService.stopPlayback(userId);
  }
}
