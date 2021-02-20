
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
		soldierToSoldier =oneHit(1, fightInfo["attacker"]["soldirPanel"], fightInfo["defender"]["soldirPanel"], 
				attackerSoldierCriticalChecked, fightInfo["attacker"]["soldier"]["isPhysics"] );
		var c = (attackerSoldierCriticalChecked? "(暴击":"(未暴击")+"&nbsp;&nbsp;&nbsp;,&nbsp;&nbsp;&nbsp;概率："+
				criticalProb(fightInfo["attacker"]["soldirPanel"],fightInfo["defender"]["soldirPanel"])+")";
														         
        fightDetails+="<p>"+fightInfo["attacker"]["soldier"]["name"]+"攻击"+fightInfo["defender"]["soldier"]["name"]+"1hit:<b>"+soldierToSoldier+"</b>"+c+"</p>";
	}
	
	if(fightInfo["attacker"]["soldier"] && fightInfo["defender"]["hero"])
	{
		soldierToHero = oneHit(1, fightInfo["attacker"]["soldirPanel"], fightInfo["defender"]["heroPanel"], 
				attackerSoldierCriticalChecked, fightInfo["attacker"]["soldier"]["isPhysics"] );
		var c = (attackerSoldierCriticalChecked? "(暴击":"(未暴击")+"&nbsp;&nbsp;&nbsp;,&nbsp;&nbsp;&nbsp;概率："+
			criticalProb(fightInfo["attacker"]["soldirPanel"],fightInfo["defender"]["heroPanel"])+")";
		fightDetails+="<p>"+fightInfo["attacker"]["soldier"]["name"]+"攻击"+fightInfo["defender"]["hero"]["name"]+"1hit:<b>"+soldierToHero+"</b>"+c+"</p>";
	}
	
	if(fightInfo["attacker"]["hero"] && fightInfo["defender"]["soldier"])
	{
		heroToSoldier = oneHit(1, fightInfo["attacker"]["heroPanel"], fightInfo["defender"]["soldierPanel"], 
				attackerHeroCriticalChecked, fightInfo["attacker"]["hero"]["isPhysics"] );
		var c = (attackerHeroCriticalChecked? "(暴击":"(未暴击")+"&nbsp;&nbsp;&nbsp;,&nbsp;&nbsp;&nbsp;概率："+
			criticalProb(fightInfo["attacker"]["heroPanel"], fightInfo["defender"]["soldierPanel"])+")";
	    fightDetails+="<p>"+fightInfo["attacker"]["hero"]["name"]+"攻击"+fightInfo["defender"]["soldier"]["name"]+"1hit:<b>"+heroToSoldier+"</b>"+c+"</p>";
	}

	if(fightInfo["attacker"]["hero"] && fightInfo["defender"]["hero"])
	{
		heroToHero = oneHit(1, fightInfo["attacker"]["heroPanel"], fightInfo["defender"]["heroPanel"], 
				attackerHeroCriticalChecked, fightInfo["attacker"]["hero"]["isPhysics"] );
		var c = (attackerHeroCriticalChecked? "(暴击":"(未暴击")+"&nbsp;&nbsp;&nbsp;,&nbsp;&nbsp;&nbsp;概率："+
			criticalProb(fightInfo["attacker"]["heroPanel"], fightInfo["defender"]["heroPanel"])+")";
	    fightDetails+="<p>"+fightInfo["attacker"]["hero"]["name"]+"攻击"+fightInfo["defender"]["hero"]["name"]+"1hit:<b>"+heroToHero+"</b>"+c+"</p>";
	}
	fightDetails+="<p>-------------------------------------------------------------------------------------------</p>";
	var soldierCount = fightInfo["attacker"]["soldier"] ? 20 : 0;
	var heroCount = fightInfo["attacker"]["hero"] ? 20 : 0;
	var soldierLeftCount = soldierCount;
	var heroLeftCount = heroCount;
	var dl = fightInfo["defender"]["hero"] ? fightInfo["defenderHeroLeftLife"] : 0;
	var dsl = fightInfo["defender"]["soldier"] ? fightInfo["defenderSoldierLeftLife"] : 0;
	var direct = fightInfo["attacker"]["action"] ? fightInfo["attacker"]["action"]["direct"] : 0;
	
	if(fightInfo["attacker"]["heroPanel"]["preBattleDamage"] > 0)
	{
		var preDamage = ightInfo["attacker"]["heroPanel"]["preBattleDamage"]*ightInfo["attacker"]["heroPanel"]["attack"];
		if(fightInfo["defender"]["hero"])
		{
			if(checkHeroEffect("ImmuneToFixedDamage", "defender"))
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
		
		var oneSoldierLife = fightInfo["defenderSoldierLife"] / 10;
		if(preDamage)
		{
			if(fightInfo["defender"]["heroPanel"]["features"]["ImmuneToFixedDamage"])
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
				fightDetails+="<p>dsl=="+dsl+"   soldierKillSoldier=="+soldierKillSoldier+"</p>";
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
						fightDetails+="<p>dsl=="+dsl+"   heroKillSoldier=="+heroKillSoldier+"</p>";
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
										
	$("#fightDetails").html(fightDetails);
}

function oneHit(coefficient, panel1, panel2, critcal, isPhysics)
{
	if(!panel1 || !panel2)
	{
		return 0;
	}
	var c = critcal ? (130 + panel1["criticalInc"] - panel1["criticalDec"]) / 100 : 1;
	if(isPhysics)
	{
		return Math.floor(coefficient * (panel1["attack"]-panel2["physics"])*(1+(panel1["damageInc"]-panel2["physicDamageInc"])/100.0)*c /2) ;
	} else	
	{
		return Math.floor(coefficient * (panel1["intel"]-panel2["magic"])*(1+(panel1["damageInc"]-panel2["magicDamageInc"])/100.0)*c /2) ;
	}
}

function criticalProb(panel1, panel2)
{
	if(!panel1 || !panel2)
	{
		return 0;
	}
	var prob = panel1["criticalProbInc"]+panel1["tech"]/10;
	if(prob > 100)
	{
		prob = 100;
	}
	return Math.floor(prob * (100-panel2["criticalProbDec"])/100)+"%";
}
