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
	var dl = defender ? fightInfo["defenderHeroLeftLife"] : 0;
	var dsl = defenderSoldier ? fightInfo["defenderSoldierLeftLife"] : 0;
	var direct = attackerAction ? attackerAction["direct"] : 0;
	if(defenderSoldier && !direct) 
	{
		
		var oneSoldierLife = fightInfo["defenderSoldierLife"] / 10;
		if(soldierCount > 0)
		{
			var hit = Math.ceil(oneSoldierLife / soldierToSoldier);
			
			
			var needHit = 0;
			var soldierKillSoldier = 0;
			
			while(needHit + hit <= soldierCount &&  dsl > oneSoldierLife)  
			{
				needHit += hit;
				dsl -= oneSoldierLife;
				soldierKillSoldier++;
			}

			// left many soldier
			if( dsl > oneSoldierLife)
			{
				dsl -= soldierToSoldier * (soldierCount - needHit);
				needHit = soldierCount;
				soldierCount = 0;
				var c = attackerSoldierCriticalChecked?" class=\"critical\"":"";
				fightDetails+="<p"+c+">"+attackerSoldier["name"]+" 用 <b>"+needHit+"</b> hit 干掉 <b>"+soldierKillSoldier+"</b>"+defenderSoldier["name"]+"</p>";

				// hero to soldier
				
				if(heroCount > 0 )
				{
					var hit = Math.ceil(oneSoldierLife / heroToSoldier);		
					
					var needHit = 0;
					var heroKillSoldier = 0;
					
					while(needHit + hit <= heroCount &&  dsl > oneSoldierLife)  
					{
						needHit += hit;
						dsl -= oneSoldierLife;
						heroKillSoldier++;
					}
					
					// soldier left
					if( dsl > oneSoldierLife)
					{
						dsl - heroToSoldier * (soldierCount - needHit);
						needHit = heroCount;
						var c = attackerHeroCriticalChecked?" class=\"critical\"":"";
						fightDetails+="<p"+c+">"+attacker["name"]+"用 <b>"+heroCount+"</b> hit 干掉 <b>"+heroKillSoldier+"</b>"+defenderSoldier["name"]+"</p>";
					} 
					else
					{
						needHit+= Math.ceil((oneSoldierLife-dsl)/heroToSoldier);
						heroKillSoldier++;
						var c = attackerHeroCriticalChecked?" class=\"critical\"":"";
						fightDetails+="<p"+c+">"+attacker["name"]+"用 <b>"+needHit+"</b> hit 干掉 <b>"+heroKillSoldier+"</b>"+defenderSoldier["name"]+"</p>";
						dsl =0;
						heroCount -= needHit;
					}
				}
			} else
			{
				// soldier to hero
				
				needHit+= Math.ceil(dsl/soldierToSoldier);
				var c = attackerSoldierCriticalChecked?" class=\"critical\"":"";
				fightDetails+="<p"+c+">"+attackerSoldier["name"]+"用 <b>"+needHit+"</b> hit 干掉 <b>10</b> "+defenderSoldier["name"]+"</p>";
				dsl = 0;
				soldierCount-=needHit
				
				if(defender && soldierCount >0)
				{
					var stohDamage = soldierToHero* soldierCount;
					if(dl > stohDamage ) 
					{
						dl -= stohDamage;
					} else
					{
						dl = 0;
					}
					var c = attackerSoldierCriticalChecked?" class=\"critical\"":"";
					fightDetails+="<p"+c+">"+attackerSoldier["name"]+"用 <b>"+soldierCount+"</b> hit 对"+defender["name"]+"造成<b>"+stohDamage+"</b>伤害</p>";
				}
				
			}
		}
	}
	
   
	if(dl > 0 && (dsl == 0 || direct) )
	{
		if(soldierCount >0 && !direct)
		{
			var stohDamage = soldierToHero* soldierCount;
			if(dl > stohDamage ) 
			{
				dl -= stohDamage;
			} else
			{
				dl = 0;
			}
			var c = attackerSoldierCriticalChecked?" class=\"critical\"":"";
			fightDetails+="<p"+c+">"+attackerSoldier["name"]+"用 <b>"+soldierCount+"</b> hit 对"+defender["name"]+"造成<b>"+stohDamage+"</b>伤害</p>";
		}
		if(heroCount > 0)
		{
			var htohDamage = heroToHero* heroCount;
			if(dl > htohDamage ) 
			{
				dl -= htohDamage;
			} else
			{
				dl = 0;
			}
			var c = attackerHeroCriticalChecked?" class=\"critical\"":"";
			fightDetails+="<p"+c+">"+attacker["name"]+"用 <b>"+heroCount+"</b> hit 对"+defender["name"]+"造成<b>"+htohDamage+"</b>伤害 </p>";
		}
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
