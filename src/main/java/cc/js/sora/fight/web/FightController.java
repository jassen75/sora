package cc.js.sora.fight.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cc.js.sora.fight.CheckedSkill;
import cc.js.sora.fight.Equip;
import cc.js.sora.fight.EquipPart;
import cc.js.sora.fight.EquipType;
import cc.js.sora.fight.Fight;
import cc.js.sora.fight.FightResult;
import cc.js.sora.fight.Hero;
import cc.js.sora.fight.HeroEquip;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.Soldier;
import cc.js.sora.fight.condition.CombinedCondition;
import cc.js.sora.fight.condition.UserCondition;
import cc.js.sora.fight.db.ActionRepository;
import cc.js.sora.fight.db.EquipRepository;
import cc.js.sora.fight.db.HeroEquipRepository;
import cc.js.sora.fight.db.HeroRepository;
import cc.js.sora.fight.db.SoldierRepository;
import cc.js.sora.fight.serivce.ConditionService;
import cc.js.sora.fight.serivce.SkillService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/fight")
@Slf4j
public class FightController {

	@Autowired
	HeroRepository heroRepository;

	@Autowired
	SoldierRepository soldierRepository;

	@Autowired
	HeroEquipRepository heroEquipRepository;
	
	@Autowired
	ActionRepository actionRepository;

	@Autowired
	SkillService skillSerivce;
	
	@Autowired
	ConditionService conditionService;

	@Autowired
	EquipRepository equipRepository;
	
	ReentrantReadWriteLock lock = new java.util.concurrent.locks.ReentrantReadWriteLock();
	
	@RequestMapping(path = "/heros", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Hero> heros() {
		List<Hero> currentRecord = heroRepository.findAll();
		return currentRecord;
	}

	@RequestMapping(path = "/soldiers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Soldier> soldier() {
		List<Soldier> currentRecord = soldierRepository.findAll();
		return currentRecord;
	}

	@RequestMapping(path = "/heros/{heroId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Hero hero(@PathVariable long heroId) {
		Hero hero = heroRepository.findById(heroId).get();
		return hero;
	}

	@RequestMapping(path = "/heros/{heroId}/heroEquip", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public HeroEquip heroEquip(@PathVariable long heroId) {
		return heroEquipRepository.findHeroEquip(heroId);
	}

	@RequestMapping(path = "/buffs", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Map getBuffs(@RequestBody Fight fight) {
		log.info("Current fight:"+fight);
		Map result = new HashMap();
		List<Skill> attackerSkills = skillSerivce.getSkills(fight.getAttackerHeroId(), fight.getAttackerSoldierId(), fight.getAttackerActionId(),
				true);
		List<Skill> defenderSkills = skillSerivce.getSkills(fight.getDefenderHeroId(), fight.getDefenderSoldierId(), 0, 
				false);

		List<CheckedSkill> attackerCheckedSkills = Lists.newArrayList();
		List<CheckedSkill> defenderCheckedSkills = Lists.newArrayList();
		
		attackerSkills.stream().forEach(s -> attackerCheckedSkills.add(checkSkill(fight, s, true)));
		defenderSkills.stream().forEach(s -> defenderCheckedSkills.add(checkSkill(fight, s, false)));
		
		result.put("attackerSkills", attackerCheckedSkills);
		result.put("defenderSkills", defenderCheckedSkills);
		result.put("attackerUserConditions", getUserConditionsFromSkill(attackerSkills));
		result.put("defenderUserConditions", getUserConditionsFromSkill(defenderSkills));
		return result;
	}

	private CheckedSkill checkSkill(Fight fight, Skill skill, boolean isAttack) {
		CheckedSkill result = new CheckedSkill();
		result.setSkill(skill);
		result.setValid(conditionService.checkCondition(fight, skill.getCondition(),
				isAttack ? fight.getAttackerUserConditionChecked() : fight.getDefenderUserConditionChecked(),
				isAttack));
		return result;

	}

	public Map<String, UserCondition> getUserConditionsFromSkill(List<Skill> skills) {
		Map<String, UserCondition> resultList = Maps.newHashMap();
		if (skills != null) {
			skills.stream().forEach(skill -> {
				if (skill == null) {
					log.error("skill is null");
				}
				if (skill.getCondition() instanceof UserCondition) {
					resultList.put(((UserCondition) skill.getCondition()).getName(), (UserCondition) skill.getCondition());
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

	@RequestMapping(path = "/skills", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map allSkills() {
		return skillSerivce.getAllSkills();
	}
	
	@RequestMapping(path = "/equips/{heroId}/{part}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Equip> getEquips(@PathVariable long heroId, @PathVariable EquipPart part) {
		log.info("equip part:"+part);
		lock.readLock().lock();
		try
		{
			Hero hero = heroRepository.findById(heroId).get();
			List<EquipType> supportedTypes = hero.getEquips();
			List<Equip> result = Lists.newArrayList();
			
			supportedTypes.forEach(et -> {
				if(et.getPart() == part) 
				{
					List<Equip> el = equipRepository.findByType(et.getId());	
					if(el != null)
					{
						result.addAll(el);
					}
				}
			});
			return result;
		} finally
		{
			lock.readLock().unlock();
		}
	}

}
