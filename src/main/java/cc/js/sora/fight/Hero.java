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
	
	@Transient
	int attck;
	
	@Transient
	int physicDef;
	
	@Transient
	int life;
	
	@Transient
	int magicDef;
	
	@Transient
	int intel;
	
	@Transient
	int tech;
	
	@Transient
	List<Soldier> soldiers;
	
	@Transient
	int soldierAttackInc;
	
	@Transient
	int soldierPhysicDefInc;
	
	@Transient
	int soldierMagicDefInc;
	
	@Transient
	int soldierLifeInc;
	
	@Transient
	List<Buff> talents;
	
	@Transient
	List<Buff> pass;
	
	@Transient
	List<Buff> skill;
	
	@JsonProperty("isPhysic")
//	@Column(name="is_physic")
	@Transient
	boolean isPhysic;
}
