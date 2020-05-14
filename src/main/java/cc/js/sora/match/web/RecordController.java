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
	
	
	@GetMapping(path = "/{matchType}/{season}/stage/{stage}", produces = MediaType.APPLICATION_JSON_VALUE)
	List<Record> getRecords(@PathVariable String matchType, @PathVariable(name = "season") Integer season,  @PathVariable Integer stage) {
		List<Record> currentRecord = recordRepository.findRecordBySeason(matchType, season, stage);
		return currentRecord;
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

		List<Record> recordList = recordRepository.findRecordBySeason(matchType, season);
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

}
