package cc.js.sora.match;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cc.js.sora.ErrorMessage;
import cc.js.sora.match.service.ChallengerModeService;
import cc.js.sora.match.service.KnockoutModeService;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class KKBMatchDef implements MatchDef
{
	@Autowired
	ChallengerModeService challengerModeService;
	
	@Autowired
	KnockoutModeService KnockoutModeService;
	
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
			return KnockoutModeService.scheduleRecords(season, 4);
		}
		throw new ErrorMessage(10009, "KK杯 只有两轮");
	}

}