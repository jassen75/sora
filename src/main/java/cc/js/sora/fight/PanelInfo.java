package cc.js.sora.fight;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

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
	
	Map<String, Object> features = Maps.newHashMap();
	List<Counter> counters = Lists.newArrayList();
	
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
	int criticalProbIncSkill;
	int criticalProbDecSkill;
	int criticalDamageIncSkill;
	int criticalDamageDecSkill;
	
	int lifeJJC;
	int attackJJC;
	int intelJJC;
	
	int physicJJC;
	int magicJJC;
	int techJJC;
	
	double preBattleDamage;
}
