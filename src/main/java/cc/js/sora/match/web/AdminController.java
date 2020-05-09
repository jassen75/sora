package cc.js.sora.match.web;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cc.js.sora.match.db.PlayerRepository;
import cc.js.sora.match.db.RecordRepository;
import cc.js.sora.match.db.SeasonRepository;
import cc.js.sora.match.Player;
import cc.js.sora.match.Record;
import cc.js.sora.match.Season;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	PlayerRepository playerRepository;

	@Autowired
	SeasonRepository seasonRepository;

	@Autowired
	RecordRepository recordRepository;

	@GetMapping(path = "/planning", produces = MediaType.APPLICATION_JSON_VALUE)
	public Season getPlanningSeason() {
		int planningNumber = getRunningSeasonNumber() + 1;
		Season planningSeason = seasonRepository.getSeason(planningNumber);
		if (planningSeason == null) {
			planningSeason = new Season();
			planningSeason.setNumber(planningNumber);
			planningSeason.setStatus(0);

			if (planningNumber == 1) {
				planningSeason.setPlayers(playerRepository.findAll());
			} else {
				Season lastSeason = seasonRepository.getSeason(planningNumber - 1);
				List<Player> players = new ArrayList();
				lastSeason.getPlayers().forEach(player -> players.add(player));
				planningSeason.setPlayers(players);
			}

			seasonRepository.saveAndFlush(planningSeason);
		}

		return planningSeason;
	}

	public Season getCurrentSeason() {
		Season season = this.getRunningSeason();
		if (season == null) {
			return this.getPlanningSeason();
		}
		return season;
	}

	public Season getRunningSeason() {
		Season runningSeason = seasonRepository.getRunningSeason();

		return runningSeason;
	}

	public int getRunningSeasonNumber() {
		Season runningSeason = seasonRepository.getRunningSeason();

		return runningSeason != null ? runningSeason.getNumber() : 0;
	}

	@RequestMapping(path = "/setPlanningTime", method = RequestMethod.POST)
	public void setPlanningTime(@RequestParam String date) {

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date d;
		try {
			d = dateFormat.parse(date);
			Season season = getPlanningSeason();
			season.setMatchTime(d);
			// List<Player> players= new ArrayList();
			// season.getPlayers().forEach(player -> players.add(player));
			// season.setPlayers(players);
			seasonRepository.saveAndFlush(season);
		} catch (ParseException e) {
			throw new RuntimeException("couldn't parse date" + date);
		}

	}

	@RequestMapping(path = "/removePlayer", method = RequestMethod.POST)
	public void removePlayer(@RequestParam String name, @RequestParam String server) {
		Season s = getPlanningSeason();
		s.getPlayers().removeIf(player -> player.getName().equals(name) && player.getServer().equals(server));
		seasonRepository.saveAndFlush(s);
	}

	@RequestMapping(path = "/addPlayer", method = RequestMethod.POST)
	public void addPlayer(@RequestParam String name, @RequestParam String server) {
		Player player = playerRepository.findPlayer(name, server);
		if (player == null) {
			player = new Player();
			player.setName(name);
			player.setServer(server);
			playerRepository.save(player);
		}
		Season s = getPlanningSeason();
		if (!s.getPlayers().stream().anyMatch(p -> p.getName().equals(name) && p.getServer().equals(server))) {
			s.getPlayers().add(player);
			seasonRepository.saveAndFlush(s);
		}

	}

	@RequestMapping(path = "/setScore", method = RequestMethod.POST)
	public void setScore(@RequestParam String player1, @RequestParam String player2, @RequestParam String score)
	{
		Season s = getRunningSeason();
		
		if(s == null) {
			throw new RuntimeException("season have not started!");
		}
		List<Record> records = recordRepository.findRecordBySeason(s.getNumber());
		
		Record record = records.stream().filter(r->(r.getPlayer1().getName().equals(player1) && r.getPlayer2().getName().equals(player2)) || 
				(r.getPlayer1().getName().equals(player2) && r.getPlayer2().getName().equals(player1))).findFirst().get();
		
		String[] p2 = score.trim().split(":");
		
		if(p2.length != 2) 
		{
			throw new RuntimeException(p2+" is not illegal");
		}
		
		int score1 = Integer.parseInt(p2[0]);
		int score2 = Integer.parseInt(p2[1]);
		if(record.getPlayer1().getName().equals(player1) && record.getPlayer2().getName().equals(player2))
		{
			record.setScore1(score1);
			record.setScore2(score2);
		}
		
		else if(record.getPlayer1().getName().equals(player2) && record.getPlayer2().getName().equals(player1))
		{
			record.setScore1(score2);
			record.setScore2(score1);
		} else
		{
			throw new RuntimeException("some trouble happened!");
		}
		recordRepository.saveAndFlush(record);
	}

	@RequestMapping(path = "/startSeason", method = RequestMethod.POST)
	public void startSeason() {
		Season s = getPlanningSeason();
		if (s.getMatchTime() != null && s.getMatchTime().getTime() > new Date().getTime()) {
			checkRunning();
			s.setStatus(1);
			seasonRepository.saveAndFlush(s);
		} else {
			throw new RuntimeException("比赛时间已过");
		}

	}

	@GetMapping(path = "/players", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Player> list() {
		Season s = this.getRunningSeason();
		if (s == null) {
			return new ArrayList();
		}
		return s.getPlayers();
	}

	private void checkRunning() {
		Season running = this.getRunningSeason();
		if (running != null) {
			List<Record> recordList = recordRepository.findRecordBySeason(running.getNumber());
			if (recordList.stream().anyMatch(record -> record.getScore1() == -1 || record.getScore2() == -1)) {
				throw new RuntimeException("比赛还没结束");
			}
			running.setStatus(2);
			seasonRepository.saveAndFlush(running);
		}
	}

}
