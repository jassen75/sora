package cc.js.sora.fight;

import cc.js.sora.match.Player;
import cc.js.sora.match.Season;
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
	
	Hero attacker;
	
	Hero defender;
	
	Environment attackerEnv;
	
	Environment deffenderEnv;
	
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
	int defenderOntel;
	int defenderTech;
	

}
