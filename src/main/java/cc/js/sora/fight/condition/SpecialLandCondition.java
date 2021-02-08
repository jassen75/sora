package cc.js.sora.fight.condition;

import java.util.List;

import com.google.common.collect.Lists;

import cc.js.sora.fight.Land;

public class SpecialLandCondition implements LandCondition {
	
	protected List<Land> lands;
	
	public SpecialLandCondition(List<Land> lands)
	{
		this.lands = lands;
	}
	
	public SpecialLandCondition(Land land)
	{
		this.lands = Lists.newArrayList(land);
	}
	@Override
	public String getDesc() {
		StringBuilder sb = new StringBuilder();
		sb.append("地形为");
		sb.append(lands.get(0).getDesc());
		for(int i=1; i<lands.size(); i++)
		{
			sb.append("或者");
			sb.append(lands.get(i).getDesc());
		}
		return sb.toString();
	}
	@Override
	public boolean valid(Land land) {
		return lands.stream().anyMatch(l -> l==land);
	}

}
