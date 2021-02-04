package cc.js.sora.fight;

import java.util.List;
import java.util.Map;

import cc.js.sora.fight.condition.UserCondition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Fight {
	
	long attackerHeroId;
	
	long defenderHeroId;

	long attackerSoldierId;
	
	long defenderSoldierId;
	
	int attackerAttck;
	int attackerPhysicDef;
	int attackerLife;
	int attackerMagicDef;
	int attackerIntel;
	int attackerTech;
	
	int defenderAttck;
	int defenderPhysicDef;
	int defenderLife;
	int defenderMagicDef;
	int defenderIntel;
	int defenderTech;
	
	int attackerHeroLeftLife;
	int attackerSoldierLeftLife;
	int defenderHeroLeftLife;
	int defenderSoldierLeftLife;
	
	Map<UserCondition, Boolean> userConditions;
	
	public List<Skill> getSkills(long heroId, long soldierId, boolean isAttacker)
	{
		return null;
	}
	

}
