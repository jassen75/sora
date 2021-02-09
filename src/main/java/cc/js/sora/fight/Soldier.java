package cc.js.sora.fight;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "soldier")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Soldier {
	
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

	@Column(name="skills")
	String skills;
	
	
	//1 步兵 2 枪兵 3 骑兵 4 水兵 5 飞兵 6 弓箭手 7 刺客 8 法师 9 魔物 10 僧兵 11 龙 
	@Column(name="soldier_type")
	int type;
	
	@JsonProperty("isPhysic")
	@Column(name="is_physic")
	boolean isPhysic;

}
