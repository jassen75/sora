package cc.js.sora.match.definition;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cc.js.sora.ErrorMessage;
import cc.js.sora.match.MatchDef;
import cc.js.sora.match.MatchType;
import cc.js.sora.match.Player;
import cc.js.sora.match.Record;
import cc.js.sora.match.Role;
import cc.js.sora.match.Season;
import cc.js.sora.match.db.RecordRepository;
import cc.js.sora.match.service.ChallengerModeService;
import cc.js.sora.match.service.KnockoutModeService;
import lombok.extern.slf4j.Slf4j;

/**
 * A Match orgnized by KlunKI 
 * 
 * @author jassen
 *
 */
@Component
@Slf4j
public class KKBMatchDef implements MatchDef
{
	@Autowired
	ChallengerModeService challengerModeService;
	
	@Autowired
	KnockoutModeService KnockoutModeService;
	
	@Autowired
	RecordRepository recordRepository;
	
	@Override
	public int getStageNumber() {
		return 2;
	}

	@Override
	public void scheduleRecords(List<Record> record) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Record> planRecords(Season season, int stage) {
		if(stage==1) 
		{
			log.info("challengerModeService:"+challengerModeService);
			return challengerModeService.scheduleRecords(season, 2);
		} else if (stage == 2) 
		{
			return KnockoutModeService.scheduleRecords(season, this.getWinner(season, 1));
		}
		throw new ErrorMessage(10009, "KK杯 只有两轮");
	}

	@Override
	public List<Player> getWinner(Season season, int stage) {
		List<Record> list = recordRepository.findRecordBySeason(MatchType.KKB, season.getNumber(), stage);
		List<Player> winners = new ArrayList<Player>();
		
		season.getRoles().stream().forEach(role->{
			if(role.getName().equals(Role.ROLE_CHALLENGER))
			{
				// find 2 win records
				int win = 0;
				for(int i=0; i<list.size(); i++) {
					Player winner = list.get(i).getWinner();
					if(winner!=null && winner.getId()==role.getPlayer().getId()) {
						win=win+1;
					}
				}
				
				if(win == 2) {
					winners.add(role.getPlayer());
				}
			}
			
		});
		if(winners.size() < 2) 
		{
			throw new ErrorMessage(10009, "没有足够的胜利者进入下一轮");
		}
		List<Player> result = new ArrayList<Player>();
		for(int j=0; j<2; j++) 
		{
			result.add(winners.get(j));
		}
		
		return result;
	}

}