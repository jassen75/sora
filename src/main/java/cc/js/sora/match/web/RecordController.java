package cc.js.sora.match.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cc.js.sora.match.db.PlayerRepository;
import cc.js.sora.match.db.RecordRepository;
import cc.js.sora.match.db.SeasonRepository;
import cc.js.sora.match.service.ChallengerModeService;
import lombok.extern.slf4j.Slf4j;
import cc.js.sora.match.Player;
import cc.js.sora.match.Record;
import cc.js.sora.match.Season;
import cc.js.sora.match.SeasonStatus;

@RestController
@RequestMapping("/record")
@Slf4j
public class RecordController {

	private static final Logger log = LoggerFactory.getLogger(RecordController.class);

	@Autowired
	RecordRepository recordRepository;

	@Autowired
	PlayerRepository playerRepository;

	@Autowired
	SeasonRepository seasonRepository;
	
	@Autowired
	ChallengerModeService challengerModeService;
	
	@Autowired
	AdminController admin;
	
	
	@GetMapping(path = "/{match_type}/{season}", produces = MediaType.APPLICATION_JSON_VALUE)
	List<Record> getRecords(@PathVariable(name = "season") Integer season) {
		List<Record> currentRecord = recordRepository.findRecordBySeason(season);
		return currentRecord;
	}
	
	

	private static class Pair {
		int i;
		int j;
		
		public String toString()
		{
			return "("+i+","+j+")";
		}
	}

	@GetMapping(path = "/{matchType}/{season}/score-board", produces = MediaType.APPLICATION_JSON_VALUE)
	List<Map<String, Object>> scoreBoard(@PathVariable String matchType, @PathVariable(name = "season") Integer season) {
		Map<String, Map<String, Integer>> result = new HashMap();

		Season s = seasonRepository.getSeason(matchType, season);
		
		List<Player> playerList = s.getPlayers();
		for (int i = 0; i < playerList.size(); i++) {
			Map r = new HashMap();
			r.put("name", playerList.get(i).getName());
			r.put("win", 0);
			r.put("lose", 0);
			r.put("draw", 0);
			r.put("score", 0);
			result.put(playerList.get(i).getName(), r);
		}

		List<Record> recordList = recordRepository.findRecordBySeason(season);
		for (int i = 0; i < recordList.size(); i++) {
			Record r = recordList.get(i);
			if (r.getScore1() == -1 || r.getScore2() == -1) {
				continue;
			}
			if (r.getScore1() > r.getScore2()) {
				result.get(r.getPlayer1().getName()).put("win", result.get(r.getPlayer1().getName()).get("win") + 1);
				result.get(r.getPlayer1().getName()).put("score",
						result.get(r.getPlayer1().getName()).get("score") + 3);
				result.get(r.getPlayer2().getName()).put("lose", result.get(r.getPlayer1().getName()).get("lose") + 1);
			}

			if (r.getScore1() < r.getScore2()) {
				result.get(r.getPlayer2().getName()).put("win", result.get(r.getPlayer2().getName()).get("win") + 1);
				result.get(r.getPlayer2().getName()).put("score",
						result.get(r.getPlayer2().getName()).get("score") + 3);
				result.get(r.getPlayer1().getName()).put("lose", result.get(r.getPlayer1().getName()).get("lose") + 1);
			}

			if (r.getScore1() == r.getScore2()) {
				result.get(r.getPlayer1().getName()).put("draw", result.get(r.getPlayer1().getName()).get("draw") + 1);
				result.get(r.getPlayer2().getName()).put("draw", result.get(r.getPlayer2().getName()).get("draw") + 1);
				result.get(r.getPlayer1().getName()).put("score",
						result.get(r.getPlayer1().getName()).get("score") + 1);
				result.get(r.getPlayer2().getName()).put("score",
						result.get(r.getPlayer2().getName()).get("score") + 1);
			}
		}
		
		List<Map<String,Object>> list = new ArrayList(result.values());
		list.sort((e, f) -> (Integer)f.get("score") - (Integer)e.get("score") );
		return list;

	}

	public List rebuildRecord(Season currentSeason ) {
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
					return rebuildRecord(currentSeason);
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
