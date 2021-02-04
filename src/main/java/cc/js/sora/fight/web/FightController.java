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

import cc.js.sora.fight.Fight;
import cc.js.sora.fight.FightResult;
import cc.js.sora.fight.Hero;
import cc.js.sora.fight.HeroEquip;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.Soldier;
import cc.js.sora.fight.condition.CombinedCondition;
import cc.js.sora.fight.condition.UserCondition;
import cc.js.sora.fight.db.HeroEquipRepository;
import cc.js.sora.fight.db.HeroRepository;
import cc.js.sora.fight.db.SoldierRepository;
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
	SkillService skillSerivce;
	
	@RequestMapping(path = "/cal", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public FightResult calculate(@RequestBody Fight fight)
	{
		log.info("fight:"+fight);
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
	public List<Hero> heros()
	{
		List<Hero> currentRecord = heroRepository.findAll();
		return currentRecord;
	}
	
	@RequestMapping(path = "/soldiers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Soldier> soldier()
	{
		List<Soldier> currentRecord = soldierRepository.findAll();
		return currentRecord;
	}
	
	@RequestMapping(path = "/heros/{heroId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Hero hero(@PathVariable long heroId)
	{
		Hero hero = heroRepository.findById(heroId).get();
		return hero;
	}

	
	@RequestMapping(path = "/heros/{heroId}/heroEquip", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public HeroEquip heroEquip(@PathVariable long heroId)
	{
		return heroEquipRepository.findHeroEquip(heroId);
	}
	
	@RequestMapping(path = "/buffs", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Map getBuffs(@RequestBody Fight fight)
	{
		Map result = new HashMap();
		List<Skill> attackerSkills = skillSerivce.getSkills(fight.getAttackerHeroId(), fight.getAttackerSoldierId(), true);
		List<Skill> defenderSkills =  skillSerivce.getSkills(fight.getDefenderHeroId(), fight.getDefenderSoldierId(), false);
		result.put("attackerSkills", attackerSkills);
		result.put("defenderSkills",defenderSkills);
		result.put("attackerUserConditions", getUserConditionsFromSkill(attackerSkills));
		result.put("defenderUserConditions", getUserConditionsFromSkill(defenderSkills));
		return result;
	}
	
	public List<UserCondition> getUserConditionsFromSkill(List<Skill> skills)
	{
		List<UserCondition> resultList = new ArrayList<UserCondition>();
		if(skills != null) 
		{
			skills.stream().forEach(skill->{
				log.info("haha");
				if(skill== null)
				{
					log.error("skill is null");
				}
				if(skill.getCondition() instanceof UserCondition)
				{
					resultList.add((UserCondition)skill.getCondition());
				}
				if(skill.getCondition() instanceof CombinedCondition)
				{
					((CombinedCondition)skill.getCondition()).getConditions().stream().forEach(c->{
						if(c instanceof UserCondition)
						{
							resultList.add((UserCondition)c);
						}
					});
				}
			});
		}
		return resultList;
	}
	
	@RequestMapping(path = "/skills", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map allSkills()
	{
		return skillSerivce.getAllSkills();
	}

}
