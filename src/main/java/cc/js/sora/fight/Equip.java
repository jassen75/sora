package cc.js.sora.fight;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "equip")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
//@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class Equip {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	
	@Column(name="name")
	String name;
	
	int attackSkill;
	int physicSkill;
	int magicSkill;
	int lifeSkill;
	int intelSkill;
	int techSkill;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "equip_type")
	EquipType equipType;
	
	@Column(name="skills")
	String skills;
	
	@Column(name="owner")
	long owner;
}
