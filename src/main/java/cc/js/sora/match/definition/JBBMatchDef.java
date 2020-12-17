package cc.js.sora.match.definition;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cc.js.sora.match.MatchDef;
import cc.js.sora.match.Player;
import cc.js.sora.match.Record;
import cc.js.sora.match.Season;
import cc.js.sora.match.service.RoundModeService;

@Component
public class JBBMatchDef implements MatchDef
{
	@Autowired
	RoundModeService roundModeService;

	@Override
	public int getStageNumber() {
		return 1;
	}

	@Override
	public void scheduleRecords(List<Record> record) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Record> planRecords(Season season, int stage) {
		return roundModeService.scheduleRecords(season);
	}

	@Override
	public List<Player> getWinner(Season season, int stage) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
