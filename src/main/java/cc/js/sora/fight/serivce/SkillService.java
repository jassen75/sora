package cc.js.sora.fight.serivce;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.primitives.Longs;

import cc.js.sora.fight.Action;
import cc.js.sora.fight.BarrackSkills;
import cc.js.sora.fight.Equip;
import cc.js.sora.fight.Hero;
import cc.js.sora.fight.Skill;
import cc.js.sora.fight.Soldier;
import cc.js.sora.fight.db.ActionRepository;
import cc.js.sora.fight.db.HeroRepository;
import cc.js.sora.fight.db.SoldierRepository;
import cc.js.sora.fight.skill.*;
import cc.js.sora.fight.skill.action.*;
import cc.js.sora.fight.skill.equip.*;
import cc.js.sora.fight.skill.passivity.*;
import cc.js.sora.fight.skill.soldier.*;
import cc.js.sora.fight.skill.talent.*;

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
	
	List<Long> ehanceSkills = Lists.newArrayList(0L, Skill.WindEnhance, 0L, 0L, 0L, Skill.FuriousEnhance, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L);
	
	public Map<Long, Skill> skills = Maps.newHashMap();

	public SkillService() {
		init();
	}

	private void init() {
		log.info("init skills");
		skills.clear();

		registerSkill(Skill.Zuihouzhifu, new LastSuit());
		registerSkill(Skill.Lage, new Lage());
		registerSkill(Skill.Xunzhang, new Xunzhang());
		
		registerSkill(Skill.WindEnhance, new WindEnhance());
		registerSkill(Skill.FuriousEnhance, new FuriousEnhance());
		
		registerSkill(Skill.BloodBattle, new BloodBattle());
		
		registerSkill(Skill.PatyleTalent, new PatyleTalent());
		registerSkill(Skill.TowaTalent, new TowaTalent());
		registerSkill(Skill.ZalrahdaTalent1, new ZalrahdaTalent1());
		registerSkill(Skill.ZalrahdaTalent2, new ZalrahdaTalent2());
		
		registerSkill(Skill.MonvSkill, new WizardSkill());
		registerSkill(Skill.HuangjiashijiuSkill, new GriffinSkill());
		registerSkill(Skill.LongxiajushouSkill, new LobsterSkill());
		
		registerSkill(Skill.Shimeng, new DreamAction());
		registerSkill(Skill.Shixuemojian, new BloodSwordAction());
		
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

	private boolean checkSkillType(int skillType,  boolean isAttacker)
	{
		if(isAttacker)
		{
			return skillType==1 || skillType==3;
		} else
		{
			return skillType==2 || skillType==3;
		}
	}
	
	private void loadSkill(String skillList, List<Skill> result, boolean isAttacker)
	{
		if (!StringUtils.isEmpty(skillList)) {
			String[] d = StringUtils.split(skillList, ",");
			for (int i = 0; i < d.length; i++) {
				long skillId = Longs.tryParse(d[i]);
				if (this.skills.containsKey(skillId)) {
					if(checkSkillType(this.getSkill(skillId).getSkillType(), isAttacker))
					{
						if(this.getSkill(skillId) != null)
						{
							result.add(this.getSkill(skillId));
						}
					}
				}
			}
		}
	}
	
	public List<Skill> getSkills(long heroId, long soldierId, long actionId, int enhance, Map<String, Equip> equips,  boolean isAttacker) {
		List<Skill> result = new ArrayList<Skill>();
		if(heroId > 0)
		{
			Hero hero = heroRepository.getOne(heroId);
			loadSkill(hero.getSkills(), result, isAttacker);
		}
		
		if(equips != null)
		{
			equips.values().stream().forEach(e->{
				
				loadSkill(e.getSkills(), result, isAttacker);
				
			});
		}
		
		if(enhance > 0)
		{
			long enhanceSkill = ehanceSkills.get(enhance);
			if(enhanceSkill > 0)
			{
				if(this.getSkill(enhanceSkill) != null)
				{
					result.add(this.getSkill(enhanceSkill));
				}
			}
		}
		
		if(soldierId > 0) 
		{
			Soldier soldier = soldierRepository.getOne(soldierId);
			loadSkill(soldier.getSkills(), result, isAttacker);

			int soldierType = soldier.getType();
			if(barrackSkills.getSkills(soldierType) != null)
			{
				result.addAll(barrackSkills.getSkills(soldierType).stream().filter(s->checkSkillType(s.getSkillType(), isAttacker)).collect(Collectors.toList()));
			}
			
		}
		
		if(actionId > 0)
		{
			Action action = actionRepository.getOne(actionId);
			loadSkill(action.getSkills(), result, isAttacker);
			
		}
		
		globalSkills.forEach(i->{
			if (this.skills.containsKey(i)) {
				if(checkSkillType(this.getSkill(i).getSkillType(), isAttacker))
				{
					if(this.getSkill(i) != null)
					{
						result.add(this.getSkill(i));
					}
				}
			}
		});


		return result;
	}

}
