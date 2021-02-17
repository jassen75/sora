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
public class Fight {
	
	long attackerHeroId;
	
	long defenderHeroId;

	long attackerSoldierId;
	
	long defenderSoldierId;
	
	long attackerActionId;
	
	int attackerAttack;
	int attackerPhysicDef;
	int attackerLife;
	int attackerMagicDef;
	int attackerIntel;
	int attackerTech;
	
	int defenderAttack;
	int defenderPhysicDef;
	int defenderLife;
	int defenderMagicDef;
	int defenderIntel;
	int defenderTech;
	
	int attackerHeroLeftLife;
	int attackerSoldierLeftLife;
	int defenderHeroLeftLife;
	int defenderSoldierLeftLife;
	
	int attackerSoldierLife;
	int defenderSoldierLife;
	
	int attackerSoldierAttack;
	int attackerSoldierPhysicDef;
	int attackerSoldierMagicDef;

	int defenderSoldierAttack;
	int defenderSoldierPhysicDef;
	int defenderSoldierMagicDef;
	
	Land attackerLand = Land.Flat;
	Land defenderLand = Land.Flat;
	Map<String, Boolean> attackerUserConditionChecked;
	Map<String, Boolean> defenderUserConditionChecked;
	
	Map<String, Equip> attackerEquip;
	Map<String, Equip> defenderEquip;
	
	// 1 轻风 2 满月 3 魔术 4 时钟 5 怒涛 6 烈日 7 流星 8 顽石 9 水晶 10 寒冰 11 大树 12 荆棘 13 钢铁
	int attackerEnhance;
	int defenderEnhance;
	
	List<String> attackerEffects = Lists.newArrayList();
	List<String> defenderEffects = Lists.newArrayList();
	List<String> attackerSoldierEffects = Lists.newArrayList();
	List<String> defenderSoldierEffects = Lists.newArrayList();
}
