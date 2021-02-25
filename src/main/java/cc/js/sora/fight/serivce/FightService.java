package cc.js.sora.fight.serivce;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cc.js.sora.fight.Buff;
import cc.js.sora.fight.CheckedSkill;
import cc.js.sora.fight.Counter;
import cc.js.sora.fight.Debuff;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Feature;
import cc.js.sora.fight.FightInfo;
import cc.js.sora.fight.Hero;
import cc.js.sora.fight.Land;
import cc.js.sora.fight.PanelInfo;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.Soldier;
import cc.js.sora.fight.condition.CombinedCondition;
import cc.js.sora.fight.condition.GroupedUserCondition;
import cc.js.sora.fight.condition.UserCondition;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FightService {

	@Autowired
	SkillService skillService;

	public  Map<String, Object> calculatePanel(FightInfo fightInfo) {
		 Map<String, Object> result = Maps.newHashMap();
		log.info(fightInfo.toString());
		log.info("****************** defender soldier left life:"+fightInfo.getDefender().getSoldierLeftLife());
		List<Skill> attackerSkills = skillService.getSkills(fightInfo.getAttacker().getHero(),
				fightInfo.getAttacker().getSoldier(), fightInfo.getAttacker().getAction()==null ? 0 :fightInfo.getAttacker().getAction().getId(),
				fightInfo.getAttacker().getEnhance(), fightInfo.getAttacker().getEquip(), fightInfo.getAttacker().getRoleType());


		List<Skill> defenderSkills = skillService.getSkills(fightInfo.getDefender().getHero(),
				fightInfo.getDefender().getSoldier(), 0, fightInfo.getDefender().getEnhance(),
				fightInfo.getDefender().getEquip(), fightInfo.getDefender().getRoleType());

		List<CheckedSkill> attackerCheckedSkills = Lists.newArrayList();
		List<CheckedSkill> defenderCheckedSkills = Lists.newArrayList();

		attackerSkills.stream().filter(s -> s.getBattleType() == 0)
				.forEach(s -> attackerCheckedSkills.add(checkSkill(fightInfo, s, true)));
		defenderSkills.stream().filter(s -> s.getBattleType() == 0)
				.forEach(s -> defenderCheckedSkills.add(checkSkill(fightInfo, s, false)));


		attackerSkills.stream().filter(s -> s.getBattleType() == 1)
				.forEach(s -> attackerCheckedSkills.add(checkSkill(fightInfo, s, true)));
		defenderSkills.stream().filter(s -> s.getBattleType() == 1)
				.forEach(s -> defenderCheckedSkills.add(checkSkill(fightInfo, s, false)));
		
		
		fightInfo.getAttacker().setBuffs(getBuffList(attackerCheckedSkills));
		fightInfo.getDefender().setBuffs(getBuffList(defenderCheckedSkills));
		fightInfo.getAttacker().setDebuffs(getDebuffList(attackerCheckedSkills));
		fightInfo.getDefender().setDebuffs(getDebuffList(defenderCheckedSkills));

		attackerSkills.stream().filter(s -> s.getBattleType() == 2)
				.forEach(s -> attackerCheckedSkills.add(checkSkill(fightInfo, s, true)));
		defenderSkills.stream().filter(s -> s.getBattleType() == 2)
				.forEach(s -> defenderCheckedSkills.add(checkSkill(fightInfo, s, false)));

		attackerSkills.stream().filter(s -> s.getBattleType() == 3)
				.forEach(s -> attackerCheckedSkills.add(checkSkill(fightInfo, s, true)));
		defenderSkills.stream().filter(s -> s.getBattleType() == 3)
				.forEach(s -> defenderCheckedSkills.add(checkSkill(fightInfo, s, false)));

		log.info("attacker skill list size:"+attackerCheckedSkills.size());
		log.info("defender skill list size:"+defenderCheckedSkills.size());
		fightInfo.getAttacker().setHeroPanel(this.calculate(fightInfo.getAttacker().getHero(),
				fightInfo.getAttacker().getHeroPanel(), attackerCheckedSkills, fightInfo.getAttacker().getLand()));
		fightInfo.getAttacker().setSoldierPanel(this.calculate(fightInfo.getAttacker().getSoldier(),fightInfo.getAttacker().getHero(),
				fightInfo.getAttacker().getSoldierPanel(), attackerCheckedSkills, fightInfo.getAttacker().getLand()));
		fightInfo.getDefender().setHeroPanel(this.calculate(fightInfo.getDefender().getHero(),
				fightInfo.getDefender().getHeroPanel(), defenderCheckedSkills, fightInfo.getDefender().getLand()));
		fightInfo.getDefender().setSoldierPanel(this.calculate(fightInfo.getDefender().getSoldier(),fightInfo.getDefender().getHero(),
				fightInfo.getDefender().getSoldierPanel(), defenderCheckedSkills, fightInfo.getDefender().getLand()));
		
//		fightInfo.getAttacker().setUserConditions(getUserConditionsFromSkill(attackerSkills));
//		fightInfo.getDefender().setUserConditions(getUserConditionsFromSkill(defenderSkills));
		
//		fightInfo.getAttacker().setCheckedSkills(attackerCheckedSkills);
//		fightInfo.getDefender().setCheckedSkills(defenderCheckedSkills);
		
		Map<String, UserCondition> attackerUserConditions = getUserConditionsFromSkill(attackerSkills);
		Map<String, UserCondition> defenderUserConditions = getUserConditionsFromSkill(defenderSkills);
		
	    Map<String, Object> sessionInfo = Maps.newHashMap();
	    
	    Map<String, Object> attackerSessionInfo = Maps.newHashMap();
	    Map<String, Object> defenderSessionInfo = Maps.newHashMap();
	    
	    
	    attackerSessionInfo.put("userConditions", attackerUserConditions);
	    defenderSessionInfo.put("userConditions", defenderUserConditions);
		
	    attackerSessionInfo.put("userConditionGroups", getUserConditionGroups(attackerUserConditions));
	    defenderSessionInfo.put("userConditionGroups", getUserConditionGroups(defenderUserConditions));
		
		attackerSessionInfo.put("checkedSkills", attackerCheckedSkills);
		defenderSessionInfo.put("checkedSkills", defenderCheckedSkills);
		
		sessionInfo.put("attacker", attackerSessionInfo);
		sessionInfo.put("defender", defenderSessionInfo);

		result.put("fightInfo", fightInfo);
		result.put("sessionInfo", sessionInfo);
		return result;
	}
	
	private Map<String, List<String>> getUserConditionGroups(Map<String, UserCondition> userConditions)
	{
		Map<String, List<String>> result = Maps.newHashMap();
		userConditions.forEach((k,v)->{
			if(v instanceof GroupedUserCondition)
			{
				GroupedUserCondition gu = (GroupedUserCondition)v;
				if(!result.containsKey(gu.getGroupName()))
				{
					result.put(gu.getGroupName(), Lists.newArrayList());
				}
				result.get(gu.getGroupName()).add(k);
			}
		});
		
		return result;
	}
	
	public List<Debuff> getDebuffList(List<CheckedSkill> skillList)
	{
		List<Debuff> buffs = Lists.newArrayList();
		skillList.stream().filter(cs->cs.isValid() && cs.getSkill().getEffects().stream().anyMatch(e->e instanceof Debuff)).forEach(cs->{
			buffs.addAll(cs.getSkill().getEffects().stream().filter(e->e instanceof Debuff).map(e->(Debuff)e).collect(Collectors.toList()));
		});
		return buffs;
	}
	
	public List<Buff> getBuffList(List<CheckedSkill> skillList)
	{
		List<Buff> buffs = Lists.newArrayList();
		skillList.stream().filter(cs->cs.isValid() && cs.getSkill().getEffects().stream().anyMatch(e->e instanceof Buff)).forEach(cs->{
			buffs.addAll(cs.getSkill().getEffects().stream().filter(e->e instanceof Buff).map(e->(Buff)e).collect(Collectors.toList()));
		});
		return buffs;
	}

	public PanelInfo calculate(Hero hero, PanelInfo panelInfo, List<CheckedSkill> skillList, Land land) {
		
		panelInfo.getFeatures().clear();
		panelInfo.getCounters().clear();
		panelInfo.setIsSoldier(0);
		int life = hero.getLife() + panelInfo.getLifeInc();
		int attack = hero.getAttack() + panelInfo.getAttackInc();
		int intel = hero.getIntel() + panelInfo.getIntelInc();
		int physic = hero.getPhysic() + panelInfo.getPhysicInc();
		int magic = hero.getMagic() + panelInfo.getMagicInc();
		int tech = hero.getTech() + panelInfo.getTechInc();

		int ai = panelInfo.getAttackSkill();
		int ii = panelInfo.getIntelSkill();
		int pi = panelInfo.getPhysicSkill();
		int mi = panelInfo.getMagicSkill();
		int li = panelInfo.getLifeSkill();
		int ti = panelInfo.getTechSkill();
		int cpi = panelInfo.getCriticalProbIncSkill();
		int cdi = panelInfo.getCriticalDamageIncSkill();
		int cpd = panelInfo.getCriticalProbDecSkill();
		int cdd = panelInfo.getCriticalDamageDecSkill();
		log.info("cpi=="+cpi+",cdi=="+cdi+",cpd==="+cpd+",cdd===="+cdd);
		int di = 0;
		int pdd = 0;
		int mdd = 0;
		int si = 0;
		double preBattleDamage = 0;

		List<Double> counters = Lists.newArrayList();
		List<Double> pd_counters = Lists.newArrayList();
		List<Double> md_counters = Lists.newArrayList();
		Map<String, Buff> buffList = Maps.newHashMap();
		Map<String, Debuff> debuffList = Maps.newHashMap();

		if (skillList != null) {
			for (int i = 0; i < skillList.size(); i++) {
				CheckedSkill cs = skillList.get(i);
				if (cs.isValid()) {
					Skill skill = cs.getSkill();
					for (int j = 0; j < skill.getEffects().size(); j++) {
						Effect e = skill.getEffects().get(j);
						if (e instanceof Buff) {
							Buff buff = (Buff) e;
							if (buff.getEnhanceList().size() == 1) {
								Enhance en = buff.getEnhanceList().get(0);
								Buff oldBuff = buffList.get(buff.getName());
								if (oldBuff != null && oldBuff.getEnhanceList().size() == 1) {
									Enhance oldEnhance = oldBuff.getEnhanceList().get(0);
									if (en.getNumber() > oldEnhance.getNumber()) {
										buffList.put(buff.getName(), buff);
									}
								} else {
									buffList.put(buff.getName(), buff);
								}

							} else {
								buffList.put(buff.getName(), buff);
							}
						} else if (e instanceof Enhance) {
							Enhance en = (Enhance) e;
							if (en.getScope() != Scope.Hero && en.getScope() != Scope.All)
							{
								continue;
							}
							double number = en.getNumber();
							switch (en.getBuffType()) {
							case Attack:
								ai += number;
								break;
							case Intel:
								ii += number;
								break;
							case Physic:
								pi += number;
								break;
							case Magic:
								mi += number;
								break;
							case DamageInc:
								di += number;
								break;
							case DamageDec:
								pdd += number;
								mdd += number;
								break;
							case PhysicDamageDec:
								pdd += number;
								break;
							case MagicDamageDec:
								mdd += number;
								break;
							case SkillDamage:
								si += number;
								break;
							case AttackCounter:
								counters.add(number);
								break;
							case PhysicDefCounter:
								pd_counters.add(number);
								break;
							case Life:
								li += number;
								break;
							case Tech:
								ti += number;
								break;
							case CriticalProbInc:
								cpi += number;
								break;
							case CriticalDamageInc:
								cdi += number;
								break;
							case CriticalProbDec:
								cpd += number;
								break;
							case CriticalDamageDec:
								cdd += number;
								break;
							case PreBattleDamage:
								preBattleDamage += number;
								log.info("preBattleDamage==="+preBattleDamage);
								break;
							default:
							}
						} else if (e instanceof Feature) {
							Feature f = (Feature)e;
							panelInfo.getFeatures().putAll(f.getFeatures());
						} else if (e instanceof Counter) {
							
							Counter c = (Counter)e;
							if(c.getScope() == Scope.All || c.getScope() == Scope.Hero)
							{
								panelInfo.getCounters().add(c);
							}
						}
					}
				}
			}
		}

		List<Buff> buffs = Lists.newArrayList(buffList.values());
		
		
		if (buffs != null && buffs.size() > 0) {
			for (int k = 0; k < buffs.size(); k++) {
				Buff bu = buffs.get(k);
				for (int m = 0; m < bu.getEnhanceList().size(); m++) {
					Enhance ek = (Enhance) bu.getEnhanceList().get(m);
					double number = ek.getNumber();
					switch (ek.getBuffType()) {
					case Attack:
						ai += number;
						break;
					case Intel:
						ii += number;
						break;
					case Physic:
						pi += number;
						break;
					case Magic:
						mi += number;
						break;
					case DamageInc:
						di += number;
						break;
					case DamageDec:
						pdd += number;
						mdd += number;
						break;
					case PhysicDamageDec:
						pdd += number;
						break;
					case MagicDamageDec:
						mdd += number;
						break;
					case SkillDamage:
						si += number;
						break;
					case AttackCounter:
						counters.add(number);
						break;
					case PhysicDefCounter:
						pd_counters.add(number);
						break;
					case Life:
						li += number;
						break;
					case Tech:
						ti += number;
						break;
					case CriticalProbInc:
						cpi += number;
						break;
					case CriticalDamageInc:
						cdi += number;
						break;
					case CriticalProbDec:
						cpd += number;
						break;
					case CriticalDamageDec:
						cdd += number;
						break;
					default:
					}
				}

			}
		}
		
		if(land == Land.Water && hero.getType() == 4)
		{
			pi+=30;
		}

		if(land == Land.Wood)
		{
			pd_counters.add(20.0);
			md_counters.add(20.0);
		}
		
		if(land == Land.Mountain)
		{
			pd_counters.add(10.0);
			md_counters.add(10.0);
		}
		
		if(land == Land.Wall)
		{
			pd_counters.add(30.0);
			md_counters.add(30.0);
		}
		
		panelInfo.setAttack( Double.valueOf(Math.round(attack * (1 + ai / 100.0) + panelInfo.getAttackJJC())).intValue());
		panelInfo.setIntel(Double.valueOf(Math.floor(intel * (1 + ii / 100.0) + panelInfo.getIntelJJC())).intValue());
		panelInfo.setPhysic(Double.valueOf(Math.floor(physic * (1 + pi / 100.0) + panelInfo.getPhysicJJC())).intValue());
		panelInfo.setMagic(Double.valueOf(Math.floor(magic * (1 + mi / 100.0) + panelInfo.getMagicJJC())).intValue());
		panelInfo.setTech(Double.valueOf(Math.floor(tech * (1 + ti / 100.0) + panelInfo.getTechJJC())).intValue());
		panelInfo.setLife(Double.valueOf(Math.floor(life * (1 + (li+40) / 100.0) + panelInfo.getLifeJJC())).intValue());

		panelInfo.setDamageInc(di);
		panelInfo.setPhysicDamageDec(pdd);
		panelInfo.setMagicDamageDec(mdd);

		panelInfo.setCriticalProbInc(cpi);
		panelInfo.setCriticalProbDec(cpd);
		panelInfo.setCriticalDamageInc(cdi);
		panelInfo.setCriticalDamageDec(cdd);

		panelInfo.setPreBattleDamage(preBattleDamage);

		for (int i = 0; i < counters.size(); i++) {
			if (hero.getPhysic() == 1) {

				panelInfo.setAttack(
						Double.valueOf(Math.floor(panelInfo.getAttack() * (1 + counters.get(i) / 100))).intValue());
			} else {
				panelInfo.setIntel(
						Double.valueOf(Math.floor(panelInfo.getIntel() * (1 + counters.get(i) / 100))).intValue());
			}
		}
		for (int i = 0; i < pd_counters.size(); i++) {
			panelInfo.setPhysic(
					Double.valueOf(Math.floor(panelInfo.getPhysic() * (1 + pd_counters.get(i) / 100))).intValue());
		}
		for (int i = 0; i < md_counters.size(); i++) {
			panelInfo.setMagic(
					Double.valueOf(Math.floor(panelInfo.getMagic() * (1 + md_counters.get(i) / 100))).intValue());
		}

		return panelInfo;
	}

	public PanelInfo calculate(Soldier soldier, Hero hero, PanelInfo panelInfo, List<CheckedSkill> skillList, Land land) {
		panelInfo.getCounters().clear();
		panelInfo.getFeatures().clear();
		panelInfo.setIsSoldier(1);
		int life = soldier.getLife();
		int attack = soldier.getAttack();
		int physic = soldier.getPhysic();
		int magic = soldier.getMagic();

		int ai = 0;
		int pi = 0;
		int mi = 0;
		int li = 0;
		int cpi = 0;
		int cdi = 0;
		int cpd = 0;
		int cdd =0;
		int di = 0;
		int pdd = 0;
		int mdd = 0;
		
		log.info("soldier b ai=="+ai);
		log.info("soldier b pi=="+pi);
		log.info("soldier b mo=="+mi);
		log.info("soldier b li=="+li);
		
		List<Double> counters = Lists.newArrayList();
		List<Double> pd_counters = Lists.newArrayList();
		List<Double> md_counters = Lists.newArrayList();
		Map<String, Buff> buffList = Maps.newHashMap();
		Map<String, Debuff> debuffList = Maps.newHashMap();

		if (skillList != null) {
			for (int i = 0; i < skillList.size(); i++) {
				CheckedSkill cs = skillList.get(i);
				if (cs.isValid()) {
					Skill skill = cs.getSkill();
					for (int j = 0; j < skill.getEffects().size(); j++) {
						Effect e = skill.getEffects().get(j);
						if (e instanceof Buff) {
							Buff buff = (Buff) e;
							if (buff.getEnhanceList().size() == 1) {
								Enhance en = buff.getEnhanceList().get(0);
								Buff oldBuff = buffList.get(buff.getName());
								if (oldBuff != null && oldBuff.getEnhanceList().size() == 1) {
									Enhance oldEnhance = oldBuff.getEnhanceList().get(0);
									if (en.getNumber() > oldEnhance.getNumber()) {
										buffList.put(buff.getName(), buff);
									}
								} else {
									buffList.put(buff.getName(), buff);
								}

							} else {
								buffList.put(buff.getName(), buff);
							}
						} else if (e instanceof Enhance) {
							Enhance en = (Enhance) e;
							if (en.getScope() != Scope.Soldier && en.getScope() != Scope.All)
							{
								continue;
							}
							double number = en.getNumber();
							switch (en.getBuffType()) {
							case Attack:
								ai += number;
								break;
							case Physic:
								pi += number;
								break;
							case Magic:
								mi += number;
								break;
							case DamageInc:
								di += number;
								break;
							case DamageDec:
								pdd += number;
								mdd += number;
								break;
							case PhysicDamageDec:
								pdd += number;
								break;
							case MagicDamageDec:
								mdd += number;
								break;
							case AttackCounter:
								counters.add(number);
								break;
							case PhysicDefCounter:
								pd_counters.add(number);
								break;
							case Life:
								li += number;
								break;
							case CriticalProbInc:
								cpi += number;
								break;
							case CriticalDamageInc:
								cdi += number;
								break;
							case CriticalProbDec:
								cpd += number;
								break;
							case CriticalDamageDec:
								cdd += number;
								break;
							default:
							}
						} else if (e instanceof Feature) {
							Feature f = (Feature)e;
							panelInfo.getFeatures().putAll(f.getFeatures());
						} else if (e instanceof Counter) {
							
							Counter c = (Counter)e;
							if(c.getScope() == Scope.All || c.getScope() == Scope.Soldier)
							{
								panelInfo.getCounters().add(c);
							}
						}
					}

				}

			}
		}

		List<Buff> buffs = Lists.newArrayList(buffList.values());
		if (buffs != null && buffs.size() > 0) {
			for (int k = 0; k < buffs.size(); k++) {
				Buff bu = buffs.get(k);
				for (int m = 0; m < bu.getEnhanceList().size(); m++) {
					Enhance ek = (Enhance) bu.getEnhanceList().get(m);
					double number = ek.getNumber();
					switch (ek.getBuffType()) {
					case Attack:
						ai += number;
						break;
					case Physic:
						pi += number;
						break;
					case Magic:
						mi += number;
						break;
					case DamageInc:
						di += number;
						break;
					case DamageDec:
						pdd += number;
						mdd += number;
						break;
					case PhysicDamageDec:
						pdd += number;
						break;
					case MagicDamageDec:
						mdd += number;
						break;
					case AttackCounter:
						counters.add(number);
						break;
					case PhysicDefCounter:
						pd_counters.add(number);
						break;
					case Life:
						li += number;
						break;
					case CriticalProbInc:
						cpi += number;
						break;
					case CriticalDamageInc:
						cdi += number;
						break;
					case CriticalProbDec:
						cpd += number;
						break;
					case CriticalDamageDec:
						cdd += number;
						break;
					default:
					}
				}

			}
		}
		
		if(land == Land.Water && (hero.getType() == 4 || soldier.getType() == 4))
		{
			pi+=30;
		}
		
		if(land == Land.Wood)
		{
			pd_counters.add(20.0);
			md_counters.add(20.0);
		}
		
		if(land == Land.Mountain)
		{
			pd_counters.add(10.0);
			md_counters.add(10.0);
		}
		
		if(land == Land.Wall)
		{
			pd_counters.add(30.0);
			md_counters.add(30.0);
		}
		
		log.info("soldier a ai=="+ai);
		log.info("soldier a pi=="+pi);
		log.info("soldier a  mo=="+mi);
		log.info("soldier a li=="+li);

		panelInfo.setAttack(Double.valueOf(Math.floor(attack * (1 + ai / 100.0) * (1+hero.getSoldierAttackInc() / 100.0))).intValue());
		panelInfo.setPhysic(Double.valueOf(Math.floor(physic * (1 + pi / 100.0) * (1+hero.getSoldierPhysicInc() / 100.0))).intValue());
		panelInfo.setMagic(Double.valueOf(Math.floor(magic * (1 + mi / 100.0)* (1+hero.getSoldierMagicInc() / 100.0))).intValue());
		panelInfo.setLife(Double.valueOf(Math.floor(life * (1 + (li+40) / 100.0)*(1+hero.getSoldierLifeInc()/100.0) )).intValue()*10);

		panelInfo.setDamageInc(di);
		panelInfo.setPhysicDamageDec(pdd);
		panelInfo.setMagicDamageDec(mdd);

		panelInfo.setCriticalProbInc(cpi);
		panelInfo.setCriticalProbDec(cpd);
		panelInfo.setCriticalDamageInc(cdi);
		panelInfo.setCriticalDamageDec(cdd);

		for (int i = 0; i < counters.size(); i++) {
			panelInfo.setAttack(Double.valueOf(Math.floor(panelInfo.getAttack() * (1 + counters.get(i) / 100))).intValue());

		}
		for (int i = 0; i < pd_counters.size(); i++) {
			panelInfo.setPhysic(
					Double.valueOf(Math.floor(panelInfo.getPhysic() * (1 + pd_counters.get(i) / 100))).intValue());
		}
		for (int i = 0; i < md_counters.size(); i++) {
			panelInfo.setMagic(
					Double.valueOf(Math.floor(panelInfo.getMagic() * (1 + md_counters.get(i) / 100))).intValue());
		}

		return panelInfo;
	}

	private CheckedSkill checkSkill(FightInfo fightInfo, Skill skill, boolean isAttack) {

		CheckedSkill result = new CheckedSkill();
		result.setSkill(skill);
		boolean valid = skill.getCondition().valid(fightInfo, isAttack);
		if (valid) {
			skill.process(fightInfo, isAttack);
		}

		result.setValid(valid);
		return result;

	}

	public Map<String, UserCondition> getUserConditionsFromSkill(List<Skill> skills) {

		Map<String, UserCondition> resultList = Maps.newHashMap();
		if (skills != null) {
			skills.stream().forEach(skill -> {
				if (skill == null) {
				}
				if (skill.getCondition() instanceof UserCondition) {
					resultList.put(((UserCondition) skill.getCondition()).getName(),
							(UserCondition) skill.getCondition());
				}
				if (skill.getCondition() instanceof CombinedCondition) {
					((CombinedCondition) skill.getCondition()).getConditions().stream().forEach(c -> {
						if (c instanceof UserCondition) {
							resultList.put(((UserCondition) c).getName(), (UserCondition) c);
						}
					});
				}
			});
		}
		return resultList;

	}
	
	

}
