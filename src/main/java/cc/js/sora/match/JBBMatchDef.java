package cc.js.sora.match;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class JBBMatchDef implements MatchDef
{

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
		// TODO Auto-generated method stub
		return null;
	}
	
}
