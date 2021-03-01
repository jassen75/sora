package cc.js.sora.fight;

import java.util.List;
import java.util.Map;

public interface Effect {
	
	public EffectType getEffectType();

	public List<Enhance> getEnhanceList();
	
	public Map<String, Object> getFeatures();
}
