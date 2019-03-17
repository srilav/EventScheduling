package com.xyz.eventscheduling.entity;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class TalkTrack {
	ArrayList<TalkStream> streams;
	
	int trackCount;
	
	public int getTrackCount() {
		return trackCount;
	}

	public void setTrackCount(int trackCount) {
		this.trackCount = trackCount;
	}

	public TalkTrack(){
	    this.streams = new ArrayList<>();
	}
	
	public TalkTrack(TalkTrack track){
		this.streams = (ArrayList<TalkStream>) track.getStreams().stream().collect(Collectors.toList());
	}

	public ArrayList<TalkStream> getStreams() {
		return streams;
	}

	public void setStreams(ArrayList<TalkStream> streams) {
		this.streams = streams;
	}
	
	public void addStream(TalkStream stream) {
		streams.add(new TalkStream(stream));
	}
}
