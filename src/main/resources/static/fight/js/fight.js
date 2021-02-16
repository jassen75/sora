// session info
var attacker;
var defender;
var attackerSoldier;
var defenderSoldier;
var attackerSkills;
var defenderSkills;
var attackerUserConditions;
var defenderUserConditions;
var isAttacker = 1;

// user input
var fightInfo = {};
var attackerUserConditionChecked = {};
var defenderUserConditionChecked = {};
var attackerHeroCriticalChecked;
var attackerSoldierCriticalChecked;
var defenderHeroCriticalChecked;
var defenderSoldierCriticalChecked;
var attackerAction;
var attackerEquip = {};
var defenderEquip = {};

// game data
var gameData = {};
var lands = {
	"Flat" : "平地",
	"Water" : "水",
	"Wood" : "树林",
	"Wall" : "城墙",
	"Grass" : "草地",
	"Mountain" : "山地"
};

var equipPart=["weapon","armor", "helmet","jewelry"];

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

			$("#calculate").click(function() {
						calculate();
					});

			$("#import_fm").click(function() {
						importFm();

					});

			$.get("/fight/fm.csv", function(data) {
						parseCSV(data);
					});

		});

function buildHeroPics(data) {
	$("#hero_pics").children("div").remove();
	for (var j = 0; j < data.length; j++) {
		var pic = $("<div onclick=\"chooseHero(" + data[j]["id"]
				+ ")\" class=\"hero_pic\"><img src=\"/fight/image/"
				+ data[j]["id"]
				+ ".png\" alt=\"\" width=\"60\" height=\"86\"></img></div>");
		pic.appendTo($("#hero_pics"));
	}
}

function loadHeroData() {
	$.ajax({
				type : "GET",
				url : "/fight/heros",
				dataType : "json",
				success : function(data) {
					buildHeroPics(data);
				},
				error : function(jqXHR) {
				}
			});
}

function buildFight() {
	var request = {

	};
	if (attacker) {
		request["attackerHeroId"] = attacker["id"];
	}
	if (defender) {
		request["defenderHeroId"] = defender["id"];
	}
	if (attackerSoldier) {
		request["attackerSoldierId"] = attackerSoldier["id"];
	}
	if (defenderSoldier) {
		request["defenderSoldierId"] = defenderSoldier["id"];
	}

	if (attackerAction) {
		request["attackerActionId"] = attackerAction["id"];

	}
	request["attackerUserConditionChecked"] = attackerUserConditionChecked;
	request["defenderUserConditionChecked"] = defenderUserConditionChecked;

	fightInfo["attackerHeroLeftLife"] = fightInfo["attackerLife"];
	fightInfo["attackerSoldierLeftLife"] = fightInfo["attackerSoldierLife"];
	fightInfo["defenderHeroLeftLife"] = fightInfo["defenderLife"];
	fightInfo["defenderSoldierLeftLife"] = fightInfo["defenderSoldierLife"];

	for (var i in fightInfo) {
		request[i] = fightInfo[i];
	}

	return request;

}
function loadSkillData(refresh) {
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

					if (refresh == true) {
						loadSkillData(false);
					}
				},
				error : function(jqXHR) {
				}
			});
}

function chooseHero(id) {
	$.ajax({
				type : "GET",
				url : "/fight/heros/" + id,
				dataType : "json",
				success : function(data) {
					buildHero(data);
				},
				error : function(jqXHR) {
				}
			});

}
function buildHero(hero) {
	if (isAttacker == 1) {
		var role = "attacker";
		isAttacker = 0;
		attacker = hero;
		attackerSoldier = undefined;
		attackerHeroCriticalChecked = undefined;
		attackerSoldierCriticalChecked = undefined;
	} else
	{
		var role = "defender";
		isAttacker = 1;
		defender = hero;
		defenderSoldier = undefined;
		defenderHeroCriticalChecked = undefined;
		defenderSoldierCriticalCheckede = undefined;
	}
	loadWeapon(hero["id"], role);
}

function loadWeapon(heroId,  role) {
	$.ajax({
			type : "GET",
			url : "/fight/equips/" + heroId + "/Weapon",
			dataType : "json",
			success : function(response) {
				displayEquip(heroId, "weapon", response, role);
				loadArmor(heroId, role);
			},
			error : function(jqXHR) {
			}
	});
}

function loadArmor(heroId, role) {
	$.ajax({
			type : "GET",
			url : "/fight/equips/" + heroId + "/Armor",
			dataType : "json",
			success : function(response) {
				displayEquip(heroId, "armor", response , role);
				loadHelmet(heroId, role);
			},
			error : function(jqXHR) {

			}
	});
}

function loadHelmet(heroId, role) {
	$.ajax({
			type : "GET",
			url : "/fight/equips/" + heroId + "/Helmet",
			dataType : "json",
			success : function(response) {
				displayEquip(heroId, "helmet", response, role);
				loadJewelry(heroId, role);
			},
			error : function(jqXHR) {
			}
	});
}

function loadJewelry(heroId, role) {
	$.ajax({
			type : "GET",
			url : "/fight/equips/" + heroId + "/Jewelry",
			dataType : "json",
			success : function(response) {
				displayEquip(heroId, "jewelry", response, role);
				
				loadComplete(role);
				
			},
			error : function(jqXHR) {
			}
	});
}

function loadComplete(role)
{
	if(role == "attacker")
	{
		var heroInc = generateHeroInc(attacker, attackerEquip);
		for(var i in heroInc)
		{
			attacker[i] = heroInc[i];
		}
		
	} else if (role == "defender")
	{
		var heroInc = generateHeroInc(defender, defenderEquip);
		for(var i in heroInc)
		{
			defender[i] = heroInc[i];
		}
	}
	displayHero(role);
}


function displayHero(role) {
	if (role == "attacker") {
		$("#attackerPic").children("div").remove();
		var pic = $("<div><img src=\"/fight/image/" + attacker["id"]
				+ ".png\" alt=\"\" width=\"60\" height=\"86\"></img>"
				+ attacker["name"] + "</div>");
		pic.appendTo($("#attackerPic"));

		if (attacker["isPhysic"]) {
			$("#aa").show();
			$("#ai").hide();

			$("#dp").show();
			$("#dm").hide();
		} else {
			$("#ai").show();
			$("#aa").hide();

			$("#dm").show();
			$("#dp").hide();
		}

		$("#attacker-soldier-list").children("li").remove();
		var soldiers = attacker["soldiers"];
		for (var i = 0; i < soldiers.length; i++) {
			var soldier = $("<li><img src=\"/fight/image/soldier_"
					+ soldiers[i]["id"]
					+ ".png\" alt=\"\" width=\"50\" height=\"62\"></img>"
					+ soldiers[i]["name"] + "</li>");
			soldier.attr("soldierid", soldiers[i]["id"]);
			soldier.appendTo($("#attacker-soldier-list"));

			if (attacker["defaultSoldierId"] == soldiers[i]["id"]) {
				attackerSoldier = soldiers[i];
			}
			soldier.click(function(event) {
						var find = $(this).attr("soldierid");
						attacker["soldiers"].forEach(function(e) {
									if (e["id"] == find) {
										attackerSoldier = e;
										refreshAttacker();
									}
								})

					})
		}

		$("#attacker-action-list").children("li").remove();
		attackerAction = undefined;
		var actions = attacker["actions"];
		for (var i = 0; i < actions.length; i++) {
			var action = $("<li><img src=\"/fight/image/action_"
					+ actions[i]["id"]
					+ ".png\" alt=\"\" width=\"40\" height=\"40\"></img>"
					+ actions[i]["name"] + "</li>");
			action.attr("actionid", actions[i]["id"]);
			action.appendTo($("#attacker-action-list"));

			if (actions[i]["id"] == 10001 || actions[i]["id"] == 10002) {
				attackerAction = actions[i];
			}
			action.click(function(event) {
						var find = $(this).attr("actionid");
						attacker["actions"].forEach(function(e) {
									if (e["id"] == find) {
										attackerAction = e;
										refreshAttacker();
									}
								})

					})
		}

		$("#attacker-land-list").children("li").remove();
		for (var i in lands) {
			var land = $("<li>" + lands[i] + "</li>");
			land.attr("landid", i);
			land.appendTo($("#attacker-land-list"));

			land.click(function(event) {
						fightInfo["attackerLand"] = $(this).attr("landid");
						refreshAttacker();
					})
		}

		refreshAttacker();

	} else if(role == "defender") { 
		$("#defenderPic").children("div").remove();
		var pic = $("<div><img src=\"/fight/image/" + defender["id"]
				+ ".png\" alt=\"\" width=\"60\" height=\"86\"></img>"
				+ defender["name"] + "</div>");
		pic.appendTo($("#defenderPic"));

		if (defender["isPhysic"]) {
			$("#da").show();
			$("#di").hide();

			$("#ap").show();
			$("#am").hide();
		} else {
			$("#da").hide();
			$("#di").show();

			$("#am").show();
			$("#ap").hide();
		}

		$("#defender-soldier-list").children("li").remove();
		var soldiers = defender["soldiers"];
		for (var i = 0; i < soldiers.length; i++) {
			if (defender["defaultSoldierId"] == soldiers[i]["id"]) {
				defenderSoldier = soldiers[i];
			}
			var soldier = $("<li><img src=\"/fight/image/soldier_"
					+ soldiers[i]["id"]
					+ ".png\" alt=\"\" width=\"50\" height=\"62\"></img>"
					+ soldiers[i]["name"] + "</li>");
			soldier.attr("soldierid", soldiers[i]["id"]);
			soldier.appendTo($("#defender-soldier-list"));
			soldier.click(function(event) {
						var find = $(this).attr("soldierid");
						defender["soldiers"].forEach(function(e) {
									if (e["id"] == find) {
										defenderSoldier = e;
										refreshDefender();
									}
								})
					})
		}

		$("#defender-land-list").children("li").remove();
		for (var i in lands) {
			var land = $("<li>" + lands[i] + "</li>");
			land.attr("landid", i);
			land.appendTo($("#defender-land-list"));

			land.click(function(event) {
						fightInfo["defenderLand"] = $(this).attr("landid");
						refreshAttacker();
					})
		}

		refreshDefender();
	}

	if (fightInfo["defenderLand"] == undefined) {
		fightInfo["defenderLand"] = "Flat";
	}

	if (fightInfo["attackerLand"] == undefined) {
		fightInfo["attackerLand"] = "Flat";
	}
}

function displayEquip(id, type, equips, role) {
	var list = $("#"+role+"-" + type + "-list");
	list.children("li").remove();
	//attackerEquip[type] = {};
	for (var i = 0; i < equips.length; i++) {
		var e = $("<li><img src=\"/fight/image/equip_" + equips[i]["id"]
				+ ".png\" alt=\"\" width=\"40\" height=\"40\"></img>"
				+ equips[i]["name"] + "</li>");
		e.attr("equipid", equips[i]["id"]);

		if(gameData["equip"][id])
		{
			if (equips[i]["id"] == gameData["equip"][id][type]) {
				if(role=="attacker")
				{
					attackerEquip[type] = equips[i];
				} else 
				{
					defenderEquip[type] = equips[i];
				}
			}
		}
		e.click(function(event) {
			var find = $(this).attr("equipid");
			equips.forEach(function(e) {
						if (e["id"] == find) {
							if(role == "attacker")
							{
								attackerEquip[type] = e;
								refreshAttacker();
							} else 
							{
								defenderEquip[type] = e;
								refreshDefender();
							}
						}
					})

				})
				
		e.appendTo(list);
	}
}

function refreshAttacker() {
	if (attacker) {
		$("#attacker-info").html("<p>生命&nbsp;" + attacker["life"] + "+"
						+ attacker["lifeInc"] + "</p><p>攻击&nbsp;"
						+ attacker["attack"] + "+" + attacker["attackInc"]
						+ "</p><p>智力&nbsp;" + attacker["intel"] + "+"
						+ attacker["intelInc"] + "</p><p>防御&nbsp;"
						+ attacker["physicDef"] + "+"
						+ attacker["physicDefInc"] + "</p><p>魔防&nbsp;"
						+ attacker["magicDef"] + "+"
						+ attacker["magicDefInc"] + "</p><p>技巧"
						+ attacker["tech"] + "+" + attacker["techInc"]
						+ "</p>");

		$("#attacker-soldier-information").html("");
	}
	
	if(attackerEquip)
	{
		for(var i in equipPart )
		{
			var part = equipPart[i];
			if(attackerEquip[part])
			{
				var pic = $("<div><img src=\"/fight/image/equip_"+attackerEquip[equipPart[i]]["id"]+".png\" alt=\"\" width=\"60\" height=\"60\"></img><p>"+attackerEquip[equipPart[i]]["name"]+"</p></div>");
				$("#attacker-"+equipPart[i]).children("div").remove();
				pic.insertBefore($("#attacker-"+equipPart[i]+" > button"));			
			} 
		}

	} 
	loadSkillData(true);
}

function refreshDefender() {
	if (defender) {
		$("#defender-info").html("<p>生命&nbsp;" + defender["life"] + "+"
						+ defender["lifeInc"] + "</p><p>攻击&nbsp;"
						+ defender["attack"] + "+" + defender["attackInc"]
						+ "</p><p>智力&nbsp;" + defender["intel"] + "+"
						+ defender["intelInc"] + "</p><p>防御&nbsp;"
						+ defender["physicDef"] + "+"
						+ defender["physicDefInc"] + "</p><p>魔防&nbsp;"
						+ defender["magicDef"] + "+"
						+ defender["magicDefInc"] + "</p><p>技巧"
						+ defender["tech"] + "+" + defender["techInc"]
						+ "</p>");
		$("#defender-soldier-information").html("");
	}
	
	if(defenderEquip)
	{
		for(var i in equipPart )
		{
			var part = equipPart[i];
			if(defenderEquip[part])
			{
				var pic = $("<div><img src=\"/fight/image/equip_"+defenderEquip[equipPart[i]]["id"]+".png\" alt=\"\" width=\"60\" height=\"60\"></img><p>"+defenderEquip[equipPart[i]]["name"]+"</p></div>");
				$("#defender-"+equipPart[i]).children("div").remove();
				pic.insertBefore($("#defender-"+equipPart[i]+" > button"));			
			} 
		}

	} 
	loadSkillData(true);
}

function buildSkill() {
	$("#attacker-buf-list").children("li").remove();
	if (attackerSkills) {
		for (var i = 0; i < attackerSkills.length; i++) {
			var valid = attackerSkills[i].valid
					? "valid-skill"
					: "not-valid-skill";
			var buff = $("<li class=\"list-group-item " + valid + "\">"
					+ attackerSkills[i]["skill"]["desc"] + "</li>");
			buff.appendTo($("#attacker-buf-list"));
		}

	}

	$("#defender-buf-list").children("li").remove();
	if (defenderSkills) {
		for (var i = 0; i < defenderSkills.length; i++) {
			var valid = defenderSkills[i].valid
					? "valid-skill"
					: "not-valid-skill";
			var buff = $("<li class=\"list-group-item " + valid + "\">"
					+ defenderSkills[i]["skill"]["desc"] + "</li>");
			buff.appendTo($("#defender-buf-list"));
		}

	}
	buildUserCondition();
	calculatePanel();
}

function buildUserCondition() {
	$("#attacker-user-condition-list").children("li").remove();
	if (attackerUserConditions) {
		for (var name in attackerUserConditions) {
			var checked = attackerUserConditionChecked[name];
			if (checked == undefined) {
				checked = attackerUserConditions[name]["defaultValid"];
			}
			var buttonClass = checked ? "btn-success" : "btn-default";
			var buff = $("<li class=\"list-group-item\">"
					+ attackerUserConditions[name]["desc"] + "</li>");
			var button = $("<button type=\"button\" class=\"btn " + buttonClass
					+ "\">选择</button>");
			button.attr("uc-name", name);
			button.click(function(e) {

				if ($(this).hasClass("btn-success")) {
					$(this).removeClass("btn-success");
					$(this).addClass("btn-default");
					attackerUserConditionChecked[$(this).attr("uc-name")] = false;
				} else {
					$(this).removeClass("btn-default");
					$(this).addClass("btn-success");
					attackerUserConditionChecked[$(this).attr("uc-name")] = true;
				}
				loadSkillData();
			});
			button.appendTo(buff);
			buff.appendTo($("#attacker-user-condition-list"));
		}
	}

	$("#defender-user-condition-list").children("li").remove();
	if (defenderUserConditions) {
		for (var name in defenderUserConditions) {
			var checked = defenderUserConditionChecked[name];
			if (checked == undefined) {
				checked = defenderUserConditions[name]["defaultValid"];
			}
			var buttonClass = checked ? "btn-success" : "btn-default";
			var buff = $("<li class=\"list-group-item\">"
					+ defenderUserConditions[name]["desc"] + "</li>");
			var button = $("<button type=\"button\" class=\"btn " + buttonClass
					+ "\">选择</button>");
			button.attr("uc-name", name);
			button.click(function(e) {

				if ($(this).hasClass("btn-success")) {
					$(this).removeClass("btn-success");
					$(this).addClass("btn-default");
					defenderUserConditionChecked[$(this).attr("uc-name")] = false;
				} else {
					$(this).removeClass("btn-default");
					$(this).addClass("btn-success");
					defenderUserConditionChecked[$(this).attr("uc-name")] = true;
				}
				loadSkillData();
			});
			button.appendTo(buff);
			buff.appendTo($("#defender-user-condition-list"));
		}
	}
}
