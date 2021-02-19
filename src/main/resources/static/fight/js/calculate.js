

function checkHeroEffect(effect, role)
{
	if(role=="attacker")
	{
		for(var i=0; i<fightInfo["attackerEffects"].length; i++)
		{
			if(effect==fightInfo["attackerEffects"][i])
			{
				return true;
			}
		}
	} 
	if(role=="defender")
	{
		for(var i=0; i<fightInfo["defenderEffects"].length; i++)
		{
			if(effect==fightInfo["defenderEffects"][i])
			{
				return true;
			}
		}
	} 
	return false;
}

function checkSoldierEffect(effect, role)
{
	if(role=="attacker")
	{
		for(var i=0; i<fightInfo["attackerSoldierEffects"].length; i++)
		{
			if(effect==fightInfo["attackerSoldierEffects"][i])
			{
				return true;
			}
		}
	} 
	if(role=="defender")
	{
		for(var i=0; i<fightInfo["defenderSoldierEffects"].length; i++)
		{
			if(effect==fightInfo["defenderSoldierEffects"][i])
			{
				return true;
			}
		}
	} 
	return false;
}

function calculate()

{
	var soldierToSoldier=0;
	var soldierToHero=0;
	var heroToSoldier=0;
	var heroToHero=0;
	var fightDetails= "";
	var coefficient = 1;
	if(attackerAction)
	{
		coefficient = attackerAction["coefficient"];
	}
	if(attackerSoldier && defenderSoldier)
	{
		soldierToSoldier = attackerSoldier["isPhysic"]? 
				oneHit(1, fightInfo["attackerSoldierAttack"], fightInfo["defenderSoldierPhysicDef"], fightInfo["attackerSoldierDamageInc"], fightInfo["defenderSoldierPhysicDamageDec"],
						fightInfo["attackerSoldierCriticalDamageInc"], fightInfo["defenderSoldierCriticalDamageDec"], attackerSoldierCriticalChecked)
			: oneHit(1, fightInfo["attackerSoldierAttack"], fightInfo["defenderSoldierMagicDef"],  fightInfo["attackerSoldierDamageInc"], fightInfo["defenderSoldierMagicDamageDec"],
					fightInfo["attackerSoldierCriticalDamageInc"], fightInfo["defenderSoldierCriticalDamageDec"], attackerSoldierCriticalChecked);
		var c = (attackerSoldierCriticalChecked? "(暴击":"(未暴击")+"&nbsp;&nbsp;&nbsp;,&nbsp;&nbsp;&nbsp;概率："+criticalProb(0, fightInfo["attackerSoldierCriticalProbInc"],fightInfo["defenderSoldierCriticalProbDec"])+")";
														         
        fightDetails+="<p>"+attackerSoldier["name"]+"攻击"+defenderSoldier["name"]+"1hit:<b>"+soldierToSoldier+"</b>"+c+"</p>";
	}
	
	if(attackerSoldier && defender)
	{
		soldierToHero = attackerSoldier["isPhysic"]? oneHit(1, fightInfo["attackerSoldierAttack"], fightInfo["defenderPhysicDef"], 
															fightInfo["attackerSoldierDamageInc"], fightInfo["defenderPhysicDamageDec"],
															fightInfo["attackerSoldierCriticalDamageInc"], fightInfo["defenderCriticalDamageDec"], attackerSoldierCriticalChecked) 
													: oneHit(1, fightInfo["attackerSoldierAttack"], fightInfo["defenderMagicDef"], 
															fightInfo["attackerSoldierDamageInc"], fightInfo["defenderMagicDamageDec"],
															fightInfo["attackerSoldierCriticalDamageInc"], fightInfo["defenderCriticalDamageDec"], attackerSoldierCriticalChecked);
		
		var c = (attackerSoldierCriticalChecked? "(暴击":"(未暴击")+"&nbsp;&nbsp;&nbsp;,&nbsp;&nbsp;&nbsp;概率："+criticalProb(0, fightInfo["attackerSoldierCriticalProbInc"],fightInfo["defenderCriticalProbDec"])+")";
		fightDetails+="<p>"+attackerSoldier["name"]+"攻击"+defender["name"]+"1hit:<b>"+soldierToHero+"</b>"+c+"</p>";
	}
	
	if(attacker && defenderSoldier)
	{
		heroToSoldier = attacker["isPhysic"]? oneHit(coefficient, fightInfo["attackerAttack"], fightInfo["defenderSoldierPhysicDef"], fightInfo["attackerDamageInc"], 
															fightInfo["defenderSoldierPhysicDamageDec"],
															fightInfo["attackerCriticalDamageInc"], fightInfo["defenderSoldierCriticalDamageDec"], attackerHeroCriticalChecked) 
													: oneHit(coefficient, fightInfo["attackerIntel"], fightInfo["defenderSoldierMagicDef"],
														fightInfo["attackerDamageInc"], fightInfo["defenderSoldierMagicDamageDec"],
														fightInfo["attackerCriticalDamageInc"], fightInfo["defenderSoldierCriticalDamageDec"], attackerHeroCriticalChecked);
		
		var c = (attackerHeroCriticalChecked? "(暴击":"(未暴击")+"&nbsp;&nbsp;&nbsp;,&nbsp;&nbsp;&nbsp;概率："+criticalProb(fightInfo["attackerTech"], fightInfo["attackerCriticalProbInc"],fightInfo["defenderSoldierCriticalProbDec"])+")";
	    fightDetails+="<p>"+attacker["name"]+"攻击"+defenderSoldier["name"]+"1hit:<b>"+heroToSoldier+"</b>"+c+"</p>";
	}

	if(attacker && defender)
	{
		heroToHero = attacker["isPhysic"]? oneHit(coefficient, fightInfo["attackerAttack"], fightInfo["defenderPhysicDef"],
															fightInfo["attackerDamageInc"], fightInfo["defenderPhysicDamageDec"],
															fightInfo["attackerCriticalDamageInc"], fightInfo["defenderCriticalDamageDec"], attackerHeroCriticalChecked) 
													: oneHit(coefficient, fightInfo["attackerIntel"], fightInfo["defenderMagicDef"], 
															fightInfo["attackerDamageInc"], fightInfo["defenderMagicDamageDec"],
															fightInfo["attackerCriticalDamageInc"], fightInfo["defenderCriticalDamageDec"], attackerHeroCriticalChecked);
		var c = (attackerHeroCriticalChecked? "(暴击":"(未暴击")+"&nbsp;&nbsp;&nbsp;,&nbsp;&nbsp;&nbsp;概率："+criticalProb(fightInfo["attackerTech"], fightInfo["attackerCriticalProbInc"],fightInfo["defenderCriticalProbDec"])+")";
	    fightDetails+="<p>"+attacker["name"]+"攻击"+defender["name"]+"1hit:<b>"+heroToHero+"</b>"+c+"</p>";
	}
	fightDetails+="<p>-------------------------------------------------------------------------------------------</p>";
	var soldierCount = attackerSoldier ? 20 : 0;
	var heroCount = attacker ? 20 : 0;
	var soldierLeftCount = soldierCount;
	var heroLeftCount = heroCount;
	var dl = defender ? fightInfo["defenderHeroLeftLife"] : 0;
	var dsl = defenderSoldier ? fightInfo["defenderSoldierLeftLife"] : 0;
	var direct = attackerAction ? attackerAction["direct"] : 0;
	
	if(fightInfo["attackerPreBattleDamage"] > 0)
	{
		var preDamage = fightInfo["attackerPreBattleDamage"]*fightInfo["attackerAttack"];
		if(defender)
		{
			if(checkHeroEffect("ImmuneToFixedDamage", "defender"))
			{
				fightDetails+="<p>"+defender["name"]+"免疫掉战前固伤</p>";
			} else
			{
				dl  -= preDamage;
				fightDetails+="<p>"+attacker["name"]+"战前对"+defender["name"]+"造成<b>"+preDamage+"</b>伤害</p>";
			}
		}
	} 
	if(defenderSoldier && !direct) 
	{
		
		var oneSoldierLife = fightInfo["defenderSoldierLife"] / 10;
		if(preDamage)
		{
			if(checkSoldierEffect("ImmuneToFixedDamage", "defender"))
			{
				fightDetails+="<p>"+defenderSoldier["name"]+"免疫掉战前固伤</p>";
			} else
			{
				dsl -= preDamage;
				var count = Math.floor(preDamage/oneSoldierLife);
				fightDetails+="<p>"+attacker["name"]+"战前对士兵造成<b>"+preDamage+"</b>伤害, 杀死<b>"+count+"</b>"+defenderSoldier["name"]+"</p>";
				//fightDetails+="<p>dsl=="+dsl+"</p>";
			}
		}
		if(soldierLeftCount > 0)
		{
			var hit = Math.ceil(oneSoldierLife / soldierToSoldier);
			var soldierKillSoldier = 0;
			
			while(soldierLeftCount >= hit &&  dsl > oneSoldierLife)  
			{
				dsl -= oneSoldierLife;
				soldierKillSoldier++;
				soldierLeftCount -= hit;
				fightDetails+="<p>dsl=="+dsl+"   soldierKillSoldier=="+soldierKillSoldier+"</p>";
			}

			// left many soldier
			if( dsl > oneSoldierLife)
			{
				dsl -= soldierToSoldier * soldierLeftCount;
				soldierLeftCount = 0;
				var c = attackerSoldierCriticalChecked?" class=\"critical\"":"";
				fightDetails+="<p"+c+">"+attackerSoldier["name"]+" 用 <b>"+soldierCount+"</b> hit 干掉 <b>"+soldierKillSoldier+"</b>"+defenderSoldier["name"]+"</p>";

				// hero to soldier
				if(heroLeftCount > 0 )
				{
					var hit = Math.ceil(oneSoldierLife / heroToSoldier);		
					var heroKillSoldier = 0;
					
					while(heroLeftCount >= hit &&  dsl > oneSoldierLife)  
					{
						dsl -= oneSoldierLife;
						heroKillSoldier++;
						heroLeftCount -= hit;
						fightDetails+="<p>dsl=="+dsl+"   heroKillSoldier=="+heroKillSoldier+"</p>";
					}
					
					// soldier left
					if( dsl > oneSoldierLife)
					{
						dsl -= heroToSoldier * heroLeftCount;
						heroLeftCount = 0;
						var c = attackerHeroCriticalChecked?" class=\"critical\"":"";
						fightDetails+="<p"+c+">"+attacker["name"]+"用 <b>"+heroCount+"</b> hit 干掉 <b>"+heroKillSoldier+"</b>"+defenderSoldier["name"]+"</p>";
					} 
					else
					{
						heroLeftCount -= Math.ceil((oneSoldierLife-dsl)/heroToSoldier);
						heroKillSoldier++;
						dsl =0;
						var c = attackerHeroCriticalChecked?" class=\"critical\"":"";
						fightDetails+="<p"+c+">"+attacker["name"]+"用 <b>"+(heroCount-heroLeftCount)+"</b> hit 干掉 <b>"+heroKillSoldier+"</b>"+defenderSoldier["name"]+"</p>";
					}
				}
			} else
			{
				// soldier to hero
				soldierLeftCount -= Math.ceil(dsl/soldierToSoldier);
				soldierKillSoldier++;
				dsl = 0;
				var c = attackerSoldierCriticalChecked?" class=\"critical\"":"";
				fightDetails+="<p"+c+">"+attackerSoldier["name"]+"用 <b>"+(soldierCount-soldierLeftCount)+"</b> hit 干掉 <b>"+soldierKillSoldier+"</b> "+defenderSoldier["name"]+"</p>";
				
				if(defender && soldierLeftCount >0)
				{
					var stohDamage = soldierToHero* soldierLeftCount;
					if(dl > stohDamage ) 
					{
						dl -= stohDamage;	
					} else
					{
						dl = 0;
					}
					var c = attackerSoldierCriticalChecked?" class=\"critical\"":"";
					fightDetails+="<p"+c+">"+attackerSoldier["name"]+"用 <b>"+soldierLeftCount+"</b> hit 对"+defender["name"]+"造成<b>"+stohDamage+"</b>伤害</p>";
					soldierLeftCount = 0;
				}
				
			}
		}
	}
	
   
	if(dl > 0 && (dsl == 0 || direct) )
	{
		if(soldierLeftCount >0 && !direct)
		{
			var stohDamage = soldierToHero* soldierLeftCount;
			if(dl > stohDamage ) 
			{
				dl -= stohDamage;
			} else
			{
				dl = 0;
			}
			var c = attackerSoldierCriticalChecked?" class=\"critical\"":"";
			fightDetails+="<p"+c+">"+attackerSoldier["name"]+"用 <b>"+soldierLeftCount+"</b> hit 对"+defender["name"]+"造成<b>"+stohDamage+"</b>伤害</p>";
		}
		if(heroLeftCount > 0)
		{
			var htohDamage = heroToHero* heroLeftCount;
			if(dl > htohDamage ) 
			{
				dl -= htohDamage;
			} else
			{
				dl = 0;
			}
			var c = attackerHeroCriticalChecked?" class=\"critical\"":"";
			fightDetails+="<p"+c+">"+attacker["name"]+"用 <b>"+heroLeftCount+"</b> hit 对"+defender["name"]+"造成<b>"+htohDamage+"</b>伤害 </p>";
		}
	} 
	
	if(dl == 0)
	{
		fightDetails+="<p>"+defender["name"]+"死亡</p>";
	}
	
	$("#defenderSoldierBar").text(dsl+"/"+fightInfo["defenderSoldierLife"]);
	
	$("#defenderSoldierBar").attr("style", "width:"+Math.ceil(dsl/fightInfo["defenderSoldierLife"]*100)+"%");
	
	$("#defenderHeroBar").text(dl+"/"+fightInfo["defenderLife"]);
	
	$("#defenderHeroBar").attr("style", "width:"+Math.ceil(dl/fightInfo["defenderLife"]*100)+"%");
										
	$("#fightDetails").html(fightDetails);
}

function oneHit(coefficient, attack, enemyDef, damageInc, enemyDamageDec, criticalInc, criticalDec, critcal)
{
	var c = critcal ? (130 + criticalInc - criticalDec) / 100 : 1;
	return Math.floor(coefficient * (attack-enemyDef)*(1+(damageInc-enemyDamageDec)/100.0)*c /2) ;
}

function criticalProb(tech, attackerProb, defenderProb)
{
	var prob = attackerProb+tech/10;
	if(prob > 100)
	{
		prob = 100;
	}
	return Math.floor(prob * (100-defenderProb)/100)+"%";
}
