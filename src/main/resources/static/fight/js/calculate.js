function calculatefastAttack(time)
{
	if(time==300)
	{
		return 14;
	}
	if(time==500)
	{
		return 14;
	}
	return 0;
}

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
	var fastAttack = 0;
	if(action)
	{
		coefficient = action["coefficient"];
		battleType = action["battleType"];
		actionName = action["name"];
		
		if(!action["simpleAttack"] && fightInfo["attacker"]["heroPanel"]["skillDamage"])
		{
			coefficient = coefficient*(1+fightInfo["attacker"]["heroPanel"]["skillDamage"]/100.0);
		}
		
		fastAttack = calculatefastAttack(action["hitTime"]);
	}
	
	if(battleType == 1)
	{
		
		if(fightInfo["attacker"]["heroPanel"]["features"]["FirstAttack"] && !fightInfo["defender"]["heroPanel"]["features"]["FirstAttack"])
		{
			// a first
			var defenderResult = battle("attacker", "defender", coefficient, attackerHeroCriticalChecked, attackerSoldierCriticalChecked,
				fightInfo["attacker"]["heroLeftLife"],  fightInfo["attacker"]["soldierLeftLife"]);
			var attackerResult = battle("defender", "attacker", 1, defenderHeroCriticalChecked, defenderSoldierCriticalChecked, 
				defenderResult["dl"], defenderResult["dsl"]);
			defenderResult["fightDetails"] = defenderResult["fightDetails"] + "<p>"+fightInfo["attacker"]["hero"]["name"]+"先于敌人攻击</p>";
		}
		else if(!fightInfo["attacker"]["heroPanel"]["features"]["FirstAttack"] && fightInfo["defender"]["heroPanel"]["features"]["FirstAttack"]) 
		{
			// d first
			var attackerResult = battle("defender", "attacker", 1, defenderHeroCriticalChecked, defenderSoldierCriticalChecked, 
			    fightInfo["defender"]["heroLeftLife"], fightInfo["defender"]["soldierLeftLife"]);
			var defenderResult= battle("attacker", "defender", coefficient, attackerHeroCriticalChecked, attackerSoldierCriticalChecked,
				attackerResult["attackerResult"], attackerResult["dsl"]);			
			attackerResult["fightDetails"] = attackerResult["fightDetails"] + "<p>"+fightInfo["attacker"]["hero"]["name"]+"先于敌人攻击</p>";
		} else
		{
			var defenderResult = battle("attacker", "defender", coefficient, attackerHeroCriticalChecked, attackerSoldierCriticalChecked, 
				fightInfo["attacker"]["heroLeftLife"],fightInfo["attacker"]["soldierLeftLife"], fastAttack);
			var attackerResult = battle("defender", "attacker", 1, defenderHeroCriticalChecked, defenderSoldierCriticalChecked, 
				defenderResult["fl"], defenderResult["fsl"]);
		}
		
	} 
	
	if(battleType == 2)
	{
		var defenderResult = aoe(coefficient, attackerHeroCriticalChecked);
	}
	
	
	if(defenderResult)	
	{
		$("#defenderSoldierBar").text(defenderResult["dsl"]+"/"+fightInfo["defender"]["soldierPanel"]["life"]);
		$("#defenderSoldierBar").attr("style", "width:"+Math.ceil(defenderResult["dsl"]/fightInfo["defender"]["soldierPanel"]["life"]*100)+"%");
		$("#defenderHeroBar").text(defenderResult["dl"]+"/"+fightInfo["defender"]["heroPanel"]["life"]);
		$("#defenderHeroBar").attr("style", "width:"+Math.ceil(defenderResult["dl"]/fightInfo["defender"]["heroPanel"]["life"]*100)+"%");							
		$("#attackerFightDetails").html(defenderResult["fightDetails"]);
	}
	
	if(attackerResult)
	{
		$("#attackerSoldierBar").text(attackerResult["dsl"]+"/"+fightInfo["attacker"]["soldierPanel"]["life"]);
		$("#attackerSoldierBar").attr("style", "width:"+Math.ceil(attackerResult["dsl"]/fightInfo["attacker"]["soldierPanel"]["life"]*100)+"%");
		$("#attackerHeroBar").text(attackerResult["dl"]+"/"+fightInfo["attacker"]["heroPanel"]["life"]);
		$("#attackerHeroBar").attr("style", "width:"+Math.ceil(attackerResult["dl"]/fightInfo["attacker"]["heroPanel"]["life"]*100)+"%");							
		$("#defenderFightDetails").html(attackerResult["fightDetails"]);
	}
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

function battle(attackerRole, defenderRole, coefficient, attackerHeroCriticalChecked, attackerSoldierCriticalChecked, attackerHeroLife, 
		attackerSoldierLife, fastAttack)
{
	//alert("attackerHeroLife=="+attackerHeroLife+", attackerSoldierLife==="+attackerSoldierLife);
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
	
	var al = attackerHeroLife;
	var asl = attackerSoldierLife;
	var dl = fightInfo[defenderRole]["hero"] ? fightInfo[defenderRole]["heroLeftLife"] : 0;
	var dsl = fightInfo[defenderRole]["soldier"] ? fightInfo[defenderRole]["soldierLeftLife"] : 0;
	var distance = fightInfo["distance"];
	
	if(fightInfo[attackerRole]["heroPanel"]["features"]["PreFixDamageAttack"])
	{
		if(fightInfo[defenderRole]["heroPanel"]["features"]["ImmuneToFixedDamage"])
		{
			fightDetails+="<p>"+fightInfo[defenderRole]["hero"]["name"]+"免疫掉战前固伤</p>";
		} else
		{
			var list = fightInfo[attackerRole]["heroPanel"]["features"]["PreFixDamageAttack"];
			for(var i in list)
			{
				var damage = Math.ceil(list[i]*fightInfo[attackerRole]["heroPanel"]["attack"]);
				dl  -= damage;
				dsl -= damage;
				fightDetails+="<p>"+fightInfo[attackerRole]["hero"]["name"]+"战前对"+fightInfo[defenderRole]["hero"]["name"]+"及士兵各造成<b>"+damage+"</b>伤害</p>";
			}
		}
	}
	
	if(fightInfo[defenderRole]["heroPanel"]["features"]["PreFixDamageAttack"])
	{
		if(!fightInfo[attackerRole]["heroPanel"]["features"]["ImmuneToFixedDamage"])
		{
			var list = fightInfo[attackerRole]["heroPanel"]["features"]["PreFixDamageAttack"];
			for(var i in list)
			{
				var damage = Math.ceil(list[i]*fightInfo[attackerRole]["heroPanel"]["attack"]);
				asl  -= damage;
				al -= damage;
			}
		}
	}
	
	if(fightInfo[attackerRole]["soldierPanel"]["features"]["FixDamageToSelf"])
	{
		if(!fightInfo[attackerRole]["heroPanel"]["features"]["ImmuneToFixedDamage"])
		{
			var list = fightInfo[attackerRole]["soldierPanel"]["features"]["FixDamageToSelf"];
			for(var i in list)
			{
				var damage = Math.ceil(asl*list[i]/100.0);
				fightDetails+="<p>"+fightInfo[attackerRole]["soldier"]["name"]+"自损"+damage+"</p>";
				asl = asl - damage;
			}
		} else
		{
			fightDetails+="<p>"+fightInfo[defenderRole]["hero"]["name"]+"免疫自损</p>";
		}
	}
	
	if(fightInfo[defenderRole]["soldierPanel"]["features"]["FixDamageToSelf"])
	{
		if(!fightInfo[defenderRole]["heroPanel"]["features"]["ImmuneToFixedDamage"])
		{
			var list = fightInfo[defenderRole]["soldierPanel"]["features"]["FixDamageToSelf"];
			for(var i in list)
			{
				var damage = Math.ceil(dsl*list[i]/100.0);
				dsl = dsl - damage;
			}
		}
	}
	
	if(fightInfo[attackerRole]["soldierPanel"]["features"]["UnImuFixDamageToSelf"])
	{
		var list = fightInfo[attackerRole]["soldierPanel"]["features"]["UnImuFixDamageToSelf"];
		for(var i in list)
		{
			var damage = Math.ceil(asl*list[i]/100.0);
			fightDetails+="<p>"+fightInfo[attackerRole]["soldier"]["name"]+"自损"+damage+"</p>";
			asl = asl - damage;
		}
	}
	
	if(fightInfo[defenderRole]["soldierPanel"]["features"]["UnImuFixDamageToSelf"])
	{
		var list = fightInfo[defenderRole]["soldierPanel"]["features"]["UnImuFixDamageToSelf"];
		for(var i in list)
		{
			var damage = Math.ceil(dsl*list[i]/100.0);
			dsl = dsl - damage;
		}
	}
	
	var soldierCount = 2*Math.ceil(asl*10 / fightInfo[attackerRole]["soldierPanel"]["life"]);
	//alert("asl===="+asl+",soldierCount=="+soldierCount+",life=="+fightInfo[attackerRole]["soldierPanel"]["life"]);
	var heroCount = al > 0  ? 20 : 0;
	
	if(getRange(attackerRole, "soldier")< distance)
	{
		soldierCount = 0;
		fightDetails+="<p>"+fightInfo[attackerRole]["soldier"]["name"]+"射程不够，无法出手</p>";
	}
	if(getRange(attackerRole, "hero")< distance)
	{
		heroCount = 0;
		fightDetails+="<p>"+fightInfo[attackerRole]["hero"]["name"]+"射程不够，无法出手</p>";
	}
	
	var soldierDirect = fightInfo[attackerRole]["soldierPanel"]["features"]["DirectToHero"];
	var heroDirect = fightInfo[attackerRole]["heroPanel"]["features"]["DirectToHero"];
	
	if(heroDirect && !soldierDirect)
	{
		soldierCount = 0;
		fightDetails+="<p>英雄直击，"+fightInfo[attackerRole]["soldier"]["name"]+"不出手</p>";
	}
	var soldierLeftCount = soldierCount;
	var heroLeftCount = heroCount;
	var fl = dl;
	var fsl = dsl;
	if(!fastAttack)
	{
		fastAttack = 0;
	}
	var fastCount = fastAttack;
	
	if(fightInfo[defenderRole]["soldier"]) 
	{
		var oneSoldierLife = fightInfo[defenderRole]["soldierPanel"]["life"] / 10;

		if(dsl > 0)
		{
			// hero to soldier
			if(fastCount > 0 && !heroDirect)
			{
				var hit = Math.ceil(oneSoldierLife / heroToSoldier);		
				var heroKillSoldier = 0;
				
				while(fastCount >= hit &&  dsl >= oneSoldierLife)  
				{
					dsl -= oneSoldierLife;
					heroKillSoldier++;
					fastCount -= hit;
					heroLeftCount -= hit;
					//fightDetails+="<p>dsl=="+dsl+"   heroKillSoldier=="+heroKillSoldier+"</p>";
				}
				
				if(fastCount > Math.ceil(dsl/heroToSoldier))
				{
					if(dsl > 0)
					{
						fastCount -= Math.ceil(dsl/heroToSoldier);
						heroLeftCount -= Math.ceil(dsl/heroToSoldier);
						heroKillSoldier++;
						dsl =0;
					}
					var c = attackerHeroCriticalChecked?" class=\"critical\"":"";
					fightDetails+="<p"+c+">"+fightInfo[attackerRole]["hero"]["name"]+"出手较快，用 <b>"+(heroCount-heroLeftCount)+
						"</b> hit 干掉 <b>"+heroKillSoldier+"</b>"+fightInfo[defenderRole]["soldier"]["name"]+"</p>";
				} 
				else if (fastAttack > 0)
				{
					dsl -= heroToSoldier * fastCount;
					heroLeftCount -= fastCount;
					fastCount = 0;
					var c = attackerHeroCriticalChecked?" class=\"critical\"":"";
					fightDetails+="<p"+c+">"+fightInfo[attackerRole]["hero"]["name"]+"出手较快，用 <b>"+fastAttack+"</b> hit 干掉 <b>"+heroKillSoldier+"</b>"+fightInfo[defenderRole]["soldier"]["name"]+"</p>";
				}
				fsl = dsl;
			}
		}
		
		if(dsl > 0 && !soldierDirect)
		{
			var hit = Math.ceil(oneSoldierLife / soldierToSoldier);
			var soldierKillSoldier = 0;
			
			while(soldierLeftCount >= hit &&  dsl >= oneSoldierLife)  
			{
				dsl -= oneSoldierLife;
				soldierKillSoldier++;
				soldierLeftCount -= hit;
				//fightDetails+="<p>dsl=="+dsl+"   soldierKillSoldier=="+soldierKillSoldier+"</p>";
			}

			if(soldierLeftCount > Math.ceil(dsl/soldierToSoldier))
			{
				if(dsl > 0)
				{
					soldierLeftCount -= Math.ceil(dsl/soldierToSoldier);
					soldierKillSoldier++;
					dsl = 0;
				}
				var c = attackerSoldierCriticalChecked?" class=\"critical\"":"";
				fightDetails+="<p"+c+">"+fightInfo[attackerRole]["soldier"]["name"]+"用 <b>"+(soldierCount-soldierLeftCount)+
					"</b> hit 干掉 <b>"+soldierKillSoldier+"</b> "+fightInfo[defenderRole]["soldier"]["name"]+"</p>";	
			
			} else if(soldierCount > 0 )  
			{
				dsl -= soldierToSoldier * soldierLeftCount;
				soldierLeftCount = 0;
				var c = attackerSoldierCriticalChecked?" class=\"critical\"":"";
				fightDetails+="<p"+c+">"+fightInfo[attackerRole]["soldier"]["name"]+" 用 <b>"+soldierCount+
					"</b> hit 干掉 <b>"+soldierKillSoldier+"</b>"+fightInfo[defenderRole]["soldier"]["name"]+"</p>";
			}
		}
		
		if(dsl > 0 && !heroDirect)
		{
			// hero to soldier
			if(heroLeftCount > 0 )
			{
				var hit = Math.ceil(oneSoldierLife / heroToSoldier);		
				var heroKillSoldier = 0;
				
				while(heroLeftCount >= hit &&  dsl >= oneSoldierLife)  
				{
					dsl -= oneSoldierLife;
					heroKillSoldier++;
					heroLeftCount -= hit;
					//fightDetails+="<p>dsl=="+dsl+"   heroKillSoldier=="+heroKillSoldier+"</p>";
				}
				
				if(heroLeftCount > Math.ceil(dsl/heroToSoldier))
				{
					if(dsl > 0)
					{
						heroLeftCount -= Math.ceil(dsl/heroToSoldier);
						heroKillSoldier++;
						dsl =0;
					}
					var c = attackerHeroCriticalChecked?" class=\"critical\"":"";
					fightDetails+="<p"+c+">"+fightInfo[attackerRole]["hero"]["name"]+"用 <b>"+(heroCount-heroLeftCount-fastAttack)+
						"</b> hit 干掉 <b>"+heroKillSoldier+"</b>"+fightInfo[defenderRole]["soldier"]["name"]+"</p>";
				} 
				else if (heroCount > 0)
				{
					dsl -= heroToSoldier * heroLeftCount;
					heroLeftCount = 0;
					var c = attackerHeroCriticalChecked?" class=\"critical\"":"";
					fightDetails+="<p"+c+">"+fightInfo[attackerRole]["hero"]["name"]+"用 <b>"+(heroCount-fastAttack)+"</b> hit 干掉 <b>"+heroKillSoldier+"</b>"+fightInfo[defenderRole]["soldier"]["name"]+"</p>";
				}
			}
		}
	}
   
	if(dl > 0 )
	{
		if(fastCount > 0 )
		{
			var htohDamage = heroToHero* fastCount;
			if(dl > htohDamage ) 
			{
				dl -= htohDamage;
			} else
			{
				dl = 0;
			}

			var c = attackerHeroCriticalChecked?" class=\"critical\"":"";
			fightDetails+="<p"+c+">"+fightInfo[attackerRole]["hero"]["name"]+"出手较快，用 <b>"+fastCount+"</b> hit 对"+fightInfo[defenderRole]["hero"]["name"]+"造成<b>"+htohDamage+"</b>伤害 </p>";
			heroLeftCount-=fastCount;
			fastCount = 0;
			fl = dl;
		}
		if(soldierLeftCount >0)
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
	
	return {"dsl":dsl, "dl":dl, "fl":fl, "fsl":fsl, "fightDetails":fightDetails };
}


function aoe(coefficient, attackerHeroCriticalChecked)
{
	var fightDetails = "";
	var attackerRole = "attacker";
	var defenderRole = "defender";
	var isAttackerPhysic = fightInfo[attackerRole]["action"]["isPhysic"];
	var actionName =  fightInfo[attackerRole]["action"]["name"];
	if(fightInfo[attackerRole]["hero"] && fightInfo[defenderRole]["soldier"])
	{
		var counter = getCounter(attackerRole, "hero", "soldier");
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
	
	var dl = fightInfo[defenderRole]["hero"] ? fightInfo[defenderRole]["heroLeftLife"] : 0;
	var dsl = fightInfo[defenderRole]["soldier"] ? fightInfo[defenderRole]["soldierLeftLife"] : 0;
	
	dl -= 20*heroToHero;
	dsl -= 20*heroToSoldier;
	
	var c = attackerHeroCriticalChecked?" class=\"critical\"":"";
	fightDetails+="<p"+c+">"+fightInfo["attacker"]["hero"]["name"]+"用"+actionName+"对"+fightInfo["defender"]["hero"]["name"]+"造成<b>"+20*heroToHero+"</b>伤害</p>";
	fightDetails+="<p"+c+">"+fightInfo["attacker"]["hero"]["name"]+"用"+actionName+"对"+fightInfo["defender"]["soldier"]["name"]+"造成<b>"+20*heroToSoldier+"</b>伤害</p>";

	if(dsl < 0)
	{
		dsl = 0;
	}
	if(dl <= 0)
	{
		dl = 0;
		fightDetails+="<p>"+fightInfo[defenderRole]["hero"]["name"]+"死亡</p>";
	}
	return  {"dsl":dsl, "dl":dl, "fightDetails":fightDetails };
	
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

