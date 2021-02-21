package cc.js.sora.fight;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;

import cc.js.sora.fight.condition.NoCondition;

public abstract class Skill {
	
	public abstract long getId();
	
	public abstract String getName();

	public abstract Condition getCondition();

	//public abstract List<Buff> getBuffs();
	
	public abstract List<Effect> getEffects();

//    public List<String> getEffects()
//    {
//    	return Lists.newArrayList();
//    }
    
    public void process(FightInfo fight, boolean isAttack)
    {
    	// do nothing
    }

    
    public String description()
    {
    	return "";
    }
	
	@JsonProperty("desc")
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		if(this.getName() != null)
		{
			sb.append(this.getName());
		}
		sb.append("]       ");
		if(this.getCondition() != null)
		{
			sb.append(this.getCondition().getDesc());
			
			if(!(this.getCondition() instanceof NoCondition))
			{
				sb.append("，");
			}
		}
		
		if(this.getEffects().size()>0)
		{
			//this.getBuffs().stream().forEach(buff->sb.append(","+buff.getTitle()));
			sb.append(StringUtils.join(this.getEffects().stream().map(b->b.toString()).toArray(), "，"));
		}
		
		if(StringUtils.isNotEmpty(description()))
		{
			sb.append(";");
			sb.append(description());
		}
		return sb.toString();
	}

	/**
	 * 1-300  talent
	 */
	public static final long PatyleTalent = 1;
	public static final long TowaTalent = 2;
	public static final long ZalrahdaTalent1 = 3;
	public static final long ZalrahdaTalent2 = 203;
	public static final long MarielTalent = 10;

	/**
	 *   300-900 passive skill
	 */
	public static final long BloodBattle = 301;
	
	/**
	 * 900-1000 enhance
	 */
	public static final long WindEnhance = 901;
	public static final long FuriousEnhance = 905;
	
	/**
	 * 1000-1600 equip
	 */
	public static final long Zuihouzhifu = 1001;
	public static final long Lage = 1002;
	public static final long Xunzhang = 1003;
	
	/**
	 * 1600-2000 action
	 */
	public static final long Shimeng = 1601;
	public static final long Shixuemojian = 1602;
	public static final long Youshidaji = 1603;
	public static final long Ziyouzhiren = 1604;
	
	public static final long Qinzhen = 1605;
	public static final long Juebi = 1606;
	
	/**
	 * 2000-3000 soldier skill
	 */
	public static final long MonvSkill = 2001;
	public static final long MojingshushiSkill = 2002;
	public static final long HuangjiashijiuSkill = 2003;
	public static final long LongxiajushouSkill = 2004;
	
	
	/**
	 * 3000-4000 barrack tech
	 */
	public static final long SorceressTech1 = 3001;
	public static final long SorceressTech2 = 3002;
	public static final long SorceressTech3 = 3003;
	public static final long SorceressTech4 = 3004;
	
	public static final long BirdTech1 = 3005;
	public static final long BirdTech2 = 3006;
	public static final long BirdTech3 = 3007;
	public static final long BirdTech4 = 3008;
	
	public static final long SailorTech1 = 3009;
	public static final long SailorTech2 = 3010;
	public static final long SailorTech3 = 3011;
	public static final long SailorTech4 = 3012;
	
	public static final long FootTech1 = 3013;
	public static final long FootTech2 = 3014;
	public static final long FootTech3 = 3015;
	public static final long FootTech4 = 3016;
	
	public static final long PikeTech1 = 3017;
	public static final long PikeTech2 = 3018;
	public static final long PikeTech3 = 3019;
	public static final long PikeTech4 = 3020;
	
	public static final long HorseTech1 = 3021;
	public static final long HorseTech2 = 3022;
	public static final long HorseTech3 = 3023;
	public static final long HorseTech4 = 3024;
	
	
	/**
	 * 4000-5000 global skill
	 */
	public static final long SuperBuff = 4001;
	
	//1 attack 2 defender 3 all
	public int getSkillType()
	{
		return 3;
	}
	
	// 0 effect  1 pre battle 2 battle 3 post battle
	public int getBattleType()
	{
		return 2;
	}
			
}
