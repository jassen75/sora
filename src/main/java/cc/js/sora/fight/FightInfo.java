package cc.js.sora.fight;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FightInfo {
	
	
	FightRole attacker;
	FightRole defender;
	
	public FightRole getRole(boolean isAttack)
	{
		return isAttack ? attacker : defender;
	}

}