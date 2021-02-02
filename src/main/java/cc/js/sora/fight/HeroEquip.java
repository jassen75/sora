package cc.js.sora.fight;

import javax.persistence.Entity;

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
public class HeroEquip {
	
	
	int attackInc;
	int attackStatic;
	int physicDefSInc;
	int physicDefStatic;
	
	int magicDefSInc;
	int magicDefStatic;
	
	int lifeInc;
	int lifeStatic;
	
	int enhanceType;
}
