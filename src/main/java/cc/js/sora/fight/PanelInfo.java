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
public class PanelInfo {
	
	int life;
	int attack;
	int intel;
	int physic;
	int magic;
	int tech;
	
	int criticalProbInc;
	int criticalProbDec;
	int criticalDamageInc;
	int criticalDamageDec;
	
	int damageInc;
	int physicDamageDec;
	int magicDamageDec;
	
	Map<String, Object> features;
	
	
	int lifeInc;
	int attackInc;
	int intelInc;
	int physicInc;
	int magicInc;
	int techInc;
	
	int lifeSkill;
	int attackSkill;
	int intelSkill;
	int physicSkill;
	int magicSkill;
	int techSkill;
	
	int lifeJJC;
	int attackJJC;
	int intelJJC;
	
	int physicJJC;
	int magicJJC;
	int techJJC;
	
	int preBattleDamage;
}
