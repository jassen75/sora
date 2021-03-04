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
@Table(name = "action")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Action {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;

	@Column(name = "name")
	String name;

	// 系数
	@Column(name = "coefficient")
	double coefficient;

//	// 直击本体
//	@Column(name = "direct")
//	boolean direct;

	@Column(name = "skills")
	String skills;

	@Column(name = "rang")
	int range;

	@Column(name = "hit_time")
	int hitTime;

	@JsonProperty("isPhysic")
	@Column(name = "is_physic")
	boolean isPhysic;

	// 1 battle 2 aoe 3 fix damage
	int battleType;

	public boolean isSimpleAttack() {
		return this.id == 10001L || this.id == 10002L;
	}
}
