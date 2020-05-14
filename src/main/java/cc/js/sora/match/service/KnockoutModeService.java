package cc.js.sora.match.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import cc.js.sora.match.Player;
import cc.js.sora.match.Record;
import cc.js.sora.match.Season;

@Service
public class KnockoutModeService {

	public List<Record> scheduleRecords(Season season, List<Player> players)
	{
		Random random = new Random();
		Date current = season.getMatchTime();
		List<Record> result = new ArrayList<Record>();
		for(int i=0; i<players.size()-1; i+=2) 
		{
			Record newRecord = new Record();
			newRecord.setPlayer1(players.get(i));
			newRecord.setPlayer2(players.get(i+1));
			newRecord.setMatchTime(current);
			newRecord.setSeason(season);
			newRecord.setScore1(-1);
			newRecord.setScore2(-1);
			newRecord.setGroup(1);
			newRecord.setMap(random.nextInt(7)+1);
			result.add(newRecord);
		}
		return result;
	}
}
