var attacker;
var defender;
var attackerSoldier;
var defenderSoldier;
var attackerAction;
var attackerEquip;
var defenderEquip;
var attackerSkills;
var defenderSkills;
var attackerUserConditions;
var defenderUserConditions;
var attackerUserConditionChecked = {};
var defenderUserConditionChecked = {};
var isAttacker = 1;
var fightInfo={};
var lands = {"Flat":"平地","Water":"水","Wood":"树林","Wall":"城墙","Grass":"草地","Mountain":"山地"};

$(document).ready(function() {


	loadHeroData();

	$("#aa").show();
	$("#ai").hide();
	$("#da").show();
	$("#di").hide();
	
	$("#ap").show();
	$("#am").hide();
	
	$("#dp").show();
	$("#dm").hide();
	
	$("#calculate").click(function(){
		calculate();
	});
});



function buildHeroPics(data)
{
	$("#hero_pics").children("div").remove();
	for (var j = 0; j < data.length; j++) {
		var pic = $("<div onclick=\"chooseHero("+data[j]["id"]+")\" class=\"hero_pic\"><img src=\"/fight/image/"+data[j]["id"]+".png\" alt=\"\" width=\"60\" height=\"86\"></img></div>");
		pic.appendTo($("#hero_pics"));
	}
}

function loadHeroData()
{
	$.ajax({
		type : "GET",
		url : "/fight/heros",
		dataType : "json",
		success : function(data) {
			buildHeroPics(data);
		},
		error : function(jqXHR) {
			// alert("Error: "+jqXHR.status);
		}
	});
}

function buildFight()
{
	var request = {
			
	};
	if(attacker)
	{
		request["attackerHeroId"] = attacker["id"];
	} 
	if(defender)
	{
		request["defenderHeroId"] = defender["id"];
	}
	if(attackerSoldier)
	{
		request["attackerSoldierId"] = attackerSoldier["id"];
	} 
	if(defenderSoldier)
	{
		request["defenderSoldierId"] = defenderSoldier["id"];
	} 
	
	if(attackerAction)
	{
		request["attackerActionId"] = attackerAction["id"];
		
	}
	request["attackerUserConditionChecked"]=attackerUserConditionChecked;
	request["defenderUserConditionChecked"]=defenderUserConditionChecked;

	fightInfo["attackerHeroLeftLife"] = fightInfo["attackerLife"];
	fightInfo["attackerSoldierLeftLife"] = fightInfo["attackerSoldierLife"];
	fightInfo["defenderHeroLeftLife"] = fightInfo["defenderLife"];
	fightInfo["defenderSoldierLeftLife"] = fightInfo["defenderSoldierLife"];
	
	
	for(var i in fightInfo)
	{
		request[i] = fightInfo[i];
	}
	

	return request; 
	
}
function loadSkillData(refresh)
{
	var fight = buildFight();
	$.ajax({
		type : "POST",
		url : "/fight/buffs",
		data : JSON.stringify(fight),
		dataType : "json",
		contentType : "application/json",
		processData : false,
		success : function(data) {
			attackerSkills = data["attackerSkills"];
			defenderSkills = data["defenderSkills"];
			attackerUserConditions = data["attackerUserConditions"];
			defenderUserConditions = data["defenderUserConditions"];
			
			buildSkill();
			
			if(refresh==true)
			{
				loadSkillData(false);
			}
		},
		error : function(jqXHR) {
			// alert("Error: "+jqXHR.status);
		}
	});
}

function chooseHero(id)
{
	$.ajax({
		type : "GET",
		url : "/fight/heros/"+id,
		dataType : "json",
		success : function(data) {
			buildHero(id, data);
		},
		error : function(jqXHR) {
			// alert("Error: "+jqXHR.status);
		}
	});
	
}
function buildHero(id, hero)
{
	$.ajax({
		type : "GET",
		url : "/fight/heros/"+id+"/heroEquip",
		dataType : "json",
		success : function(data) {
			displayHero(id, hero, data);
		},
		error : function(jqXHR) {
			// alert("Error: "+jqXHR.status);
		}
	});
}

function displayHero(id, hero, heroEquip)
{
	if(isAttacker==1)
	{
		attacker = hero;
		attackerEquip = heroEquip;
		attackerSoldier = undefined;
		
		$("#attackerPic").children("div").remove();
		var pic = $("<div><img src=\"/fight/image/"+id+".png\" alt=\"\" width=\"60\" height=\"86\"></img>"+hero["name"]+"</div>");
		pic.appendTo($("#attackerPic"));
		
		if(hero["isPhysic"])
		{
			$("#aa").show();
			$("#ai").hide();
			
			$("#dp").show();
			$("#dm").hide();
		} else
		{
			$("#ai").show();
			$("#aa").hide();
			
			$("#dm").show();
			$("#dp").hide();
		}
		
		$("#attacker-soldier-list").children("li").remove();
		var soldiers = hero["soldiers"];
		for(var i=0; i<soldiers.length; i++)
		{
			var soldier = $("<li><img src=\"/fight/image/soldier_"+soldiers[i]["id"]+".png\" alt=\"\" width=\"50\" height=\"62\"></img>"+soldiers[i]["name"]+"</li>");
			soldier.attr("soldierid", soldiers[i]["id"]);
			soldier.appendTo($("#attacker-soldier-list"));
			
			if(hero["defaultSoldierId"] == soldiers[i]["id"])
			{
				attackerSoldier = soldiers[i];
			}
			soldier.click(function(event){
				var find = $(this).attr("soldierid");
				attacker["soldiers"].forEach(function(e){
					if(e["id"]==find)
					{
						attackerSoldier = e;
						refreshAttacker();
					}
				})
				
			})
		}
		
		$("#attacker-action-list").children("li").remove();
		var actions = hero["actions"];
		for(var i=0; i<actions.length; i++)
		{
			var action = $("<li><img src=\"/fight/image/action_"+actions[i]["id"]+".png\" alt=\"\" width=\"40\" height=\"40\"></img>"+actions[i]["name"]+"</li>");
			action.attr("actionid", actions[i]["id"]);
			action.appendTo($("#attacker-action-list"));
			
			if(actions[i]["id"] == 10001 || actions[i]["id"] == 10002)
			{
				attackerAction = actions[i];
			}
			action.click(function(event){
				var find = $(this).attr("actionid");
				attacker["actions"].forEach(function(e){
					if(e["id"]==find)
					{
						attackerAction = e;
						refreshAttacker();
					}
				})
				
			})
		}

		$("#attacker-land-list").children("li").remove();
		for(var i in lands)
		{
			var land = $("<li>"+lands[i]+"</li>");
			land.attr("landid", i);
			land.appendTo($("#attacker-land-list"));

			land.click(function(event){
				fightInfo["attackerLand"] = $(this).attr("landid");
				refreshAttacker();
			})
		}

		refreshAttacker();
		isAttacker=0;
	} else
	{
		defender = hero;
		defenderEquip = heroEquip;
		defenderSoldier = undefined;
		$("#defenderPic").children("div").remove();
		var pic = $("<div><img src=\"/fight/image/"+id+".png\" alt=\"\" width=\"60\" height=\"86\"></img>"+hero["name"]+"</div>");
		pic.appendTo($("#defenderPic"));
		
		if(hero["isPhysic"])
		{
			$("#da").show();
			$("#di").hide();
			
			$("#ap").show();
			$("#am").hide();
		} else
		{
			$("#da").hide();
			$("#di").show();
			
			$("#am").show();
			$("#ap").hide();
		}
		
		$("#defender-soldier-list").children("li").remove();
		var soldiers = hero["soldiers"];
		for(var i=0; i<soldiers.length; i++)
		{
			if(hero["defaultSoldierId"] == soldiers[i]["id"])
			{
				defenderSoldier = soldiers[i];
			}
			var soldier = $("<li><img src=\"/fight/image/soldier_"+soldiers[i]["id"]+".png\" alt=\"\" width=\"50\" height=\"62\"></img>"+soldiers[i]["name"]+"</li>");
			soldier.attr("soldierid", soldiers[i]["id"]);
			soldier.appendTo($("#defender-soldier-list"));
			soldier.click(function(event){
				var find = $(this).attr("soldierid");
				defender["soldiers"].forEach(function(e){
					if(e["id"]==find)
					{
						defenderSoldier = e;
						refreshDefender();
					}
				})
			})
		}
		
		$("#defender-land-list").children("li").remove();
		for(var i in lands)
		{
			var land = $("<li>"+lands[i]+"</li>");
			land.attr("landid", i);
			land.appendTo($("#defender-land-list"));

			land.click(function(event){
				fightInfo["defenderLand"] = $(this).attr("landid");
				refreshAttacker();
			})
		}

		refreshDefender();
		isAttacker=1;
	}
	
	if(fightInfo["defenderLand"] == undefined)
	{
		fightInfo["defenderLand"] = "Flat";
	}

	if(fightInfo["attackerLand"] == undefined)
	{
		fightInfo["attackerLand"] = "Flat";
	}
}

function refreshAttacker()
{
	if(attacker)
	{
		$("#attacker-info").html(
		"<p>生命&nbsp;"+attacker["life"]+"+"+attackerEquip["lifeInc"]+
		"</p><p>攻击&nbsp;"+attacker["attack"]+"+"+attackerEquip["attackInc"]+
		"</p><p>智力&nbsp;"+attacker["intel"]+"+"+attackerEquip["intelInc"]+
		"</p><p>防御&nbsp;"+attacker["physicDef"]+"+"+attackerEquip["physicDefInc"]+
		"</p><p>魔防&nbsp;"+attacker["magicDef"]+"+"+attackerEquip["magicDefInc"]+
		"</p><p>技巧"+attacker["tech"]+"</p>");
		
		$("#attacker-soldier-information").html("");
	}

	loadSkillData(true);
}

function refreshDefender()
{
	if(defender)
	{
		$("#defender-info").html(
		"<p>生命&nbsp;"+defender["life"]+"+"+defenderEquip["lifeInc"]+
		"</p><p>攻击&nbsp;"+defender["attack"]+"+"+defenderEquip["attackInc"]+
		"</p><p>智力&nbsp;"+defender["intel"]+"+"+defenderEquip["intelInc"]+
		"</p><p>防御&nbsp;"+defender["physicDef"]+"+"+defenderEquip["physicDefInc"]+
		"</p><p>魔防&nbsp;"+defender["magicDef"]+"+"+defenderEquip["magicDefInc"]+
		"</p><p>技巧"+defender["tech"]+"</p>");
		$("#defender-soldier-information").html("");
	}
	loadSkillData(true);
}

function buildSkill()
{
	$("#attacker-buf-list").children("li").remove();
	if(attackerSkills)
	{
		for(var i=0; i<attackerSkills.length; i++)
		{
			var valid = attackerSkills[i].valid ? "valid-skill":"not-valid-skill";
			var buff = $("<li class=\"list-group-item "+valid+"\">"+attackerSkills[i]["skill"]["desc"]+"</li>");
			buff.appendTo($("#attacker-buf-list"));
		}
		
	}

	$("#defender-buf-list").children("li").remove();
	if(defenderSkills)
	{
		for(var i=0; i<defenderSkills.length; i++)
		{
			var valid = defenderSkills[i].valid ? "valid-skill":"not-valid-skill";
			var buff = $("<li class=\"list-group-item "+valid+"\">"+defenderSkills[i]["skill"]["desc"]+"</li>");
			buff.appendTo($("#defender-buf-list"));
		}
		
	}
	buildUserCondition();
	calculatePanel();
}

function buildUserCondition()
{
	$("#attacker-user-condition-list").children("li").remove();
	if(attackerUserConditions)
	{
		for(var name in attackerUserConditions)
		{
			var checked = attackerUserConditionChecked[name];
			if(checked == undefined)
			{
				checked = attackerUserConditions[name]["defaultValid"];
			}
			var buttonClass = checked ? "btn-success":"btn-default";
			var buff = $("<li class=\"list-group-item\">"+attackerUserConditions[name]["desc"]+"</li>");
			var button = $("<button type=\"button\" class=\"btn "+buttonClass+"\">选择</button>");
			button.attr("uc-name", name);
			button.click(function(e){
			
				if ( $(this).hasClass("btn-success"))
				{
					$(this).removeClass("btn-success");
					$(this).addClass("btn-default");
					attackerUserConditionChecked[$(this).attr("uc-name")]=false;
				} else
				{
					$(this).removeClass("btn-default");
					$(this).addClass("btn-success");
					attackerUserConditionChecked[$(this).attr("uc-name")]=true;
				}
				loadSkillData();
			});
			button.appendTo(buff);
			buff.appendTo($("#attacker-user-condition-list"));
		}
	}
	
	$("#defender-user-condition-list").children("li").remove();
	if(defenderUserConditions)
	{
		for(var name in defenderUserConditions)
		{
			var checked = defenderUserConditionChecked[name];
			if(checked == undefined)
			{
				checked = defenderUserConditions[name]["defaultValid"];
			}
			var buttonClass = checked ? "btn-success":"btn-default";
			var buff = $("<li class=\"list-group-item\">"+defenderUserConditions[name]["desc"]+"</li>");
			var button = $("<button type=\"button\" class=\"btn "+buttonClass+"\">选择</button>");
			button.attr("uc-name", name);
			button.click(function(e){
			
				if ( $(this).hasClass("btn-success"))
				{
					$(this).removeClass("btn-success");
					$(this).addClass("btn-default");
					defenderUserConditionChecked[$(this).attr("uc-name")]=false;
				} else
				{
					$(this).removeClass("btn-default");
					$(this).addClass("btn-success");
					defenderUserConditionChecked[$(this).attr("uc-name")]=true;
				}
				loadSkillData();
			});
			button.appendTo(buff);
			buff.appendTo($("#defender-user-condition-list"));
		}
	}
}

function calculatePanel()
{
	if(attacker && attackerEquip)
	{
		var attack = attacker["attack"] + attackerEquip["attackInc"];
		var intel = attacker["intel"] + attackerEquip["intelInc"];
		var physicDef = attacker["physicDef"] + attackerEquip["physicDefInc"];
		var magicDef = attacker["magicDef"] + attackerEquip["magicDefInc"];
		var life = attacker["life"] + attackerEquip["lifeInc"];
		
		var ai  = attackerEquip["attackSkill"];
		var ii  = attackerEquip["intelSkill"];
		var pi  = attackerEquip["physicDefSkill"];
		var mi  = attackerEquip["magicDefSkill"];
		var lii  = attackerEquip["lifeSkill"];
		var di = 0;
		var dd = 0;
		var counters = [];
		
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
								dd = dd+buffs[j]["number"];
							}
						}
						if(buffs[j]["buffType"]=="Counter")
						{
							counters.push(buffs[j]["number"]);
						}
					}
				}
			}
			ai = ai+attackBasic;
			ii = ii+intelBasic;
			pi = pi+physicDefBasic;
			mi = mi+magicDefBasic;
			lii=lii+lifeBasic+40;  //jjc inc =0.4
			di = di+damageIncBasic;
			dd = dd+damageDecBasic;

			fightInfo["attackerAttack"] = Math.ceil(attack*(1+ai/100.0)+attackerEquip["attackJJC"]);
			fightInfo["attackerIntel"] =  Math.ceil(intel*(1+ii/100.0)+attackerEquip["intelJJC"]);
			fightInfo["attackerPhysicDef"] = Math.ceil(physicDef*(1+pi/100.0)+attackerEquip["physicDefJJC"]);
			fightInfo["attackerMagicDef"] = Math.ceil(magicDef*(1+mi/100.0)+attackerEquip["magicDefJJC"]);
			fightInfo["attackerLife"] = Math.ceil(life*(1+lii/100.0)+attackerEquip["lifeJJC"]);
			fightInfo["attackerDamageInc"] = di;
			fightInfo["attackerDamageDec"] = dd;
			
						
			for(var i=0; i<counters.length; i++)
			{
				if(attacker["isPhysic"]) 
				{
					fightInfo["attackerAttack"] = Math.ceil(fightInfo["attackerAttack"]*(1+counters[i]/100));
				} else 
				{
					fightInfo["attackerIntel"] = Math.ceil(fightInfo["attackerIntel"]*(1+counters[i]/100));
				}
			}
			
			$("#attackerAttack").attr("value", fightInfo["attackerAttack"]);
			$("#attackerIntel").attr("value", fightInfo["attackerIntel"]);
			$("#attackerPhysicDef").attr("value", fightInfo["attackerPhysicDef"]);
			$("#attackerMagicDef").attr("value", fightInfo["attackerMagicDef"]);
			$("#attackerLife").attr("value", fightInfo["attackerLife"]);
			
			var detail="<p>";
			if(attackerAction)
			{
				detail+="<b>"+attackerAction["name"]+"</b>&nbsp;&nbsp;&nbsp;<b>"+attackerAction["coefficient"]+"倍</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
			}
			detail+="增伤："+fightInfo["attackerDamageInc"]+"&nbsp;&nbsp;&nbsp;减伤："+fightInfo["attackerDamageDec"]+"</p>";
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
		
		var ai  = defenderEquip["attackSkill"];
		var ii  = defenderEquip["intelSkill"];
		var pi  = defenderEquip["physicDefSkill"];
		var mi  = defenderEquip["magicDefSkill"];
		var lii  = defenderEquip["lifeSkill"];
		
		var di = 0;
		var dd = 0;
		var counters = [];

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
								dd = dd+buffs[j]["number"];
							}
						}
						if(buffs[j]["buffType"]=="Counter")
						{
							counters.push(buffs[j]["number"]);
						}
					}
				}
			}
			ai = ai+attackBasic;
			ii = ii+intelBasic;
			pi = pi+physicDefBasic;
			mi = mi+magicDefBasic;
			lii=lii+lifeBasic+40;  //jjc inc =0.4
			di = di+damageIncBasic;
			dd = dd+damageDecBasic;
			
			fightInfo["defenderAttack"] = Math.ceil(attack*(1+ai/100.0)+defenderEquip["attackJJC"]);
			fightInfo["defenderIntel"] = Math.ceil(intel*(1+ii/100.0)+defenderEquip["intelJJC"]);
			fightInfo["defenderPhysicDef"] = Math.ceil(physicDef*(1+pi/100.0)+defenderEquip["physicDefJJC"]);
			fightInfo["defenderMagicDef"] = Math.ceil(magicDef*(1+mi/100.0)+defenderEquip["magicDefJJC"]);
			fightInfo["defenderLife"] = Math.ceil(life*(1+lii/100.0)+defenderEquip["lifeJJC"]);
			
			fightInfo["defenderDamageInc"] = di;
			fightInfo["defenderDamageDec"] = dd;
			
			for(var i=0; i<counters.length; i++)
			{
				if(defender["isPhysic"]) 
				{
					fightInfo["defenderAttack"] = Math.ceil(fightInfo["defenderAttack"]*(1+counters[i]/100));
				} else 
				{
					fightInfo["defenderIntel"] = Math.ceil(fightInfo["defenderIntel"]*(1+counters[i]/100));
				}
			}
			
			$("#defenderAttack").attr("value", fightInfo["defenderAttack"]);
			$("#defenderIntel").attr("value", fightInfo["defenderIntel"]);
			$("#defenderPhysicDef").attr("value", fightInfo["defenderPhysicDef"]);
			$("#defenderMagicDef").attr("value", fightInfo["defenderMagicDef"]);
			$("#defenderLife").attr("value", fightInfo["defenderLife"]);
						
			$("#defenderDetail").html("<p>增伤："+fightInfo["defenderDamageInc"]+"&nbsp;&nbsp;&nbsp;减伤："+fightInfo["defenderDamageDec"]+"</p>");
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
		var dd = 0;
		var counters = [];
		
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
								dd = dd+buffs[j]["number"];
							}
						}
						if(buffs[j]["buffType"]=="Counter")
						{
							counters.push(buffs[j]["number"]);
						}
					}
				}
			}
			ai = ai+attackBasic;
			pi = pi+physicDefBasic;
			mi = mi+magicDefBasic;
			lii=lii+lifeBasic+40;  //jjc inc =0.4
			di = di+damageIncBasic;
			dd = dd+damageDecBasic;

			var attackPanel = Math.ceil(attack*(1+ai/100.0));
			var physicDefPanel = Math.ceil(physicDef*(1+pi/100.0));
			var magicDefPanel = Math.ceil(magicDef*(1+mi/100.0));
			var lifePanel = Math.ceil(life*(1+lii/100.0))*10;
			
			fightInfo["attackerSoldierAttack"]=attackPanel;
			fightInfo["attackerSoldierPhysicDef"]=physicDefPanel;
			fightInfo["attackerSoldierMagicDef"]=magicDefPanel;
			fightInfo["attackerSoldierLife"]=lifePanel;
			
			fightInfo["attackerSoldierDamageInc"] = di;
			fightInfo["attackerSoldierDamageDec"] = dd;
			
			for(var i=0; i<counters.length; i++)
			{
				fightInfo["attackerSoldierAttack"] = Math.ceil(fightInfo["attackerSoldierAttack"]*(1+counters[i]/100));
			}
		}
		$("#attacker-soldier-information").html("<p><b>"+attackerSoldier["name"]+"</b></p><p>攻击："+fightInfo["attackerSoldierAttack"]+"&nbsp;&nbsp;&nbsp;防御："+
		fightInfo["attackerSoldierPhysicDef"]+"&nbsp;&nbsp;&nbsp;魔防："+fightInfo["attackerSoldierMagicDef"]+"</p><p>生命："+fightInfo["attackerSoldierLife"]+
		"&nbsp;&nbsp;&nbsp;增伤:"+fightInfo["attackerSoldierDamageInc"]+"&nbsp;&nbsp;&nbsp;减伤:"+fightInfo["attackerSoldierDamageDec"]+"</p>");
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
		var dd = 0;
		var counters = [];
		
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
								dd = dd+buffs[j]["number"];
							}
						}
						if(buffs[j]["buffType"]=="Counter")
						{
							counters.push(buffs[j]["number"]);
						}
					}
				}
			}
			ai = ai+attackBasic;
			pi = pi+physicDefBasic;
			mi = mi+magicDefBasic;
			lii=lii+lifeBasic+40;  //jjc inc =0.4
			di = di+damageIncBasic;
			dd = dd+damageDecBasic;

			var attackPanel = Math.ceil(attack*(1+ai/100.0));
			var physicDefPanel = Math.ceil(physicDef*(1+pi/100.0));
			var magicDefPanel = Math.ceil(magicDef*(1+mi/100.0));
			var lifePanel = Math.ceil(life*(1+lii/100.0))*10;
			
			fightInfo["defenderSoldierAttack"]=attackPanel;
			fightInfo["defenderSoldierPhysicDef"]=physicDefPanel;
			fightInfo["defenderSoldierMagicDef"]=magicDefPanel;
			fightInfo["defenderSoldierLife"]=lifePanel;
			fightInfo["defenderSoldierDamageInc"] = di;
			fightInfo["defenderSoldierDamageDec"] = dd;
			for(var i=0; i<counters.length; i++)
			{
				fightInfo["defenderSoldierAttack"] = Math.ceil(fightInfo["defenderSoldierAttack"]*(1+counters[i]/100));
			}
		}
		$("#defender-soldier-information").html("<p><b>"+defenderSoldier["name"]+"</b></p><p>攻击："+fightInfo["defenderSoldierAttack"]+"&nbsp;&nbsp;&nbsp;防御："+
		fightInfo["defenderSoldierPhysicDef"]+"&nbsp;&nbsp;&nbsp;魔防："+fightInfo["defenderSoldierMagicDef"]+"</p><p>生命："+fightInfo["defenderSoldierLife"]+
		"&nbsp;&nbsp;&nbsp;增伤:"+fightInfo["defenderSoldierDamageInc"]+"&nbsp;&nbsp;&nbsp;减伤:"+fightInfo["defenderSoldierDamageDec"]+"</p>");
		
	}
	
	
	$("#attacker-land-information").html("<p>地形:<b>"+lands[fightInfo["attackerLand"]]+"</b></p>");
	$("#defender-land-information").html("<p>地形:<b>"+lands[fightInfo["defenderLand"]]+"</b></p>");
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
	
		soldierToSoldier = attackerSoldier["isPhysic"]? oneHit(1, fightInfo["attackerSoldierAttack"], fightInfo["defenderSoldierPhysicDef"], 
																fightInfo["attackerSoldierDamageInc"], fightInfo["defenderSoldierDamageDec"]) 
														: oneHit(1, fightInfo["attackerSoldierAttack"], fightInfo["defenderSoldierMagicDef"], 
														         fightInfo["attackerSoldierDamageInc"], fightInfo["defenderSoldierDamageDec"]);
														         
        fightDetails+="<p>"+attackerSoldier["name"]+"攻击"+defenderSoldier["name"]+"1hit:"+soldierToSoldier+"</p>";
	}
	
	if(attackerSoldier && defender)
	{
		soldierToHero = attackerSoldier["isPhysic"]? oneHit(1, fightInfo["attackerSoldierAttack"], fightInfo["defenderPhysicDef"], 
															fightInfo["attackerSoldierDamageInc"], fightInfo["defenderDamageDec"]) 
													: oneHit(1, fightInfo["attackerSoldierAttack"], fightInfo["defenderMagicDef"], 
															fightInfo["attackerSoldierDamageInc"], fightInfo["defenderDamageDec"]);
		
		fightDetails+="<p>"+attackerSoldier["name"]+"攻击"+defender["name"]+"1hit:"+soldierToHero+"</p>";
	}
	
	if(attacker && defenderSoldier)
	{
		heroToSoldier = attacker["isPhysic"]? oneHit(coefficient, fightInfo["attackerAttack"], fightInfo["defenderSoldierPhysicDef"], fightInfo["attackerDamageInc"], 
															fightInfo["defenderSoldierDamageDec"]) 
													: oneHit(coefficient, fightInfo["attackerIntel"], fightInfo["defenderSoldierMagicDef"],
														fightInfo["attackerDamageInc"], fightInfo["defenderSoldierDamageDec"]);		
	    fightDetails+="<p>"+attacker["name"]+"攻击"+defenderSoldier["name"]+"1hit:"+heroToSoldier+"</p>";
	}

	if(attacker && defender)
	{
		heroToHero = attacker["isPhysic"]? oneHit(coefficient, fightInfo["attackerAttack"], fightInfo["defenderPhysicDef"], fightInfo["attackerDamageInc"], fightInfo["defenderDamageDec"]) 
													: oneHit(coefficient, fightInfo["attackerIntel"], fightInfo["defenderMagicDef"], fightInfo["attackerDamageInc"], fightInfo["defenderDamageDec"]);	
	    fightDetails+="<p>"+attacker["name"]+"攻击"+defender["name"]+"1hit:"+heroToHero+"</p>";
	}
	fightDetails+="<p>-------------------------------------------------------------------------------------------</p>";
	var soldierCount = attackerSoldier ? 20 : 0;
	var heroCount = attacker ? 20 : 0;
	var dl = defender ? fightInfo["defenderHeroLeftLife"] : 0;
	var dsl = defenderSoldier ? fightInfo["defenderSoldierLeftLife"] : 0;
	if(defenderSoldier) 
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
				dsl - soldierToSoldier * (soldierCount - needHit);
				needHit = soldierCount;
				
				fightDetails+="<p>"+attackerSoldier["name"]+" 用 <b>"+soldierCount+"</b> hit 干掉 <b>"+soldierKillSoldier+"</b>"+defenderSoldier["name"]+"</p>";
				
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
				
						fightDetails+="<p>"+attacker["name"]+"用 <b>"+heroCount+"</b> hit 干掉 <b>"+heroKillSoldier+"</b>"+defenderSoldier["name"]+"</p>";
					} 
					else
					{
						needHit+= Math.ceil((oneSoldierLife-dsl)/heroToSoldier);
						heroKillSoldier++;
						fightDetails+="<p>"+attacker["name"]+"用 <b>"+needHit+"</b> hit 干掉 <b>"+heroKillSoldier+"</b>"+defenderSoldier["name"]+"</p>";
						dsl =0;
						heroCount -= needHit;
					}
				}
			} else
			{
				// soldier to hero
				
				needHit+= Math.ceil(dsl/soldierToSoldier);
				fightDetails+="<p>"+attackerSoldier["name"]+"用 <b>"+needHit+"</b> hit 干掉 <b>10</b> "+defenderSoldier["name"]+"</p>";
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
					fightDetails+="<p>"+attackerSoldier["name"]+"用 <b>"+soldierCount+"</b> hit 对"+defender["name"]+"造成<b>"+stohDamage+"</b>伤害</p>";
				}
				
			}
		}
	}
	

	if(dl > 0 && dsl == 0)
	{
		if(soldierCount >0)
		{
			var stohDamage = soldierToHero* soldierCount;
			if(dl > stohDamage ) 
			{
				dl -= stohDamage;
			} else
			{
				dl = 0;
			}
			fightDetails+="<p>"+attackerSoldier["name"]+"用 <b>"+soldierCount+"</b> hit 对"+defender["name"]+"造成<b>"+stohDamage+"</b>伤害</p>";
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
			fightDetails+="<p>"+attacker["name"]+"用 <b>"+heroCount+"</b> hit 对"+defender["name"]+"造成<b>"+htohDamage+"</b>伤害 </p>";
		} else
		{
			alert("no hero count");
		}
	} else
	{
		alert("dl=="+dl+", dsl=="+dsl);
	}
	
	$("#defenderSoldierBar").text(dsl+"/"+fightInfo["defenderSoldierLife"]);
	
	$("#defenderSoldierBar").attr("style", "width:"+Math.ceil(dsl/fightInfo["defenderSoldierLife"]*100)+"%");
	
	$("#defenderHeroBar").text(dl+"/"+fightInfo["defenderLife"]);
	
	$("#defenderHeroBar").attr("style", "width:"+Math.ceil(dl/fightInfo["defenderLife"]*100)+"%");
										
	$("#fightDetails").html(fightDetails);
}

function oneHit(coefficient, attack, enemyDef, damageInc, enemyDamageDec)
{
	return Math.ceil(coefficient * (attack-enemyDef)*(1+(damageInc-enemyDamageDec)/100.0) /2) ;
}


















