package cc.js.sora.fight;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Counter implements Effect{
	
	int enemyType;
	boolean soldier;
	boolean hero;
	int number;
	Scope scope;
	Map<String, Object> features;
	
	@Override
	public EffectType getEffectType() {
		// TODO Auto-generated method stub
		return EffectType.Counter;
	}
	@Override
	public List<Enhance> getEnhanceList() {
		return Lists.newArrayList();
	}
	@Override
	public Map<String, Object> getFeatures() {
		return features;
	}
}
