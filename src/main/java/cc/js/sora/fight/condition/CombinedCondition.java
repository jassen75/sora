package cc.js.sora.fight.condition;

import java.util.ArrayList;
import java.util.List;

import cc.js.sora.fight.Condition;
import cc.js.sora.fight.FightInfo;

public class CombinedCondition implements Condition {

	List<Condition> conditions = new ArrayList();
	
	public List<Condition> getConditions() {
		return conditions;
	}

	public void setConditions(List<Condition> conditions) {
		this.conditions = conditions;
	}

	@Override
	public String getDesc() {
		StringBuilder sb = new StringBuilder();
		sb.append(conditions.get(0).getDesc());
		for(int i=1; i<conditions.size(); i++)
		{
			sb.append(",");
			sb.append(conditions.get(i).getDesc());
		}
		
		return sb.toString();
	}

	@Override
	public boolean valid(FightInfo fightInfo, boolean isAttack) {
		// TODO Auto-generated method stub
		return getConditions().stream().allMatch(c->c.valid(fightInfo, isAttack));
	}
	
	

}
