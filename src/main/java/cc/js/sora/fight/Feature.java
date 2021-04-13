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
	public final static String FirstAttack = "FirstAttack";
	public final static String DirectToHero = "DirectToHero";
	public final static String FixDamageToSelf = "FixDamageToSelf";
	public final static String UnImuFixDamageToSelf = "UnImuFixDamageToSelf";
	public final static String PreFixDamageAttack = "PreFixDamageAttack";
	public final static String PreFixDamagePhysic = "PreFixDamagePhysic";
	public final static String PreFixDamageMagic = "PreFixDamageMagic";
	public final static String PreFixDamage2Def = "PreFixDamage2Def";
	public final static String PreFixDamageChenhun = "PreFixDamageChenhun";
	public final static String PreUnImuFixDamageAttackIntel = "PreUnImuFixDamageAttackIntel";
	public final static String PostFixDamagePercent = "PostFixDamagePercent";
	public final static String ImmuenToCounter = "ImmuenToCounter";
	public final static String AddAttackToDef = "AddAttackToDef";
	public final static String RangeAttackBack = "RangeAttackBack";
	
	public final static String MagicToIntel = "MagicToIntel";
	public final static String MagicToPhysic = "MagicToPhysic";
	public final static String MedicalToDamageInc = "MedicalToDamageInc";
	public final static String PhysicToAttack= "PhysicToAttack";
	public final static String MagicToAttack= "MagicToAttack";
	public final static String AddPhysicToAttack= "AddPhysicToAttack";
	public final static String AddMagicToAttack= "AddMagicToAttack";
	public final static String LandAsWater= "LandAsWater";
	
	public final static String XieshenShield= "XieshenShield";
	
	
	String featureName;
	Object value;
	String desc;
	Scope scope;
	boolean aggregate;
	
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
