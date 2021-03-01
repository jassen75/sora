package cc.js.sora.fight;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Buff implements Effect {
	
	public Buff(String name, List<Enhance> enhanceList)
	{
		this.name = name;
		this.enhanceList = enhanceList;
		
	}
	
	public Buff(BuffType bufftType, int number)
	{
		this.name = bufftType.name();
		this.enhanceList = Lists.newArrayList(new Enhance(bufftType, number, Scope.All));
		
	}
	List<Enhance> enhanceList;
	Map<String, Object> features;
	String name;
	@Override
	public EffectType getEffectType() {
		// TODO Auto-generated method stub
		return EffectType.Buff;
	}
	@Override
	public Map<String, Object> getFeatures() {
		return features;
	}
	
	public String toString()
	{
		return StringUtils.join(this.getEnhanceList().stream().map(b->b.getTitle()).toArray(), "，");
	}
	


	
}
