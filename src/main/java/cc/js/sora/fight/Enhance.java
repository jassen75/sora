package cc.js.sora.fight;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class Enhance implements Effect{
	
	
	BuffType buffType;
	double number;
	
	//1 hero 2 soldier 3 all 4 enemy hero 5 enemy soldier 6 enemy all
	Scope scope;
	
	public String getTitle()
	{
		if(buffType == BuffType.PreBattleDamage || buffType == BuffType.PostBattleDamage)
		{
			return printBufferType() + number+ "倍伤害";
		}
		
		else if(buffType == BuffType.Range)
		{
			return printBufferType() + number;
		}
		else
		{
			return printBufferType()+printValue();		
		}
		
	}
	
	public String printValue()
	{
		if(number > 0)
		{
			return "+"+Double.valueOf(number).intValue()+"%";
		} else
		{
			return Double.valueOf(number).intValue()+"%";
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
		case Physic:
			return "防御";
		case Magic:
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
		case Range:
			return "射程增加";
		case IgnoreDef:
			return "无视防御";
		}
		return "";
	}

	@Override
	public EffectType getEffectType() {
		return EffectType.Enhance;
	}

	@Override
	@JsonIgnore
	public List<Enhance> getEnhanceList() {
		return Lists.newArrayList(this);
	}

	@Override
	@JsonIgnore
	public Map<String, Object> getFeatures() {
		return Maps.newHashMap();
	}
	
	public String toString()
	{
		return this.getTitle();
	}

}
