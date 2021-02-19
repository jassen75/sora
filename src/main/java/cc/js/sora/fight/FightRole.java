package cc.js.sora.fight;

import java.util.Map;

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
	
	PanelInfo hero;
	PanelInfo soldier;
	Land land;
	Map<String, Boolean> userConditionChecked;
	int enhance;
	
	int heroLeftLife;
	int soldierLeftLife;

	Map<String, Equip> equip;
	
	Boolean heroCriticalChecked;
	Boolean soldierCriticalChecked;
	
	Action action;
	
	
}
