package cc.js.sora.match.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;

import cc.js.sora.ErrorMessage;
import cc.js.sora.match.JBBMatchDef;
import cc.js.sora.match.KKBMatchDef;
import cc.js.sora.match.MatchDef;
import cc.js.sora.match.MatchType;
import cc.js.sora.match.Player;
import cc.js.sora.match.Record;
import cc.js.sora.match.Season;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MatchDefService {
	
	@Autowired
	JBBMatchDef jbb;
	
	@Autowired
	KKBMatchDef kkb;
	
	public MatchDefService()
	{
	}

	
	public MatchDef getMatchDef(String matchType) {
		if(MatchType.JBB.equals(matchType)) {
			return jbb;
		} else if (MatchType.KKB.equals(matchType)) {
			return kkb;
		}
		return new MatchDef() {

			@Override
			public int getStageNumber() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public void scheduleRecords(List<Record> record) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public List<Record> planRecords(Season season, int stage) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public List<Player> getWinner(Season season, int stage) {
				// TODO Auto-generated method stub
				return null;
			}};
	}
	
	public List<Record> planRecords(Season season, int stage) {
		
		MatchDef def = this.getMatchDef(season.getMatchType());
		log.info("loaded Match definition "+def.getClass().getCanonicalName());
		if(stage > def.getStageNumber()) 
		{
			throw new ErrorMessage(10009, "没有第"+stage+"轮比赛");
		}
		
		List<Record> result = def.planRecords(season, stage);
		
		if(result == null) 
		{
			return new ArrayList<Record>();
		}
		result.stream().forEach(r->r.setStage(stage));
		def.scheduleRecords(result);
		
		return result;
	}
	

	

}
