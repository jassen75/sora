var fightInfo = {};

var sessionInfo = {};
var isAttacker = 1;
var heroLife = 1;
var soldierLife = 1;

// game data
var userData = {};
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
	} else
	{
		var role = "defender";
		isAttacker = 1;
	}
	fightInfo[role]={};
	sessionInfo[role] = {};
	fightInfo[role]["hero"] = hero;
	fightInfo[role]["soldier"] = {};
	fightInfo[role]["equip"] = {};
	fightInfo[role]["heroPanel"] = {};
	fightInfo[role]["soldierPanel"] = {};
	fightInfo[role]["userConditionChecked"]={};
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


function displayEquip(id, type, equips, role) {
	var list = $("#"+role+"-" + type + "-list");
	list.children("li").remove();
	//attackerEquip[type] = {};
	for (var i = 0; i < equips.length; i++) {
		var e = $("<li><img src=\"/fight/image/equip_" + equips[i]["id"]
				+ ".png\" alt=\"\" width=\"40\" height=\"40\"></img>"
				+ equips[i]["name"] + "</li>");
		e.attr("equipid", equips[i]["id"]);

		if(userData["equip"][id])
		{
			if (equips[i]["id"] == userData["equip"][id][type]) {
				
				fightInfo[role]["equip"][type] = equips[i];
			}
		}
		e.click(function(event) {
			var find = $(this).attr("equipid");
			equips.forEach(function(e) {
						if (e["id"] == find) {
							fightInfo[role]["equip"][type] = e;
							loadComplete(role);
						}
					})

				})
				
		e.appendTo(list);
	}
}

function loadComplete(role)
{
	var heroInc = generateHeroInc(fightInfo[role]["hero"], fightInfo[role]["equip"]);
	for(var i in heroInc)
	{
		fightInfo[role]["heroPanel"][i] = heroInc[i];
	}
	displayHero(role);
}

function displayHero(role) {
	if (role == "attacker") {
		if (fightInfo["attacker"]["isPhysic"]) {
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
	} else if(role == "defender") { 
		if (fightInfo["defender"]["isPhysic"]) {
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
	}
	var rolePic = $("#"+role+"Pic");
	rolePic.children("div").remove();
	var pic = $("<div><img src=\"/fight/image/" + fightInfo[role]["hero"]["id"]
			+ ".png\" alt=\"\" width=\"60\" height=\"86\"></img>"
			+ fightInfo[role]["hero"]["name"] + "</div>");
	pic.appendTo(rolePic);
	
	$("#"+role+"-soldier-list").children("li").remove();
	var soldiers = fightInfo[role]["hero"]["soldiers"];
	for (var i = 0; i < soldiers.length; i++) {
		var soldier = $("<li><img src=\"/fight/image/soldier_"
				+ soldiers[i]["id"]
				+ ".png\" alt=\"\" width=\"50\" height=\"62\"></img>"
				+ soldiers[i]["name"] + "</li>");
		soldier.attr("soldierid", soldiers[i]["id"]);
		soldier.appendTo($("#"+role+"-soldier-list"));

		if (fightInfo[role]["hero"]["defaultSoldierId"] == soldiers[i]["id"]) {
			fightInfo[role]["soldier"] = soldiers[i];
		}
		soldier.click(function(event) {
					var find = $(this).attr("soldierid");
					fightInfo[role]["hero"]["soldiers"].forEach(function(e) {
								if (e["id"] == find) {
									fightInfo[role]["soldier"] = e;
									sync(false);
								}
							})

				})
	}
	$("#"+role+"-land-list").children("li").remove();
	for (var i in lands) {
		var land = $("<li>" + lands[i] + "</li>");
		land.attr("landid", i);
		land.appendTo($("#"+role+"-land-list"));

		land.click(function(event) {
					fightInfo[role]["land"] = $(this).attr("landid");
					sync(false);
				})
	}
	
	$("#"+role+"-info").html("<p>生命&nbsp;" + fightInfo[role]["hero"]["life"] + "+"
			+ fightInfo[role]["heroPanel"]["lifeInc"] + "</p><p>攻击&nbsp;"
			+ fightInfo[role]["hero"]["attack"] + "+" + fightInfo[role]["heroPanel"]["attackInc"]
			+ "</p><p>智力&nbsp;" + fightInfo[role]["hero"]["intel"] + "+"
			+ fightInfo[role]["heroPanel"]["intelInc"] + "</p><p>防御&nbsp;"
			+ fightInfo[role]["hero"]["physic"] + "+"
			+ fightInfo[role]["heroPanel"]["physicInc"] + "</p><p>魔防&nbsp;"
			+ fightInfo[role]["hero"]["magic"] + "+"
			+ fightInfo[role]["heroPanel"]["magicInc"] + "</p><p>技巧&nbsp;"
			+ fightInfo[role]["hero"]["tech"] + "+" + fightInfo[role]["heroPanel"]["techInc"]
			+ "</p>");

	$("#"+role+"-soldier-information").html("");
	
	if(fightInfo[role]["equip"])
	{
		for(var i in equipPart )
		{
			var part = equipPart[i];
			$("#"+role+"-"+equipPart[i]).children("div").remove();
			if(fightInfo[role]["equip"][part])
			{
				var pic = $("<div><img src=\"/fight/image/equip_"+fightInfo[role]["equip"][equipPart[i]]["id"]+".png\" alt=\"\" width=\"60\" height=\"60\"></img><p>"
						+fightInfo[role]["equip"][equipPart[i]]["name"]+"</p></div>");
				pic.insertBefore($("#"+role+"-"+equipPart[i]+" > button"));			
			} 
		}
	} 
	
	$("#"+role+"-critical").children("li").remove();
	if(fightInfo[role]["heroCriticalChecked"] == undefined)
	{
		fightInfo[role]["heroCriticalChecked"] = fightInfo[role]["heroPanel"]["criticalProbInc"]+fightInfo[role]["heroPanel"]["tech"]/10>70;
	}
	var buttonClass = fightInfo[role]["heroCriticalChecked"] ? "btn-warning":"btn-default";
	var buff = $("<li class=\"list-group-item\">英雄暴击</li>");
	var button = $("<button type=\"button\" class=\"btn "+buttonClass+"\">暴击</button>");
	button.attr("uc-name", name);
	button.click(function(e){
	
		if (fightInfo[role]["heroCriticalChecked"])
		{
			$(this).removeClass("btn-warning");
			$(this).addClass("btn-default");
			fightInfo[role]["heroCriticalChecked"] = false;
		} else
		{
			$(this).removeClass("btn-default");
			$(this).addClass("btn-warning");
			fightInfo[role]["heroCriticalChecked"] = true;
		}
	});
	button.appendTo(buff);
	buff.appendTo($("#"+role+"-critical"));	
	
	if(fightInfo[role]["soldierCriticalChecked"] == undefined)
	{
		fightInfo[role]["soldierCriticalChecked"] = fightInfo[role]["soldierPanel"]["criticalProbInc"]>70;
	}
	var buff = $("<li class=\"list-group-item\">士兵暴击</li>");
	if(fightInfo[role]["soldierPanel"]["criticalProbInc"]>0) 
	{
		var buttonClass = (fightInfo[role]["soldierCriticalChecked"]) ? "btn-warning":"btn-default";

		var button = $("<button type=\"button\" class=\"btn "+buttonClass+"\">暴击</button>");
		button.attr("uc-name", name);
		button.click(function(e){
		
			if (fightInfo[role]["soldierCriticalChecked"])
			{
				$(this).removeClass("btn-warning");
				$(this).addClass("btn-default");
				fightInfo[role]["soldierCriticalChecked"] = false;
			} else
			{
				$(this).removeClass("btn-default");
				$(this).addClass("btn-warning");
				fightInfo[role]["soldierCriticalChecked"] = true;
			}
			//loadSkillData();
		});
		button.appendTo(buff);
	}
	buff.appendTo($("#defender-critical"));	
	
	if(!fightInfo[role]["land"])	
	{
		fightInfo[role]["land"]="Flat";
	}
	$("#"+role+"-land-information").html("<p><b>地形:"+lands[fightInfo[role]["land"]]+"</b></p>");

	displayAttackerAction();
	
	sync(true);
}

function displayAttackerAction()
{
	//alert(JSON.stringify(fightInfo));
	$("#attacker-action-list").children("li").remove();
	var actions = fightInfo["attacker"]["hero"]["actions"];
	for (var i = 0; i < actions.length; i++) {
		var action = $("<li><img src=\"/fight/image/action_"
				+ actions[i]["id"]
				+ ".png\" alt=\"\" width=\"40\" height=\"40\"></img>"
				+ actions[i]["name"] + "</li>");
		action.attr("actionid", actions[i]["id"]);
		action.appendTo($("#attacker-action-list"));

		if (actions[i]["id"] == 10001 || actions[i]["id"] == 10002) {
			fightInfo["attacker"]["action"] = actions[i];
		}
		action.click(function(event) {
					var find = $(this).attr("actionid");
					fightInfo["attacker"]["hero"]["actions"].forEach(function(e) {
								if (e["id"] == find) {
									fightInfo["attacker"]["action"] = e;
									sync(false);
								}
							})

				})
	}
}

function sync(refresh)
{
	if(fightInfo["attacker"] && fightInfo["defender"])
	{
		$.ajax({
			type : "POST",
			url : "/fight/sync",
			data : JSON.stringify(fightInfo),
			dataType : "json",
			contentType : "application/json",
			processData : false,
			success : function(data) {
				fightInfo["attacker"]["heroPanel"] = data["fightInfo"]["attacker"]["heroPanel"];
				fightInfo["defender"]["heroPanel"] = data["fightInfo"]["defender"]["heroPanel"];
				fightInfo["attacker"]["soldierPanel"] = data["fightInfo"]["attacker"]["soldierPanel"];
				fightInfo["defender"]["soldierPanel"] = data["fightInfo"]["defender"]["soldierPanel"];
				sessionInfo["attacker"]["checkedSkills"] = data["attackerSkills"];
				sessionInfo["defender"]["checkedSkills"] = data["defenderSkills"];
				
				sessionInfo["attacker"]["userConditions"] = data["attackerUserConditions"];
				sessionInfo["defender"]["userConditions"] = data["defenderUserConditions"];	
					
				//alert(JSON.stringify(fightInfo));
				syncComplete("attacker");
				syncComplete("defender");
				
				if(refresh)
				{
					sync(false);
				}
			},
			error : function(jqXHR) {
			}
		});
	}
}


function syncComplete(role)
{
	fightInfo[role]["heroLeftLife"] = Math.ceil(fightInfo[role]["heroPanel"]["life"]*heroLife);
	fightInfo[role]["soldierLeftLife"] = Math.ceil(fightInfo[role]["soldierPanel"]["life"]*soldierLife);
	buildSkill(role);
	buildUserCondition(role);
	buildHeroPanel(role);
	buildSoldierPanel(role)
}


function buildHeroPanel(role) {
	$("#"+role+"Attack").attr("value", fightInfo[role]["heroPanel"]["attack"]);
	$("#"+role+"Intel").attr("value", fightInfo[role]["heroPanel"]["intel"]);
	$("#"+role+"hysicDef").attr("value", fightInfo[role]["heroPanel"]["physic"]);
	$("#"+role+"MagicDef").attr("value", fightInfo[role]["heroPanel"]["magic"]);
	$("#"+role+"Life").attr("value", fightInfo[role]["heroPanel"]["life"]);
	var detail="<p>";
	if(fightInfo[role]["action"])
	{
		detail+="<b>"+fightInfo[role]["action"]["name"]+"</b>&nbsp;&nbsp;&nbsp;<b>"+fightInfo[role]["action"]["coefficient"]+"倍</b></p>";
	}
	
	detail+="<p><b>增伤："+fightInfo[role]["heroPanel"]["damageInc"]+
	"&nbsp;&nbsp;&nbsp;物理减伤："+fightInfo[role]["heroPanel"]["physicDamageDec"]+
	"&nbsp;&nbsp;&nbsp;魔法减伤："+fightInfo[role]["heroPanel"]["magicDamageDec"]+"</b></p>"+
	"<p>技巧："+fightInfo[role]["heroPanel"]["tech"]+
	"&nbsp;&nbsp;&nbsp;暴击："+fightInfo[role]["heroPanel"]["criticalProbInc"]+
	"&nbsp;&nbsp;&nbsp;暴伤："+fightInfo[role]["heroPanel"]["criticalDamageInc"]+
	"&nbsp;&nbsp;&nbsp;防爆:"+fightInfo[role]["heroPanel"]["criticalProbDec"]+
	"&nbsp;&nbsp;&nbsp;减爆伤："+fightInfo[role]["heroPanel"]["criticalDamageDec"]+"</p>";
	$("#"+role+"Detail").html(detail);
	
}

function buildSoldierPanel(role) {
	$("#"+role+"-soldier-information").html(
			"<p><b>"+fightInfo[role]["soldier"]["name"]+
			"</b></p><p>攻击："+fightInfo[role]["soldierPanel"]["attack"]+
			"&nbsp;&nbsp;&nbsp;防御："+fightInfo[role]["soldierPanel"]["physic"]+
			"&nbsp;&nbsp;&nbsp;魔防："+fightInfo[role]["soldierPanel"]["magic"]+
			"&nbsp;&nbsp;&nbsp;生命："+fightInfo[role]["soldierPanel"]["life"]+
			"</p><p><b>增伤:"+fightInfo[role]["soldierPanel"]["damageInc"]+
			"&nbsp;&nbsp;&nbsp;物理减伤:"+fightInfo[role]["soldierPanel"]["physicDamageDec"]+
			"&nbsp;&nbsp;&nbsp;魔法减伤:"+fightInfo[role]["soldierPanel"]["magicDamageDec"]+"</b></p><p>"+
			"暴击："+fightInfo[role]["soldierPanel"]["criticalProbInc"]+
			"&nbsp;&nbsp;&nbsp;暴伤："+fightInfo[role]["soldierPanel"]["criticalDamageInc"]+
			"&nbsp;&nbsp;&nbsp;防爆："+fightInfo[role]["soldierPanel"]["criticalProbDec"]+
			"&nbsp;&nbsp;&nbsp;减爆伤："+fightInfo[role]["soldierPanel"]["criticalDamageDec"]+"</p>");
	
}

function buildSkill(role) {
	$("#"+role+"-buf-list").children("li").remove();
	if (sessionInfo[role]["checkedSkills"]) {
		for (var i = 0; i < sessionInfo[role]["checkedSkills"].length; i++) {
			var valid = sessionInfo[role]["checkedSkills"][i].valid
					? "valid-skill"
					: "not-valid-skill";
			var buff = $("<li class=\"list-group-item " + valid + "\">"
					+ sessionInfo[role]["checkedSkills"][i]["skill"]["desc"] + "</li>");
			buff.appendTo($("#"+role+"-buf-list"));
		}

	}
}

function buildUserCondition(role) {
	$("#"+role+"-user-condition-list").children("li").remove();
	if (sessionInfo[role]["userConditions"]) {
		for (var name in sessionInfo[role]["userConditions"][role]) {
			var checked = sessionInfo[role]["userConditions"][role][name];
			if (checked == undefined) {
				checked = sessionInfo[role]["userConditions"][role][name]["defaultValid"];
			}
			var buttonClass = checked ? "btn-success" : "btn-default";
			var buff = $("<li class=\"list-group-item\">"
					+ userConditions[role][name]["desc"] + "</li>");
			var button = $("<button type=\"button\" class=\"btn " + buttonClass
					+ "\">选择</button>");
			button.attr("uc-name", name);
			button.click(function(e) {

				if ($(this).hasClass("btn-success")) {
					$(this).removeClass("btn-success");
					$(this).addClass("btn-default");
					fightInfo[role]["userConditionChecked"][$(this).attr("uc-name")] = false;
				} else {
					$(this).removeClass("btn-default");
					$(this).addClass("btn-success");
					fightInfo[role]["userConditionChecked"][$(this).attr("uc-name")] = true;
				}
				sync(false);
			});
			button.appendTo(buff);
			buff.appendTo($("#"+role+"-user-condition-list"));
		}
	}
}
