package cc.js.sora.fight;

import java.util.List;
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
	int criticalProbDecc;
	int criticalDamageInc;
	int criticalDamageDnc;
	
	int damageInc;
	int physicDamageDec;
	int magicDamageDec;
	
	Map<String, Object> effects;
}
