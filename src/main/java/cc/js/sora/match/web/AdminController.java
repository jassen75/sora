package cc.js.sora.match.web;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cc.js.sora.ErrorMessage;
import cc.js.sora.ResponseMessage;
import cc.js.sora.match.PlanningSeasonFailed;
import cc.js.sora.match.Player;
import cc.js.sora.match.Record;
import cc.js.sora.match.Role;
import cc.js.sora.match.Season;
import cc.js.sora.match.SeasonStatus;
import cc.js.sora.match.db.PlayerRepository;
import cc.js.sora.match.db.RecordRepository;
import cc.js.sora.match.db.RoleRepository;
import cc.js.sora.match.db.SeasonRepository;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController {

	@Autowired
	PlayerRepository playerRepository;

	@Autowired
	SeasonRepository seasonRepository;

	@Autowired
	RecordRepository recordRepository;

	@Autowired
	RoleRepository roleRepository;

	public Season getPlanningSeason() {
		int planningNumber = getMaxCompleteSeasonNumber() + 1;
		Season planningSeason = seasonRepository.getSeason(planningNumber);
		if (planningSeason == null) {
			log.info("*********************************************************************");
			log.info("planning new season, season " + planningNumber);
			log.info("*********************************************************************");
			planningSeason = new Season();
			planningSeason.setNumber(planningNumber);
			planningSeason.setStatus(SeasonStatus.PLANNING);

			if (planningNumber == 1) {
				planningSeason.setPlayers(playerRepository.findAll());
			} else {
				Season lastSeason = seasonRepository.getSeason(planningNumber - 1);
				List<Player> players = new ArrayList();
				lastSeason.getPlayers().forEach(player -> players.add(player));
				planningSeason.setPlayers(players);
			}

			seasonRepository.saveAndFlush(planningSeason);
		} else if (planningSeason.getStatus() == SeasonStatus.RUNNING) {
			throw new PlanningSeasonFailed(planningSeason.getStatus());
		} else if (planningSeason.getStatus() == SeasonStatus.UNKNOWN) {
			planningSeason.setStatus(SeasonStatus.PLANNING);
			seasonRepository.saveAndFlush(planningSeason);
		}

		return planningSeason;
	}

	@GetMapping(path = "/seasons", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Season> getSeasons() {

		return seasonRepository.findAll();
	}

	@GetMapping(path = "/currentSeason", produces = MediaType.APPLICATION_JSON_VALUE)
	public Season getCurrentSeason() {
		Season season = this.getRunningSeason();
		if (season == null) {
			return this.getPlanningSeason();
		}
		return season;
	}

	@GetMapping(path = "/runningSeason", produces = MediaType.APPLICATION_JSON_VALUE)
	public Season getRunningSeason() {
		Season runningSeason = seasonRepository.getRunningSeason();

		return runningSeason;
	}

	public int getRunningSeasonNumber() {
		Season runningSeason = seasonRepository.getRunningSeason();

		return runningSeason != null ? runningSeason.getNumber() : 0;
	}

	public int getMaxCompleteSeasonNumber() {
		List<Season> seasons = seasonRepository.getCompleteSeasons();
		if (seasons.size() > 0) {
			return seasons.get(seasons.size() - 1).getNumber();
		} else {
			return 0;
		}

	}

	@RequestMapping(path = "/setPlanningTime", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseMessage setPlanningTime(@RequestParam String date) {

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
			return ResponseMessage.successMessage();
		} catch (ParseException e) {
			throw new ErrorMessage(99998, "couldn't parse date" + date);
		}

	}

	@RequestMapping(path = "/removePlayer", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseMessage removePlayer(@RequestParam String name, @RequestParam String server) {
		Season s = getPlanningSeason();
		s.getPlayers().removeIf(player -> player.getName().equals(name) && player.getServer().equals(server));
		seasonRepository.saveAndFlush(s);
		return ResponseMessage.successMessage();
	}

	@RequestMapping(path = "/removeChallenger", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseMessage removeChallenger(@RequestParam String name, @RequestParam String server) {
		Season s = getPlanningSeason();
		List<Role> roles = roleRepository.findRoleBySeason(s.getNumber());
		roles.forEach(
				role->{
					if(role.getPlayer().getName().equals(name) && role.getPlayer().getServer().equals(server))
						roleRepository.delete(role);
					});
		return ResponseMessage.successMessage();
	}

	@RequestMapping(path = "/addPlayer", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseMessage addPlayer(@RequestParam String name, @RequestParam String server) {

		if (name == null || "".equals(name)) {
			throw new ErrorMessage(40000, "空的队员名字");
		}

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

		return ResponseMessage.successMessage();

	}

	@GetMapping(path = "/challengers", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Player> challengers() {
		Season s = getPlanningSeason();
		List<Player> result = new ArrayList<Player>();
		roleRepository.findRoleBySeason(s.getNumber()).stream().forEach(role -> result.add(role.getPlayer()));
		return result;
	}

	@RequestMapping(path = "/addChallenger", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseMessage addChallenger(@RequestParam String name, @RequestParam String server) {

		if (name == null || "".equals(name)) {
			throw new ErrorMessage(40000, "空的队员名字");
		}

		Season s = getPlanningSeason();
		if (!s.getPlayers().stream().anyMatch(p -> p.getName().equals(name) && p.getServer().equals(server))) {
			throw new ErrorMessage(40000, "该队员并没有参赛");
		}
		Player player = playerRepository.findPlayer(name, server);
		if (player == null) {
			throw new ErrorMessage(40000, "没有找到该队员");
		}
		Role role = new Role();
		role.setName("challenger");
		role.setPlayer(player);
		role.setSeason(s);
		roleRepository.saveAndFlush(role);
		return ResponseMessage.successMessage();

	}

	@RequestMapping(path = "/setScore", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseMessage setScore(@RequestParam String player1, @RequestParam String player2,
			@RequestParam String score) {
		Season s = getRunningSeason();

		if (s == null) {
			throw new ErrorMessage(10005, "season have not started!");
		}
		List<Record> records = recordRepository.findRecordBySeason(s.getNumber());

		Record record = records.stream()
				.filter(r -> (r.getPlayer1().getName().equals(player1) && r.getPlayer2().getName().equals(player2))
						|| (r.getPlayer1().getName().equals(player2) && r.getPlayer2().getName().equals(player1)))
				.findFirst().get();

		String[] p2 = score.trim().split(":");

		if (p2.length != 2) {
			throw new RuntimeException(p2 + " is not illegal");
		}

		int score1 = Integer.parseInt(p2[0]);
		int score2 = Integer.parseInt(p2[1]);
		if (record.getPlayer1().getName().equals(player1) && record.getPlayer2().getName().equals(player2)) {
			record.setScore1(score1);
			record.setScore2(score2);
		}

		else if (record.getPlayer1().getName().equals(player2) && record.getPlayer2().getName().equals(player1)) {
			record.setScore1(score2);
			record.setScore2(score1);
		} else {
			throw new ErrorMessage(99999, "some trouble happened!");
		}
		log.info("save match score:" + record);
		recordRepository.saveAndFlush(record);

		checkComplete();
		return ResponseMessage.successMessage();
	}

	@RequestMapping(path = "/startSeason", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseMessage startSeason() {

		Season running = this.getRunningSeason();
		if (running != null) {
			throw new ErrorMessage(10004, "比赛还没结束");
		}
		Season s = getPlanningSeason();
		if (s.getMatchTime() != null && s.getMatchTime().getTime() > new Date().getTime()) {
			s.setStatus(SeasonStatus.RUNNING);
			seasonRepository.saveAndFlush(s);

			// clear records if exist!
			log.warn(">>>>>  change season " + s.getNumber()
					+ " from planning to running, clear records if exist!<<<<<");
			List<Record> records = recordRepository.findRecordBySeason(s.getNumber());
			recordRepository.deleteAll(records);
			recordRepository.flush();
			return ResponseMessage.successMessage();
		} else {
			throw new ErrorMessage(10003, "比赛时间已过");
		}

	}

	@RequestMapping(path = "/cancelSeason", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseMessage cancelSeason() {
		Season s = this.getRunningSeason();
		if (s != null) {
			log.info("Season " + s.getNumber() + " is canceled by admin!");
			log.info(">>>>>  change season " + s.getNumber() + " from running to planning   <<<<<");
			s.setStatus(SeasonStatus.PLANNING);
			List<Record> records = recordRepository.findRecordBySeason(s.getNumber());
			recordRepository.deleteAll(records);

			seasonRepository.saveAndFlush(s);
			recordRepository.flush();
			return ResponseMessage.successMessage();
		} else {
			throw new ErrorMessage(10002, "赛季还没开始");
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

	private void checkComplete() {
		Season running = this.getRunningSeason();
		if (running != null) {
			List<Record> recordList = recordRepository.findRecordBySeason(running.getNumber());
			if (recordList.stream().anyMatch(record -> record.getScore1() == -1 || record.getScore2() == -1)) {
				return;
			} else {
				seasonComplete(running);
				getPlanningSeason();
			}
		}
	}

	private void seasonComplete(Season running) {
		log.info("*********************************************************************");
		log.info("all matchs of season " + running.getNumber() + " completedm stop season");
		log.info(">>>>>  change season " + running.getNumber() + " from running to complete   <<<<<");
		log.info("*********************************************************************");

		running.setStatus(SeasonStatus.COMPLETE);
		seasonRepository.saveAndFlush(running);
	}

}
