package cc.js.sora.match.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cc.js.sora.ErrorMessage;
import cc.js.sora.match.Player;
import cc.js.sora.match.Record;
import cc.js.sora.match.Role;
import cc.js.sora.match.Season;
import cc.js.sora.match.db.PlayerRepository;
import cc.js.sora.match.db.RecordRepository;
import cc.js.sora.match.db.RoleRepository;
import cc.js.sora.match.db.SeasonRepository;
import cc.js.sora.match.web.AdminController;

@Service
public class ChallengerModeService {

	@Autowired
	RecordRepository recordRepository;

	@Autowired
	PlayerRepository playerRepository;

	@Autowired
	SeasonRepository seasonRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	AdminController admin;
	
	public List<Record> scheduleRecords(Season currentSeason , int times)
	{
		Random random = new Random();
		
		List<Record> result = new ArrayList<Record>();
		List<Player> players = Role.toPlayerList(roleRepository.findRoleBySeason(currentSeason.getNumber(), Role.ROLE_PLAYER));
		List<Player> challengers =   Role.toPlayerList(roleRepository.findRoleBySeason(currentSeason.getNumber(), Role.ROLE_CHALLENGER));
		int group=0;
		Date current = currentSeason.getMatchTime();
		while(challengers.size()>0) {
			int ci = random.nextInt(challengers.size());
			Player challenger = challengers.get(ci);
			
			for(int i=0; i<times; i++) 
			{
				if(players.size()==0) 
				{
					throw new ErrorMessage(10007, "挑战者人数不够");
				}
				int index = random.nextInt(players.size());
				Player player = players.get(index);
				Record newRecord = new Record();
				newRecord.setSeason(currentSeason);
				newRecord.setPlayer1(player);
				newRecord.setPlayer2(challenger);
				newRecord.setScore1(-1);
				newRecord.setScore2(-1);
				newRecord.setGroup(group);
				newRecord.setMap(random.nextInt(7)+1);
				newRecord.setMatchTime(current);
				result.add(newRecord);
				
				players.remove(index);
			}
			group++;
			
			if(group%2==1) {
				current = new Date(new Long(current.getTime() + 1 * 24 * 60 * 60 * 1000));
			} else
			{
				current = new Date(new Long(current.getTime() + 6 * 24 * 60 * 60 * 1000));
			}
			challengers.remove(ci);
		}
		return result;
	}
	
	

}
