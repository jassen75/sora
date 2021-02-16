package cc.js.sora.fight;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name = "hero")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
//@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class Hero {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	
	@Column(name="name")
	String name;
	
	@Column(name="attack")
	int attack;
	
	@Column(name="physic_def")
	int physicDef;
	
	@Column(name="life")
	int life;
	
	@Column(name="magic_def")
	int magicDef;
	
	@Column(name="intel")
	int intel;
	
	@Column(name="tech")
	int tech;
	
	@ManyToMany(cascade=CascadeType.MERGE, fetch=FetchType.LAZY)
	Set<Soldier> soldiers;
	
	@ManyToMany(cascade=CascadeType.MERGE, fetch=FetchType.LAZY)
	Set<Action> actions;
	
//	@ManyToMany(cascade=CascadeType.MERGE, fetch=FetchType.LAZY)
//	@JsonIgnore
//	List<EquipType> equipTypes;
	@Column(name="supported_equip_types")
	String supportedEquipTypes;
	
	@Column(name="soldier_attack_inc")
	int soldierAttackInc;
	
	@Column(name="soldier_physic_def_inc")
	int soldierPhysicDefInc;
	
	@Column(name="soldier_magic_def_inc")
	int soldierMagicDefInc;
	
	@Column(name="soldier_life_inc")
	int soldierLifeInc;
	
	@Column(name="skills")
	String skills;

	@JsonProperty("isPhysic")
	@Column(name="is_physic")
	boolean isPhysic;
	
	@Column(name="default_soldier")
	long defaultSoldierId; 
	
	@Column(name="is_woman")
	boolean isWoman;
	
	//1 步兵 2 枪兵 3 骑兵 4 水兵 5 飞兵 6 弓箭手 7 刺客 8 法师 9 魔物 10 僧兵 11 龙
	@Column(name="type")
	int type;

	
}
