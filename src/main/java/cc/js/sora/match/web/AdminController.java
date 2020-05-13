package cc.js.sora.match.web;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;

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
import cc.js.sora.match.service.ChallengerModeService;
import cc.js.sora.match.service.MatchDefService;
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
	
	@Autowired
	MatchDefService matchDefService;

	public Season getPlanningSeason (String matchType) {
		int planningNumber = getMaxCompleteSeasonNumber(matchType) + 1;
		log.info("planning season:"+planningNumber+" for matchType:"+matchType);
		Season planningSeason = seasonRepository.getSeason(matchType, planningNumber);
		if (planningSeason == null) {
			log.info("*********************************************************************");
			log.info("planning new season, season " + planningNumber);
			log.info("*********************************************************************");
			planningSeason = new Season();
			planningSeason.setNumber(planningNumber);
			planningSeason.setStatus(SeasonStatus.PLANNING);
			planningSeason.setMatchType(matchType);
			if (planningNumber == 1) {
				planningSeason.setPlayers(playerRepository.findAll());
			} else {
				Season lastSeason = seasonRepository.getSeason(matchType, planningNumber - 1);
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

	@GetMapping(path = "/{matchType}/seasons", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Season> getSeasons(@PathVariable String matchType) {

		return seasonRepository.findSeasons(matchType);
	}
	
	@GetMapping(path = "/seasons", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Season> seasons(String matchType) {

		return seasonRepository.findAll();
	}

	@GetMapping(path = "/{matchType}/currentSeason", produces = MediaType.APPLICATION_JSON_VALUE)
	public Season getCurrentSeason(@PathVariable String matchType) {
		Season season = this.getRunningSeason(matchType);
		if (season == null) {
			return this.getPlanningSeason(matchType);
		}
		return season;
	}

	@GetMapping(path = "/{matchType}/runningSeason", produces = MediaType.APPLICATION_JSON_VALUE)
	public Season getRunningSeason(@PathVariable String matchType) {
		Season runningSeason = seasonRepository.getRunningSeason(matchType);

		return runningSeason;
	}

	public int getRunningSeasonNumber(String matchType) {
		Season runningSeason = seasonRepository.getRunningSeason(matchType);

		return runningSeason != null ? runningSeason.getNumber() : 0;
	}

	public int getMaxCompleteSeasonNumber(String matchType) {
		List<Season> seasons = seasonRepository.getCompleteSeasons(matchType);
		if (seasons.size() > 0) {
			return seasons.get(seasons.size() - 1).getNumber();
		} else {
			return 0;
		}

	}

	@RequestMapping(path = "/{matchType}/setPlanningTime", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseMessage setPlanningTime(@PathVariable String matchType, @RequestParam String date) {

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date d;
		try {
			d = dateFormat.parse(date);
			Season season = getPlanningSeason(matchType);
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

	@RequestMapping(path = "/{matchType}/removePlayer", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	public ResponseMessage removePlayer(@PathVariable String matchType, @RequestParam String name, @RequestParam String server) {
		Season s = getPlanningSeason(matchType);
		//s.getPlayers().removeIf(player -> player.getName().equals(name) && player.getServer().equals(server));
		Role role = roleRepository.findRole(matchType, s.getNumber(), name,  server);
		if (role != null) {
			roleRepository.delete(role);
			s.getRoles().removeIf(r->r.getPlayer().getName().equals(name) && r.getPlayer().getServer().equals(server));
			roleRepository.flush();
			seasonRepository.saveAndFlush(s);
			log.info("player:"+name+" removed!!");
		} else
		{
			log.info("can't find player:"+name);
		}
		
		return ResponseMessage.successMessage();
	}

	@RequestMapping(path = "/{matchType}/removeChallenger", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseMessage removeChallenger(@PathVariable String matchType, @RequestParam String name, @RequestParam String server) {
		Season s = getPlanningSeason(matchType);
		List<Role> roles = roleRepository.findAll();
		roles.forEach(role -> {
			if (role.getPlayer().getName().equals(name) && role.getPlayer().getServer().equals(server)
					&& role.getSeason().getNumber() == s.getNumber()
					&& role.getSeason().getMatchType() == s.getMatchType())
				role.setName(Role.ROLE_PLAYER);
			roleRepository.saveAndFlush(role);
		});
		return ResponseMessage.successMessage();
	}

	@RequestMapping(path = "/{matchType}/addPlayer", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseMessage addPlayer(@PathVariable String matchType, @RequestParam String name, @RequestParam String server) {

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
		Season s = getPlanningSeason(matchType);
		Role role = roleRepository.findRole(matchType, s.getNumber(), player.getName(), player.getServer());
		if (role == null) {
			role = new Role();
			role.setSeason(s);
			role.setPlayer(player);
		}
		role.setName(Role.ROLE_PLAYER);
		roleRepository.saveAndFlush(role);

		return ResponseMessage.successMessage();

	}

	@GetMapping(path = "/{matchType}/challengers", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Player> challengers(@PathVariable String matchType) {
		Season s = getPlanningSeason(matchType);
		List<Role> roles = roleRepository.findRoleBySeason(matchType, s.getNumber(), Role.ROLE_CHALLENGER);
		List<Player> players = new ArrayList();
		roles.forEach(role -> players.add(role.getPlayer()));
		return players;
	}

	@RequestMapping(path = "/{matchType}/addChallenger", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseMessage addChallenger(@PathVariable String matchType, @RequestParam String name, @RequestParam String server) {

		if (name == null || "".equals(name)) {
			throw new ErrorMessage(40000, "空的队员名字");
		}

		Season s = getPlanningSeason(matchType);
		Player player = playerRepository.findPlayer(name, server);
		if (player == null) {
			throw new ErrorMessage(40000, "没有找到该队员");
		}

		Role role = roleRepository.findRole(matchType, s.getNumber(), player.getName(), player.getServer());
		if (role == null) {
			role = new Role();
			role.setSeason(s);
			role.setPlayer(player);
		}
		role.setName(Role.ROLE_CHALLENGER);
		roleRepository.saveAndFlush(role);
		return ResponseMessage.successMessage();

	}

	@RequestMapping(path = "/{matchType}/setScore", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseMessage setScore(@PathVariable String matchType, @RequestParam String player1, @RequestParam String player2,
			@RequestParam String score) {
		Season s = getRunningSeason(matchType);

		if (s == null) {
			throw new ErrorMessage(10005, "season have not started!");
		}
		
		Record record = recordRepository.findRecord(matchType, s.getNumber(), player1, player2);
//		List<Record> records = recordRepository.findRecordBySeason(s.getNumber());
//
//		Record record = records.stream()
//				.filter(r -> (r.getPlayer1().getName().equals(player1) && r.getPlayer2().getName().equals(player2))
//						|| (r.getPlayer1().getName().equals(player2) && r.getPlayer2().getName().equals(player1)))
//				.findFirst().get();

		if(record==null)
		{
			throw new ErrorMessage(10008, player1+ " vs " + player2 + "的比赛没有找到");
		}
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

		checkComplete(matchType);
		return ResponseMessage.successMessage();
	}

	@RequestMapping(path = "/{matchType}/startSeason", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseMessage startSeason(@PathVariable String matchType) {

		Season running = this.getRunningSeason(matchType);
		if (running != null) {
			throw new ErrorMessage(10004, "比赛还没结束");
		}
		Season s = getPlanningSeason(matchType);
		if (s.getMatchTime() != null && s.getMatchTime().getTime() > new Date().getTime()) {
			
			// clear records if exist!
			log.warn(">>>>>  change season " + s.getNumber()
					+ " from planning to running, clear records if exist!<<<<<");
			List<Record> records = recordRepository.findRecordBySeason(matchType, s.getNumber());
			recordRepository.deleteAll(records);
			recordRepository.flush();
			
			records = matchDefService.planRecords(s, 1);
			
			
			// only save when running 
			log.info("schedule records for season "+s.getNumber());
			recordRepository.saveAll(records);
			recordRepository.flush();
			
			s.setStatus(SeasonStatus.RUNNING);
			seasonRepository.saveAndFlush(s);


			return ResponseMessage.successMessage();
		} else {
			throw new ErrorMessage(10003, "比赛时间已过");
		}

	}

	@RequestMapping(path = "/{matchType}/cancelSeason", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseMessage cancelSeason(@PathVariable String matchType) {
		Season s = this.getRunningSeason(matchType);
		if (s != null) {
			log.info("Season " + s.getNumber() + " is canceled by admin!");
			log.info(">>>>>  change season " + s.getNumber() + " from running to planning   <<<<<");
			s.setStatus(SeasonStatus.PLANNING);
			List<Record> records = recordRepository.findRecordBySeason(matchType, s.getNumber());
			recordRepository.deleteAll(records);

			seasonRepository.saveAndFlush(s);
			recordRepository.flush();
			return ResponseMessage.successMessage();
		} else {
			throw new ErrorMessage(10002, "赛季还没开始");
		}

	}

	@GetMapping(path = "/{matchType}/players", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Player> list(@PathVariable String matchType) {
		Season s = this.getRunningSeason(matchType);
		if (s == null) {
			return new ArrayList();
		}
		return s.getPlayers();
	}

	private void checkComplete(String matchType) {
		Season running = this.getRunningSeason(matchType);
		if (running != null) {
			List<Record> recordList = recordRepository.findRecordBySeason(matchType, running.getNumber());
			if (recordList.stream().anyMatch(record -> record.getScore1() == -1 || record.getScore2() == -1)) {
				return;
			} else {
				int maxStage = recordList.stream().max((x,y)->x.getStage()-y.getStage()).get().getStage();
				
				log.info("stage["+maxStage+"] complete");
				
				if(maxStage < matchDefService.getMatchDef(running.getMatchType()).getStageNumber()) 
				{
					log.info("start stage "+(maxStage+1));
					List<Record> newStageRecords = matchDefService.planRecords(running, maxStage+1);
					
					recordRepository.saveAll(newStageRecords);
					recordRepository.flush();
				} else {
				
					seasonComplete(running);
					getPlanningSeason(matchType);
				}
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
