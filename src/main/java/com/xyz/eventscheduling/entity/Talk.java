package com.xyz.eventscheduling.entity;

import java.util.Date;

public class Talk {

	public Talk(String title, Date startTime){
		this.title = title;
		this.startTime = startTime;
	}
	
	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	private String title;

	private String duration;

	private Date endTime;

	private Date startTime;
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Talk() {
	}

	public Talk(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return String.format("Talk[%s]", title);
	}
}