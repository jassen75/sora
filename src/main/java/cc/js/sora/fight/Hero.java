package cc.js.sora.fight;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

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
	
	@Column(name="physic")
	int physic;
	
	@Column(name="life")
	int life;
	
	@Column(name="magic")
	int magic;
	
	@Column(name="intel")
	int intel;
	
	@Column(name="tech")
	int tech;
	
	@ManyToMany(cascade=CascadeType.MERGE, fetch=FetchType.LAZY)
	@JoinTable(name="hero_soldiers",joinColumns={@JoinColumn(name="hero_id")},inverseJoinColumns={@JoinColumn(name="soldiers_id")})
	Set<Soldier> soldiers;
	
	@ManyToMany(cascade=CascadeType.MERGE, fetch=FetchType.LAZY)
	@JoinTable(name="hero_actions",joinColumns={@JoinColumn(name="hero_id")},inverseJoinColumns={@JoinColumn(name="actions_id")})
	Set<Action> actions;
	
//	@ManyToMany(cascade=CascadeType.MERGE, fetch=FetchType.LAZY)
//	@JsonIgnore
//	List<EquipType> equipTypes;
	@Column(name="supported_equip_types")
	String supportedEquipTypes;
	
	@Column(name="soldier_attack_inc")
	int soldierAttackInc;
	
	@Column(name="soldier_physic_inc")
	int soldierPhysicInc;
	
	@Column(name="soldier_magic_inc")
	int soldierMagicInc;
	
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
