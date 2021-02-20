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
public class FightRole {
	
	Hero hero;
	Soldier soldier;
	PanelInfo heroPanel;
	PanelInfo soldierPanel;
	Land land;
	Map<String, Boolean> userConditionChecked;
	int enhance;
	
	int heroLeftLife;
	int soldierLeftLife;

	Map<String, Equip> equip;
	
	Boolean heroCriticalChecked;
	Boolean soldierCriticalChecked;
	
	Action action;
	
	List<Buff> buffList;
	List<Debuff> debuffList;
	
	List<Skill> skills;
	List<UserCondition> userConditions;
	
	int range;
}
