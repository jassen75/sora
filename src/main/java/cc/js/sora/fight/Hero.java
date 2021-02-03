package cc.js.sora.fight;

import java.util.ArrayList;
import java.util.List;

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
import javax.persistence.OneToMany;
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
	
	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	List<Soldier> soldiers;
	
	@Column(name="soldier_attack_inc")
	int soldierAttackInc;
	
	@Column(name="soldier_physic_def_inc")
	int soldierPhysicDefInc;
	
	@Column(name="soldier_magic_def_inc")
	int soldierMagicDefInc;
	
	@Column(name="soldier_life_inc")
	int soldierLifeInc;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy = "hero")
	List<Buff> buffs = new ArrayList();

	@JsonProperty("isPhysic")
	@Column(name="is_physic")
	boolean isPhysic;

	
}
