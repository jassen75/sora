package cc.js.sora.fight.condition;

import cc.js.sora.fight.Condition;
import cc.js.sora.fight.FightInfo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NoFeatureCondition implements Condition {

	String featureName;
	String desc;

	public NoFeatureCondition(String featureName, String desc) {
		this.featureName = featureName;
		this.desc = desc;
	}

	@Override
	public String getDesc() {
		// TODO Auto-generated method stub
		return desc;
	}

	@Override
	public boolean valid(FightInfo fightInfo, boolean isAttack) {
		if (fightInfo.getEnemyRole(isAttack).getHeroPanel().getFeatures().containsKey(featureName)) {
//			log.info(fightInfo.getEnemyRole(isAttack).getHero().getName() + " feature[" + featureName + "]"
//					+ fightInfo.getEnemyRole(isAttack).getHeroPanel().getFeatures().get(featureName));
			return !(Boolean) fightInfo.getEnemyRole(isAttack).getHeroPanel().getFeatures().get(featureName);
		}
		// TODO Auto-generated method stub
		return true;
	}

}
