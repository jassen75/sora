package cc.js.sora.match.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.stereotype.Service;

import cc.js.sora.match.Player;
import cc.js.sora.match.Record;
import cc.js.sora.match.Season;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RoundModeService {
	
	

	private static class Pair {
		int i;
		int j;
		
		public String toString()
		{
			return "("+i+","+j+")";
		}
	}

	

	public List<Record> scheduleRecords(Season currentSeason) {
		Random random = new Random();
		Date startTime = currentSeason.getMatchTime();
		if(startTime == null) 
		{
			startTime = new Date();
		}
		
		List<Player> playerList = currentSeason.getPlayers();
		int number = playerList.size();

		List<Record> recordList = new ArrayList<Record>();

		List<Pair> all = new ArrayList();

		log.info("number. size==" + number);
		for (int i = 0; i < number; i++) {
			for (int j = 0; j < number; j++) {
				Pair p = new Pair();
				p.i = i;
				p.j = j;
				if (!all.stream().anyMatch(e -> (e.i == p.i && e.j == p.j) || (e.i == p.j && e.j == p.i))) {
					if (i != j) {
						all.add(p);
					}
				}
			}
		}

		log.info("all. size==" + all.size());
		Date current = startTime;
		int failedTime = 0;
		while (all.size() > 0) {
			Set p = new HashSet();
			List<Pair> weekRecords = new ArrayList();

			boolean failed = false;
			while (p.size() < number-1) {
				if (!all.stream().anyMatch(e -> !p.contains(e.i) && !p.contains(e.j))) {
					failed = true;
					failedTime++;
					break;
				}
				
				if(failedTime > 30) 
				{
					log.info("**********  failed too much ,rebuild****************");
					return scheduleRecords(currentSeason);
				}

				int index = random.nextInt(all.size());

				Pair pair = all.get(index);
				if (p.contains(pair.i))
					continue;
				if (p.contains(pair.j))
					continue;

				weekRecords.add(all.get(index));
				p.add(pair.i);
				p.add(pair.j);
			}

			if (!failed) {
				for (int k = 0; k < weekRecords.size(); k++) {
					Record newRecord = new Record();
					newRecord.setPlayer1(playerList.get(weekRecords.get(k).i));
					newRecord.setPlayer2(playerList.get(weekRecords.get(k).j));
					newRecord.setMatchTime(current);
					newRecord.setSeason(currentSeason);
					newRecord.setScore1(-1);
					newRecord.setScore2(-1);
					newRecord.setMap(random.nextInt(7)+1);
					recordList.add(newRecord);
					log.info("add record:" + newRecord);

					all.remove(weekRecords.get(k));

				}
				current = new Date(new Long(current.getTime() + 7 * 24 * 60 * 60 * 1000));

				log.info(
						"=================================================================================================");
			} else {
				log.info("**********  failed ****************all size:"+all.size());
			}
		}
		log.info("recordLise .size:" + recordList.size());
		return recordList;

	}

}
