package cc.js.sora.fight;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cc.js.sora.fight.skill.barrack.AssTech1;
import cc.js.sora.fight.skill.barrack.AssTech2;
import cc.js.sora.fight.skill.barrack.AssTech3;
import cc.js.sora.fight.skill.barrack.AssTech4;
import cc.js.sora.fight.skill.barrack.BirdTech1;
import cc.js.sora.fight.skill.barrack.BirdTech2;
import cc.js.sora.fight.skill.barrack.BirdTech3;
import cc.js.sora.fight.skill.barrack.BirdTech4;
import cc.js.sora.fight.skill.barrack.BowTech1;
import cc.js.sora.fight.skill.barrack.BowTech2;
import cc.js.sora.fight.skill.barrack.BowTech3;
import cc.js.sora.fight.skill.barrack.BowTech4;
import cc.js.sora.fight.skill.barrack.FootTech1;
import cc.js.sora.fight.skill.barrack.FootTech2;
import cc.js.sora.fight.skill.barrack.FootTech3;
import cc.js.sora.fight.skill.barrack.FootTech4;
import cc.js.sora.fight.skill.barrack.FootTech5;
import cc.js.sora.fight.skill.barrack.HorseTech1;
import cc.js.sora.fight.skill.barrack.HorseTech2;
import cc.js.sora.fight.skill.barrack.HorseTech3;
import cc.js.sora.fight.skill.barrack.HorseTech4;
import cc.js.sora.fight.skill.barrack.HorseTech5;
import cc.js.sora.fight.skill.barrack.MonkTech1;
import cc.js.sora.fight.skill.barrack.MonkTech2;
import cc.js.sora.fight.skill.barrack.MonkTech3;
import cc.js.sora.fight.skill.barrack.MonkTech4;
import cc.js.sora.fight.skill.barrack.MonsterTech1;
import cc.js.sora.fight.skill.barrack.MonsterTech2;
import cc.js.sora.fight.skill.barrack.MonsterTech3;
import cc.js.sora.fight.skill.barrack.MonsterTech4;
import cc.js.sora.fight.skill.barrack.SailorTech1;
import cc.js.sora.fight.skill.barrack.SailorTech2;
import cc.js.sora.fight.skill.barrack.SailorTech3;
import cc.js.sora.fight.skill.barrack.SailorTech4;
import cc.js.sora.fight.skill.barrack.SorceressTech1;
import cc.js.sora.fight.skill.barrack.SorceressTech2;
import cc.js.sora.fight.skill.barrack.SorceressTech3;
import cc.js.sora.fight.skill.barrack.SorceressTech4;
import cc.js.sora.fight.skill.barrack.SpearTech1;
import cc.js.sora.fight.skill.barrack.SpearTech2;
import cc.js.sora.fight.skill.barrack.SpearTech3;
import cc.js.sora.fight.skill.barrack.SpearTech4;
import cc.js.sora.fight.skill.barrack.SpearTech5;


public class BarrackSkills {
	
	Map<Integer, List<Skill>> skills = Maps.newHashMap();
	
	
	public BarrackSkills()
	{
		init();
	}
	//1 步兵 2 枪兵 3 骑兵 4 水兵 5 飞兵 6 弓箭手 7 刺客 8 法师 9 魔物 10 僧兵 11 龙
	public List<Skill> getSkills(int type)
	{
		return skills.get(type);
	}
	private void init()
	{
		
		// 8
		initWizardSkill();
	}
	
	public Map<Long, Skill> getAllBarrackSkills()
	{
		Map<Long, Skill> result = new HashMap<Long, Skill>();
		skills.values().forEach(list->{
			list.stream().forEach(skill->result.put(skill.getId(), skill));
		});
		return result;
	}
	
	
	private void initWizardSkill()
	{
		List<Skill> wizardSkillList = Lists.newArrayList(new SorceressTech1(),new SorceressTech2(), new SorceressTech3(), new SorceressTech4());
		
		List<Skill> birdSkillList = Lists.newArrayList(new BirdTech1(),new BirdTech2(), new BirdTech3(), new BirdTech4());
		List<Skill> sailorSkillList = Lists.newArrayList(new SailorTech1(),new SailorTech2(), new SailorTech3(), new SailorTech4());
		
		skills.put(8, wizardSkillList);
		skills.put(5, birdSkillList);
		skills.put(4, sailorSkillList);
		
		skills.put(1, Lists.newArrayList(new FootTech1(),new FootTech2(),new FootTech3(),new FootTech4(),new FootTech5()));
		skills.put(2, Lists.newArrayList(new SpearTech1(),new SpearTech2(),new SpearTech3(),new SpearTech4(),new SpearTech5()));
		skills.put(3, Lists.newArrayList(new HorseTech1(),new HorseTech2(),new HorseTech3(),new HorseTech4(),new HorseTech5()));
		
		skills.put(6, Lists.newArrayList(new BowTech1(),new BowTech2(),new BowTech3(),new BowTech4()));
		skills.put(7, Lists.newArrayList(new AssTech1(),new AssTech2(),new AssTech3(),new AssTech4()));
		skills.put(9, Lists.newArrayList(new MonsterTech1(),new MonsterTech2(),new MonsterTech3(),new MonsterTech4()));
		skills.put(10, Lists.newArrayList(new MonkTech1(),new MonkTech2(),new MonkTech3(),new MonkTech4()));
	}
	

}
