package cc.js.sora.fight.condition;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.Condition;
import cc.js.sora.fight.FightInfo;
import cc.js.sora.fight.Land;

public class DefLandCondition implements Condition {

	@Override
	public String getDesc() {
		return "处于防御地形";
	}

	private List<Land> defLand = Lists.newArrayList(Land.Mountain, Land.Wall, Land.Wood, Land.Stone);
	
	@Override
	public boolean valid(FightInfo fightInfo, boolean isAttack) {
		Land land = fightInfo.getRole(isAttack).getLand();
		return defLand.stream().anyMatch(l-> l==land);
	}

}
