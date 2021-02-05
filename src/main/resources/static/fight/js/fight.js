var attacker;
var defender;
var attackerSoldier;
var defenderSoldier;
var attackerEquip;
var defenderEquip;
var attackerSkills;
var defenderSkills;
var attackerUserConditions;
var defenderUserConditions;
var attackerUserConditionChecked = {};
var defenderUserConditionChecked = {};
var isAttacker = 1;

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
	request["attackerUserConditionChecked"]=attackerUserConditionChecked;
	request["defenderUserConditionChecked"]=defenderUserConditionChecked;
	return request;
	
}
function loadSkillData()
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
		refreshDefender();
		isAttacker=1;
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

	loadSkillData();
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
	loadSkillData();
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

		if(attackerSkills)
		{
			var attackBasic = 0;
			var intelBasic = 0;
			var physicDefBasic = 0;
			var magicDefBasic = 0;
			var lifeBasic = 0;
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
					}
				}
			}
			ai = ai+attackBasic;
			ii = ii+intelBasic;
			pi = pi+physicDefBasic;
			mi = mi+magicDefBasic;
			lii=lii+lifeBasic+40;  //jjc inc =0.4

			var attackPanel = Math.ceil(attack*(1+ai/100.0)+attackerEquip["atackJJC"]);
			var intelPanel = Math.ceil(intel*(1+ii/100.0)+attackerEquip["intelJJC"]);
			var physicDefPanel = Math.ceil(physicDef*(1+pi/100.0)+attackerEquip["physicDefJJC"]);
			var magicDefPanel = Math.ceil(magicDef*(1+mi/100.0)+attackerEquip["magicDefJJC"]);
			var lifePanel = Math.ceil(life*(1+lii/100.0)+attackerEquip["lifeJJC"]);
			
			$("#attackerAttack").attr("value", attackPanel);
			$("#attackerIntel").attr("value", intelPanel);
			$("#attackerPhysicDef").attr("value", physicDefPanel);
			$("#attackerMagicDef").attr("value", magicDefPanel);
			$("#attackerLife").attr("value", lifePanel);
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

		if(defenderSkills)
		{
			var attackBasic = 0;
			var intelBasic = 0;
			var physicDefBasic = 0;
			var magicDefBasic = 0;
			var lifeBasic = 0;
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
					}
				}
			}
			ai = ai+attackBasic;
			ii = ii+intelBasic;
			pi = pi+physicDefBasic;
			mi = mi+magicDefBasic;
			lii=lii+lifeBasic+40;  //jjc inc =0.4
			
			var attackPanel = Math.ceil(attack*(1+ai/100.0)+defenderEquip["atackJJC"]);
			var intelPanel = Math.ceil(intel*(1+ii/100.0)+defenderEquip["intelJJC"]);
			var physicDefPanel = Math.ceil(physicDef*(1+pi/100.0)+defenderEquip["physicDefJJC"]);
			var magicDefPanel = Math.ceil(magicDef*(1+mi/100.0)+defenderEquip["magicDefJJC"]);
			var lifePanel = Math.ceil(life*(1+lii/100.0)+defenderEquip["lifeJJC"]);
			
			$("#defenderAttack").attr("value", attackPanel);
			$("#defenderIntel").attr("value", intelPanel);
			$("#defenderPhysicDef").attr("value", physicDefPanel);
			$("#defenderMagicDef").attr("value", magicDefPanel);
			$("#defenderLife").attr("value", lifePanel);
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
		
		if(attackerSkills)
		{
			var attackBasic = 0;
			var intelBasic = 0;
			var physicDefBasic = 0;
			var magicDefBasic = 0;
			var lifeBasic = 0;
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
					}
				}
			}
			ai = ai+attackBasic;
			pi = pi+physicDefBasic;
			mi = mi+magicDefBasic;
			lii=lii+lifeBasic+40;  //jjc inc =0.4

			var attackPanel = Math.ceil(attack*(1+ai/100.0));
			var physicDefPanel = Math.ceil(physicDef*(1+pi/100.0));
			var magicDefPanel = Math.ceil(magicDef*(1+mi/100.0));
			var lifePanel = Math.ceil(life*(1+lii/100.0))*10;
		}
		$("#attacker-soldier-information").html("<p><b>"+attackerSoldier["name"]+"</b></p><p>攻击："+attackPanel+"&nbsp;&nbsp;&nbsp;防御："+physicDefPanel+"&nbsp;&nbsp;&nbsp;魔防："+magicDefPanel+"</p><p>生命："+lifePanel+"</p>");
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
		
		if(defenderSkills)
		{
			var attackBasic = 0;
			var intelBasic = 0;
			var physicDefBasic = 0;
			var magicDefBasic = 0;
			var lifeBasic = 0;
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
					}
				}
			}
			ai = ai+attackBasic;
			pi = pi+physicDefBasic;
			mi = mi+magicDefBasic;
			lii=lii+lifeBasic+40;  //jjc inc =0.4

			var attackPanel = Math.ceil(attack*(1+ai/100.0));
			var physicDefPanel = Math.ceil(physicDef*(1+pi/100.0));
			var magicDefPanel = Math.ceil(magicDef*(1+mi/100.0));
			var lifePanel = Math.ceil(life*(1+lii/100.0))*10;
		}
		$("#defender-soldier-information").html("<p><b>"+defenderSoldier["name"]+"</b></p><p>攻击："+attackPanel+"&nbsp;&nbsp;&nbsp;防御："+physicDefPanel+"&nbsp;&nbsp;&nbsp;魔防："+magicDefPanel+"</p><p>生命："+lifePanel+"</p>");
	}
}


















