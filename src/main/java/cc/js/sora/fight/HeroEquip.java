package cc.js.sora.fight;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "hero_equip")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HeroEquip {
	
	
	int attackInc;
	int physicDefInc;
	int magicDefInc;
	int lifeInc;
	int intelInc;
	int techInc;
	
	int attackSkill;
	int physicDefSkill;
	int magicDefSkill;
	int lifeSkill;
	int intelSkill;
	int techSkill;
	
	@Column(name="attack_jjc")
	int attackJJC;
	
	@Column(name="physic_def_jjc")
	int physicDefJJC;
	
	@Column(name="magic_def_jjc")
	int magicDefJJC;
	
	@Column(name="life_jjc")
	int lifeJJC;
	
	@Column(name="intel_jjc")
	int intelJJC;
	
	@Column(name="tech_jjc")
	int techJJC;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	
	int criticalProbInc;
	int criticalProbDec;
	int criticalDamageInc;
	int criticalDamageDec;
	
	@OneToOne
	@JsonIgnore
	Hero hero;
}
