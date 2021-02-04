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
	if(attackerSoldier && attacker) 
	{
		var attack = Math.floor(attackerSoldier["attack"]*(1+attacker["soldierAttackInc"]/100)*(1+0.2));
		var physicalDef = Math.floor(attackerSoldier["physicDef"]*(1+attacker["soldierPhysicDefInc"]/100)*(1+0.2));
		var magicDef = Math.floor(attackerSoldier["magicDef"]*(1+attacker["soldierMagicDefInc"]/100)*(1+0.2));
		var life = Math.floor(attackerSoldier["life"]*(1+attacker["soldierLifeInc"]/100)*14);
		$("#attacker-soldier-information").html("<p><b>"+attackerSoldier["name"]+"</b></p><p>攻击："+attack+"&nbsp;&nbsp;&nbsp;防御："+physicalDef+"&nbsp;&nbsp;&nbsp;魔防："+magicDef+"</p><p>生命："+life+"</p>");
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
	if(defenderSoldier && defender) 
	{
		var attack = Math.floor(defenderSoldier["attack"]*(1+defender["soldierAttackInc"]/100)*(1+0.2));
		var physicalDef = Math.floor(defenderSoldier["physicDef"]*(1+defender["soldierPhysicDefInc"]/100)*(1+0.2));
		var magicDef = Math.floor(defenderSoldier["magicDef"]*(1+defender["soldierMagicDefInc"]/100)*(1+0.2));
		var life = Math.floor(defenderSoldier["life"]*(1+defender["soldierLifeInc"]/100)*14);
		$("#defender-soldier-information").html("<p><b>"+defenderSoldier["name"]+"</b></p><p>攻击："+attack+"&nbsp;&nbsp;&nbsp;防御："+physicalDef+"&nbsp;&nbsp;&nbsp;魔防："+magicDef+"</p><p>生命："+life+"</p>");
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
		for(var i in defenderUserConditions)
		{
			var buff = $("<li class=\"list-group-item\">"+defenderUserConditions[i]["desc"]+"</li>");
			buff.appendTo($("#defender-user-condition-list"));
		}
	}
	


}
