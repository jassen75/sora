package cc.js.sora.fight;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;

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
public class FightRole {
	
	Hero hero;
	Soldier soldier;

	Land land = Land.Flat;
	Map<String, Boolean> userConditionChecked;
	Map<String, Integer> buffCounts;
	int enhance;
	int heroLeftLife;
	int soldierLeftLife;
	Map<String, Equip> equip;
	Boolean heroCriticalChecked;
	Boolean soldierCriticalChecked;
	Action action;
	
	PanelInfo heroPanel;
	PanelInfo soldierPanel;
	List<Buff> buffs;
	List<Debuff> debuffs;
//	Map<String, UserCondition> userConditions;
//	List<CheckedSkill> checkedSkills = Lists.newArrayList();
	
	int range;
	
	// 1 battle attacker 2 battle defender 3 aoe attacker 4 aoe defender
	int roleType;
	
	@JsonIgnore
	public double getLifePercent()
	{
		return (Double.valueOf(heroLeftLife)+Double.valueOf(soldierLeftLife))/(Double.valueOf(heroPanel.getLife())+Double.valueOf(soldierPanel.getLife()));
	}
}
