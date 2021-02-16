function calculatePanel()
{
	if(attacker && attackerEquip)
	{
		var attack = attacker["attack"] + attackerEquip["attackInc"];
		var intel = attacker["intel"] + attackerEquip["intelInc"];
		var physicDef = attacker["physicDef"] + attackerEquip["physicDefInc"];
		var magicDef = attacker["magicDef"] + attackerEquip["magicDefInc"];
		var life = attacker["life"] + attackerEquip["lifeInc"];
		var tech = attacker["tech"] + attackerEquip["techInc"];
		
		var ai  = attackerEquip["attackSkill"];
		var ii  = attackerEquip["intelSkill"];
		var pi  = attackerEquip["physicDefSkill"];
		var mi  = attackerEquip["magicDefSkill"];
		var lii  = attackerEquip["lifeSkill"];
		var ti = attackerEquip["techSkill"];
		
		var di = 0;
		var pdd = 0;
		var mdd = 0;
		var counters = [];
		var pd_counters = [];
		
		var cpi = attackerEquip["criticalProbInc"];
		var cdi = attackerEquip["criticalDamageInc"];
		var cpd = attackerEquip["criticalProbDec"];
		var cdd = attackerEquip["criticalDamageDec"];
		
		if(attackerSkills)
		{
			var attackBasic = 0;
			var intelBasic = 0;
			var physicDefBasic = 0;
			var magicDefBasic = 0;
			var lifeBasic = 0;
			var techBasic = 0;
			var damageIncBasic = 0;
			var damageDecBasic = 0;
			
			for(var i=0; i<attackerSkills.length; i++)
			{
				if(attackerSkills[i]["valid"] && (attackerSkills[i]["skill"]["scope"]=="All" || attackerSkills[i]["skill"]["scope"]=="Hero" ))
				{
					var buffs = attackerSkills[i]["skill"]["buffs"];
					for(var j=0; j<buffs.length; j++)
					{
						if(buffs[j]["buffType"]=="Attack")
						{
							if(buffs[j]["basic"])
							{
								attackBasic = Math.max(attackBasic, buffs[j]["number"]);
							}else
							{
								ai = ai+buffs[j]["number"];
							}
						}
						if(buffs[j]["buffType"]=="Intel")
						{
							if(buffs[j]["basic"])
							{
								intelBasic = Math.max(intelBasic, buffs[j]["number"]);
							}else
							{
								ii = ii+buffs[j]["number"];
							}
						}
						if(buffs[j]["buffType"]=="PhysicDef")
						{
							if(buffs[j]["basic"])
							{
								physicDefBasic = Math.max(physicDefBasic, buffs[j]["number"]);
							}else
							{
								pi = pi+buffs[j]["number"];
							}
						}
						if(buffs[j]["buffType"]=="MagicDef")
						{
							if(buffs[j]["basic"])
							{
								magicDefBasic = Math.max(magicDefBasic, buffs[j]["number"]);
							}else
							{
								mi = mi+buffs[j]["number"];
							}
						}
						
						if(buffs[j]["buffType"]=="Life")
						{
							if(buffs[j]["basic"])
							{
								lifeBasic = Math.max(lifeBasic, buffs[j]["number"]);
							}else
							{
								lii = lii+buffs[j]["number"];
							}
						}
						if(buffs[j]["buffType"]=="Tech")
						{
							if(buffs[j]["basic"])
							{
								techBasic = Math.max(techBasic, buffs[j]["number"]);
							}else
							{
								ti = ti+buffs[j]["number"];
							}
						}
						if(buffs[j]["buffType"]=="DamageInc")
						{
							if(buffs[j]["basic"])
							{
								damageIncBasic = Math.max(damageIncBasic, buffs[j]["number"]);
							}else
							{
								di = di+buffs[j]["number"];
							}
						}
						if(buffs[j]["buffType"]=="DamageDec")
						{
							if(buffs[j]["basic"])
							{
								damageDecBasic = Math.max(damageDncBasic, buffs[j]["number"]);
							}else
							{
								pdd = pdd+buffs[j]["number"];
								mdd = mdd+buffs[j]["number"];
							}
						}
						
						if(buffs[j]["buffType"]=="PhysicDamageDec")
						{
							pdd = pdd+buffs[j]["number"];
						}
						if(buffs[j]["buffType"]=="MagicDamageDec")
						{
							mdd = mdd+buffs[j]["number"];
						}
						
						if(buffs[j]["buffType"]=="AttackCounter")
						{
							counters.push(buffs[j]["number"]);
						}
						
						if(buffs[j]["buffType"]=="PhysicDefCounter")
						{
							pd_counters.push(buffs[j]["number"]);
						}						
						
						if(buffs[j]["buffType"]=="CriticalProbInc")
						{
							cpi = cpi+buffs[j]["number"];
						}
						if(buffs[j]["buffType"]=="CriticalProbDec")
						{
							cpd = cpd+buffs[j]["number"];
						}
						if(buffs[j]["buffType"]=="CriticalDamageInc")
						{
							cdi = cdi+buffs[j]["number"];
						}
						if(buffs[j]["buffType"]=="CriticalDamageDec")
						{
							cdd = cdd+buffs[j]["number"];
						}
					}
				}
			}
			ai = ai+attackBasic;
			ii = ii+intelBasic;
			pi = pi+physicDefBasic;
			if(fightInfo["attackerLand"]=="Water" && attacker["type"]==4)
			{
				pi=pi+30;  
			}
			mi = mi+magicDefBasic;
			lii=lii+lifeBasic+40;  //jjc inc =0.4
			di = di+damageIncBasic;
			pdd = pdd+damageDecBasic;
			mdd = mdd+damageDecBasic;
			ti = ti+techBasic;

			fightInfo["attackerAttack"] = Math.floor(attack*(1+ai/100.0)+attackerEquip["attackJJC"]);
			fightInfo["attackerIntel"] =  Math.floor(intel*(1+ii/100.0)+attackerEquip["intelJJC"]);
			fightInfo["attackerPhysicDef"] = Math.floor(physicDef*(1+pi/100.0)+attackerEquip["physicDefJJC"]);
			fightInfo["attackerMagicDef"] = Math.floor(magicDef*(1+mi/100.0)+attackerEquip["magicDefJJC"]);
			fightInfo["attackerLife"] = Math.floor(life*(1+lii/100.0)+attackerEquip["lifeJJC"]);
			fightInfo["attackerTech"] = Math.floor(tech*(1+ti/100.0)+attackerEquip["techJJC"]);
			fightInfo["attackerDamageInc"] = di;
			fightInfo["attackerPhysicDamageDec"] = pdd;
			fightInfo["attackerMagicDamageDec"] = mdd;		
			
			fightInfo["attackerCriticalProbInc"] = cpi;	
			fightInfo["attackerCriticalProbDec"] = cpd;	
			fightInfo["attackerCriticalDamageInc"] = cdi;	
			fightInfo["attackerCriticalDamageDec"] = cdd;	
						
			for(var i=0; i<counters.length; i++)
			{
				if(attacker["isPhysic"]) 
				{
					fightInfo["attackerAttack"] = Math.floor(fightInfo["attackerAttack"]*(1+counters[i]/100));
				} else 
				{
					fightInfo["attackerIntel"] = Math.floor(fightInfo["attackerIntel"]*(1+counters[i]/100));
				}
			}
			
			for(var i=0; i<pd_counters.length; i++)
			{
				fightInfo["attackerPhysicDef"] = Math.floor(fightInfo["attackerPhysicDef"]*(1+pd_counters[i]/100));
			}
			

			fightInfo["attackerPhysicDef"]=fightInfo["attackerPhysicDef"];
			
			$("#attackerAttack").attr("value", fightInfo["attackerAttack"]);
			$("#attackerIntel").attr("value", fightInfo["attackerIntel"]);
			$("#attackerPhysicDef").attr("value", fightInfo["attackerPhysicDef"]);
			$("#attackerMagicDef").attr("value", fightInfo["attackerMagicDef"]);
			$("#attackerLife").attr("value", fightInfo["attackerLife"]);
			
			var detail="<p>";
			if(attackerAction)
			{
				detail+="<b>"+attackerAction["name"]+"</b>&nbsp;&nbsp;&nbsp;<b>"+attackerAction["coefficient"]+"倍</b></p>";
			}
			
			detail+="<p>增伤："+fightInfo["attackerDamageInc"]+
			"&nbsp;&nbsp;&nbsp;物理减伤："+fightInfo["attackerPhysicDamageDec"]+
			"&nbsp;&nbsp;&nbsp;魔法减伤："+fightInfo["attackerMagicDamageDec"]+
			"&nbsp;&nbsp;&nbsp;技巧："+fightInfo["attackerTech"]+"</p><p>" + 
			"暴击："+fightInfo["attackerCriticalProbInc"]+
			"&nbsp;&nbsp;&nbsp;暴伤："+fightInfo["attackerCriticalDamageInc"]+
			"&nbsp;&nbsp;&nbsp;防爆:"+fightInfo["attackerCriticalProbDec"]+
			"&nbsp;&nbsp;&nbsp;减爆伤："+fightInfo["attackerCriticalDamageDec"]+"</p>";
			$("#attackerDetail").html(detail);
		}
		
	}
	
	
	if(defender && defenderEquip)
	{
		var attack = defender["attack"] + defenderEquip["attackInc"];
		var intel = defender["intel"] + defenderEquip["intelInc"];
		var physicDef = defender["physicDef"] + defenderEquip["physicDefInc"];
		var magicDef = defender["magicDef"] + defenderEquip["magicDefInc"];
		var life = defender["life"] + defenderEquip["lifeInc"];
		var tech = defender["tech"] + defenderEquip["techInc"];
		
		var ai  = defenderEquip["attackSkill"];
		var ii  = defenderEquip["intelSkill"];
		var pi  = defenderEquip["physicDefSkill"];
		var mi  = defenderEquip["magicDefSkill"];
		var lii  = defenderEquip["lifeSkill"];
		var ti = defenderEquip["techSkill"];
		
		var di = 0;
		var pdd = 0;
		var mdd = 0;
		var counters = [];
		var pd_counters = [];
		
		var cpi = defenderEquip["criticalProbInc"];
		var cdi = defenderEquip["criticalDamageInc"];
		var cpd = defenderEquip["criticalProbDec"];
		var cdd = defenderEquip["criticalDamageDec"];
		
		if(defenderSkills)
		{
			var attackBasic = 0;
			var intelBasic = 0;
			var physicDefBasic = 0;
			var magicDefBasic = 0;
			var lifeBasic = 0;
			var techBasic = 0;
			var damageIncBasic = 0;
			var damageDecBasic = 0;
									
			for(var i=0; i<defenderSkills.length; i++)
			{
				if(defenderSkills[i]["valid"]  && (defenderSkills[i]["skill"]["scope"]=="All" || defenderSkills[i]["skill"]["scope"]=="Hero" ))
				{
					var buffs = defenderSkills[i]["skill"]["buffs"];
					for(var j=0; j<buffs.length; j++)
					{
						if(buffs[j]["buffType"]=="Attack")
						{
							if(buffs[j]["basic"])
							{
								attackBasic = Math.max(attackBasic, buffs[j]["number"]);
							}else
							{
								ai = ai+buffs[j]["number"];
							}
						}
						
						if(buffs[j]["buffType"]=="Intel")
						{
							if(buffs[j]["basic"])
							{
								intelBasic = Math.max(intelBasic, buffs[j]["number"]);
							}else
							{
								ii = ii+buffs[j]["number"];
							}
						}
						
						if(buffs[j]["buffType"]=="PhysicDef")
						{
							if(buffs[j]["basic"])
							{
								physicDefBasic = Math.max(physicDefBasic, buffs[j]["number"]);
							}else
							{
								pi = pi+buffs[j]["number"];
							}
						}
						
						if(buffs[j]["buffType"]=="MagicDef")
						{
							if(buffs[j]["basic"])
							{
								magicDefBasic = Math.max(magicDefBasic, buffs[j]["number"]);
							}else
							{
								mi = mi+buffs[j]["number"];
							}
						}
						
						if(buffs[j]["buffType"]=="Life")
						{
							if(buffs[j]["basic"])
							{
								lifeBasic = Math.max(lifeBasic, buffs[j]["number"]);
							}else
							{
								lii = lii+buffs[j]["number"];
							}
						}
						if(buffs[j]["buffType"]=="Tech")
						{
							if(buffs[j]["basic"])
							{
								techBasic = Math.max(techBasic, buffs[j]["number"]);
							}else
							{
								ti = ti+buffs[j]["number"];
							}
						}
						if(buffs[j]["buffType"]=="DamageInc")
						{
							if(buffs[j]["basic"])
							{
								damageIncBasic = Math.max(damageIncBasic, buffs[j]["number"]);
							}else
							{
								di = di+buffs[j]["number"];
							}
						}
						
						if(buffs[j]["buffType"]=="DamageDec")
						{
							if(buffs[j]["basic"])
							{
								damageDecBasic = Math.max(damageDncBasic, buffs[j]["number"]);
							}else
							{
								pdd = pdd+buffs[j]["number"];
								mdd = mdd+buffs[j]["number"];
							}
						}
						if(buffs[j]["buffType"]=="PhysicDamageDec")
						{
							pdd = pdd+buffs[j]["number"];
						}
						if(buffs[j]["buffType"]=="MagicDamageDec")
						{
							mdd = mdd+buffs[j]["number"];
						}
						
						
						if(buffs[j]["buffType"]=="AttackCounter")
						{
							counters.push(buffs[j]["number"]);
						}
						
						if(buffs[j]["buffType"]=="PhysicDefCounter")
						{
							pd_counters.push(buffs[j]["number"]);
						}
						
						if(buffs[j]["buffType"]=="CriticalProbInc")
						{
							cpi = cpi+buffs[j]["number"];
						}
						if(buffs[j]["buffType"]=="CriticalProbDec")
						{
							cpd = cpd+buffs[j]["number"];
						}
						if(buffs[j]["buffType"]=="CriticalDamageInc")
						{
							cdi = cdi+buffs[j]["number"];
						}
						if(buffs[j]["buffType"]=="CriticalDamageDec")
						{
							cdd = cdd+buffs[j]["number"];
						}
					}
				}
			}
			ai = ai+attackBasic;
			ii = ii+intelBasic;
			pi = pi+physicDefBasic;
			if(fightInfo["defenderLand"]=="Water" && defender["type"]==4)
			{
				pi=pi+30;  
			}
			
			mi = mi+magicDefBasic;
			lii=lii+lifeBasic+40;  //jjc inc =0.4
			di = di+damageIncBasic;
			pdd = pdd+damageDecBasic;
			mdd = mdd+damageDecBasic;
			ti = ti+techBasic;
			
			fightInfo["defenderAttack"] = Math.floor(attack*(1+ai/100.0)+defenderEquip["attackJJC"]);
			fightInfo["defenderIntel"] = Math.floor(intel*(1+ii/100.0)+defenderEquip["intelJJC"]);
			fightInfo["defenderPhysicDef"] = Math.floor(physicDef*(1+pi/100.0)+defenderEquip["physicDefJJC"]);
			fightInfo["defenderMagicDef"] = Math.floor(magicDef*(1+mi/100.0)+defenderEquip["magicDefJJC"]);
			fightInfo["defenderLife"] = Math.floor(life*(1+lii/100.0)+defenderEquip["lifeJJC"]);
			fightInfo["defenderTech"] = Math.floor(tech*(1+ti/100.0)+defenderEquip["techJJC"]);
			fightInfo["defenderDamageInc"] = di;
			fightInfo["defenderPhysicDamageDec"] = pdd;
			fightInfo["defenderMagicDamageDec"] = mdd;
			fightInfo["defenderCriticalProbInc"] = cpi;	
			fightInfo["defenderCriticalProbDec"] = cpd;	
			fightInfo["defenderCriticalDamageInc"] = cdi;	
			fightInfo["defenderCriticalDamageDec"] = cdd;
			
			for(var i=0; i<counters.length; i++)
			{
				if(defender["isPhysic"]) 
				{
					fightInfo["defenderAttack"] = Math.floor(fightInfo["defenderAttack"]*(1+counters[i]/100));
				} else 
				{
					fightInfo["defenderIntel"] = Math.floor(fightInfo["defenderIntel"]*(1+counters[i]/100));
				}
			}
			for(var i=0; i<pd_counters.length; i++)
			{
				fightInfo["defenderPhysicDef"] = Math.floor(fightInfo["defenderPhysicDef"]*(1+pd_counters[i]/100));
			}
			
			$("#defenderAttack").attr("value", fightInfo["defenderAttack"]);
			$("#defenderIntel").attr("value", fightInfo["defenderIntel"]);
			$("#defenderPhysicDef").attr("value", fightInfo["defenderPhysicDef"]);
			$("#defenderMagicDef").attr("value", fightInfo["defenderMagicDef"]);
			$("#defenderLife").attr("value", fightInfo["defenderLife"]);
						
			$("#defenderDetail").html("<p>增伤："+fightInfo["defenderDamageInc"]+
					"&nbsp;&nbsp;&nbsp;物理减伤："+fightInfo["defenderPhysicDamageDec"]+
					"&nbsp;&nbsp;&nbsp;魔法减伤："+fightInfo["defenderMagicDamageDec"]+
					"&nbsp;&nbsp;&nbsp;技巧："+fightInfo["defenderTech"]+"</p><p>" +
					"暴击："+fightInfo["defenderCriticalProbInc"]+
					"&nbsp;&nbsp;&nbsp;暴伤："+fightInfo["defenderCriticalDamageInc"]+
					"&nbsp;&nbsp;&nbsp;防爆："+fightInfo["defenderCriticalProbDec"]+
					"&nbsp;&nbsp;&nbsp;减爆伤："+fightInfo["defenderCriticalDamageDec"]+"</p>");
		}
		
	}
	
	if(attackerSoldier && attacker) 
	{
		var attack = Math.ceil(attackerSoldier["attack"]*(1+attacker["soldierAttackInc"]/100));
		var physicDef = Math.ceil(attackerSoldier["physicDef"]*(1+attacker["soldierPhysicDefInc"]/100));
		var magicDef = Math.ceil(attackerSoldier["magicDef"]*(1+attacker["soldierMagicDefInc"]/100));
		var life = Math.ceil(attackerSoldier["life"]*(1+attacker["soldierLifeInc"]/100));
		
		var ai  = 0;
		var pi  = 0;
		var mi  = 0;
		var lii = 0;
		var di = 0;
		var pdd = 0;
		var mdd = 0;
		var counters = [];
		var pd_counters = [];
		var cpi = 0;
		var cdi = 0;
		var cpd = 0;
		var cdd = 0;
		
		if(attackerSkills)
		{
			var attackBasic = 0;
			var intelBasic = 0;
			var physicDefBasic = 0;
			var magicDefBasic = 0;
			var lifeBasic = 0;
			var damageIncBasic = 0;
			var damageDecBasic = 0;
			
			for(var i=0; i<attackerSkills.length; i++)
			{
				if(attackerSkills[i]["valid"] && (attackerSkills[i]["skill"]["scope"]=="All" || attackerSkills[i]["skill"]["scope"]=="Soldier" ))
				{
					var buffs = attackerSkills[i]["skill"]["buffs"];
					for(var j=0; j<buffs.length; j++)
					{
						if(buffs[j]["buffType"]=="Attack")
						{
							if(buffs[j]["basic"])
							{
								attackBasic = Math.max(attackBasic, buffs[j]["number"]);
							}else
							{
								ai = ai+buffs[j]["number"];
							}
						}
						
						if(buffs[j]["buffType"]=="PhysicDef")
						{
							if(buffs[j]["basic"])
							{
								physicDefBasic = Math.max(physicDefBasic, buffs[j]["number"]);
							}else
							{
								pi = pi+buffs[j]["number"];
							}
						}
						
						if(buffs[j]["buffType"]=="MagicDef")
						{
							if(buffs[j]["basic"])
							{
								magicDefBasic = Math.max(magicDefBasic, buffs[j]["number"]);
							}else
							{
								mi = mi+buffs[j]["number"];
							}
						}
						
						if(buffs[j]["buffType"]=="Life")
						{
							if(buffs[j]["basic"])
							{
								lifeBasic = Math.max(lifeBasic, buffs[j]["number"]);
							}else
							{
								lii = lii+buffs[j]["number"];
							}
						}
						if(buffs[j]["buffType"]=="DamageInc")
						{
							if(buffs[j]["basic"])
							{
								damageIncBasic = Math.max(damageIncBasic, buffs[j]["number"]);
							}else
							{
								di = di+buffs[j]["number"];
							}
						}
						
						if(buffs[j]["buffType"]=="DamageDec")
						{
							if(buffs[j]["basic"])
							{
								damageDecBasic = Math.max(damageDncBasic, buffs[j]["number"]);
							}else
							{
								pdd = pdd+buffs[j]["number"];
								mdd = mdd+buffs[j]["number"];
							}
						}
						if(buffs[j]["buffType"]=="PhysicDamageDec")
						{
							pdd = pdd+buffs[j]["number"];
						}
						if(buffs[j]["buffType"]=="MagicDamageDec")
						{
							mdd = mdd+buffs[j]["number"];
						}
						
						if(buffs[j]["buffType"]=="AttackCounter")
						{
							counters.push(buffs[j]["number"]);
						}
						
						if(buffs[j]["buffType"]=="PhysicDefCounter")
						{
							pd_counters.push(buffs[j]["number"]);
						}
						if(buffs[j]["buffType"]=="CriticalProbInc")
						{
							cpi = cpi+buffs[j]["number"];
						}
						if(buffs[j]["buffType"]=="CriticalProbDec")
						{
							cpd = cpd+buffs[j]["number"];
						}
						if(buffs[j]["buffType"]=="CriticalDamageInc")
						{
							cdi = cdi+buffs[j]["number"];
						}
						if(buffs[j]["buffType"]=="CriticalDamageDec")
						{
							cdd = cdd+buffs[j]["number"];
						}
					}
				}
			}
			ai = ai+attackBasic;
			pi = pi+physicDefBasic;
			if(fightInfo["attackerLand"]=="Water" && attackerSoldier["type"]==4)
			{
				pi = pi + 30;
			}
			mi = mi+magicDefBasic;
			lii=lii+lifeBasic+40;  //jjc inc =0.4
			di = di+damageIncBasic;
			pdd = pdd+damageDecBasic;
			mdd = mdd+damageDecBasic;
			
			var attackPanel = Math.floor(attack*(1+ai/100.0));
			var physicDefPanel = Math.floor(physicDef*(1+pi/100.0));
			var magicDefPanel = Math.floor(magicDef*(1+mi/100.0));
			var lifePanel = Math.floor(life*(1+lii/100.0))*10;
			
			fightInfo["attackerSoldierAttack"]=attackPanel;
			fightInfo["attackerSoldierPhysicDef"]=physicDefPanel;
			fightInfo["attackerSoldierMagicDef"]=magicDefPanel;
			fightInfo["attackerSoldierLife"]=lifePanel;
			
			fightInfo["attackerSoldierDamageInc"] = di;
			fightInfo["attackerSoldierPhysicDamageDec"] = pdd;
			fightInfo["attackerSoldierMagicDamageDec"] = mdd;
			
			for(var i=0; i<counters.length; i++)
			{
				fightInfo["attackerSoldierAttack"] = Math.floor(fightInfo["attackerSoldierAttack"]*(1+counters[i]/100));
			}
			for(var i=0; i<pd_counters.length; i++)
			{
				fightInfo["attackerSoldierPhysicDef"] = Math.floor(fightInfo["attackerSoldierPhysicDef"]*(1+pd_counters[i]/100));
			}
			
			fightInfo["attackerSoldierCriticalProbInc"] = cpi;	
			fightInfo["attackerSoldierCriticalProbDec"] = cpd;	
			fightInfo["attackerSoldierCriticalDamageInc"] = cdi;	
			fightInfo["attackerSoldierCriticalDamageDec"] = cdd;
		}
		$("#attacker-soldier-information").html("<p><b>"+attackerSoldier["name"]+"</b></p><p>攻击："+fightInfo["attackerSoldierAttack"]+"&nbsp;&nbsp;&nbsp;防御："+
		fightInfo["attackerSoldierPhysicDef"]+"&nbsp;&nbsp;&nbsp;魔防："+fightInfo["attackerSoldierMagicDef"]+"</p><p>生命："+fightInfo["attackerSoldierLife"]+
		"&nbsp;&nbsp;&nbsp;增伤:"+fightInfo["attackerSoldierDamageInc"]+
		"&nbsp;&nbsp;&nbsp;物理减伤:"+fightInfo["attackerSoldierPhysicDamageDec"]+
		"&nbsp;&nbsp;&nbsp;魔法减伤:"+fightInfo["attackerSoldierMagicDamageDec"]+"</p><p>"+
		"暴击："+fightInfo["attackerSoldierCriticalProbInc"]+
		"&nbsp;&nbsp;&nbsp;暴伤："+fightInfo["attackerSoldierCriticalDamageInc"]+
		"&nbsp;&nbsp;&nbsp;防爆："+fightInfo["attackerSoldierCriticalProbDec"]+
		"&nbsp;&nbsp;&nbsp;减爆伤："+fightInfo["attackerSoldierCriticalDamageDec"]+"</p>");
		
	} 
	
	if(defenderSoldier && defender) 
	{
		var attack = Math.ceil(defenderSoldier["attack"]*(1+defender["soldierAttackInc"]/100));
		var physicDef = Math.ceil(defenderSoldier["physicDef"]*(1+defender["soldierPhysicDefInc"]/100));
		var magicDef = Math.ceil(defenderSoldier["magicDef"]*(1+defender["soldierMagicDefInc"]/100));
		var life = Math.ceil(defenderSoldier["life"]*(1+defender["soldierLifeInc"]/100));
		
		var ai  = 0;
		var pi  = 0;
		var mi  = 0;
		var lii = 0;
		var di = 0;
		var pdd = 0;
		var mdd = 0;
		var counters = [];
		var pd_counters = [];
		var cpi = 0;
		var cdi = 0;
		var cpd = 0;
		var cdd = 0;
		
		if(defenderSkills)
		{
			var attackBasic = 0;
			var intelBasic = 0;
			var physicDefBasic = 0;
			var magicDefBasic = 0;
			var lifeBasic = 0;
			var damageIncBasic = 0;
			var damageDecBasic = 0;
			
			for(var i=0; i<defenderSkills.length; i++)
			{
				if(defenderSkills[i]["valid"] && (defenderSkills[i]["skill"]["scope"]=="All" || defenderSkills[i]["skill"]["scope"]=="Soldier" ))
				{
					var buffs = defenderSkills[i]["skill"]["buffs"];
					for(var j=0; j<buffs.length; j++)
					{
						if(buffs[j]["buffType"]=="Attack")
						{
							if(buffs[j]["basic"])
							{
								attackBasic = Math.max(attackBasic, buffs[j]["number"]);
							}else
							{
								ai = ai+buffs[j]["number"];
							}
						}
						
						if(buffs[j]["buffType"]=="PhysicDef")
						{
							if(buffs[j]["basic"])
							{
								physicDefBasic = Math.max(physicDefBasic, buffs[j]["number"]);
							}else
							{
								pi = pi+buffs[j]["number"];
							}
						}
						
						if(buffs[j]["buffType"]=="MagicDef")
						{
							if(buffs[j]["basic"])
							{
								magicDefBasic = Math.max(magicDefBasic, buffs[j]["number"]);
							}else
							{
								mi = mi+buffs[j]["number"];
							}
						}		
						if(buffs[j]["buffType"]=="Life")
						{
							if(buffs[j]["basic"])
							{
								lifeBasic = Math.max(lifeBasic, buffs[j]["number"]);
							}else
							{
								lii = lii+buffs[j]["number"];
							}
						}
						if(buffs[j]["buffType"]=="DamageInc")
						{
							if(buffs[j]["basic"])
							{
								damageIncBasic = Math.max(damageIncBasic, buffs[j]["number"]);
							}else
							{
								di = di+buffs[j]["number"];
							}
						}
						
						if(buffs[j]["buffType"]=="DamageDec")
						{
							if(buffs[j]["basic"])
							{
								damageDecBasic = Math.max(damageDncBasic, buffs[j]["number"]);
							}else
							{
								pdd = pdd+buffs[j]["number"];
								mdd = mdd+buffs[j]["number"];
							}
						}
						if(buffs[j]["buffType"]=="PhysicDamageDec")
						{
							pdd = pdd+buffs[j]["number"];
						}
						if(buffs[j]["buffType"]=="MagicDamageDec")
						{
							mdd = mdd+buffs[j]["number"];
						}
						
						if(buffs[j]["buffType"]=="AttackCounter")
						{
							counters.push(buffs[j]["number"]);
						}
						
						if(buffs[j]["buffType"]=="PhysicDefCounter")
						{
							pd_counters.push(buffs[j]["number"]);
						}
						if(buffs[j]["buffType"]=="CriticalProbInc")
						{
							cpi = cpi+buffs[j]["number"];
						}
						if(buffs[j]["buffType"]=="CriticalProbDec")
						{
							cpd = cpd+buffs[j]["number"];
						}
						if(buffs[j]["buffType"]=="CriticalDamageInc")
						{
							cdi = cdi+buffs[j]["number"];
						}
						if(buffs[j]["buffType"]=="CriticalDamageDec")
						{
							cdd = cdd+buffs[j]["number"];
						}
					}
				}
			}
			ai = ai+attackBasic;
			pi = pi+physicDefBasic;
			if(fightInfo["defenderLand"]=="Water" && defenderSoldier["type"]==4)
			{
				pi = pi +30;
			}
			mi = mi+magicDefBasic;
			lii=lii+lifeBasic+40;  //jjc inc =0.4
			di = di+damageIncBasic;
			pdd = pdd+damageDecBasic;
			mdd = mdd+damageDecBasic;
			
			var attackPanel = Math.floor(attack*(1+ai/100.0));
			var physicDefPanel = Math.floor(physicDef*(1+pi/100.0));
			var magicDefPanel = Math.floor(magicDef*(1+mi/100.0));
			var lifePanel = Math.floor(life*(1+lii/100.0))*10;
			
			fightInfo["defenderSoldierAttack"]=attackPanel;
			fightInfo["defenderSoldierPhysicDef"]=physicDefPanel;
			fightInfo["defenderSoldierMagicDef"]=magicDefPanel;
			fightInfo["defenderSoldierLife"]=lifePanel;
			fightInfo["defenderSoldierDamageInc"] = di;
			fightInfo["defenderSoldierPhysicDamageDec"] = pdd;
			fightInfo["defenderSoldierMagicDamageDec"] = mdd;
			fightInfo["defenderSoldierCriticalProbInc"] = cpi;	
			fightInfo["defenderSoldierCriticalProbDec"] = cpd;	
			fightInfo["defenderSoldierCriticalDamageInc"] = cdi;	
			fightInfo["defenderSoldierCriticalDamageDec"] = cdd;
			
			for(var i=0; i<counters.length; i++)
			{
				fightInfo["defenderSoldierAttack"] = Math.floor(fightInfo["defenderSoldierAttack"]*(1+counters[i]/100));
			}
			for(var i=0; i<pd_counters.length; i++)
			{
				fightInfo["defenderSoldierPhysicDef"] = Math.floor(fightInfo["defenderSoldierPhysicDef"]*(1+pd_counters[i]/100));
			}
		}
		$("#defender-soldier-information").html("<p><b>"+defenderSoldier["name"]+"</b></p><p>攻击："+fightInfo["defenderSoldierAttack"]+"&nbsp;&nbsp;&nbsp;防御："+
		fightInfo["defenderSoldierPhysicDef"]+"&nbsp;&nbsp;&nbsp;魔防："+fightInfo["defenderSoldierMagicDef"]+"</p><p>生命："+fightInfo["defenderSoldierLife"]+
		"&nbsp;&nbsp;&nbsp;增伤:"+fightInfo["defenderSoldierDamageInc"]+"&nbsp;&nbsp;&nbsp;物理减伤:"+fightInfo["defenderSoldierPhysicDamageDec"]+
		"&nbsp;&nbsp;&nbsp;魔法减伤:"+fightInfo["defenderSoldierMagicDamageDec"]+"</p><p>"+
		"暴击："+fightInfo["defenderSoldierCriticalProbInc"]+
		"&nbsp;&nbsp;&nbsp;暴伤："+fightInfo["defenderSoldierCriticalDamageInc"]+
		"&nbsp;&nbsp;&nbsp;防爆："+fightInfo["defenderSoldierCriticalProbDec"]+
		"&nbsp;&nbsp;&nbsp;减爆伤："+fightInfo["defenderSoldierCriticalDamageDec"]+"</p>");
	}
	
	$("#attacker-land-information").html("<p>地形:<b>"+lands[fightInfo["attackerLand"]]+"</b></p>");
	$("#defender-land-information").html("<p>地形:<b>"+lands[fightInfo["defenderLand"]]+"</b></p>");
	
	
	$("#attacker-critical").children("li").remove();
	if(attacker)
	{
		if(attackerHeroCriticalChecked == undefined)
		{
			attackerHeroCriticalChecked = fightInfo["attackerCriticalProbInc"]+fightInfo["attackerTech"]/10>70;
		}

		var buttonClass = attackerHeroCriticalChecked ? "btn-warning":"btn-default";
		var buff = $("<li class=\"list-group-item\">英雄暴击</li>");
		var button = $("<button type=\"button\" class=\"btn "+buttonClass+"\">暴击</button>");
		button.attr("uc-name", name);
		button.click(function(e){
		
			if ( $(this).hasClass("btn-warning"))
			{
				$(this).removeClass("btn-warning");
				$(this).addClass("btn-default");
				attackerHeroCriticalChecked = false;
			} else
			{
				$(this).removeClass("btn-default");
				$(this).addClass("btn-warning");
				attackerHeroCriticalChecked = true;
			}
			//loadSkillData();
		});
		button.appendTo(buff);
		buff.appendTo($("#attacker-critical"));	
	}
	if(attackerSoldier)
	{
		if(attackerSoldierCriticalChecked == undefined)
		{
			attackerSoldierCriticalChecked = fightInfo["attackerSoldierCriticalProbInc"]>70;
		}
		
		var buff = $("<li class=\"list-group-item\">士兵暴击</li>");
		
		if( fightInfo["attackerSoldierCriticalProbInc"]>0) 
		{
			var buttonClass = (attackerSoldierCriticalChecked) ? "btn-warning":"btn-default";
			var button = $("<button type=\"button\" class=\"btn "+buttonClass+"\">暴击</button>");
			button.attr("uc-name", name);
			button.click(function(e){
			
				if ( $(this).hasClass("btn-warning"))
				{
					$(this).removeClass("btn-warning");
					$(this).addClass("btn-default");
					attackerSoldierCriticalChecked = false;
				} else
				{
					$(this).removeClass("btn-default");
					$(this).addClass("btn-warning");
					attackerSoldierCriticalChecked = true;
				}
				//loadSkillData();
			});
			button.appendTo(buff);
		}
		buff.appendTo($("#attacker-critical"));	
	}
	
	$("#defender-critical").children("li").remove();
	if(defender)
	{
		if(defenderHeroCriticalChecked == undefined)
		{
			defenderHeroCriticalChecked = fightInfo["defenderCriticalProbInc"]+fightInfo["defenderTech"]/10>70;
		}
		var buttonClass = defenderHeroCriticalChecked ? "btn-warning":"btn-default";
		var buff = $("<li class=\"list-group-item\">英雄暴击</li>");
		var button = $("<button type=\"button\" class=\"btn "+buttonClass+"\">暴击</button>");
		button.attr("uc-name", name);
		button.click(function(e){
		
			if ( $(this).hasClass("btn-warning"))
			{
				$(this).removeClass("btn-warning");
				$(this).addClass("btn-default");
				defenderHeroCriticalChecked = false;
			} else
			{
				$(this).removeClass("btn-default");
				$(this).addClass("btn-warning");
				defenderHeroCriticalChecked = true;
			}
			//loadSkillData();
		});
		button.appendTo(buff);
		buff.appendTo($("#defender-critical"));	
	}
	if(defenderSoldier)
	{
		if(defenderSoldierCriticalChecked == undefined)
		{
			defenderSoldierCriticalChecked = fightInfo["defenderSoldierCriticalProbInc"]>70;
		}
		var buff = $("<li class=\"list-group-item\">士兵暴击</li>");
		if(fightInfo["defenderSoldierCriticalProbInc"]>0) 
		{
			var buttonClass = (defenderSoldierCriticalChecked) ? "btn-warning":"btn-default";
	
			var button = $("<button type=\"button\" class=\"btn "+buttonClass+"\">暴击</button>");
			button.attr("uc-name", name);
			button.click(function(e){
			
				if ( $(this).hasClass("btn-warning"))
				{
					$(this).removeClass("btn-warning");
					$(this).addClass("btn-default");
					defenderSoldierCriticalChecked = false;
				} else
				{
					$(this).removeClass("btn-default");
					$(this).addClass("btn-warning");
					defenderSoldierCriticalChecked = true;
				}
				//loadSkillData();
			});
			button.appendTo(buff);
		}
		buff.appendTo($("#defender-critical"));	
	}
}
