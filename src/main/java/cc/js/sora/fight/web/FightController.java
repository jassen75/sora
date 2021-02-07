package cc.js.sora.fight.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import cc.js.sora.fight.Fight;
import cc.js.sora.fight.FightResult;
import cc.js.sora.fight.Hero;
import cc.js.sora.fight.HeroEquip;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.Soldier;
import cc.js.sora.fight.condition.CombinedCondition;
import cc.js.sora.fight.condition.UserCondition;
import cc.js.sora.fight.db.ActionRepository;
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

	@RequestMapping(path = "/cal", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public FightResult calculate(@RequestBody Fight fight) {
		log.info("fight:" + fight);
		FightResult result = new FightResult(fight);
//		if(fight.getAttacker().isPhysic())
//		{
//			log.info("come here");
//			int damage = (fight.getAttackerAttck() - fight.getDefenderPhysicDef())/2;
//			int number = 20;
//			result.setAttackerHeroDamage2(damage*number);
//		} else
//		{
//			
//		}
//		
//		if(fight.getDefender().isPhysic())
//		{
//			
//		} else
//		{
//			
//		}
		return result;
	}

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

}
