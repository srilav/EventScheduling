package com.xyz.eventscheduling.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CalendarService {
		
	@Value("${dateString}")
	private String dateString;
	
    private CalendarService() {}

	static String pattern = "yyyy-MM-dd hh:mm";

	Date getTimeSlot(int currentAllocatedSession) throws ParseException {
		 Date eventDate = new SimpleDateFormat(pattern).parse(dateString);
			// convert date to calendar
	        Calendar c = Calendar.getInstance();
	        c.setTime(eventDate);
	        c.add(Calendar.MINUTE, currentAllocatedSession);
		 return c.getTime();
	}
	public static void main(String[] args) throws ParseException {
		CalendarService calSrv = new CalendarService(); 
		calSrv.getTimeSlot(30);
	}
	 
}
