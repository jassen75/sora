package cc.js.sora.fight;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Feature implements Effect{

	public final static String ImmuneToFixedDamage = "ImmuneToFixedDamage";
	public final static String ImmuneToMeleeDamageReduce = "ImmuneToMeleeDamageReduce";
	String featureName;
	Object value;
	String desc;
	Scope scope;

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
