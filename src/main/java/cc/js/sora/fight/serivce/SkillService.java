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
import cc.js.sora.fight.skill.support.ElwinSuper;
import cc.js.sora.fight.skill.support.ZillagodSuper;
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
		registerSkill(Skill.Yuguan, new Yuguan());
		registerSkill(Skill.Erzhui, new Erzhui());
		registerSkill(Skill.Shenpan, new Shenpan());
		registerSkill(Skill.Tulong, new Tulong());
		registerSkill(Skill.Shenyi, new Shenyi());
		registerSkill(Skill.Yicai, new Yicai());
		registerSkill(Skill.Jixianmogong, new Jixianmogong());
		
		registerSkill(Skill.WindEnhance, new WindEnhance());
		registerSkill(Skill.FuriousEnhance, new FuriousEnhance());
		
		registerSkill(Skill.BloodBattle, new BloodBattle());
		
		registerSkill(Skill.PatyleTalent, new PatyleTalent());
		registerSkill(Skill.TowaTalent, new TowaTalent());
		registerSkill(Skill.ZalrahdaTalent1, new ZalrahdaTalent1());
		registerSkill(Skill.ZalrahdaTalent2, new ZalrahdaTalent2());
		registerSkill(Skill.MarielTalent, new MarielTalent());
		registerSkill(Skill.LandiusTalent, new LandiusTalent());
		registerSkill(Skill.LedynTalent, new LedynTalent());
		registerSkill(Skill.HimikoTalent, new HimikoTalent());
		registerSkill(Skill.MagusOfTheTreeTalent, new MagusOfTheTreeTalent());
		registerSkill(Skill.ReanTalent, new ReanTalent());
		registerSkill(Skill.AresTalent, new AresTalent());
		registerSkill(Skill.RozaliaTalent, new RozaliaTalent());
		
		registerSkill(Skill.MonvSkill, new WizardSkill());
		registerSkill(Skill.HuangjiashijiuSkill, new GriffinSkill());
		registerSkill(Skill.LongxiajushouSkill, new LobsterSkill());
		
		registerSkill(Skill.Gangyiyongshi, new Gangyiyongshi());
		registerSkill(Skill.Dujiaoshou, new Dujiaoshou());
		registerSkill(Skill.Fangzhenliebing, new Gangyiyongshi());
		registerSkill(Skill.Gaodiyongshi, new Gangyiyongshi());
		
		registerSkill(Skill.Huangjiaqibing, new Huangjiaqibing());
		registerSkill(Skill.Jixieqishi, new Jixieqishi());
		registerSkill(Skill.Kuangrezhe, new Kuangrezhe());
		registerSkill(Skill.Senlinjisi, new Senlinjisi());
		registerSkill(Skill.Shixianggui, new Shixianggui());
		registerSkill(Skill.Tianshi, new Tianshi());
		registerSkill(Skill.Wumianzhe, new Wumianzhe());
		registerSkill(Skill.Xiyidaoke, new Xiyidaoke());
		registerSkill(Skill.Xizuizhe, new Xizuizhe());
		registerSkill(Skill.Xuanfengyouqibing, new Xuanfengyouqibing());
		registerSkill(Skill.Shurenshouwei, new Shurenshouwei());
		registerSkill(Skill.Jiamiannvpu, new Jiamiannvpu());
		registerSkill(Skill.Fangzhenliebing, new Fangzhenliebing());
		registerSkill(Skill.Longqi, new Longqi());
		
		registerSkill(Skill.Shimeng, new DreamAction());
		registerSkill(Skill.Shixuemojian, new BloodSwordAction());
		registerSkill(Skill.Weifengchongzhen, new Weifengchongzhen());
		
		registerSkill(Skill.Qinzhen, new QinZhenSKill());
		registerSkill(Skill.Juebi, new JuebiSkill());
		
		registerSkill(Skill.ZillagodSuper, new ZillagodSuper());
		registerSkill(Skill.ElwinSuper, new ElwinSuper());
		
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

	private boolean checkSkillType(int skillType,  int roleType)
	{
		if(roleType == 1)
		{
			return skillType==1 || skillType==3 || skillType ==4 || skillType==9;
		} else if(roleType == 2)
		{
			return skillType==2 || skillType==3 || skillType == 5 || skillType==9;
		} else if(roleType == 3)
		{
			return skillType==1 || skillType==8 || skillType == 6 || skillType==9;
		} else if(roleType == 4)
		{
			return skillType==2 || skillType==8 || skillType == 7 || skillType==9;
		}
		return false;
	}
	
	private void loadSkill(String skillList, List<Skill> result, int roleType)
	{
		if (!StringUtils.isEmpty(skillList)) {
			String[] d = StringUtils.split(skillList, ",");
			for (int i = 0; i < d.length; i++) {
				long skillId = Longs.tryParse(d[i].trim());
				if (this.skills.containsKey(skillId)) {
					if(checkSkillType(this.getSkill(skillId).getSkillType(), roleType))
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
	
	public List<Skill> getSkills(Hero hero, Soldier soldier, long actionId, int enhance, Map<String, Equip> equips,  int roleType) {
		List<Skill> result = new ArrayList<Skill>();
		if(hero != null)
		{
			loadSkill(hero.getSkills(), result, roleType);
		}
		
		if(equips != null)
		{
			equips.values().stream().forEach(e->{
				
				loadSkill(e.getSkills(), result, roleType);
				
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
		
		if(soldier != null) 
		{
			loadSkill(soldier.getSkills(), result, roleType);

			int soldierType = soldier.getType();
			if(barrackSkills.getSkills(soldierType) != null)
			{
				result.addAll(barrackSkills.getSkills(soldierType).stream().filter(s->checkSkillType(s.getSkillType(), roleType)).collect(Collectors.toList()));
			}
			
		}
		
		if(actionId > 0)
		{
			Action action = actionRepository.getOne(actionId);
			loadSkill(action.getSkills(), result, roleType);
			
		}
		
		globalSkills.forEach(i->{
			if (this.skills.containsKey(i)) {
				if(checkSkillType(this.getSkill(i).getSkillType(), roleType))
				{
					result.add(this.getSkill(i));
				}
			}
		});

		List<Skill> childSkills = Lists.newArrayList();

		result.forEach(s->{
			s.childSkill().forEach(cs->{
				if(checkSkillType(cs.getSkillType(), roleType))
				{
					childSkills.add(cs);
				}
				
			});

		});
		if(childSkills.size() > 0 )
		{
			result.addAll(childSkills);
		}
		return result;
	}

}
