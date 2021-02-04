package cc.js.sora.fight;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

import cc.js.sora.fight.condition.CombinedCondition;
import cc.js.sora.fight.skill.PatyleTalent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 1-300  hero talent
 * 300-1000 passive skill
 * 2000-2500 soldier skill
 * 2500-3000 bz kj
 * 3000-4000 equip skill
 * 4000-5000 global skill
 * 
 * 
 * 
 * @author jassen
 *
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public abstract class Skill {
	
	protected String name;

	protected Condition condition;

	protected List<Buff> buffs = new ArrayList<Buff>();
	
	//1 hero 2 soldier 3 all 4 enemy hero 5 enemy soldier 6 enemy all
	protected Scope scope;
	
	static public Map<Long, Skill> skills = Maps.newHashMap();
	
	public static final long PatyleTalent = 1;
	public static final long TowaTalent=2;
	
	static
	{
		init();
	}
	
	private static void init()
	{
		log.info("init skills");
		registerSkill(Skill.PatyleTalent, new PatyleTalent());
	}
	
	public static Map<Long, Skill> getAllSkills() {
		if(skills.size()==0)
		{
			init();
		}
		return skills;
	}
	
	public static Skill getSkill(long id) {
		
		if(skills.size()==0)
		{
			init();
		}
		return skills.get(id);
		
	}
	
	public static Skill registerSkill(long id, Skill skill) {
		
		return skills.put(id, skill);
		
	}
	
	protected static boolean checkCondition(Fight fight, Condition condition)
	{
		if(condition instanceof CombinedCondition)
		{
			return (((CombinedCondition)condition).getConditions().stream().allMatch(c->checkCondition(fight, c)));
		}
		
		return false;
		
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		sb.append(name);
		sb.append("]");
		sb.append(this.condition.getDesc());
		buffs.stream().forEach(buff->sb.append(","+buff.getTitle()));
		return sb.toString();
	}
	
	
	
	
}
