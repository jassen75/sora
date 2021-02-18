package cc.js.sora.fight.web;

import java.util.ArrayList;
import java.util.HashMap;
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
import cc.js.sora.fight.Fight;
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
	ActionRepository actionRepository;

	@Autowired
	SkillService skillSerivce;

	@Autowired
	ConditionService conditionService;

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
			log.info(hero.toString());
			return hero;
		} finally {
			lock.unlock();
		}
	}

	@RequestMapping(path = "/buffs", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> getBuffs(@RequestBody Fight fight) {
		lock.lock();
		try {
			log.info("Current fight:" + fight);
			Map<String, Object> result = Maps.newHashMap();
			List<Skill> attackerSkills = skillSerivce.getSkills(fight.getAttackerHeroId(), fight.getAttackerSoldierId(),
					fight.getAttackerActionId(), fight.getAttackerEnhance(), fight.getAttackerEquip(), true);
			List<Skill> defenderSkills = skillSerivce.getSkills(fight.getDefenderHeroId(), fight.getDefenderSoldierId(),
					0, fight.getDefenderEnhance(), fight.getDefenderEquip(), false);

			List<CheckedSkill> attackerCheckedSkills = Lists.newArrayList();
			List<CheckedSkill> defenderCheckedSkills = Lists.newArrayList();
			
			attackerSkills.stream().filter(s->s.getBattleType()==0).forEach(s -> attackerCheckedSkills.add(checkSkill(fight, s, true)));
			defenderSkills.stream().filter(s->s.getBattleType()==0).forEach(s -> defenderCheckedSkills.add(checkSkill(fight, s, false)));
			
			attackerSkills.stream().filter(s->s.getBattleType()==1).forEach(s -> attackerCheckedSkills.add(checkSkill(fight, s, true)));
			defenderSkills.stream().filter(s->s.getBattleType()==1).forEach(s -> defenderCheckedSkills.add(checkSkill(fight, s, false)));

			attackerSkills.stream().filter(s->s.getBattleType()==2).forEach(s -> attackerCheckedSkills.add(checkSkill(fight, s, true)));
			defenderSkills.stream().filter(s->s.getBattleType()==2).forEach(s -> defenderCheckedSkills.add(checkSkill(fight, s, false)));
			
			attackerSkills.stream().filter(s->s.getBattleType()==3).forEach(s -> attackerCheckedSkills.add(checkSkill(fight, s, true)));
			defenderSkills.stream().filter(s->s.getBattleType()==3).forEach(s -> defenderCheckedSkills.add(checkSkill(fight, s, false)));

			result.put("attackerSkills", attackerCheckedSkills);
			result.put("defenderSkills", defenderCheckedSkills);
			result.put("attackerUserConditions", getUserConditionsFromSkill(attackerSkills));
			result.put("defenderUserConditions", getUserConditionsFromSkill(defenderSkills));
			return result;
		} finally {
			lock.unlock();

		}
	}

	private CheckedSkill checkSkill(Fight fight, Skill skill, boolean isAttack) {
		lock.lock();
		try {

			CheckedSkill result = new CheckedSkill();
			result.setSkill(skill);
			boolean valid = conditionService.checkCondition(fight, skill.getCondition(),
					isAttack ? fight.getAttackerUserConditionChecked() : fight.getDefenderUserConditionChecked(),
					isAttack);
			if(valid)
			{
				skill.process(fight, isAttack);
			}
			
			result.setValid(valid);
			return result;
		} finally {
			lock.unlock();

		}

	}

	public Map<String, UserCondition> getUserConditionsFromSkill(List<Skill> skills) {
		lock.lock();
		try
		{
			Map<String, UserCondition> resultList = Maps.newHashMap();
			if (skills != null) {
				skills.stream().forEach(skill -> {
					if (skill == null) {
						log.error("skill is null");
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
		} finally
		{
			lock.unlock();
		}
	}

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
			
			List<Equip> ow = equipRepository.findByOwner(heroId);
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
