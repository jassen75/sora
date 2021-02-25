
function calculate()
{
	var soldierToSoldier=0;
	var soldierToHero=0;
	var heroToSoldier=0;
	var heroToHero=0;
	var fightDetails= "";
	var coefficient = 1;
	var attackerSoldierCriticalChecked = fightInfo["attacker"]["soldierCriticalChecked"];
	var attackerHeroCriticalChecked = fightInfo["attacker"]["heroCriticalChecked"];
	var action = fightInfo["attacker"]["action"];
	if(action)
	{
		coefficient = action["coefficient"];
	}
	
	if( fightInfo["attacker"]["soldier"] && fightInfo["defender"]["soldier"])
	{
		var counter = getCounter("attacker", "soldier", "soldier");
		//alert(JSON.stringify(counter));
		
		soldierToSoldier =oneHit(1, fightInfo["attacker"]["soldierPanel"], fightInfo["defender"]["soldierPanel"], 
				attackerSoldierCriticalChecked, fightInfo["attacker"]["soldier"]["isPhysic"], counter);
		var c = "&nbsp;&nbsp;&nbsp;(暴击概率："+ criticalProb(fightInfo["attacker"]["soldierPanel"],fightInfo["defender"]["soldierPanel"])+
			"，攻击克制：" +getCounterXS(counter["attack"])+ "，防御克制：" +getCounterXS(counter["physic"])+")";
														         
        fightDetails+="<p>"+fightInfo["attacker"]["soldier"]["name"]+"攻击"+fightInfo["defender"]["soldier"]["name"]+"("+
        	(fightInfo["attacker"]["hero"]["isPhysic"] ? "物理":"魔法")+")&nbsp;&nbsp;&nbsp;,&nbsp;1hit:<b>"+soldierToSoldier+"</b>"+c+"</p>";
	}
	
	if(fightInfo["attacker"]["soldier"] && fightInfo["defender"]["hero"])
	{
		var counter = getCounter("attacker", "soldier", "hero");
		soldierToHero = oneHit(1, fightInfo["attacker"]["soldierPanel"], fightInfo["defender"]["heroPanel"], 
				attackerSoldierCriticalChecked, fightInfo["attacker"]["soldier"]["isPhysic"], counter);
		var c = "&nbsp;&nbsp;&nbsp;(暴击概率："+ criticalProb(fightInfo["attacker"]["soldierPanel"],fightInfo["defender"]["heroPanel"])+
			"，攻击克制：" +getCounterXS(counter["attack"])+ "，防御克制：" +getCounterXS(counter["physic"])+")";
		fightDetails+="<p>"+fightInfo["attacker"]["soldier"]["name"]+"攻击"+fightInfo["defender"]["hero"]["name"]+"("+
			(fightInfo["attacker"]["soldier"]["isPhysic"] ? "物理":"魔法")+")" +
				"&nbsp;&nbsp;&nbsp;,&nbsp;1hit:<b>"+soldierToHero+"</b>"+c+"</p>";
	}
	
	var isAttackerPhysic = fightInfo["attacker"]["action"] ? fightInfo["attacker"]["action"]["isPhysic"] : fightInfo["attacker"]["hero"]["isPhysic"] ;

	
	if(fightInfo["attacker"]["hero"] && fightInfo["defender"]["soldier"])
	{
		var counter = getCounter("attacker", "hero", "soldier");
		heroToSoldier = oneHit(coefficient, fightInfo["attacker"]["heroPanel"], fightInfo["defender"]["soldierPanel"], 
				attackerHeroCriticalChecked, isAttackerPhysic ,counter);
		var c = "&nbsp;&nbsp;&nbsp;(暴击概率："+ criticalProb(fightInfo["attacker"]["heroPanel"], fightInfo["defender"]["soldierPanel"])+
			"，攻击克制：" +getCounterXS(counter["attack"])+ "，防御克制：" +getCounterXS(counter["physic"])+")";
	    fightDetails+="<p>"+fightInfo["attacker"]["hero"]["name"]+"攻击"+fightInfo["defender"]["soldier"]["name"]+"("+
	    	(isAttackerPhysic?"物理":"魔法")+")" +
	    		"&nbsp;&nbsp;&nbsp;,&nbsp;1hit:<b>"+heroToSoldier+"</b>"+c+"</p>";
	}

	if(fightInfo["attacker"]["hero"] && fightInfo["defender"]["hero"])
	{
		var counter = getCounter("attacker", "hero", "hero");
		heroToHero = oneHit(coefficient, fightInfo["attacker"]["heroPanel"], fightInfo["defender"]["heroPanel"], 
				attackerHeroCriticalChecked, isAttackerPhysic , counter);
		var c = "&nbsp;&nbsp;&nbsp;(暴击概率："+ criticalProb(fightInfo["attacker"]["heroPanel"], fightInfo["defender"]["heroPanel"])+
			"，攻击克制：" +getCounterXS(counter["attack"])+ "，防御克制：" +getCounterXS(counter["physic"])+")";
	    fightDetails+="<p>"+fightInfo["attacker"]["hero"]["name"]+"攻击"+fightInfo["defender"]["hero"]["name"]+"("+
	    		(isAttackerPhysic?"物理":"魔法")+")" +
	    		"&nbsp;&nbsp;&nbsp;,&nbsp;1hit:<b>"+heroToHero+"</b>"+c+"</p>";
	}
	fightDetails+="<p>-------------------------------------------------------------------------------------------</p>";
	var soldierCount = fightInfo["attacker"]["soldier"] ? 20 : 0;
	var heroCount = fightInfo["attacker"]["hero"] ? 20 : 0;
	var soldierLeftCount = soldierCount;
	var heroLeftCount = heroCount;
	var dl = fightInfo["defender"]["hero"] ? fightInfo["defender"]["heroLeftLife"] : 0;
	var dsl = fightInfo["defender"]["soldier"] ? fightInfo["defender"]["soldierLeftLife"] : 0;
	var direct = fightInfo["attacker"]["action"] ? fightInfo["attacker"]["action"]["direct"] : 0;
	
	if(fightInfo["attacker"]["heroPanel"]["preBattleDamage"] > 0)
	{
		var preDamage = fightInfo["attacker"]["heroPanel"]["preBattleDamage"]*fightInfo["attacker"]["heroPanel"]["attack"];
		if(fightInfo["defender"]["hero"])
		{
			if(fightInfo["defender"]["heroPanel"]["features"]["ImmuneToFixedDamage"])
			{
				fightDetails+="<p>"+fightInfo["defender"]["hero"]["name"]+"免疫掉战前固伤</p>";
			} else
			{
				dl  -= preDamage;
				fightDetails+="<p>"+fightInfo["attacker"]["hero"]["name"]+"战前对"+fightInfo["defender"]["hero"]["name"]+"造成<b>"+preDamage+"</b>伤害</p>";
			}
		}
	} 
	if(fightInfo["defender"]["soldier"] && !direct) 
	{
		
		var oneSoldierLife = fightInfo["defender"]["soldierPanel"]["life"] / 10;
		if(preDamage)
		{
			if(fightInfo["defender"]["soldierPanel"]["features"]["ImmuneToFixedDamage"])
			{
				fightDetails+="<p>"+fightInfo["defender"]["soldier"]["name"]+"免疫掉战前固伤</p>";
			} else
			{
				dsl -= preDamage;
				var count = Math.floor(preDamage/oneSoldierLife);
				fightDetails+="<p>"+fightInfo["attacker"]["hero"]["name"]+"战前对士兵造成<b>"+preDamage+"</b>伤害, 杀死<b>"+count+"</b>"+
							fightInfo["defender"]["soldier"]["name"]+"</p>";
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
				//fightDetails+="<p>dsl=="+dsl+"   soldierKillSoldier=="+soldierKillSoldier+"</p>";
			}

			// left many soldier
			if( dsl > oneSoldierLife)
			{
				dsl -= soldierToSoldier * soldierLeftCount;
				soldierLeftCount = 0;
				var c = attackerSoldierCriticalChecked?" class=\"critical\"":"";
				fightDetails+="<p"+c+">"+fightInfo["attacker"]["soldier"]["name"]+" 用 <b>"+soldierCount+"</b> hit 干掉 <b>"+soldierKillSoldier+"</b>"+fightInfo["defender"]["soldier"]["name"]+"</p>";

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
						//fightDetails+="<p>dsl=="+dsl+"   heroKillSoldier=="+heroKillSoldier+"</p>";
					}
					
					// soldier left
					if( dsl > oneSoldierLife)
					{
						dsl -= heroToSoldier * heroLeftCount;
						heroLeftCount = 0;
						var c = attackerHeroCriticalChecked?" class=\"critical\"":"";
						fightDetails+="<p"+c+">"+fightInfo["attacker"]["hero"]["name"]+"用 <b>"+heroCount+"</b> hit 干掉 <b>"+heroKillSoldier+"</b>"+fightInfo["defender"]["soldier"]["name"]+"</p>";
					} 
					else
					{
						heroLeftCount -= Math.ceil((oneSoldierLife-dsl)/heroToSoldier);
						heroKillSoldier++;
						dsl =0;
						var c = attackerHeroCriticalChecked?" class=\"critical\"":"";
						fightDetails+="<p"+c+">"+fightInfo["attacker"]["hero"]["name"]+"用 <b>"+(heroCount-heroLeftCount)+"</b> hit 干掉 <b>"+heroKillSoldier+"</b>"+fightInfo["defender"]["soldier"]["name"]+"</p>";
					}
				}
			} else
			{
				// soldier to hero
				soldierLeftCount -= Math.ceil(dsl/soldierToSoldier);
				soldierKillSoldier++;
				dsl = 0;
				var c = attackerSoldierCriticalChecked?" class=\"critical\"":"";
				fightDetails+="<p"+c+">"+fightInfo["attacker"]["soldier"]["name"]+"用 <b>"+(soldierCount-soldierLeftCount)+"</b> hit 干掉 <b>"+soldierKillSoldier+"</b> "+fightInfo["defender"]["soldier"]["name"]+"</p>";
				
				if(fightInfo["defender"]["hero"] && soldierLeftCount >0)
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
					fightDetails+="<p"+c+">"+fightInfo["attacker"]["soldier"]["name"]+"用 <b>"+soldierLeftCount+"</b> hit 对"+fightInfo["defender"]["hero"]["name"]+"造成<b>"+stohDamage+"</b>伤害</p>";
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
			fightDetails+="<p"+c+">"+fightInfo["attacker"]["soldier"]["name"]+"用 <b>"+soldierLeftCount+"</b> hit 对"+fightInfo["defender"]["hero"]["name"]+"造成<b>"+stohDamage+"</b>伤害</p>";
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
			fightDetails+="<p"+c+">"+fightInfo["attacker"]["hero"]["name"]+"用 <b>"+heroLeftCount+"</b> hit 对"+fightInfo["defender"]["hero"]["name"]+"造成<b>"+htohDamage+"</b>伤害 </p>";
		}
	} 
	
	if(dl == 0)
	{
		fightDetails+="<p>"+fightInfo["defender"]["hero"]["name"]+"死亡</p>";
	}
	
	$("#defenderSoldierBar").text(dsl+"/"+fightInfo["defender"]["soldierPanel"]["life"]);
	
	$("#defenderSoldierBar").attr("style", "width:"+Math.ceil(dsl/fightInfo["defender"]["soldierPanel"]["life"]*100)+"%");
	
	$("#defenderHeroBar").text(dl+"/"+fightInfo["defender"]["heroPanel"]["life"]);
	
	$("#defenderHeroBar").attr("style", "width:"+Math.ceil(dl/fightInfo["defender"]["heroPanel"]["life"]*100)+"%");
										
	$("#attackerFightDetails").html(fightDetails);
	
	calculateDefender();
}

function oneHit(coefficient, panel1, panel2, critcal, isPhysics, counter)
{
	var c = critcal ? (130 + panel1["criticalDamageInc"] - panel2["criticalDamageDec"]) / 100 : 1;
	var ca = counter["attack"];
	var cp = counter["physic"];
	var cm = counter["magic"];
	if(isPhysics)
	{
		if(panel1["attack"]*(1+ca/100.0) <= panel2["physic"]*(1+cp/100.0))
		{
			return 1;
		}
		return Math.floor(coefficient * (panel1["attack"]*(1+ca/100.0)-panel2["physic"]*(1+cp/100.0))*
			(1+(panel1["damageInc"]-panel2["physicDamageDec"])/100.0)*c /2) ;
	} else	
	{
		var attack = panel1["isSoldier"] ? panel1["attack"]:panel1["intel"];
		if(attack*(1+counter["attack"]/100.0)<=panel2["magic"]*(1+cm/100.0))
		{
			return 1;
		}
		return Math.floor(coefficient * (attack*(1+counter["attack"])/100.0-panel2["magic"]*(1+cm/100.0))*
			(1+(panel1["damageInc"]-panel2["magicDamageDec"])/100.0)* c /2) ;
	}
}

function criticalProb(panel1, panel2)
{
	var prob = panel1["criticalProbInc"]+panel1["tech"]/10;
	if(prob > 100)
	{
		prob = 100;
	}
	return Math.floor(prob * (100-panel2["criticalProbDec"])/100)+"%";
}

function getCounter(attackerRole, attackerKind, defenderKind)
{
	var defenderRole = attackerRole=="attacker" ? "defender":"attacker";
	var attackerType=fightInfo[attackerRole][attackerKind]["type"];
	var defenderType=fightInfo[defenderRole][defenderKind]["type"];

	var attackerPanel = fightInfo[attackerRole][attackerKind+"Panel"];
	var defenderPanel = fightInfo[defenderRole][defenderKind+"Panel"];
	var attackerCounters = attackerPanel["counters"];
	var defenderCounters = defenderPanel["counters"];
	var result = {};
	result["attack"]=0;
	result["physic"]=0;
	result["magic"]=0;
	for(var i=0; i<attackerCounters.length; i++)
	{
		var counter = attackerCounters[i];
		if(counter["buffType"]=="Attack")
		{
			if(counter[attackerKind] && counter["enemyType"] == defenderType)
			{
				result["attack"]+=counter["number"];
			}
		}
	}
	result["attack"]+=getDefaultCounter(attackerType, defenderType);
	
	//alert("defenderCounters"+defenderCounters);
	for(var i=0; i<defenderCounters.length; i++)
	{
		var counter = defenderCounters[i];
		if(counter["buffType"]=="Physic")
		{
			if(counter[defenderKind] && counter["enemyType"] == attackerType)
			{
				result["physic"]+=counter["number"];
			}
		}
		if(counter["buffType"]=="Magic" )
		{
			if(counter[defenderKind] && counter["enemyType"] == attackerType)
			{
				result["magic"]+=counter["number"];
			}
		}
	}
	return result;
}

function getCounterXS(counter)
{
	return (100.0+counter)/100;
}

function getDefaultCounter(attackerType, defenderType)
{
	// 步克制枪
	if(attackerType==1 && defenderType==2)
	{
		return 40;
	}
	// 步被骑克
	if(attackerType==1 && defenderType==3)
	{
		return -30;
	}
	// 枪被步兵克
	if(attackerType==2 && defenderType==1)
	{
		return -20;
	}
	// 枪克制骑
	if(attackerType==2 && defenderType==3)
	{
		return 30;
	}
	
	// 骑克制步
	if(attackerType==3 && defenderType==1)
	{
		return 20;
	}
	
	// 骑被枪克
	if(attackerType==3 && defenderType==1)
	{
		return -30;
	}
	// 弓克制飞
	if(attackerType==6 && defenderType==5)
	{
		return 30;
	}
	
	// 僧克制魔
	if(attackerType==10 && defenderType==9)
	{
		return 80;
	}
	
	// 魔被僧克制
	if(attackerType==10 && defenderType==9)
	{
		return -40;
	}
	
	return 0;
}

function calculateDefender()
{
	var soldierToSoldier=0;
	var soldierToHero=0;
	var heroToSoldier=0;
	var heroToHero=0;
	var fightDetails= "";
	var coefficient = 1;
	var defenderSoldierCriticalChecked = fightInfo["defender"]["soldierCriticalChecked"];
	var defenderHeroCriticalChecked = fightInfo["defender"]["heroCriticalChecked"];
	if( fightInfo["defender"]["soldier"] && fightInfo["defender"]["soldier"])
	{
		var counter = getCounter("defender", "soldier", "soldier");
		soldierToSoldier =oneHit(1, fightInfo["defender"]["soldierPanel"], fightInfo["attacker"]["soldierPanel"], 
				defenderSoldierCriticalChecked, fightInfo["defender"]["soldier"]["isPhysic"], counter);
		var c = "&nbsp;&nbsp;&nbsp;(暴击概率："+ criticalProb(fightInfo["defender"]["soldierPanel"],fightInfo["attacker"]["soldierPanel"])+
			"，攻击克制：" +getCounterXS(counter["attack"])+ "，防御克制：" +getCounterXS(counter["physic"])+")";
														         
        fightDetails+="<p>"+fightInfo["defender"]["soldier"]["name"]+"攻击"+fightInfo["attacker"]["soldier"]["name"]+"("+
        	(fightInfo["defender"]["hero"]["isPhysic"] ? "物理":"魔法")+")&nbsp;&nbsp;&nbsp;,&nbsp;1hit:<b>"+soldierToSoldier+"</b>"+c+"</p>";
	}
	
	if(fightInfo["defender"]["soldier"] && fightInfo["attacker"]["hero"])
	{
		var counter = getCounter("defender", "soldier", "hero");
		soldierToHero = oneHit(1, fightInfo["defender"]["soldierPanel"], fightInfo["attacker"]["heroPanel"], 
				defenderSoldierCriticalChecked, fightInfo["defender"]["soldier"]["isPhysic"], counter);
		var c = "&nbsp;&nbsp;&nbsp;(暴击概率："+ criticalProb(fightInfo["defender"]["soldierPanel"],fightInfo["attacker"]["heroPanel"])+
			"，攻击克制：" +getCounterXS(counter["attack"])+ "，防御克制：" +getCounterXS(counter["physic"])+")";
		fightDetails+="<p>"+fightInfo["defender"]["soldier"]["name"]+"攻击"+fightInfo["attacker"]["hero"]["name"]+"("+
			(fightInfo["defender"]["soldier"]["isPhysic"] ? "物理":"魔法")+")" +
				"&nbsp;&nbsp;&nbsp;,&nbsp;1hit:<b>"+soldierToHero+"</b>"+c+"</p>";
	}
	
	var isAttackerPhysic = fightInfo["defender"]["hero"]["isPhysic"] ;

	if(fightInfo["defender"]["hero"] && fightInfo["attacker"]["soldier"])
	{
		var counter = getCounter("defender", "hero", "soldier");
		heroToSoldier = oneHit(coefficient, fightInfo["defender"]["heroPanel"], fightInfo["attacker"]["soldierPanel"], 
				defenderSoldierCriticalChecked, isAttackerPhysic ,counter);
		var c = "&nbsp;&nbsp;&nbsp;(暴击概率："+ criticalProb(fightInfo["defender"]["heroPanel"], fightInfo["attacker"]["soldierPanel"])+
				"，攻击克制：" +getCounterXS(counter["attack"])+ "，防御克制：" +getCounterXS(counter["physic"])+")";
	    fightDetails+="<p>"+fightInfo["defender"]["hero"]["name"]+"攻击"+fightInfo["attacker"]["soldier"]["name"]+"("+
	    	(isAttackerPhysic?"物理":"魔法")+")" +
	    		"&nbsp;&nbsp;&nbsp;,&nbsp;1hit:<b>"+heroToSoldier+"</b>"+c+"</p>";
	}

	if(fightInfo["defender"]["hero"] && fightInfo["attacker"]["hero"])
	{
		var counter = getCounter("defender", "hero", "hero");
		heroToHero = oneHit(coefficient, fightInfo["defender"]["heroPanel"], fightInfo["attacker"]["heroPanel"], 
				defenderHeroCriticalChecked, isAttackerPhysic , counter);
		var c = "&nbsp;&nbsp;&nbsp;(暴击概率："+ criticalProb(fightInfo["defender"]["heroPanel"], fightInfo["attacker"]["heroPanel"])+
			    "，攻击克制：" +getCounterXS(counter["attack"])+ "，防御克制：" +getCounterXS(counter["physic"])+")";
	    fightDetails+="<p>"+fightInfo["defender"]["hero"]["name"]+"攻击"+fightInfo["attacker"]["hero"]["name"]+"("+
	    		(isAttackerPhysic?"物理":"魔法")+")" +
	    		"&nbsp;&nbsp;&nbsp;,&nbsp;1hit:<b>"+heroToHero+"</b>"+c+"</p>";
	}
	fightDetails+="<p>-------------------------------------------------------------------------------------------</p>";
	var soldierCount = fightInfo["defender"]["soldier"] ? 20 : 0;
	var heroCount = fightInfo["defender"]["hero"] ? 20 : 0;
	var soldierLeftCount = soldierCount;
	var heroLeftCount = heroCount;
	var dl = fightInfo["attacker"]["hero"] ? fightInfo["attacker"]["heroLeftLife"] : 0;
	var dsl = fightInfo["attacker"]["soldier"] ? fightInfo["attacker"]["soldierLeftLife"] : 0;
	var direct = 0;
	
	if(fightInfo["defender"]["heroPanel"]["preBattleDamage"] > 0)
	{
		var preDamage = fightInfo["defender"]["heroPanel"]["preBattleDamage"]*fightInfo["defender"]["heroPanel"]["attack"];
		if(fightInfo["attacker"]["hero"])
		{
			if(fightInfo["attacker"]["heroPanel"]["features"]["ImmuneToFixedDamage"])
			{
				fightDetails+="<p>"+fightInfo["attacker"]["hero"]["name"]+"免疫掉战前固伤</p>";
			} else
			{
				dl  -= preDamage;
				fightDetails+="<p>"+fightInfo["defender"]["hero"]["name"]+"战前对"+fightInfo["attacker"]["hero"]["name"]+"造成<b>"+preDamage+"</b>伤害</p>";
			}
		}
	} 
	if(fightInfo["attacker"]["soldier"] && !direct) 
	{
		
		var oneSoldierLife = fightInfo["attacker"]["soldierPanel"]["life"] / 10;
		if(preDamage)
		{
			if(fightInfo["attacker"]["soldierPanel"]["features"]["ImmuneToFixedDamage"])
			{
				fightDetails+="<p>"+fightInfo["attacker"]["soldier"]["name"]+"免疫掉战前固伤</p>";
			} else
			{
				dsl -= preDamage;
				var count = Math.floor(preDamage/oneSoldierLife);
				fightDetails+="<p>"+fightInfo["defender"]["hero"]["name"]+"战前对士兵造成<b>"+preDamage+"</b>伤害, 杀死<b>"+count+"</b>"+
							fightInfo["attacker"]["soldier"]["name"]+"</p>";
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
				//fightDetails+="<p>dsl=="+dsl+"   soldierKillSoldier=="+soldierKillSoldier+"</p>";
			}

			// left many soldier
			if( dsl > oneSoldierLife)
			{
				dsl -= soldierToSoldier * soldierLeftCount;
				soldierLeftCount = 0;
				var c = defenderSoldierCriticalChecked?" class=\"critical\"":"";
				fightDetails+="<p"+c+">"+fightInfo["defender"]["soldier"]["name"]+" 用 <b>"+soldierCount+"</b> hit 干掉 <b>"+soldierKillSoldier+"</b>"+fightInfo["attacker"]["soldier"]["name"]+"</p>";

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
						//fightDetails+="<p>dsl=="+dsl+"   heroKillSoldier=="+heroKillSoldier+"</p>";
					}
					
					// soldier left
					if( dsl > oneSoldierLife)
					{
						dsl -= heroToSoldier * heroLeftCount;
						heroLeftCount = 0;
						var c = defenderHeroCriticalChecked?" class=\"critical\"":"";
						fightDetails+="<p"+c+">"+fightInfo["defender"]["hero"]["name"]+"用 <b>"+heroCount+"</b> hit 干掉 <b>"+heroKillSoldier+"</b>"+fightInfo["attacker"]["soldier"]["name"]+"</p>";
					} 
					else
					{
						heroLeftCount -= Math.ceil((oneSoldierLife-dsl)/heroToSoldier);
						heroKillSoldier++;
						dsl =0;
						var c = defenderHeroCriticalChecked?" class=\"critical\"":"";
						fightDetails+="<p"+c+">"+fightInfo["defender"]["hero"]["name"]+"用 <b>"+(heroCount-heroLeftCount)+"</b> hit 干掉 <b>"+heroKillSoldier+"</b>"+fightInfo["attacker"]["soldier"]["name"]+"</p>";
					}
				}
			} else
			{
				// soldier to hero
				soldierLeftCount -= Math.ceil(dsl/soldierToSoldier);
				soldierKillSoldier++;
				dsl = 0;
				var c = defenderSoldierCriticalChecked?" class=\"critical\"":"";
				fightDetails+="<p"+c+">"+fightInfo["defender"]["soldier"]["name"]+"用 <b>"+(soldierCount-soldierLeftCount)+"</b> hit 干掉 <b>"+soldierKillSoldier+"</b> "+fightInfo["attacker"]["soldier"]["name"]+"</p>";
				
				if(fightInfo["defender"]["hero"] && soldierLeftCount >0)
				{
					var stohDamage = soldierToHero* soldierLeftCount;
					if(dl > stohDamage ) 
					{
						dl -= stohDamage;	
					} else
					{
						dl = 0;
					}
					var c = defenderSoldierCriticalChecked?" class=\"critical\"":"";
					fightDetails+="<p"+c+">"+fightInfo["defender"]["soldier"]["name"]+"用 <b>"+soldierLeftCount+"</b> hit 对"+fightInfo["attacker"]["hero"]["name"]+"造成<b>"+stohDamage+"</b>伤害</p>";
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
			var c = defenderSoldierCriticalChecked?" class=\"critical\"":"";
			fightDetails+="<p"+c+">"+fightInfo["defender"]["soldier"]["name"]+"用 <b>"+soldierLeftCount+"</b> hit 对"+fightInfo["attacker"]["hero"]["name"]+"造成<b>"+stohDamage+"</b>伤害</p>";
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
			var c = defenderHeroCriticalChecked?" class=\"critical\"":"";
			fightDetails+="<p"+c+">"+fightInfo["defender"]["hero"]["name"]+"用 <b>"+heroLeftCount+"</b> hit 对"+fightInfo["attacker"]["hero"]["name"]+"造成<b>"+htohDamage+"</b>伤害 </p>";
		}
	} 
	
	if(dl == 0)
	{
		fightDetails+="<p>"+fightInfo["attacker"]["hero"]["name"]+"死亡</p>";
	}
	
	$("#attackerSoldierBar").text(dsl+"/"+fightInfo["attacker"]["soldierPanel"]["life"]);
	
	$("#attackerSoldierBar").attr("style", "width:"+Math.ceil(dsl/fightInfo["attacker"]["soldierPanel"]["life"]*100)+"%");
	
	$("#attackerHeroBar").text(dl+"/"+fightInfo["defender"]["heroPanel"]["life"]);
	
	$("#attackerHeroBar").attr("style", "width:"+Math.ceil(dl/fightInfo["attacker"]["heroPanel"]["life"]*100)+"%");
										
	$("#defenderFightDetails").html(fightDetails);
}
