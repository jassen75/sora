package cc.js.sora.fight.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.primitives.Longs;

import cc.js.sora.fight.CheckedSkill;
import cc.js.sora.fight.Equip;
import cc.js.sora.fight.EquipPart;
import cc.js.sora.fight.EquipType;
import cc.js.sora.fight.FightInfo;
import cc.js.sora.fight.Hero;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.Soldier;
import cc.js.sora.fight.condition.CombinedCondition;
import cc.js.sora.fight.condition.UserCondition;
import cc.js.sora.fight.db.ActionRepository;
import cc.js.sora.fight.db.EquipRepository;
import cc.js.sora.fight.db.EquipTypeRepository;
import cc.js.sora.fight.db.HeroRepository;
import cc.js.sora.fight.db.SoldierRepository;
import cc.js.sora.fight.serivce.FightService;
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
	ActionRepository actionRepository;

	@Autowired
	SkillService skillSerivce;
	
	@Autowired
	FightService fightService;

	@Autowired
	EquipRepository equipRepository;

	@Autowired
	EquipTypeRepository equipTypeRepository;

	Lock lock = new ReentrantLock();

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
		lock.lock();
		try {
			Hero hero = heroRepository.findById(heroId).get();
			//log.info(hero.toString());
			return hero;
		} finally {
			lock.unlock();
		}
	}
	
	
	@RequestMapping(path = "/sync", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object>  sync(@RequestBody FightInfo fight) {
		if(fight.getAttacker() == null || fight.getDefender()==null || fight.getAttacker().getHero()==null || fight.getDefender().getHero()==null)
		{
			log.warn("information lose!");
		} else
		{
			log.info("calculating:["+fight.getAttacker().getHero().getName()+"] vs ["+fight.getDefender().getHero().getName()+"]");
		}
		 Map<String, Object> result =fightService.calculatePanel(fight);
		return result;
	}

//	@RequestMapping(path = "/buffs", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
//	public Map<String, Object> getBuffs(@RequestBody FightInfo fight) {
//		lock.lock();
//		try {
//			log.info("Current fight:" + fight);
//			Map<String, Object> result = Maps.newHashMap();
//			List<Skill> attackerSkills = skillSerivce.getSkills(fight.getAttacker().getHero(), fight.getAttacker().getSoldier(),
//					fight.getAttacker().getAction().getId(), fight.getAttacker().getEnhance(), fight.getAttacker().getEquip(), true);
//			List<Skill> defenderSkills = skillSerivce.getSkills(fight.getDefender().getHero(), fight.getDefender().getSoldier(),
//					0, fight.getDefender().getEnhance(), fight.getDefender().getEquip(), false);
//
//			List<CheckedSkill> attackerCheckedSkills = Lists.newArrayList();
//			List<CheckedSkill> defenderCheckedSkills = Lists.newArrayList();
//			
//			attackerSkills.stream().filter(s->s.getBattleType()==0).forEach(s -> attackerCheckedSkills.add(checkSkill(fight, s, true)));
//			defenderSkills.stream().filter(s->s.getBattleType()==0).forEach(s -> defenderCheckedSkills.add(checkSkill(fight, s, false)));
//			
//			attackerSkills.stream().filter(s->s.getBattleType()==1).forEach(s -> attackerCheckedSkills.add(checkSkill(fight, s, true)));
//			defenderSkills.stream().filter(s->s.getBattleType()==1).forEach(s -> defenderCheckedSkills.add(checkSkill(fight, s, false)));
//
//			attackerSkills.stream().filter(s->s.getBattleType()==2).forEach(s -> attackerCheckedSkills.add(checkSkill(fight, s, true)));
//			defenderSkills.stream().filter(s->s.getBattleType()==2).forEach(s -> defenderCheckedSkills.add(checkSkill(fight, s, false)));
//			
//			attackerSkills.stream().filter(s->s.getBattleType()==3).forEach(s -> attackerCheckedSkills.add(checkSkill(fight, s, true)));
//			defenderSkills.stream().filter(s->s.getBattleType()==3).forEach(s -> defenderCheckedSkills.add(checkSkill(fight, s, false)));
//
//			result.put("attackerSkills", attackerCheckedSkills);
//			result.put("defenderSkills", defenderCheckedSkills);
//			result.put("attackerUserConditions", getUserConditionsFromSkill(attackerSkills));
//			result.put("defenderUserConditions", getUserConditionsFromSkill(defenderSkills));
//			return result;
//		} finally {
//			lock.unlock();
//
//		}
//	}



	@RequestMapping(path = "/skills", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<Long, Skill> allSkills() {
		lock.lock();
		try
		{
			return skillSerivce.getAllSkills();
		} finally
		{
			lock.unlock();
		}
	}

	@RequestMapping(path = "/equips/{heroId}/{part}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Equip> getEquips(@PathVariable long heroId, @PathVariable EquipPart part) {
		lock.lock();
		try {
			Hero hero = heroRepository.findById(heroId).get();
			String supportedTypes = hero.getSupportedEquipTypes();

			String[] data = supportedTypes.split(",");
			List<Equip> result = Lists.newArrayList();

			for (int i = 0; i < data.length; i++) {
				EquipType et = equipTypeRepository.findById(Longs.tryParse(data[i])).get();
				if (et.getPart() == part) {
					List<Equip> el = equipRepository.findByType(et.getId());
					if (el != null) {
						result.addAll(el.stream().filter(e->e.getOwner()==0).collect(Collectors.toList()));
					}
				}
			}
			
			long ownerId = heroId ;
			while(ownerId > 10000)
			{
				ownerId-=10000;
			}
			List<Equip> ow = equipRepository.findByOwner(ownerId);
			if(ow != null && ow.size()>0)
			{
				if(ow.get(0).getEquipType().getPart()==part)
				{
					result.add(ow.get(0));
				}
			}
			return result;
		} catch (Exception ex) {
			log.error(ex.getMessage());
			return new ArrayList<Equip>();
		} finally {
			lock.unlock();
		}
	}

}
