package cc.js.sora.fight;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "soldier_buff")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SoldierBuff {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	
	int condition;
	
	String name;

	public String getTitle()
	{
		return Condition.getContition(condition).getTitle()+","+printBufferType()+"+"+number;				
	}
	
	//1 attack 2 physicDef 3 magicDef 4 damageInc 5 damageReduce 6 Skill 7 counter 
	int buffType;
	
	double number;
	
	//1 hero 2 soldier 3 all 4 enemy hero 5 enemy soldier 6 enemy all
	int scope;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "soldier_id")
	@JsonIgnore
	Soldier soldier;
	
	public String printBufferType()
	{
		switch(buffType)
		{
		case 1:
			return "攻击";
		case 2:
			return "防御";
		case 3:
			return "魔防";
		case 4:
			return "增伤";
		case 5:
			return "减伤";
		case 6:
			return "技能伤害";
		case 7:
			return "克制伤害";
			

		}
		return "";
	}

}
