package cc.js.sora.fight.condition;

import cc.js.sora.fight.Condition;
import cc.js.sora.fight.Land;

public interface LandCondition extends Condition {
	
	boolean valid(Land land);

}
