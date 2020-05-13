package cc.js.sora.match.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;

import cc.js.sora.ErrorMessage;
import cc.js.sora.match.JBBMatchDef;
import cc.js.sora.match.KKBMatchDef;
import cc.js.sora.match.MatchDef;
import cc.js.sora.match.MatchType;
import cc.js.sora.match.Record;
import cc.js.sora.match.Season;

@Service
public class MatchDefService {
	
	private Map<String, MatchDef> defs = Maps.newHashMap();
	
	public MatchDefService()
	{
		defs.put(MatchType.JBB, new JBBMatchDef());
		defs.put(MatchType.KKB, new KKBMatchDef());
	}

	
	public MatchDef getMatchDef(String matchType) {
		
		return defs.get(matchType);
		
	}
	
	public List<Record> planRecords(Season season, int stage) {
		
		MatchDef def = this.getMatchDef(season.getMatchType());
		if(stage > def.getStageNumber()) 
		{
			throw new ErrorMessage(10009, "没有第"+stage+"轮比赛");
		}
		
		List<Record> result = def.planRecords(season, stage);
		
		result.stream().forEach(r->r.setStage(stage));
		def.scheduleRecords(result);
		
		return result;
	}
	

	

}
