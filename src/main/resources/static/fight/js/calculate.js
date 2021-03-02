
function calculate()
{
	var coefficient = 1;
	var battleType = 1;
	var actionName = "";
	var attackerSoldierCriticalChecked = fightInfo["attacker"]["soldierCriticalChecked"];
	var attackerHeroCriticalChecked = fightInfo["attacker"]["heroCriticalChecked"];
	var defenderSoldierCriticalChecked = fightInfo["attacker"]["soldierCriticalChecked"];
	var defenderHeroCriticalChecked = fightInfo["attacker"]["heroCriticalChecked"];
	var action = fightInfo["attacker"]["action"];
	if(action)
	{
		coefficient = action["coefficient"];
		battleType = action["battleType"];
		actionName = action["name"];
		
		if(!action["simpleAttack"] && fightInfo["attacker"]["heroPanel"]["skillDamage"])
		{
			coefficient = coefficient*(1+fightInfo["attacker"]["heroPanel"]["skillDamage"]/100.0);
		}
	}
	
	if(battleType == 1)
	{
		
		if(fightInfo["attacker"]["heroPanel"]["features"]["FirstAttack"] && !fightInfo["defender"]["heroPanel"]["features"]["FirstAttack"])
		{
			// a first
			var defenderResult = battle("attacker", "defender", coefficient, attackerHeroCriticalChecked, attackerSoldierCriticalChecked,
				fightInfo["attacker"]["heroPanel"]["life"],  fightInfo["attacker"]["soldierPanel"]["life"]);
			var attackerResult = battle("defender", "attacker", 1, defenderHeroCriticalChecked, defenderSoldierCriticalChecked, defenderResult["dl"], defenderResult["dsl"]);
		}
		else if(!fightInfo["attacker"]["heroPanel"]["features"]["FirstAttack"] && fightInfo["defender"]["heroPanel"]["features"]["FirstAttack"]) 
		{
			// d first
			var attackerResult = battle("defender", "attacker", 1, defenderHeroCriticalChecked, defenderSoldierCriticalChecked, 
			    fightInfo["defender"]["heroPanel"]["life"], fightInfo["defender"]["soldierPanel"]["life"]);
			var defenderResult= battle("attacker", "defender", coefficient, attackerHeroCriticalChecked, attackerSoldierCriticalChecked, attackerResult["attackerResult"], attackerResult["dsl"]);			
		} else
		{
			var defenderResult = battle("attacker", "defender", coefficient, attackerHeroCriticalChecked, attackerSoldierCriticalChecked, 
				fightInfo["attacker"]["heroPanel"]["life"],fightInfo["attacker"]["soldierPanel"]["life"]);
			var attackerResult = battle("defender", "attacker", 1, defenderHeroCriticalChecked, defenderSoldierCriticalChecked, 
				fightInfo["defender"]["heroPanel"]["life"], fightInfo["defender"]["soldierPanel"]["life"]);
		}
		
	} 
	
	if(battleType == 2)
	{
		aoe(actionName, heroToSoldier, heroToHero, attackerHeroCriticalChecked, fightDetails);
	}
	
	
		
	$("#defenderSoldierBar").text(defenderResult["dsl"]+"/"+fightInfo["defender"]["soldierPanel"]["life"]);
	$("#defenderSoldierBar").attr("style", "width:"+Math.ceil(defenderResult["dsl"]/fightInfo["defender"]["soldierPanel"]["life"]*100)+"%");
	$("#defenderHeroBar").text(defenderResult["dl"]+"/"+fightInfo["defender"]["heroPanel"]["life"]);
	$("#defenderHeroBar").attr("style", "width:"+Math.ceil(defenderResult["dl"]/fightInfo["defender"]["heroPanel"]["life"]*100)+"%");							
	$("#attackerFightDetails").html(defenderResult["fightDetails"]);
	
		
	$("#attackerSoldierBar").text(attackerResult["dsl"]+"/"+fightInfo["attacker"]["soldierPanel"]["life"]);
	$("#attackerSoldierBar").attr("style", "width:"+Math.ceil(attackerResult["dsl"]/fightInfo["attacker"]["soldierPanel"]["life"]*100)+"%");
	$("#attackerHeroBar").text(attackerResult["dl"]+"/"+fightInfo["attacker"]["heroPanel"]["life"]);
	$("#attackerHeroBar").attr("style", "width:"+Math.ceil(attackerResult["dl"]/fightInfo["attacker"]["heroPanel"]["life"]*100)+"%");							
	$("#defenderFightDetails").html(attackerResult["fightDetails"]);
}

function getRange(role, kind)
{
	return fightInfo[role][kind+"Panel"]["range"];
}

function getMeleeDamageReduce(role, kind)
{
	if(fightInfo["distance"] == 1)
	{
		if(fightInfo[role][kind]["range"]>1)
		{
			if(fightInfo[role][kind+"Panel"]["features"]["ImmuneToMeleeDamageReduce"])
			{
				return false;
			}
			
			return true;
		}		
		return false;
	}
	return false;
}

function battle(attackerRole, defenderRole, coefficient, attackerHeroCriticalChecked, attackerSoldierCriticalChecked, attackerHeroLife, attackerSoldierLife)
{
	var fightDetails= "";
	if( fightInfo[attackerRole]["soldier"] && fightInfo[defenderRole]["soldier"])
	{
		var counter = getCounter(attackerRole, "soldier", "soldier");
		//alert(JSON.stringify(counter));
		
		soldierToSoldier =oneHit(1, fightInfo[attackerRole]["soldierPanel"], fightInfo[defenderRole]["soldierPanel"], 
				attackerSoldierCriticalChecked, fightInfo[attackerRole]["soldier"]["isPhysic"], counter);
		var mdr = getMeleeDamageReduce(attackerRole, "soldier");
		if(mdr)
		{
			soldierToSoldier = Math.ceil(0.3 * soldierToSoldier);
		}
		var c = "&nbsp;&nbsp;&nbsp;(暴击概率："+ criticalProb(fightInfo[attackerRole]["soldierPanel"],fightInfo[defenderRole]["soldierPanel"])+
				"，攻击克制：" +getCounterXS(counter["attack"])+ "，防御克制：" +getCounterXS(counter["physic"])+(mdr?"，近战惩罚":"")+")";
															         
	    fightDetails+="<p>"+fightInfo[attackerRole]["soldier"]["name"]+"攻击"+fightInfo[defenderRole]["soldier"]["name"]+"("+
	        	(fightInfo[attackerRole]["soldier"]["isPhysic"] ? "物理":"魔法")+")&nbsp;&nbsp;&nbsp;,&nbsp;1hit:<b>"+soldierToSoldier+"</b>"+c+"</p>";	

	}
	
	if(fightInfo[attackerRole]["soldier"] && fightInfo["defender"]["hero"])
	{
		var counter = getCounter(attackerRole, "soldier", "hero");
		soldierToHero = oneHit(1, fightInfo[attackerRole]["soldierPanel"], fightInfo[defenderRole]["heroPanel"], 
				attackerSoldierCriticalChecked, fightInfo[attackerRole]["soldier"]["isPhysic"], counter);
		var mdr = getMeleeDamageReduce(attackerRole, "soldier");
		if(mdr)
		{
			soldierToHero = Math.ceil(0.3 * soldierToHero);
		}
		var c = "&nbsp;&nbsp;&nbsp;(暴击概率："+ criticalProb(fightInfo[attackerRole]["soldierPanel"],fightInfo[defenderRole]["heroPanel"])+
			"，攻击克制：" +getCounterXS(counter["attack"])+ "，防御克制：" +getCounterXS(counter["physic"])+(mdr?"，近战惩罚":"")+")";
		fightDetails+="<p>"+fightInfo[attackerRole]["soldier"]["name"]+"攻击"+fightInfo[defenderRole]["hero"]["name"]+"("+
			(fightInfo[attackerRole]["soldier"]["isPhysic"] ? "物理":"魔法")+")" +
				"&nbsp;&nbsp;&nbsp;,&nbsp;1hit:<b>"+soldierToHero+"</b>"+c+"</p>";

	}
	
	var isAttackerPhysic = fightInfo[attackerRole]["action"] ? fightInfo[attackerRole]["action"]["isPhysic"] : fightInfo[attackerRole]["hero"]["isPhysic"] ;

	
	if(fightInfo[attackerRole]["hero"] && fightInfo[defenderRole]["soldier"])
	{
		var counter = getCounter(attackerRole, "hero", "soldier");
		//alert(JSON.stringify(counter));
		heroToSoldier = oneHit(coefficient, fightInfo[attackerRole]["heroPanel"], fightInfo[defenderRole]["soldierPanel"], 
				attackerHeroCriticalChecked, isAttackerPhysic ,counter);
		var mdr = getMeleeDamageReduce(attackerRole, "hero");
		if(mdr)
		{
			heroToSoldier = Math.ceil(0.3 * heroToSoldier);
		}
		var c = "&nbsp;&nbsp;&nbsp;(暴击概率："+ criticalProb(fightInfo[attackerRole]["heroPanel"], fightInfo[defenderRole]["soldierPanel"])+
			"，攻击克制：" +getCounterXS(counter["attack"])+ "，防御克制：" +getCounterXS(counter["physic"])+(mdr?"，近战惩罚":"")+")";
	    fightDetails+="<p>"+fightInfo[attackerRole]["hero"]["name"]+"攻击"+fightInfo[defenderRole]["soldier"]["name"]+"("+
	    	(isAttackerPhysic?"物理":"魔法")+")" +
	    		"&nbsp;&nbsp;&nbsp;,&nbsp;1hit:<b>"+heroToSoldier+"</b>"+c+"</p>";
	}

	if(fightInfo[attackerRole]["hero"] && fightInfo[defenderRole]["hero"])
	{
		var counter = getCounter(attackerRole, "hero", "hero");
		heroToHero = oneHit(coefficient, fightInfo[attackerRole]["heroPanel"], fightInfo[defenderRole]["heroPanel"], 
				attackerHeroCriticalChecked, isAttackerPhysic , counter);
		var mdr = getMeleeDamageReduce(attackerRole, "hero");
		if(mdr)
		{
			heroToHero = Math.ceil(0.3 * heroToHero);
		}
		var c = "&nbsp;&nbsp;&nbsp;(暴击概率："+ criticalProb(fightInfo[attackerRole]["heroPanel"], fightInfo[defenderRole]["heroPanel"])+
			"，攻击克制：" +getCounterXS(counter["attack"])+ "，防御克制：" +getCounterXS(counter["physic"])+(mdr?"，近战惩罚":"")+")";
		fightDetails+="<p>"+fightInfo[attackerRole]["hero"]["name"]+"攻击"+fightInfo[defenderRole]["hero"]["name"]+"("+
	    		(isAttackerPhysic?"物理":"魔法")+")" +
	    		"&nbsp;&nbsp;&nbsp;,&nbsp;1hit:<b>"+heroToHero+"</b>"+c+"</p>";

	}	
	fightDetails+="<p>-------------------------------------------------------------------------------------------</p>";
	var distance = fightInfo["distance"];
	var soldierCount = Math.ceil(attackerSoldierLife*20 / fightInfo[attackerRole]["soldierPanel"]["life"]);
	//var soldierCount = fightInfo["attacker"]["soldier"] ? 20 : 0;
	var heroCount = attackerHeroLife > 0  ? 20 : 0;
	var soldierLeftCount = soldierCount;
	var heroLeftCount = heroCount;
//	var dl = attackerHeroLife;
//	var dsl = attackerSoldierLife;
	var dl = fightInfo[defenderRole]["hero"] ? fightInfo[defenderRole]["heroLeftLife"] : 0;
	var dsl = fightInfo[defenderRole]["soldier"] ? fightInfo[defenderRole]["soldierLeftLife"] : 0;
	var direct = fightInfo[attackerRole]["action"] ? fightInfo[attackerRole]["action"]["direct"] : 0;
	
	if(fightInfo[attackerRole]["heroPanel"]["preBattleDamage"] > 0)
	{
		var preDamage = fightInfo[attackerRole]["heroPanel"]["preBattleDamage"]*fightInfo[attackerRole]["heroPanel"]["attack"];
		if(fightInfo[defenderRole]["hero"])
		{
			if(fightInfo[defenderRole]["heroPanel"]["features"]["ImmuneToFixedDamage"])
			{
				fightDetails+="<p>"+fightInfo[defenderRole]["hero"]["name"]+"免疫掉战前固伤</p>";
			} else
			{
				dl  -= preDamage;
				fightDetails+="<p>"+fightInfo[attackerRole]["hero"]["name"]+"战前对"+fightInfo[defenderRole]["hero"]["name"]+"造成<b>"+preDamage+"</b>伤害</p>";
			}
		}
	} 
	if(getRange(attackerRole, "soldier")< distance)
	{
		soldierLeftCount = 0;
		fightDetails+="<p>"+fightInfo[attackerRole]["soldier"]["name"]+"射程不够，无法出手</p>";
	}
	if(getRange(attackerRole, "hero")< distance)
	{
		heroLeftCount = 0;
		fightDetails+="<p>"+fightInfo[attackerRole]["hero"]["name"]+"射程不够，无法出手</p>";
	}
	
	if(fightInfo[defenderRole]["soldier"] && !direct) 
	{
		
		var oneSoldierLife = fightInfo[defenderRole]["soldierPanel"]["life"] / 10;
		if(preDamage)
		{
			if(fightInfo[defenderRole]["soldierPanel"]["features"]["ImmuneToFixedDamage"])
			{
				fightDetails+="<p>"+fightInfo[defenderRole]["soldier"]["name"]+"免疫掉战前固伤</p>";
			} else
			{
				dsl -= preDamage;
				var count = Math.floor(preDamage/oneSoldierLife);
				fightDetails+="<p>"+fightInfo[attackerRole]["hero"]["name"]+"战前对士兵造成<b>"+preDamage+"</b>伤害, 杀死<b>"+count+"</b>"+
							fightInfo[defenderRole]["soldier"]["name"]+"</p>";
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
				fightDetails+="<p"+c+">"+fightInfo[attackerRole]["soldier"]["name"]+" 用 <b>"+soldierCount+"</b> hit 干掉 <b>"+soldierKillSoldier+"</b>"+fightInfo[defenderRole]["soldier"]["name"]+"</p>";

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
						fightDetails+="<p"+c+">"+fightInfo[attackerRole]["hero"]["name"]+"用 <b>"+heroCount+"</b> hit 干掉 <b>"+heroKillSoldier+"</b>"+fightInfo[defenderRole]["soldier"]["name"]+"</p>";
					} 
					else
					{
						heroLeftCount -= Math.ceil((oneSoldierLife-dsl)/heroToSoldier);
						heroKillSoldier++;
						dsl =0;
						var c = attackerHeroCriticalChecked?" class=\"critical\"":"";
						fightDetails+="<p"+c+">"+fightInfo[attackerRole]["hero"]["name"]+"用 <b>"+(heroCount-heroLeftCount)+"</b> hit 干掉 <b>"+heroKillSoldier+"</b>"+fightInfo["defender"]["soldier"]["name"]+"</p>";
					}
				}
			} else
			{
				// soldier to hero
				soldierLeftCount -= Math.ceil(dsl/soldierToSoldier);
				soldierKillSoldier++;
				dsl = 0;
				var c = attackerSoldierCriticalChecked?" class=\"critical\"":"";
				fightDetails+="<p"+c+">"+fightInfo[attackerRole]["soldier"]["name"]+"用 <b>"+(soldierCount-soldierLeftCount)+"</b> hit 干掉 <b>"+soldierKillSoldier+"</b> "+fightInfo["defender"]["soldier"]["name"]+"</p>";
				
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
					fightDetails+="<p"+c+">"+fightInfo[attackerRole]["soldier"]["name"]+"用 <b>"+soldierLeftCount+"</b> hit 对"+fightInfo[defenderRole]["hero"]["name"]+"造成<b>"+stohDamage+"</b>伤害</p>";
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
			fightDetails+="<p"+c+">"+fightInfo[attackerRole]["soldier"]["name"]+"用 <b>"+soldierLeftCount+"</b> hit 对"+fightInfo[defenderRole]["hero"]["name"]+"造成<b>"+stohDamage+"</b>伤害</p>";
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
			fightDetails+="<p"+c+">"+fightInfo[attackerRole]["hero"]["name"]+"用 <b>"+heroLeftCount+"</b> hit 对"+fightInfo[defenderRole]["hero"]["name"]+"造成<b>"+htohDamage+"</b>伤害 </p>";
		}
	} 
	
			
	if(dl == 0)
	{
		fightDetails+="<p>"+fightInfo[defenderRole]["hero"]["name"]+"死亡</p>";
	}
	
	return {"dsl":dsl, "dl":dl, "fightDetails":fightDetails };
}


function aoe(actionName, heroToSoldier,heroToHero, attackerHeroCriticalChecked, fightDetails)
{
	var dl = fightInfo["defender"]["hero"] ? fightInfo["defender"]["heroLeftLife"] : 0;
	var dsl = fightInfo["defender"]["soldier"] ? fightInfo["defender"]["soldierLeftLife"] : 0;
	
	dl -= 20*heroToHero;
	dsl -= 20*heroToSoldier;
	
	var c = attackerHeroCriticalChecked?" class=\"critical\"":"";
	fightDetails+="<p"+c+">"+fightInfo["attacker"]["hero"]["name"]+"用"+actionName+"对"+fightInfo["defender"]["hero"]["name"]+"造成<b>"+20*heroToHero+"</b>伤害</p>";
	fightDetails+="<p"+c+">"+fightInfo["attacker"]["hero"]["name"]+"用"+actionName+"对"+fightInfo["defender"]["soldier"]["name"]+"造成<b>"+20*heroToSoldier+"</b>伤害</p>";
	
	$("#"+defenderRole+"SoldierBar").text(dsl+"/"+fightInfo[defenderRole]["soldierPanel"]["life"]);
	$("#"+defenderRole+"SoldierBar").attr("style", "width:"+Math.ceil(dsl/fightInfo[defenderRole]["soldierPanel"]["life"]*100)+"%");
	$("#"+defenderRole+"HeroBar").text(dl+"/"+fightInfo[defenderRole]["heroPanel"]["life"]);
	$("#"+defenderRole+"HeroBar").attr("style", "width:"+Math.ceil(dl/fightInfo["defender"]["heroPanel"]["life"]*100)+"%");							
	$("#"+attackerRole+"FightDetails").html(fightDetails);
	
}

function oneHit(coefficient, panel1, panel2, critcal, isPhysics, counter)
{
	var c = critcal ? (130 + panel1["criticalDamageInc"] - panel2["criticalDamageDec"]) / 100 : 1;
	var ca = counter["attack"];
	var cp = counter["physic"];
	var cm = counter["magic"];
	var igd = panel1["ignoreDef"];
	
	if(isPhysics)
	{
		var realAttack = panel1["attack"]*(1+ca/100.0);
		var realDef = panel2["physic"]*(1+cp/100.0) * (1-igd/100.0);
		if(realAttack <= realDef)
		{
			return 1;
		}
		return Math.floor(coefficient * (realAttack - realDef) * (1+(panel1["damageInc"]-panel2["physicDamageDec"])/100.0)*c /2) ;
	} else	
	{
		var attack = panel1["isSoldier"] ? panel1["attack"]:panel1["intel"];
		var realAttack = attack*(1+counter["attack"]/100.0);
		var realDef = panel2["magic"]*(1+cm/100.0) * (1-igd/100.0);
		if(realAttack <= realDef)
		{
			return 1;
		}
		return Math.floor(coefficient * (realAttack- realDef)*
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
			if(counter[attackerKind] && counter["enemyType"] == defenderType || counter["enemyType"] == 0)
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
			if(counter[defenderKind] && counter["enemyType"] == attackerType || counter["enemyType"] == 0)
			{
				result["physic"]+=counter["number"];
			}
		}
		if(counter["buffType"]=="Magic" )
		{
			if(counter[defenderKind] && counter["enemyType"] == attackerType || counter["enemyType"] == 0)
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
	if(attackerType==3 && defenderType==2)
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

