package com.xyz.eventscheduling;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.xyz.eventscheduling.constants.EventSchConstants;
import com.xyz.eventscheduling.entity.Talk;
import com.xyz.eventscheduling.service.ProgramStream;

@SpringBootApplication
public class EventSchedulingApplication implements CommandLineRunner {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Value("${dateString}")
	private String dateString;

	ArrayList<Talk> talks = new ArrayList<>();

	public static void main(String[] args) {
		SpringApplication.run(EventSchedulingApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		try {
			ArrayList<String> lines= readEventsinFile();
			ArrayList<Talk> talkList = parseTalkEvents(lines);
			scheduleTrack(talkList);
		}
		catch(ParseException p) {
			log.info("Failed to read event data to provide schedule.");
		}
	}
	ArrayList<String> readEventsinFile() throws FileNotFoundException {
		ArrayList<String> lines= new ArrayList<>();
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(EventSchConstants.FILENAME).getFile());
		try (Scanner scanner = new Scanner(file)) {
			while(scanner.hasNextLine()) {
				lines.add(scanner.nextLine());
			}
		}
		return lines;		
	}
	/**
	 * 
	 * @param lines
	 * @return
	 * Note: avoid () in text. 
	 */
	ArrayList<Talk> parseTalkEvents(ArrayList<String> lines){
		final Pattern pattern = Pattern.compile(EventSchConstants.EVENTFORMAT);
		ArrayList<Talk> talkList = new ArrayList<>();
		lines.forEach(line ->  {
			if (line.toLowerCase().contains(EventSchConstants.TALKTYPE_LIGHTNING)) {
				Talk talk = new Talk();
				talk.setTitle(line.toLowerCase().replaceAll(EventSchConstants.TALKTYPE_LIGHTNING, EventSchConstants.CLEARTEXT));
				talk.setDuration("5");
				talkList.add(talk);
			} else {
				final Matcher matcher = pattern.matcher(line);
				while (matcher.find()) {
					Talk talk = new Talk();
					talk.setTitle(matcher.group(1));
					talk.setDuration(matcher.group(2));
					talkList.add(talk);
				}
			}
		});
		return talkList;
	}

	void scheduleTrack(ArrayList<Talk> talkList) throws ParseException{
		log.info("Scheduled Event Date ---"+dateString);
		ProgramStream programStream = new ProgramStream(dateString); 
			
		talkList.forEach(talk -> {
			try {
				programStream.addToStream(talk);
			} catch (ParseException e) {
				log.info("Failed to schedule event.");
			} 	
		});
		programStream.addTalkstream();
		programStream.getTalkEvent().getTracks().forEach(track ->{
			track.getStreams().forEach(stream -> { log.info("Session - {}",stream.getSessionCount());
			stream.getTalks().forEach(talk -> log.info("Talk - {} {} {}", talk.getTitle() , talk.getStartTime() , talk.getEndTime()));
			});
		});
	}
}
