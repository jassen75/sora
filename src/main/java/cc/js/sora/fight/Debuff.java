package cc.js.sora.fight;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Maps;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@AllArgsConstructor
@ToString
public class Debuff implements Effect{
	
	public Debuff(String name, List<Enhance> enhanceList)
	{
		this.name = name;
		this.enhanceList = enhanceList;
		
	}
	List<Enhance> enhanceList;
	String name;

	@Override
	public EffectType getEffectType() {
		// TODO Auto-generated method stub
		return EffectType.Buff;
	}


	@Override
	public Map<String, Object> getFeatures() {
		// TODO Auto-generated method stub
		return Maps.newHashMap();
	}
	
	public String toString()
	{
		return StringUtils.join(this.getEnhanceList().stream().map(b->b.getTitle()).toArray(), "，");
	}
}
