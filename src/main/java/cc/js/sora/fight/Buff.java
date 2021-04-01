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
	
	public Buff(String name, Feature feature)
	{
		this.name = name;
		this.feature = feature;
		this.enhanceList = Lists.newArrayList();
	}
	List<Enhance> enhanceList;
	String name;
	Feature feature;
	@Override
	public EffectType getEffectType() {
		// TODO Auto-generated method stub
		return EffectType.Buff;
	}
	@Override
	public Map<String, Object> getFeatures() {
		if(feature != null)
		{
			return feature.getFeatures();
		} else
		{
			return Maps.newHashMap();
		}
	}
	
	public String toString()
	{
		return StringUtils.join(this.getEnhanceList().stream().map(b->b.getTitle()).toArray(), "，");
	}
	


	
}
