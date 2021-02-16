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
var attackerHeroCriticalChecked;
var attackerSoldierCriticalChecked;
var defenderHeroCriticalChecked;
var defenderSoldierCriticalChecked;
var isAttacker = 1;
var fightInfo={};
var gameData={};
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
	
	$("#import_fm").click(function(){
		importFm();
		
	});
	
	 $.get("/fight/fm.csv", function(data){
		parseCSV(data);
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
			
			var equipTypes = ["Weapon", "Armor", "Helmet", "Jewelry"];

			for(var i=0; i<1; i++)
			{
				var eq = equipTypes[i].toLowerCase();
				$.ajax({
					type : "GET",
					url : "/fight/equips/"+id+"/"+equipTypes[i],
					dataType : "json",
					success : function(data) {
						displayEquip(eq, data);
					},
					error : function(jqXHR) {
						// alert("Error: "+jqXHR.status);
					}
				});
			}
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
		attackerHeroCriticalChecked = undefined;
		attackerSoldierCriticalChecked = undefined;
		
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
		
		attackerSoldier = undefined;
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
		attackerAction = undefined;
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
		defenderHeroCriticalChecked = undefined;
        defenderSoldierCriticalCheckede = undefined;

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
		
		defenderSoldier = undefined;
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

function displayEquip(type, equips) 
{
	var list = $("#attacker-"+type+"-list");
	list.children("li").remove();
	attackerEquip[type] = {};
	alert(equips.length);
	for(var i=0; i<equips.length; i++)
	{
		var equip = $("<li><img src=\"/fight/image/equip_"+equips[i]["id"]+".png\" alt=\"\" width=\"40\" height=\"40\"></img>"+equips[i]["name"]+"</li>");
		equip.attr("equipid", equips[i]["id"]);
		equip.appendTo(list);
		
		if(equips[i]["id"] == fightInfo["equip"][attacker["id"]][type])
		{
			attackerEquip[type] = equips[i];
		}
		equip.click(function(event){
			var find = $(this).attr("equipid");
			equips.forEach(function(e){
				if(e["id"]==find)
				{
					attackerEquip[type] = e;
					refreshAttacker();
				}
			})
			
		})
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
		"</p><p>技巧"+attacker["tech"]+"+"+attackerEquip["techInc"]+"</p>");
		
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
		"</p><p>技巧"+defender["tech"]+"+"+attackerEquip["techInc"]+"</p>");
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



















