package cc.js.sora.fight;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import cc.js.sora.fight.condition.CombinedCondition;
import cc.js.sora.fight.condition.EnemyHeroCondition;
import cc.js.sora.fight.condition.UserCondition;
import cc.js.sora.fight.condition.health.ForceHealthCondition;

public abstract class Skill {
	
	public abstract long getId();
	
	public abstract String getName();

	public abstract Condition getCondition();

	public abstract List<Buff> getBuffs();
	
	//1 hero 2 soldier 3 all 4 enemy hero 5 enemy soldier 6 enemy all
	public abstract Scope getScope();




	
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
		}
		this.getBuffs().stream().forEach(buff->sb.append(","+buff.getTitle()));
		return sb.toString();
	}
	
	/**
	 * 
	 * 1-300  hero talent
	 * 300-1000 passive skill
	 * 
	 * 1000-1100 enhance
	 * 
	 * 1100-1600 equip skill
	 * 
	 * 1600-2000 action skill
	 * 
	 * 2000-3000 soldier skill
	 * 
	 * 3000-4000 barrack tech

	 * 4000-5000 global skill
	 */
	

	public static final long PatyleTalent = 1;
	public static final long TowaTalent = 2;
	public static final long ZalrahdaTalent1 = 3;
	public static final long ZalrahdaTalent2 = 4;

	public static final long BloodBattle = 301;
	
	public static final long WindEnhance = 1001;
	public static final long FuriousEnhance = 1002;
	
	public static final long Zuihouzhifu = 1101;
	
	public static final long Shimeng = 1601;
	public static final long Shixuemojian = 1602;
	public static final long Youshidaji = 1603;
	public static final long Ziyouzhiren = 1604;
	
	public static final long MonvSkill = 2001;
	public static final long MojingshushiSkill = 2002;
	public static final long HuangjiashijiuSkill = 2003;
	public static final long LongxiajushouSkill = 2004;
	
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
	
	
	
	public static final long SuperBuff = 4001;
	
	//1 attack 2 defender 3 all
	public int getSkillType()
	{
		return 3;
	}
			
}
