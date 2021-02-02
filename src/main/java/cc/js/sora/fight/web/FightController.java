package cc.js.sora.fight.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cc.js.sora.fight.Fight;
import cc.js.sora.fight.FightResult;
import cc.js.sora.fight.Hero;
import cc.js.sora.fight.db.HeroRepository;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/fight")
@Slf4j
public class FightController {
	
	@Autowired
	HeroRepository heroRepository;
	
	@RequestMapping(path = "/cal", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public FightResult calculate(@RequestBody Fight fight)
	{
		log.info("fight:"+fight);
		FightResult result = new FightResult(fight);
		if(fight.getAttacker().isPhysic())
		{
			log.info("come here");
			int damage = (fight.getAttackerAttck() - fight.getDefenderPhysicDef())/2;
			int number = 20;
			result.setAttackerHeroDamage2(damage*number);
		} else
		{
			
		}
		
		if(fight.getDefender().isPhysic())
		{
			
		} else
		{
			
		}
		return result;
	}
	
	@RequestMapping(path = "/heros", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Hero> heros()
	{
		List<Hero> currentRecord = heroRepository.findAll();
		log.info("size:"+heroRepository.count());
		return currentRecord;
	}


}
