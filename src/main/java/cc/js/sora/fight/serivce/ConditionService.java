package cc.js.sora.fight.serivce;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Fight;
import cc.js.sora.fight.Hero;
import cc.js.sora.fight.condition.CombinedCondition;
import cc.js.sora.fight.condition.EnemyHeroCondition;
import cc.js.sora.fight.condition.UserCondition;
import cc.js.sora.fight.condition.health.ForceHealthCondition;
import cc.js.sora.fight.db.HeroRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ConditionService {
	
	@Autowired
	HeroRepository heroRepository;
	
	public boolean checkCondition(Fight fight, Condition condition, Map<String, Boolean> userCondtionChecked, boolean isAttack)
	{
		if(condition instanceof CombinedCondition)
		{
			return (((CombinedCondition)condition).getConditions().stream().allMatch(c->checkCondition(fight, c, userCondtionChecked, isAttack)));
		}
		
		if(condition instanceof UserCondition)
		{
			String name = ((UserCondition)condition).getName();
			if(userCondtionChecked.containsKey(name))
			{
				return userCondtionChecked.get(name);
			}else
			{
				return ((UserCondition)condition).defaultValid();
			}
		}
		
		if(condition instanceof ForceHealthCondition)
		{
			if(isAttack)
			{
				return ((ForceHealthCondition)condition).valid(fight.getAttackerLife(), fight.getAttackerSoldierLife(), fight.getAttackerHeroLeftLife(), 
						fight.getAttackerSoldierLeftLife(), fight.getDefenderLife(), fight.getDefenderSoldierLife(),
						fight.getDefenderHeroLeftLife(), fight.getDefenderSoldierLeftLife());
			} else
			{
				return ((ForceHealthCondition)condition).valid(fight.getDefenderLife(), fight.getDefenderSoldierLife(),
						fight.getDefenderHeroLeftLife(), fight.getDefenderSoldierLeftLife(), fight.getAttackerLife(), fight.getAttackerSoldierLife(), fight.getAttackerHeroLeftLife(), 
						fight.getAttackerSoldierLeftLife());
			}

		}
		
		if(condition instanceof EnemyHeroCondition)
		{
			Hero h = null;
			if(isAttack)
			{
				h = heroRepository.getOne(fight.getDefenderHeroId());
				if(h != null)
				{
					return ((EnemyHeroCondition)condition).valid(h);
				}
				 
			} else
			{
				h = heroRepository.getOne(fight.getAttackerHeroId());
				if(h != null)
				{
					return ((EnemyHeroCondition)condition).valid(h);
				}
			}
			
		}
		
		return false;
		
	}

}
