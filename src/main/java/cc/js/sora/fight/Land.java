package cc.js.sora.fight;

public enum Land {

	
	Flat("平地"),Water("水中"),Wood("树林"),Wall("城墙"),Grass("草地"),Mountain("山地"),Sand("沙丘"),Cave("洞穴");
	
	private String desc;
	
	private Land(String desc)
	{
		this.desc = desc;
	}
	
	public String getDesc()
	{
		return this.desc;
	}
}
