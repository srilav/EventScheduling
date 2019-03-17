

package com.xyz.eventscheduling.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xyz.eventscheduling.constants.EventSchConstants;
import com.xyz.eventscheduling.entity.Talk;
import com.xyz.eventscheduling.entity.TalkEvent;
import com.xyz.eventscheduling.entity.TalkStream;
import com.xyz.eventscheduling.entity.TalkTrack;

public class ProgramStream {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	// instance fields: these variables are the "attributes" of a schedule Event Programming  
	// these variables are global.
	private String dateString;
	private String modifiedDateString;
	private int sessionCount;      // total number of session in the track 
	private int totalAllocatedDuration;  // total allocated talks in the session 
	private int capacity;       // capacity of session
	private int totalSessionsDuration;  // total allocated sessions 
	private int totalCapacity; // capacity of track
	private int trackCount;
	private TalkStream talkStream;
	private TalkTrack talkTrack;
	private TalkEvent talkEvent;

	// ----------------------------------------------------------- 
	//  Creates an empty shopping cart with a capacity of 5 items. 
	// 
	//  the instance fields declared above get initialized in 
	//  the constructor method 
	// ----------------------------------------------------------- 
	public ProgramStream(String dateString) { 
		this.dateString = dateString;
		this.modifiedDateString = dateString;
		capacity = 180; // session starts at 9.00 am and ends at 12.00 pm.
		totalSessionsDuration = 0;
		totalCapacity = 420;  // session starts at 9.00 am and ends at 12.00 pm. and 1 pm to 4.00 pm.
		sessionCount = 0; 
		totalAllocatedDuration = 0; 

		talkEvent = new TalkEvent();
		talkStream = new TalkStream(); 
		trackCount =0;
		talkTrack = new TalkTrack();
	} 

	// ----------------------------------------------------------- 
	//  Creates an empty shopping cart with a capacity of 5 items. 
	// 
	//  the instance fields declared above get initialized in 
	//  the constructor method 
	// ----------------------------------------------------------- 
	public ProgramStream() { 
		this.dateString = "2019-01-01 09:00";
		this.modifiedDateString = "2019-01-01 09:00";
		capacity = 180; // session starts at 9.00 am and ends at 12.00 pm. 
		totalCapacity = 420;   // session starts at 9.00 am and ends at 12.00 pm. and 1 pm to 4.00 pm.
		sessionCount = 0; 
		totalSessionsDuration = 0;
		totalAllocatedDuration = 0; 		     
		talkStream = new TalkStream(); 
		talkTrack = new TalkTrack();
		trackCount =0;
		talkTrack = new TalkTrack();
	} 

	public TalkEvent getTalkEvent() {
		return talkEvent;		
	}

	// ------------------------------------------------------- 
	//  Adds an Talk to the Program Stream. 
	// ------------------------------------------------------- 
	public void addToStream(Talk talk) throws ParseException { 
		int duration = Integer.valueOf(talk.getDuration());

		if (totalSessionsDuration + duration >= totalCapacity) {
			// Next Track start time same for all 
			modifiedDateString = dateString;
			// Next Session duration capacity
			capacity = 180;
			addTrack(); 
		} else if (totalAllocatedDuration + duration >= capacity) {
			// Next session start time 
			modifiedDateString = new SimpleDateFormat("yyyy-MM-dd hh:mm").format(changeStartTime(dateString, 240));
			// Next Session duration capacity
			capacity = 240;
			totalSessionsDuration +=totalAllocatedDuration;
			addSession(); 
		}

		// create a new talk and assign it to stream 
		talk.setStartTime(getTimeSlot(modifiedDateString,totalAllocatedDuration));
		// adjust the talk Count and the total allocated duration appropriately 
		totalAllocatedDuration += duration; 
		talk.setEndTime(getTimeSlot(modifiedDateString, totalAllocatedDuration));
		talkStream.addTalk(talk); 		
	} 

	public TalkEvent addTalkstream(){
		talkTrack.addStream(talkStream);
		talkEvent.addTrack(talkTrack);
		return talkEvent;
	}

	Date changeStartTime(String dateString, int nextsessionStartAfter) throws ParseException{
		return getTimeSlot(dateString, nextsessionStartAfter);
	}

	Date getTimeSlot(String dateString, int currentAllocatedSession) throws ParseException{
		Date eventDate = new SimpleDateFormat(EventSchConstants.DATEPATTERN).parse(dateString);
		// convert date to calendar
		Calendar c = Calendar.getInstance();
		c.setTime(eventDate);
		c.add(Calendar.MINUTE, currentAllocatedSession);
		Date test = c.getTime();
		return test;
	}

	// --------------------------------------------------------- 
	//  Add Session  
	// --------------------------------------------------------- 
	private void addSession() throws ParseException { 
		log.info("Session - "+totalAllocatedDuration);
		talkStream.setSessionCount(++sessionCount);
		if (2 == sessionCount ) {
			talkStream.addTalk(new Talk("Networking Event", getTimeSlot(dateString, 420)));
		}
		talkTrack.addStream(talkStream);
		talkStream = new TalkStream();
		totalAllocatedDuration =0;
	} 

	// --------------------------------------------------------- 
	//  Add track 
	// --------------------------------------------------------- 
	private void addTrack() { 
		log.info("Track - "+totalSessionsDuration);
		talkTrack.setTrackCount(++trackCount);
		talkEvent.addTrack(talkTrack);
		talkTrack = new TalkTrack();
		talkStream = new TalkStream();
		totalSessionsDuration =0;
		totalAllocatedDuration =0;
	} 
}
