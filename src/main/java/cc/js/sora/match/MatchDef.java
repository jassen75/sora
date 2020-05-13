package cc.js.sora.match;

import java.util.List;

public interface MatchDef {
	
	
	int getStageNumber();
	
	void scheduleRecords(List<Record> record);
	
	List<Record> planRecords(Season season, int stage);

}
