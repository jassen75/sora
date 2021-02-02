package cc.js.sora.fight;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

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
public class Hero {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	
	@Column(name="name")
	String name;
	
	@Column(name="attack")
	int attck;
	
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
	
	@Transient
	List<Soldier> soldiers;
	
	@Column(name="soldier_attack_inc")
	int soldierAttackInc;
	
	@Column(name="soldier_physic_def_inc")
	int soldierPhysicDefInc;
	
	@Column(name="soldier_magic_def_inc")
	int soldierMagicDefInc;
	
	@Column(name="soldier_life_inc")
	int soldierLifeInc;
	
	@Transient
	List<Buff> talents;
	
	@Transient
	List<Buff> pass;
	
	@Transient
	List<Buff> skill;
	
	@JsonProperty("isPhysic")
	@Column(name="is_physic")
	boolean isPhysic;

	
}
