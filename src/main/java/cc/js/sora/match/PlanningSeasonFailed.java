package cc.js.sora.match;

import cc.js.sora.ErrorMessage;

@SuppressWarnings("serial")
public class PlanningSeasonFailed extends ErrorMessage { 
	
	public PlanningSeasonFailed(SeasonStatus status)
	{
		this.message = "无法计划新赛季，因为最近赛季状态是"+status;
		this.code = 10001;
	}
	
	

}
