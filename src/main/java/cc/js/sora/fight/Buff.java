package cc.js.sora.fight;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@ToString
public class Buff {
	
	public Buff(BuffType buffType,  int number)
	{
		this.buffType = buffType;
		this.number = number;
		this.basic = false;
	}
	
	//1 attack 2 physicDef 3 magicDef 4 damageInc 5 damageReduce 6 Skill 7 counter 
	BuffType buffType;
	
	int number;
	
	boolean basic;
	
	public String getTitle()
	{
		if(buffType == BuffType.PreBattleDamage || buffType == BuffType.PostBattleDamage)
		{
			return printBufferType() + number+ "倍伤害";
		}else
		{
			return printBufferType()+"+"+number+"%";		
		}
	}
	
	public String printBufferType()
	{
		switch(buffType)
		{
		case Attack:
			return "攻击";
		case Intel:
			return "智力";
		case PhysicDef:
			return "防御";
		case MagicDef:
			return "魔防";
		case DamageInc:
			return "增伤";
		case DamageDec:
			return "减伤";
		case SkillDamage:
			return "技能伤害";
		case AttackCounter:
			return "克制伤害";
		case PhysicDamageDec:
			return "物理减伤";
		case MagicDamageDec:
			return "魔法减伤";
		case PhysicDefCounter:
			return "防御提升";
		case MagicDefCounter:
			return "魔防提升";
		case Life:
			return "生命";
			
		case Tech:
			return "技巧";
		case CriticalProbInc:
			return "暴击概率增加";
		case CriticalDamageInc:
			return "暴击伤害增加";
		case CriticalProbDec:
			return "暴击概率减少";
		case CriticalDamageDec:
			return "暴击伤害减少";
		case PreBattleDamage:
			return "战前造成";
		case PostBattleDamage:
			return "战后造成";
		}
		return "";
	}

	
}
