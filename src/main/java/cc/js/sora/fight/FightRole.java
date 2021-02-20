package cc.js.sora.fight;

import java.util.List;
import java.util.Map;

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
	int enhance;
	int heroLeftLife;
	int soldierLeftLife;
	Map<String, Equip> equip;
	Boolean heroCriticalChecked;
	Boolean soldierCriticalChecked;
	Action action;
	
	
	PanelInfo heroPanel;
	PanelInfo soldierPanel;
	List<Buff> buffList;
	List<Debuff> debuffList;
//	Map<String, UserCondition> userConditions;
//	List<CheckedSkill> checkedSkills = Lists.newArrayList();
	
	int range;
}
