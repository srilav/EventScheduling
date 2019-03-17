package com.xyz.eventscheduling.entity;

import java.util.ArrayList;

public class TalkEvent {
	ArrayList<TalkTrack> tracks;
	
	public TalkEvent(){
	    this.tracks = new ArrayList<>();
	}

	public ArrayList<TalkTrack> getTracks() {
		return tracks;
	}

	public void setTracks(ArrayList<TalkTrack> tracks) {
		this.tracks = tracks;
	}
	
	public void addTrack(TalkTrack track) {
		tracks.add(new TalkTrack(track));
	}
}
