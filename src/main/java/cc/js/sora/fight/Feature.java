package cc.js.sora.fight;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class Feature implements Effect{

	public final static String ImmuneToFixedDamage = "ImmuneToFixedDamage";
	String featureName;
	Object value;
	String desc;
	public Feature(String featureName, Object value, String desc)
	{
		this.featureName = featureName;
		this.value = value;
		this.desc = desc;
	}
	@Override
	public EffectType getEffectType() {
		return EffectType.Feature;
	}

	@Override
	public List<Enhance> getEnhanceList() {
		return Lists.newArrayList();
	}

	@Override
	public Map<String, Object> getFeatures() {
		// TODO Auto-generated method stub
		Map<String, Object>  result= Maps.newHashMap();
		result.put(featureName, value);
		return result;
	}

	public String toString()
	{
		return this.desc;
	}
}
