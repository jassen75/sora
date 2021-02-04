package cc.js.sora.fight;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
		return printBufferType()+"+"+number+"%";				
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
		case Counter:
			return "克制伤害";
		case Life:
			return "生命";
		}
		return "";
	}

	
}
