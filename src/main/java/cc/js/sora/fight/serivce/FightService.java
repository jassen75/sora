package cc.js.sora.fight.serivce;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cc.js.sora.fight.Buff;
import cc.js.sora.fight.CheckedSkill;
import cc.js.sora.fight.Debuff;
import cc.js.sora.fight.Effect;
import cc.js.sora.fight.Enhance;
import cc.js.sora.fight.FightInfo;
import cc.js.sora.fight.Hero;
import cc.js.sora.fight.PanelInfo;
import cc.js.sora.fight.Scope;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.Soldier;
import cc.js.sora.fight.condition.CombinedCondition;
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
		List<Skill> attackerSkills = skillService.getSkills(fightInfo.getAttacker().getHero(),
				fightInfo.getAttacker().getSoldier(), fightInfo.getAttacker().getAction().getId(),
				fightInfo.getAttacker().getEnhance(), fightInfo.getAttacker().getEquip(), true);


		List<Skill> defenderSkills = skillService.getSkills(fightInfo.getDefender().getHero(),
				fightInfo.getDefender().getSoldier(), 0, fightInfo.getDefender().getEnhance(),
				fightInfo.getDefender().getEquip(), false);


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
				fightInfo.getAttacker().getHeroPanel(), attackerCheckedSkills));
		fightInfo.getAttacker().setSoldierPanel(this.calculate(fightInfo.getAttacker().getSoldier(),
				fightInfo.getAttacker().getSoldierPanel(), attackerCheckedSkills));
		fightInfo.getDefender().setHeroPanel(this.calculate(fightInfo.getDefender().getHero(),
				fightInfo.getDefender().getHeroPanel(), defenderCheckedSkills));
		fightInfo.getDefender().setSoldierPanel(this.calculate(fightInfo.getDefender().getSoldier(),
				fightInfo.getDefender().getSoldierPanel(), defenderCheckedSkills));
		
//		fightInfo.getAttacker().setUserConditions(getUserConditionsFromSkill(attackerSkills));
//		fightInfo.getDefender().setUserConditions(getUserConditionsFromSkill(defenderSkills));
		
//		fightInfo.getAttacker().setCheckedSkills(attackerCheckedSkills);
//		fightInfo.getDefender().setCheckedSkills(defenderCheckedSkills);

		result.put("attackerUserConditions", getUserConditionsFromSkill(attackerSkills));
		result.put("defenderUserConditions", getUserConditionsFromSkill(defenderSkills));
		
		result.put("attackerSkills", attackerCheckedSkills);
		result.put("defenderSkills", defenderCheckedSkills);
		
		result.put("fightInfo", fightInfo);
		return result;
	}

	public PanelInfo calculate(Hero hero, PanelInfo panelInfo, List<CheckedSkill> skillList) {

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
		int cpi = panelInfo.getCriticalProbInc();
		int cdi = panelInfo.getCriticalDamageInc();
		int cpd = panelInfo.getCriticalProbDec();
		int cdd = panelInfo.getCriticalDamageDec();
		int di = 0;
		int pdd = 0;
		int mdd = 0;
		int si = 0;
		int preBattleDamage = 0;
		
		log.info("hero b ai=="+ai);
		log.info("hero b ii=="+ii);
		log.info("hero b pi=="+pi);
		log.info("hero b mo=="+mi);
		log.info("hero b ti=="+ti);
		log.info("hero b li=="+li);
		List<Integer> counters = Lists.newArrayList();
		List<Integer> pd_counters = Lists.newArrayList();
		Map<String, Buff> buffList = Maps.newHashMap();
		Map<String, Debuff> debuffList = Maps.newHashMap();

		if (skillList != null) {
			for (int i = 0; i < skillList.size(); i++) {
				CheckedSkill cs = skillList.get(i);
				if (cs.isValid()
						&& (cs.getSkill().getScope() == Scope.Hero || cs.getSkill().getScope() == Scope.All)) {
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
							int number = en.getNumber();
							switch (en.getBuffType()) {
							case Attack:
								ai += number;
								break;
							case Intel:
								ii += number;
								break;
							case PhysicDef:
								pi += number;
								break;
							case MagicDef:
								mi += number;
								break;
							case DamageInc:
								di += number;
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
								break;
							default:
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
					int number = ek.getNumber();
					switch (ek.getBuffType()) {
					case Attack:
						ai += number;
						break;
					case Intel:
						ii += number;
						break;
					case PhysicDef:
						pi += number;
						break;
					case MagicDef:
						mi += number;
						break;
					case DamageInc:
						di += number;
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

		log.info("hero a ai=="+ai);
		log.info("hero a ii=="+ii);
		log.info("hero a pi=="+pi);
		log.info("hero a mo=="+mi);
		log.info("hero a ti=="+ti);
		log.info("hero a li=="+li);
		panelInfo.setAttack(new Double(Math.floor(attack * (1 + ai / 100.0) + panelInfo.getAttackJJC())).intValue());
		panelInfo.setIntel(new Double(Math.floor(intel * (1 + ii / 100.0) + panelInfo.getIntelJJC())).intValue());
		panelInfo.setPhysic(new Double(Math.floor(physic * (1 + pi / 100.0) + panelInfo.getPhysicJJC())).intValue());
		panelInfo.setMagic(new Double(Math.floor(magic * (1 + mi / 100.0) + panelInfo.getMagicJJC())).intValue());
		panelInfo.setTech(new Double(Math.floor(tech * (1 + ti / 100.0) + panelInfo.getTechJJC())).intValue());
		panelInfo.setLife(new Double(Math.floor(life * (1 + li / 100.0) + panelInfo.getLifeJJC())).intValue());

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
						new Double(Math.floor(panelInfo.getAttack() * (1 + counters.get(i) / 100))).intValue());
			} else {
				panelInfo.setIntel(
						new Double(Math.floor(panelInfo.getIntel() * (1 + counters.get(i) / 100))).intValue());
			}
		}
		for (int i = 0; i < pd_counters.size(); i++) {
			panelInfo.setPhysic(
					new Double(Math.floor(panelInfo.getPhysic() * (1 + pd_counters.get(i) / 100))).intValue());
		}

		return panelInfo;
	}

	public PanelInfo calculate(Soldier soldier, PanelInfo panelInfo, List<CheckedSkill> skillList) {
		int life = soldier.getLife();
		int attack = soldier.getAttack();
		int physic = soldier.getPhysic();
		int magic = soldier.getMagic();

		int ai = panelInfo.getAttackSkill();
		int pi = panelInfo.getPhysicSkill();
		int mi = panelInfo.getMagicSkill();
		int li = panelInfo.getLifeSkill();
		int cpi = panelInfo.getCriticalProbInc();
		int cdi = panelInfo.getCriticalDamageInc();
		int cpd = panelInfo.getCriticalProbDec();
		int cdd = panelInfo.getCriticalDamageDec();
		int di = 0;
		int pdd = 0;
		int mdd = 0;
		
		log.info("soldier b ai=="+ai);
		log.info("soldier b pi=="+pi);
		log.info("soldier b mo=="+mi);
		log.info("soldier b li=="+li);
		
		List<Integer> counters = Lists.newArrayList();
		List<Integer> pd_counters = Lists.newArrayList();
		Map<String, Buff> buffList = Maps.newHashMap();
		Map<String, Debuff> debuffList = Maps.newHashMap();

		if (skillList != null) {
			for (int i = 0; i < skillList.size(); i++) {
				CheckedSkill cs = skillList.get(i);
				if (cs.isValid()
						&& (cs.getSkill().getScope() == Scope.Soldier || cs.getSkill().getScope() == Scope.All)) {
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
							int number = en.getNumber();
							switch (en.getBuffType()) {
							case Attack:
								ai += number;
								break;
							case PhysicDef:
								pi += number;
								break;
							case MagicDef:
								mi += number;
								break;
							case DamageInc:
								di += number;
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

			}
		}

		List<Buff> buffs = Lists.newArrayList(buffList.values());
		if (buffs != null && buffs.size() > 0) {
			for (int k = 0; k < buffs.size(); k++) {
				Buff bu = buffs.get(k);
				for (int m = 0; m < bu.getEnhanceList().size(); m++) {
					Enhance ek = (Enhance) bu.getEnhanceList().get(m);
					int number = ek.getNumber();
					switch (ek.getBuffType()) {
					case Attack:
						ai += number;
						break;
					case PhysicDef:
						pi += number;
						break;
					case MagicDef:
						mi += number;
						break;
					case DamageInc:
						di += number;
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
		
		log.info("soldier a ai=="+ai);
		log.info("soldier a pi=="+pi);
		log.info("soldier a  mo=="+mi);
		log.info("soldier a li=="+li);

		panelInfo.setAttack(new Double(Math.floor(attack * (1 + ai / 100.0) + panelInfo.getAttackJJC())).intValue());
		panelInfo.setPhysic(new Double(Math.floor(physic * (1 + pi / 100.0) + panelInfo.getPhysicJJC())).intValue());
		panelInfo.setMagic(new Double(Math.floor(magic * (1 + mi / 100.0) + panelInfo.getMagicJJC())).intValue());
		panelInfo.setLife(new Double(Math.floor(life * (1 + li / 100.0) + panelInfo.getLifeJJC())).intValue());

		panelInfo.setDamageInc(di);
		panelInfo.setPhysicDamageDec(pdd);
		panelInfo.setMagicDamageDec(mdd);

		panelInfo.setCriticalProbInc(cpi);
		panelInfo.setCriticalProbDec(cpd);
		panelInfo.setCriticalDamageInc(cdi);
		panelInfo.setCriticalDamageDec(cdd);

		for (int i = 0; i < counters.size(); i++) {
			panelInfo.setAttack(new Double(Math.floor(panelInfo.getAttack() * (1 + counters.get(i) / 100))).intValue());

		}
		for (int i = 0; i < pd_counters.size(); i++) {
			panelInfo.setPhysic(
					new Double(Math.floor(panelInfo.getPhysic() * (1 + pd_counters.get(i) / 100))).intValue());
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
