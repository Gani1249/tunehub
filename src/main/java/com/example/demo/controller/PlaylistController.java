package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entities.Playlist;
import com.example.demo.entities.Song;
import com.example.demo.services.PlaylistService;
import com.example.demo.services.SongService;


@Controller
public class PlaylistController {
	
	@Autowired
	SongService service;
	
	@Autowired
	PlaylistService plservice;
	
	@GetMapping("/createPlaylist")
	public String createPlaylist(Model model) {
		List<Song> songlist=service.fetchAllSongs();
		model.addAttribute("songs",songlist);
		return "createPlaylist";	
	}
	
	@PostMapping("/addPlaylist")
	public String addPlaylist(@ModelAttribute Playlist playlist) {
		//updating playlist table
		plservice.addPlaylist(playlist);
		
		//updating song table
		List<Song> songList=playlist.getSongs();
		for(Song s:songList) {
			s.getPlaylists().add(playlist);
			service.updateSong(s);
		}
		return "adminHome";
	}
	
	@GetMapping("/viewPlaylist")
	public String viewPlaylist(Model model) {		
		List<Playlist> playlists=plservice.fetchAllPlaylists();
		model.addAttribute("allplaylists", playlists);
		return "displayPlaylist";
	}
	
}
