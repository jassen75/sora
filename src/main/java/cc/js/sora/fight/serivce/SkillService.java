package cc.js.sora.fight.serivce;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.primitives.Longs;

import cc.js.sora.fight.Action;
import cc.js.sora.fight.BarrackSkills;
import cc.js.sora.fight.Hero;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.Soldier;
import cc.js.sora.fight.db.ActionRepository;
import cc.js.sora.fight.db.HeroRepository;
import cc.js.sora.fight.db.SoldierRepository;
import cc.js.sora.fight.skill.GriffinSkill;
import cc.js.sora.fight.skill.PatyleTalent;
import cc.js.sora.fight.skill.SuperBuff;
import cc.js.sora.fight.skill.TowaTalent;
import cc.js.sora.fight.skill.WizardSkill;
import cc.js.sora.fight.skill.action.DreamAction;
import cc.js.sora.fight.skill.equip.LastSuit;
import cc.js.sora.fight.skill.equip.WindEnhance;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SkillService {

	@Autowired
	HeroRepository heroRepository;

	@Autowired
	SoldierRepository soldierRepository;
	
	@Autowired
	ActionRepository actionRepository;

	BarrackSkills barrackSkills = new BarrackSkills() ;
	
	List<Long> globalSkills = Lists.newArrayList(Skill.SuperBuff);

	public Map<Long, Skill> skills = Maps.newHashMap();

	public SkillService() {
		init();
	}

	private void init() {
		log.info("init skills");
		skills.clear();

		registerSkill(Skill.Zuihouzhifu, new LastSuit());
		registerSkill(Skill.WindEnhance, new WindEnhance());
		
		registerSkill(Skill.PatyleTalent, new PatyleTalent());
		registerSkill(Skill.TowaTalent, new TowaTalent());
		
		registerSkill(Skill.MonvSkill, new WizardSkill());
		registerSkill(Skill.HuangjiashijiuSkill, new GriffinSkill());
		
		registerSkill(Skill.Shimeng, new DreamAction());
		
		registerSkill(Skill.SuperBuff, new SuperBuff());
		skills.putAll(barrackSkills.getAllBarrackSkills());
	}

	public Map<Long, Skill> getAllSkills() {
		if (skills.size() == 0) {
			init();
		}
		return skills;
	}

	public Skill getSkill(long id) {

		if (skills.size() == 0) {
			init();
		}
		return skills.get(id);

	}

	public void registerSkill(long id, Skill skill) {

		skills.put(id, skill);

	}

	public List<Skill> getSkills(long heroId, long soldierId, long actionId,  boolean isAttacker) {
		List<Skill> result = new ArrayList<Skill>();
		if(heroId > 0)
		{
			Hero hero = heroRepository.getOne(heroId);
			String hs = hero.getSkills();
			if (!StringUtils.isEmpty(hs)) {
				String[] d = StringUtils.split(hs, ",");
				for (int i = 0; i < d.length; i++) {
					long skillId = Longs.tryParse(d[i]);
					if (this.skills.containsKey(skillId)) {
						result.add(this.getSkill(skillId));
					}
				}
			}
		}
		
		if(soldierId > 0) 
		{
			Soldier soldier = soldierRepository.getOne(soldierId);
			String ss = soldier.getSkills();
			if (!StringUtils.isEmpty(ss)) {
				String[] d = StringUtils.split(ss, ",");
				for (int i = 0; i < d.length; i++) {
					long skillId = Longs.tryParse(d[i]);
					if (this.skills.containsKey(skillId)) {
						result.add(this.getSkill(skillId));
					}
				}
			}

			int soldierType = soldier.getType();
			if(barrackSkills.getSkills(soldierType) != null)
			{
				result.addAll(barrackSkills.getSkills(soldierType));
			}
			
		}
		
		if(actionId > 0)
		{
			Action action = actionRepository.getOne(actionId);
			String as = action.getSkills();
			if (!StringUtils.isEmpty(as)) {
				String[] d = StringUtils.split(as, ",");
				for (int i = 0; i < d.length; i++) {
					long skillId = Longs.tryParse(d[i]);
					if (this.skills.containsKey(skillId)) {
						result.add(this.getSkill(skillId));
					}
				}
			}
			
		}
		
		globalSkills.forEach(i->{
			if (this.skills.containsKey(i)) {
				result.add(this.getSkill(i));
			}
		});


		return result;
	}

}
