package cc.js.sora.fight;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;

import cc.js.sora.fight.condition.CounterCondition;
import cc.js.sora.fight.condition.NoCondition;
import cc.js.sora.fight.condition.UserCondition;

public abstract class Skill {

	public long getId() {
		return 0;
	}

	public abstract String getName();

	public Condition getCondition() {
		// TODO Auto-generated method stub
		return new NoCondition();
	}

	// public abstract List<Buff> getBuffs();

	public abstract List<Effect> getEffects();
	
	public List<Effect> getEffects(int count)
	{
		return getEffects();
	}
	
	public List<Effect> getEffects(FightRole role)
	{
		if(this.getCondition() instanceof CounterCondition)
		{
			CounterCondition cc = (CounterCondition)this.getCondition();

			return this.getEffects(cc.getCount(role));
		}
		return getEffects();
	}

	public boolean isSupportSkill()
	{
		if(this.getCondition() instanceof UserCondition)
		{
			return ((UserCondition) this.getCondition()).getSupport();
		}
		return false;
	}

	public boolean filterSupportSkill(Map<String, Boolean> userConditionChecked)
	{
		if(isSupportSkill())
		{
			if(this.getCondition() instanceof UserCondition)
			{
				String name = ((UserCondition)this.getCondition()).getName();
				if(userConditionChecked.containsKey(name))
				{
					return userConditionChecked.get(name); 
				} else
				{
					return false;
				}
			}
		}
		return true;
	}
	
//	public List<Effect> getEffects(Map<String, Integer> buffCounts)
//	{
//		if(this.getCondition() instanceof CounterCondition)
//		{
//			CounterCondition cc = (CounterCondition)this.getCondition();
//			if(buffCounts != null && buffCounts.containsKey(cc.getName()))
//			{
//				return this.getEffects(buffCounts.get(cc.getName()));
//			}
//		}
//		return getEffects();
//		//return Lists.newArrayList();
//	}
//    public List<String> getEffects()
//    {
//    	return Lists.newArrayList();
//    }

	public void process(FightInfo fight, boolean isAttack) {
		// do nothing
	}

	public String description() {
		return "";
	}

	public String printSkillType() {
		switch (this.getSkillType()) {
		case 1:
			return "主动进攻时，";
		case 2:
			return "被攻击时，";
		case 3:
			return "进入战斗时，";
		case 4:
			return "主动攻击进入战斗时，";
		case 5:
			return "被攻击进入战斗时，";
		case 6:
			return "使用范围技能时，";

		case 7:
			return "被范围技能攻击时，";
		case 8:
			return "";
		case 9:
			return "";
		default:
			return "不确定的情况";
		}
	}

	@JsonProperty("desc")
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		if (this.getName() != null) {
			sb.append(this.getName());
		}
		sb.append("]       ");
		sb.append(printSkillType());
		if (this.getCondition() != null) {
			sb.append(this.getCondition().getDesc());

			if (!(this.getCondition() instanceof NoCondition)) {
				sb.append("，");
			}
		}

		if(this.getCondition() instanceof CounterUserCondition) {
			if (this.getEffects(1).size() > 0) {
				// this.getBuffs().stream().forEach(buff->sb.append(","+buff.getTitle()));
				sb.append(StringUtils.join(this.getEffects(1).stream().map(b -> b.toString()).toArray(), "，"));
			}
			sb.append("，最高"+((CounterUserCondition)this.getCondition()).getMaxCount()+"层");
			
		} else
		{
			if (this.getEffects().size() > 0) {
				// this.getBuffs().stream().forEach(buff->sb.append(","+buff.getTitle()));
				sb.append(StringUtils.join(this.getEffects().stream().map(b -> b.toString()).toArray(), "，"));
			}
		}

		if (StringUtils.isNotEmpty(description())) {
			sb.append(";");
			sb.append(description());
		}
		return sb.toString();
	}

	/**
	 * 1-300 talent
	 */
	public static final long PatyleTalent = 1;
	public static final long TowaTalent = 2;
	public static final long ZalrahdaTalent1 = 3;
	public static final long HimikoTalent = 5;
	public static final long MarielTalent = 10;
	public static final long MagusOfTheTreeTalent = 18;
	public static final long RozaliaTalent = 21;
	public static final long ReanTalent = 22;
	public static final long AresTalent = 28;
	public static final long ZalrahdaTalent2 = 203;
	public static final long LandiusTalent = 58;
	public static final long ZillagodTalent = 63;
	public static final long BernhardtTalent = 74;
	public static final long ElwinTalent = 76;
	public static final long LedynTalent = 78;

	/**
	 * 300-900 passive skill
	 */
	public static final long BloodBattle = 301;

	/**
	 * 900-1000 enhance
	 */
	public static final long WindEnhance = 901;
	public static final long FuriousEnhance = 905;
	public static final long ManyueEnhance = 902;
	
	/**
	 * 1000-1600 equip
	 */
	public static final long Zuihouzhifu = 1001;
	public static final long Yicai = 1002;
	public static final long Yuguan = 1003;
	public static final long Erzhui = 1004;
	public static final long Lage = 1005;
	public static final long Shenyi = 1006;
	public static final long Jixianmogong = 1007;

	public static final long Shenpan = 1009;
	public static final long Xunzhang = 1010;
	public static final long Tulong = 1012;

	/**
	 * 1600-2000 action
	 */
	public static final long Shimeng = 1601;
	public static final long Shixuemojian = 1602;
	public static final long Youshidaji = 1603;
	public static final long Ziyouzhiren = 1604;
	public static final long Weifengchongzhen = 1606;
	

	public static final long Qinzhen = 1805;
	public static final long Juebi = 1806;
	public static final long Qinzhen2 = 1807;

	/**
	 * 2000-3000 soldier skill
	 */
	public static final long MonvSkill = 2001;
	public static final long MojingshushiSkill = 2002;
	public static final long HuangjiashijiuSkill = 2003;
	public static final long LongxiajushouSkill = 2004;
	public static final long HuoyankulougongshouSkill = 2005;
	public static final long DijingqishiSkill = 2006;
	public static final long Shuijingsuxingzhe = 2007;
	public static final long Jixieqishi = 2008;
	public static final long Gaodiyongshi = 2009;
	public static final long Wunv = 2010;
	public static final long Kuangrezhe = 2011;
	public static final long Senlinjisi = 2012;
	public static final long Gangyiyongshi = 2013;
	public static final long Tianshi = 2014;
	public static final long Dujiaoshou = 2015;
	public static final long Shixianggui = 2016;
	public static final long Gangzonglangren = 2017;
	public static final long Xiyidaoke = 2018;
	public static final long Yingshi = 2019;
	public static final long Yaojinggongqibing = 2020;
	public static final long Wumianzhe = 2021;
	public static final long Huangjiaqibing = 2022;
	public static final long Xuanfengyouqibing = 2023;
	public static final long Xizuizhe = 2024;
	public static final long Fangzhenliebing = 2025;
	public static final long Shurenshouwei = 2027;
	public static final long Jiamiannvpu = 2026;
	public static final long Longqi = 2028;
	

	/**
	 * 4000-5000 support skill
	 */
	public static final long SuperBuff = 4001;
	public static final long ZillagodSuper = 4002;
	public static final long ElwinSuper = 4003;
	public static final long BernhardtSuper = 4004;
	

	// 1 battle/aoe attack
	// 2 battle/aoe defender 3 battle all
	// 4 battle attack 5 battle defender
	// 6 aoe attacker 7 aoe defender
	// 8 aoe all
	// 9 all
	public int getSkillType() {
		return 9;
	}

	// 0 effect 1 pre battle 2 battle 3 post battle 4
	public int getBattleType() {
		return 2;
	}

	public List<Skill> childSkill() {
		return Lists.newArrayList();
	}

}
