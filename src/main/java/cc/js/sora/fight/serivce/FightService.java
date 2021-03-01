package cc.js.sora.fight.serivce;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cc.js.sora.fight.Action;
import cc.js.sora.fight.Buff;
import cc.js.sora.fight.CheckedSkill;
import cc.js.sora.fight.Counter;
import cc.js.sora.fight.Debuff;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.Feature;
import cc.js.sora.fight.FightInfo;
import cc.js.sora.fight.FightRole;
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

	public Map<String, Object> calculatePanel(FightInfo fightInfo) {
		Map<String, Object> result = Maps.newHashMap();
		log.info(fightInfo.toString());
		log.info("****************** defender soldier left life:" + fightInfo.getDefender().getSoldierLeftLife());
		List<Skill> attackerSkills = skillService.getSkills(
				fightInfo.getAttacker().getHero(),
				fightInfo.getAttacker().getSoldier(),
				fightInfo.getAttacker().getAction() == null ? 0 : fightInfo.getAttacker().getAction().getId(),
				fightInfo.getAttacker().getEnhance(), fightInfo.getAttacker().getEquip(),
				fightInfo.getAttacker().getRoleType());

		List<Skill> defenderSkills = skillService.getSkills(
				fightInfo.getDefender().getHero(),
				fightInfo.getDefender().getSoldier(), 0, 
				fightInfo.getDefender().getEnhance(),
				fightInfo.getDefender().getEquip(), 
				fightInfo.getDefender().getRoleType());
		
		List<Skill> attackerSupportSKills = getSupportSkills(attackerSkills);
		List<Skill> defenderSupportSKills = getSupportSkills(defenderSkills);

		List<CheckedSkill> attackerCheckedSkills = Lists.newArrayList();
		List<CheckedSkill> defenderCheckedSkills = Lists.newArrayList();

		attackerSkills.stream().filter(s -> s.getBattleType() == 0).filter(s->s.filterSupportSkill(fightInfo.getAttacker().getUserConditionChecked()))
				.forEach(s -> attackerCheckedSkills.add(checkSkill(fightInfo, s, true)));
		defenderSkills.stream().filter(s -> s.getBattleType() == 0).filter(s->s.filterSupportSkill(fightInfo.getDefender().getUserConditionChecked()))
				.forEach(s -> defenderCheckedSkills.add(checkSkill(fightInfo, s, false)));

		attackerSkills.stream().filter(s -> s.getBattleType() == 1).filter(s->s.filterSupportSkill(fightInfo.getAttacker().getUserConditionChecked()))
				.forEach(s -> attackerCheckedSkills.add(checkSkill(fightInfo, s, true)));
		defenderSkills.stream().filter(s -> s.getBattleType() == 1).filter(s->s.filterSupportSkill(fightInfo.getDefender().getUserConditionChecked()))
				.forEach(s -> defenderCheckedSkills.add(checkSkill(fightInfo, s, false)));

		fightInfo.getAttacker().setBuffs(getBuffList(attackerCheckedSkills));
		fightInfo.getDefender().setBuffs(getBuffList(defenderCheckedSkills));
		fightInfo.getAttacker().setDebuffs(getDebuffList(attackerCheckedSkills));
		fightInfo.getDefender().setDebuffs(getDebuffList(defenderCheckedSkills));

		log.info("attacker debuffs:"+fightInfo.getAttacker().getDebuffs());
		log.info("defender debuffs:"+fightInfo.getDefender().getDebuffs());
		attackerSkills.stream().filter(s -> s.getBattleType() == 2).filter(s->s.filterSupportSkill(fightInfo.getAttacker().getUserConditionChecked()))
				.forEach(s -> attackerCheckedSkills.add(checkSkill(fightInfo, s, true)));
		defenderSkills.stream().filter(s -> s.getBattleType() == 2).filter(s->s.filterSupportSkill(fightInfo.getDefender().getUserConditionChecked()))
				.forEach(s -> defenderCheckedSkills.add(checkSkill(fightInfo, s, false)));

		attackerSkills.stream().filter(s -> s.getBattleType() == 3).filter(s->s.filterSupportSkill(fightInfo.getAttacker().getUserConditionChecked()))
				.forEach(s -> attackerCheckedSkills.add(checkSkill(fightInfo, s, true)));
		defenderSkills.stream().filter(s -> s.getBattleType() == 3).filter(s->s.filterSupportSkill(fightInfo.getDefender().getUserConditionChecked()))
				.forEach(s -> defenderCheckedSkills.add(checkSkill(fightInfo, s, false)));

		log.info("attacker skill list size:" + attackerCheckedSkills.size());
		log.info("defender skill list size:" + defenderCheckedSkills.size());
		fightInfo.getAttacker().setHeroPanel(calculateHero(fightInfo.getAttacker(), attackerCheckedSkills, fightInfo.getDefender().getDebuffs()));
		fightInfo.getAttacker().setSoldierPanel(calculateSoldier(fightInfo.getAttacker(), attackerCheckedSkills, fightInfo.getDefender().getDebuffs()));
		fightInfo.getDefender().setHeroPanel(calculateHero(fightInfo.getDefender(), defenderCheckedSkills, fightInfo.getAttacker().getDebuffs()));
		fightInfo.getDefender().setSoldierPanel(calculateSoldier(fightInfo.getDefender(), defenderCheckedSkills, fightInfo.getAttacker().getDebuffs()));

//		fightInfo.getAttacker().setUserConditions(getUserConditionsFromSkill(attackerSkills));
//		fightInfo.getDefender().setUserConditions(getUserConditionsFromSkill(defenderSkills));

//		fightInfo.getAttacker().setCheckedSkills(attackerCheckedSkills);
//		fightInfo.getDefender().setCheckedSkills(defenderCheckedSkills);

		Map<String, UserCondition> attackerUserConditions = getUserConditionsFromSkill(attackerSkills,
				fightInfo.getAttacker().getUserConditionChecked());
		Map<String, UserCondition> defenderUserConditions = getUserConditionsFromSkill(defenderSkills,
				fightInfo.getDefender().getUserConditionChecked());

		Map<String, Object> sessionInfo = Maps.newHashMap();

		Map<String, Object> attackerSessionInfo = Maps.newHashMap();
		Map<String, Object> defenderSessionInfo = Maps.newHashMap();

		attackerSessionInfo.put("userConditions", attackerUserConditions);
		defenderSessionInfo.put("userConditions", defenderUserConditions);


		attackerSessionInfo.put("userConditionGroups", getUserConditionGroups(attackerSkills));
		defenderSessionInfo.put("userConditionGroups", getUserConditionGroups(defenderSkills));

		attackerSessionInfo.put("checkedSkills", attackerCheckedSkills);
		defenderSessionInfo.put("checkedSkills", defenderCheckedSkills);
		
		attackerSessionInfo.put("supportSkills", attackerSupportSKills);
		defenderSessionInfo.put("supportSkills", defenderSupportSKills);

		sessionInfo.put("attacker", attackerSessionInfo);
		sessionInfo.put("defender", defenderSessionInfo);

		result.put("fightInfo", fightInfo);
		result.put("sessionInfo", sessionInfo);
		return result;
	}

	private Map<String, List<String>> getUserConditionGroups(List<Skill> skills) {
		Map<String, List<String>> result = Maps.newHashMap();
		skills.forEach(s -> {
			if (s.getCondition() instanceof GroupedUserCondition) {
				GroupedUserCondition gu = (GroupedUserCondition) s.getCondition() ;
				if (!result.containsKey(gu.getGroupName())) {
					result.put(gu.getGroupName(), Lists.newArrayList());
				}
				result.get(gu.getGroupName()).add(gu.getName());
			}

		});

		return result;
	}

	public List<Debuff> getDebuffList(List<CheckedSkill> skillList) {
		List<Debuff> buffs = Lists.newArrayList();
		skillList.stream()
				.filter(cs -> cs.isValid() && cs.getSkill().getEffects().stream().anyMatch(e -> e instanceof Debuff))
				.forEach(cs -> {
					buffs.addAll(cs.getSkill().getEffects().stream().filter(e -> e instanceof Debuff)
							.map(e -> (Debuff) e).collect(Collectors.toList()));
				});
		return buffs;
	}
	
	public Map<String, Debuff> getDebuffMap(List<Debuff> debuffList) {
		Map<String, Debuff> result = Maps.newHashMap();
	    debuffList.stream().forEach(d->result.put(d.getName(), d));
	    return result;
		
	}

	public List<Buff> getBuffList(List<CheckedSkill> skillList) {
		List<Buff> buffs = Lists.newArrayList();
		skillList.stream()
				.filter(cs -> cs.isValid() && cs.getSkill().getEffects().stream().anyMatch(e -> e instanceof Buff))
				.forEach(cs -> {
					buffs.addAll(cs.getSkill().getEffects().stream().filter(e -> e instanceof Buff).map(e -> (Buff) e)
							.collect(Collectors.toList()));
				});
		return buffs;
	}

	public int getRange(Hero hero, Action action) {
		if (action != null) {
			return action.getRange();
		}
		return hero.getRange();
	}

	public PanelInfo calculateHero(FightRole role, List<CheckedSkill> skillList, List<Debuff> debuffList) {
		Hero hero = role.getHero();
		PanelInfo panelInfo = role.getHeroPanel();
		Land land = role.getLand();
		Action action = role.getAction();

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
		int range = getRange(hero, action);
		int di = 0;
		int pdd = 0;
		int mdd = 0;
		int si = 0;
		double preBattleDamage = 0;

		List<Double> counters = Lists.newArrayList();
		List<Double> pd_counters = Lists.newArrayList();
		List<Double> md_counters = Lists.newArrayList();
		Map<String, Buff> buffList = Maps.newHashMap();
		Map<String, Debuff> debuffs = getDebuffMap(debuffList);

		if (skillList != null) {
			for (int i = 0; i < skillList.size(); i++) {
				CheckedSkill cs = skillList.get(i);
				if (cs.isValid()) {
					Skill skill = cs.getSkill();
					List<Effect> effects = skill.getEffects(role);
					for (int j = 0; j < effects.size(); j++) {
						Effect e = effects.get(j);
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
							if (en.getScope() != Scope.Hero && en.getScope() != Scope.All) {
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
								log.info("preBattleDamage===" + preBattleDamage);
								break;
							case Range:
								range += number;
								break;
							default:
							}
						} else if (e instanceof Feature) {
							Feature f = (Feature) e;
							if (f.getScope() == Scope.All || f.getScope() == Scope.Hero) {
								panelInfo.getFeatures().putAll(f.getFeatures());
							}
						} else if (e instanceof Counter) {

							Counter c = (Counter) e;
							if (c.getScope() == Scope.All || c.getScope() == Scope.Hero) {
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
					case Range:
						range += number;
						break;
					default:
					}
				}

			}
		}
		
		List<Debuff> dbs = Lists.newArrayList(debuffs.values());
		log.info("debuffs:"+dbs);
		if (dbs != null && dbs.size() > 0) {
			for (int k = 0; k < dbs.size(); k++) {
				Debuff bu = dbs.get(k);
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
					case Range:
						range += number;
						break;
					default:
					}
				}

			}
		}

		if (land == Land.Water && hero.getType() == 4) {
			pi += 30;
		}

		if (land == Land.Wood) {
			pd_counters.add(20.0);
			md_counters.add(20.0);
		}

		if (land == Land.Mountain) {
			pd_counters.add(10.0);
			md_counters.add(10.0);
		}

		if (land == Land.Wall) {
			pd_counters.add(30.0);
			md_counters.add(30.0);
		}

		panelInfo
				.setAttack(Double.valueOf(Math.round(attack * (1 + ai / 100.0) + panelInfo.getAttackJJC())).intValue());
		panelInfo.setIntel(Double.valueOf(Math.floor(intel * (1 + ii / 100.0) + panelInfo.getIntelJJC())).intValue());
		panelInfo
				.setPhysic(Double.valueOf(Math.floor(physic * (1 + pi / 100.0) + panelInfo.getPhysicJJC())).intValue());
		panelInfo.setMagic(Double.valueOf(Math.floor(magic * (1 + mi / 100.0) + panelInfo.getMagicJJC())).intValue());
		panelInfo.setTech(Double.valueOf(Math.floor(tech * (1 + ti / 100.0) + panelInfo.getTechJJC())).intValue());
		panelInfo.setLife(
				Double.valueOf(Math.floor(life * (1 + (li + 40) / 100.0) + panelInfo.getLifeJJC())).intValue());

		panelInfo.setDamageInc(di);
		panelInfo.setPhysicDamageDec(pdd);
		panelInfo.setMagicDamageDec(mdd);

		panelInfo.setCriticalProbInc(cpi);
		panelInfo.setCriticalProbDec(cpd);
		panelInfo.setCriticalDamageInc(cdi);
		panelInfo.setCriticalDamageDec(cdd);

		panelInfo.setPreBattleDamage(preBattleDamage);
		panelInfo.setRange(range);

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

	public PanelInfo calculateSoldier(FightRole role, List<CheckedSkill> skillList, List<Debuff> debuffList) {
		Soldier soldier = role.getSoldier();
		Hero hero = role.getHero();
		PanelInfo panelInfo = role.getSoldierPanel();
		Land land = role.getLand();
		Map<String, Integer> buffCounts = role.getBuffCounts();
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
		int cdd = 0;
		int di = 0;
		int pdd = 0;
		int mdd = 0;
		int range = getRange(hero, null);

		List<Double> counters = Lists.newArrayList();
		List<Double> pd_counters = Lists.newArrayList();
		List<Double> md_counters = Lists.newArrayList();
		Map<String, Buff> buffList = Maps.newHashMap();
		Map<String, Debuff> debuffs =  getDebuffMap(debuffList);

		if (skillList != null) {
			for (int i = 0; i < skillList.size(); i++) {
				CheckedSkill cs = skillList.get(i);
				if (cs.isValid()) {
					Skill skill = cs.getSkill();
					List<Effect> effects = skill.getEffects(role);
					for (int j = 0; j < effects.size(); j++) {
						Effect e = effects.get(j);
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
							if (en.getScope() != Scope.Soldier && en.getScope() != Scope.All) {
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
							case Range:
								range += number;
								break;
							default:
							}
						} else if (e instanceof Feature) {
							Feature f = (Feature) e;
							if (f.getScope() == Scope.All || f.getScope() == Scope.Soldier) {
								panelInfo.getFeatures().putAll(f.getFeatures());
							}
						} else if (e instanceof Counter) {

							Counter c = (Counter) e;
							if (c.getScope() == Scope.All || c.getScope() == Scope.Soldier) {
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
					case Range:
						range += number;
						break;
					default:
					}
				}

			}
		}
		
		List<Debuff> dbs = Lists.newArrayList(debuffs.values());
		if (buffs != null && dbs.size() > 0) {
			for (int k = 0; k < dbs.size(); k++) {
				Debuff bu = dbs.get(k);
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
					case Range:
						range += number;
						break;
					default:
					}
				}

			}
		}

		if (land == Land.Water && (hero.getType() == 4 || soldier.getType() == 4)) {
			pi += 30;
		}

		if (land == Land.Wood) {
			pd_counters.add(20.0);
			md_counters.add(20.0);
		}

		if (land == Land.Mountain) {
			pd_counters.add(10.0);
			md_counters.add(10.0);
		}

		if (land == Land.Wall) {
			pd_counters.add(30.0);
			md_counters.add(30.0);
		}

		panelInfo.setAttack(Double
				.valueOf(Math.floor(attack * (1 + ai / 100.0) * (1 + hero.getSoldierAttackInc() / 100.0))).intValue());
		panelInfo.setPhysic(Double
				.valueOf(Math.floor(physic * (1 + pi / 100.0) * (1 + hero.getSoldierPhysicInc() / 100.0))).intValue());
		panelInfo.setMagic(Double
				.valueOf(Math.floor(magic * (1 + mi / 100.0) * (1 + hero.getSoldierMagicInc() / 100.0))).intValue());
		panelInfo.setLife(
				Double.valueOf(Math.floor(life * (1 + (li + 40) / 100.0) * (1 + hero.getSoldierLifeInc() / 100.0)))
						.intValue() * 10);

		panelInfo.setDamageInc(di);
		panelInfo.setPhysicDamageDec(pdd);
		panelInfo.setMagicDamageDec(mdd);

		panelInfo.setCriticalProbInc(cpi);
		panelInfo.setCriticalProbDec(cpd);
		panelInfo.setCriticalDamageInc(cdi);
		panelInfo.setCriticalDamageDec(cdd);
		panelInfo.setRange(range);

		for (int i = 0; i < counters.size(); i++) {
			panelInfo.setAttack(
					Double.valueOf(Math.floor(panelInfo.getAttack() * (1 + counters.get(i) / 100))).intValue());

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
		if(skill.getCondition() == null)
		{
			log.info("skill:"+skill.getClass().getName()+"is null");
			return null;
		}
		boolean valid = skill.getCondition().valid(fightInfo, isAttack);
		if (valid) {
			skill.process(fightInfo, isAttack);
		}

		result.setValid(valid);
		return result;
	}

	private List<Skill> getSupportSkills(List<Skill> skills) {
		return skills.stream().filter(s-> s.isSupportSkill()).collect(Collectors.toList());
	}

	public Map<String, UserCondition> getUserConditionsFromSkill(List<Skill> skills, Map<String, Boolean> ucc) {

		Map<String, UserCondition> resultList = Maps.newHashMap();
		if (skills != null) {
			skills.stream().forEach(skill -> {
				if (skill.getCondition() instanceof UserCondition) {
					String name = ((UserCondition) skill.getCondition()).getName();
					resultList.put(name, (UserCondition) skill.getCondition());
				
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
