package com.xyz.eventscheduling.entity;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class TalkStream {
	
	ArrayList<Talk> talks;
	int sessionCount;
	
	public TalkStream(){
	    this.talks = new ArrayList<>();
	}
	
	public TalkStream(TalkStream stream){
		this.talks = (ArrayList<Talk>) stream.getTalks().stream().collect(Collectors.toList());
	}

	public ArrayList<Talk> getTalks() {
		return talks;
	}

	public void setTalks(ArrayList<Talk> talks) {
		this.talks = talks;
	}
	
	public void addTalk(Talk talk) {
		
		talks.add(talk);
	}
	
public void setSessionCount(int sessionCount) {
		
		this.sessionCount = sessionCount;
	}
public int getSessionCount() {
	
	return this.sessionCount;
}



}
