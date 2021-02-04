package cc.js.sora.fight;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cc.js.sora.fight.skill.barrack.SorceressTech1;
import cc.js.sora.fight.skill.barrack.SorceressTech2;
import cc.js.sora.fight.skill.barrack.SorceressTech3;
import cc.js.sora.fight.skill.barrack.SorceressTech4;

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
		

		skills.put(8, wizardSkillList);
	}
	

}
