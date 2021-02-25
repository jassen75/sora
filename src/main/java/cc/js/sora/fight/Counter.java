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
public class Counter implements Effect{
	
	public Counter(BuffType buffType, int number, int enemyType, boolean soldier, boolean hero, Scope scope)
	{
		this.buffType = buffType;
		this.number = number;
		this.enemyType = enemyType;
		this.soldier = soldier;
		this.hero = hero;
		this.scope = scope;
		
	}
	
	public Counter(BuffType buffType, int number, int enemyType,  Scope scope)
	{
		this.buffType = buffType;
		this.number = number;
		this.enemyType = enemyType;
		this.soldier = true;
		this.hero = true;
		this.scope = scope;
	}
	
	BuffType buffType;
	int number;	
	int enemyType;
	boolean soldier;
	boolean hero;
	Scope scope;
	Map<String, Object> features;
	
	@Override
	public EffectType getEffectType() {
		// TODO Auto-generated method stub
		return EffectType.Counter;
	}
	@Override
	public List<Enhance> getEnhanceList() {
		return Lists.newArrayList();
	}
	@Override
	public Map<String, Object> getFeatures() {
		return features;
	}
	
	public String toString()
	{
		return "对"+printBufferType()+printCharType()+"增强"+this.getNumber();
	}
	public String printBufferType()
	{
		switch(enemyType)
		{
		case 1:
			return "步兵";
		case 2:
			return "枪兵";
		case 3:
			return "骑兵";
		case 4:
			return "水兵";
		case 5:
			return "飞兵";
		case 6:
			return "弓兵";
		case 7:
			return "刺客";
		case 8:
			return "法师";
		case 9:
			return "魔物";
		case 10:
			return "僧侣";
		default:
			
		}
		return "所有兵种";
	}
	
	public String printCharType()
	{
		switch(buffType)
		{
			case Attack:
				return "攻击";
			case Physic:
				return "防御";
			case Magic:
				return "魔防";
			default:
		}
		return "";
	}
	
}
