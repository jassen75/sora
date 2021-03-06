var fightInfo = {};

var sessionInfo = {};
var isAttacker = 1;
var heroLife = 1;
var soldierLife = 1;

// game data
var userData = {};

var stage = 0;
var editingRole;
var editingEquipPart;

var lands = {
	"Flat" : "平地",
	"Water" : "水",
	"Wood" : "树林",
	"Wall" : "城墙",
	"Grass" : "草地",
	"Mountain" : "山地"
};

var equipPart = [ "weapon", "armor", "helmet", "jewelry" ];

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
	
	$("#reset").click(function() {
		if(stage==2)
		{
			fightInfo["attacker"]["heroLeftLife"] = fightInfo["attacker"]["heroPanel"]["life"];
			fightInfo["attacker"]["soldierLeftLife"] = fightInfo["attacker"]["soldierPanel"]["life"];
			fightInfo["defender"]["heroLeftLife"] = fightInfo["defender"]["heroPanel"]["life"];
			fightInfo["defender"]["soldierLeftLife"] = fightInfo["defender"]["soldierPanel"]["life"];
			refreshLife("attacker");
			refreshLife("defender");
			sync(false);
		}
	});

	$("#import_fm").click(function() {
		importFm();

	});
	
	$("#export_fm").click(function() {
		exportFm();

	});
	
	$("#attackerHeroBar_c").click(function(e) {
		if(stage==2)
		{
			if(event.offsetX && event.offsetX < $(this).width())
			{
				fightInfo["attacker"]["heroLeftLife"] = Math.floor(fightInfo["attacker"]["heroPanel"]["life"] * smooth(event.offsetX / $(this).width()));
			}
			refreshLife("attacker");
			refreshLife("defender");
			sync(false);
		}
	});

	$("#attackerSoldierBar_c").click(function(e) {
		if(stage==2)
		{
			if(event.offsetX && event.offsetX < $(this).width())
			{
				fightInfo["attacker"]["soldierLeftLife"] = Math.floor(fightInfo["attacker"]["soldierPanel"]["life"] * smooth(event.offsetX / $(this).width()));
			}
			refreshLife("attacker");
			refreshLife("defender");
			sync(false);
		}
	});
	
	$("#defenderHeroBar_c").click(function(e) {
		if(stage==2)
		{
			
			if(event.offsetX && event.offsetX < $(this).width())
			{
				fightInfo["defender"]["heroLeftLife"] = Math.floor(fightInfo["defender"]["heroPanel"]["life"] * smooth(event.offsetX / $(this).width()));
			}
			refreshLife("attacker");
			refreshLife("defender");
			sync(false);
		}
	});
	
	$("#defenderSoldierBar_c").click(function(e) {
		if(stage==2)
		{
			if(event.offsetX && event.offsetX < $(this).width())
			{
				fightInfo["defender"]["soldierLeftLife"] = Math.floor(fightInfo["defender"]["soldierPanel"]["life"] * smooth(event.offsetX / $(this).width()));
			}
			refreshLife("attacker");
			refreshLife("defender");
			sync(false);
		}
	});
	$.get("/fight/fm.csv", function(data) {
		parseCSV(data);
	});

	$('#equip-editor').on('show.bs.modal', function (event) {
		var button = $(event.relatedTarget) // Button that triggered the modal
  		editingEquipPart = button.data('equip');
  		editingRole = button.data('role');
  		//alert("equip:"+equipPart+", role=="+role);
  		if(editingEquipPart!="jewelry")
  		{
  			$("#fm-edit-critical").hide();
  		} else
  		{
  			$("#fm-edit-critical").show();
  		}
  		var heroId = fightInfo[editingRole]["hero"]["id"];
  		var fmInfo = userData["fm"][heroId][editingEquipPart];
  		//alert(JSON.stringify(fmInfo));
  		if(fmInfo)
  		{
  			$("#equip-editor").find(":text").attr("value", "");
  			for(var i in fmInfo["fm_info"])
  			{
  				//alert("i=="+i+", fminfo=="+fmInfo["fm_info"][i]);
  				$("#fm-edit-"+i).attr("value", fmInfo["fm_info"][i]);
  			}
  		}
  		$("#fm-edit-desc").html(displayFM(fmInfo));
  		$("#fm-edit-type").attr("value", fmInfo["fm_type"]);
  		$("#fm-edit-type").text(fm_type[fmInfo["fm_type"]]);
  		
	});
	
	$("#saveFm").click(function(event) {
		var fm = {};
		var t = ["attackInc", "attack", "lifeInc","life","intelInc","intel","physicInc","physic","magicInc","magic","criticalInc"];
		for(var i in t)
		{
			//var value = $("#fm-edit-"+t[i]).attr("value");
			
			var value = $("#fm-edit-"+t[i]).val();
			if(value)
			{
				fm[t[i]] = parseInt(value);
			} else
			{
				if(fm[t[i]])
				{
					delete fm[t[i]];
				}
			}
		}
		var heroId = fightInfo[editingRole]["hero"]["id"];
		userData["fm"][heroId][editingEquipPart]["fm_info"] = fm;
		userData["fm"][heroId][editingEquipPart]["fm_type"] = $("#fm-edit-type").attr("value");
		$("#fm-edit-desc").html(displayFM(userData["fm"][heroId][editingEquipPart]));
		loadComplete(editingRole);
		refreshTable();
	});
	$("#fm-edit-type-list > li").click(function (e) {
 		//alert("show"+$(this).attr("data-fm-type"));
		var t = $(this).attr("data-fm-type");
		$("#fm-edit-type").attr("value", t);
  		$("#fm-edit-type").text(fm_type[t]);
  		var heroId = fightInfo[editingRole]["hero"]["id"];
		
	});

});
function smooth(c)
{
	if(c>0.98)
	{
		return 1;
	}
	if(c<0.02)
	{
		return 0;
	}
	return c;
}

function refreshLife(role)
{
	$("#"+role+"SoldierBar").text(fightInfo[role]["soldierLeftLife"]+"/"+fightInfo[role]["soldierPanel"]["life"]);
	$("#"+role+"SoldierBar").attr("style", "width:"+Math.ceil(fightInfo[role]["soldierLeftLife"]/fightInfo[role]["soldierPanel"]["life"]*100)+"%");
	$("#"+role+"HeroBar").text(fightInfo[role]["heroLeftLife"]+"/"+fightInfo[role]["heroPanel"]["life"]);
	$("#"+role+"HeroBar").attr("style", "width:"+Math.ceil(fightInfo[role]["heroLeftLife"]/fightInfo[role]["heroPanel"]["life"]*100)+"%");							
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
		var roleType = 1;
		isAttacker = 0;
		fightInfo["distance"]=hero["range"];
		if (hero["attackType"]) {

			hero["actions"].push({
				"id" : 10001,
				"name" : "物理普攻",
				"coefficient" : 1,
				"range" : hero["range"],
				"attackType" : 1,
				"battleType" : 1
			});
		} else {
			hero["actions"].push({
				"id" : 10002,
				"name" : "魔法普攻",
				"coefficient" : 1,
				"range" : hero["range"],
				"attackType" : 0,
				"battleType" : 1
			});
		}
	
		$("#attackerFightDetails").html("");
		$("#attackerHeroBar").attr("style", "width:100%");
		$("#attackerSoldierBar").attr("style", "width:100%");
		$("#attackerHeroBar").text("100%");
		$("#attackerSoldierBar").text("100%");
	
	} else {
		var role = "defender";
		var roleType = 2;
		isAttacker = 1;
		$("#defenderFightDetails").html("");
		$("#defenderHeroBar").attr("style", "width:100%");
		$("#defenderHeroBar").text("100%");
		$("#defenderSoldierBar").attr("style", "width:100%");
		$("#defenderSoldierBar").text("100%");
	}

	fightInfo[role] = {};
	sessionInfo[role] = {};
	fightInfo[role]["hero"] = hero;
	fightInfo[role]["soldier"] = {};
	fightInfo[role]["equip"] = {};
	fightInfo[role]["heroPanel"] = {};
	fightInfo[role]["soldierPanel"] = {};
	fightInfo[role]["userConditionChecked"] = {};
	fightInfo[role]["buffCounts"] = {};
	fightInfo[role]["roleType"] = roleType;
	
	stage = 1;
	
	loadWeapon(hero["id"], role);
}

function loadWeapon(heroId, role) {
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
			displayEquip(heroId, "armor", response, role);
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
	var list = $("#" + role + "-" + type + "-list");
	list.children("li").remove();
	// attackerEquip[type] = {};
	for (var i = 0; i < equips.length; i++) {
		var e = $("<li><img src=\"/fight/image/equip_" + equips[i]["id"]
				+ ".png\" alt=\"\" width=\"40\" height=\"40\"></img>"
				+ equips[i]["name"] + "</li>");
		e.attr("equipid", equips[i]["id"]);

		if (userData["equip"][id]) {
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

function loadComplete(role) {
	var heroInc = generateHeroInc(fightInfo[role]["hero"],
			fightInfo[role]["equip"]);
	for ( var i in heroInc) {
		fightInfo[role]["heroPanel"][i] = heroInc[i];
	}
	fightInfo[role]["enhance"] = heroInc["fmSkill"];
	displayHero(role);
}

function displayHero(role) {
	if (role == "attacker") {
		if (fightInfo["attacker"]["hero"]["attackType"]) {
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
	} else if (role == "defender") {
		if (fightInfo["defender"]["hero"]["attackType"]) {
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
	var rolePic = $("#" + role + "Pic");
	rolePic.children("div").remove();
	var pic = $("<div><img src=\"/fight/image/" + fightInfo[role]["hero"]["id"]
			+ ".png\" alt=\"\" width=\"60\" height=\"86\"></img>"
			+ fightInfo[role]["hero"]["name"] + "</div>");
	pic.appendTo(rolePic);

	$("#" + role + "-soldier-list").children("li").remove();
	var soldiers = fightInfo[role]["hero"]["soldiers"];
	for (var i = 0; i < soldiers.length; i++) {
		var soldier = $("<li><img src=\"/fight/image/soldier_"
				+ soldiers[i]["id"]
				+ ".png\" alt=\"\" width=\"50\" height=\"62\"></img>"
				+ soldiers[i]["name"] + "</li>");
		soldier.attr("soldierid", soldiers[i]["id"]);
		soldier.appendTo($("#" + role + "-soldier-list"));

		if (fightInfo[role]["hero"]["defaultSoldierId"] == soldiers[i]["id"]) {
			fightInfo[role]["soldier"] = soldiers[i];
		}
		soldier.click(function(event) {
			var find = $(this).attr("soldierid");
			fightInfo[role]["hero"]["soldiers"].forEach(function(e) {
				if (e["id"] == find) {
					fightInfo[role]["soldier"] = e;
					stage = 1;
					sync(false);
				}
			})

		})
	}
	$("#" + role + "-land-list").children("li").remove();
	for ( var i in lands) {
		var land = $("<li>" + lands[i] + "</li>");
		land.attr("landid", i);
		land.appendTo($("#" + role + "-land-list"));

		land.click(function(event) {
			fightInfo[role]["land"] = $(this).attr("landid");
			sync(false);
		})
	}

	$("#" + role + "-info").html(
			"<p>生命&nbsp;" + fightInfo[role]["hero"]["life"] + "+"
					+ fightInfo[role]["heroPanel"]["lifeInc"]
					+ "</p><p>攻击&nbsp;" + fightInfo[role]["hero"]["attack"]
					+ "+" + fightInfo[role]["heroPanel"]["attackInc"]
					+ "</p><p>智力&nbsp;" + fightInfo[role]["hero"]["intel"]
					+ "+" + fightInfo[role]["heroPanel"]["intelInc"]
					+ "</p><p>防御&nbsp;" + fightInfo[role]["hero"]["physic"]
					+ "+" + fightInfo[role]["heroPanel"]["physicInc"]
					+ "</p><p>魔防&nbsp;" + fightInfo[role]["hero"]["magic"]
					+ "+" + fightInfo[role]["heroPanel"]["magicInc"]
					+ "</p><p>技巧&nbsp;" + fightInfo[role]["hero"]["tech"] + "+"
					+ fightInfo[role]["heroPanel"]["techInc"] + "</p>");

	$("#" + role + "-soldier-information").html("");

	if (fightInfo[role]["equip"]) {
		for ( var i in equipPart) {
			var part = equipPart[i];
			$("#" + role + "-" + equipPart[i]).children("div").remove();
			if (fightInfo[role]["equip"][part]) {
				var pic = $("<div data-toggle=\"modal\" data-target=\"#equip-editor\" data-equip=\""+part+"\" data-role=\""+role+"\"><img src=\"/fight/image/equip_"
						+ fightInfo[role]["equip"][equipPart[i]]["id"]
						+ ".png\" alt=\"\" width=\"60\" height=\"60\"></img><p>"
						+ fightInfo[role]["equip"][equipPart[i]]["name"]
						+ "</p></div>");
				pic.insertBefore($("#" + role + "-" + equipPart[i]
						+ " > button"));
			}
		}
	}

	if (!fightInfo[role]["land"]) {
		fightInfo[role]["land"] = "Flat";
	}
	

	displayAttackerAction();

	sync(true);
}

function displayAttackerAction() {
	// alert(JSON.stringify(fightInfo));
	$("#attacker-action-list").children("li").remove();
	var actions = fightInfo["attacker"]["hero"]["actions"];
	for (var i = 0; i < actions.length; i++) {
		var action = $("<li><img src=\"/fight/image/action_" + actions[i]["id"]
				+ ".png\" alt=\"\" width=\"40\" height=\"40\"></img>"
				+ actions[i]["name"] + "</li>");
		action.attr("actionid", actions[i]["id"]);
		action.appendTo($("#attacker-action-list"));

		if(!fightInfo["attacker"]["action"])
		{
			if (actions[i]["id"] == 10001 || actions[i]["id"] == 10002) {
				fightInfo["attacker"]["action"] = actions[i];
			}
		}
		action.click(function(event) {
			var find = $(this).attr("actionid");
			fightInfo["attacker"]["hero"]["actions"].forEach(function(e) {
				if (e["id"] == find) {
					fightInfo["attacker"]["action"] = e;
					if(e["battleType"]==1)
					{
						fightInfo["attacker"]["roleType"] = 1;
						fightInfo["defender"]["roleType"] = 2;
					} else if(e["battleType"]==2)
					{
						fightInfo["attacker"]["roleType"] = 3;
						fightInfo["defender"]["roleType"] = 4;
					}
					sync(false);
				}
			})

		})
	}
}

function sync(refresh) {
	if (fightInfo["attacker"] && fightInfo["defender"]) {
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

						sessionInfo = data["sessionInfo"];

						// alert(JSON.stringify(fightInfo));
						syncComplete("attacker");
						syncComplete("defender");

						if (refresh) {
							sync(false);
							
							
							if(stage==1)
							{
								stage=2;
							}
						}
					},
					error : function(jqXHR) {
					}
				});
	}
}

function syncComplete(role) {
//	fightInfo[role]["heroLeftLife"] = Math
//			.ceil(fightInfo[role]["heroPanel"]["life"] * heroLife);
//	fightInfo[role]["soldierLeftLife"] = Math
//			.ceil(fightInfo[role]["soldierPanel"]["life"] * soldierLife);
	buildSkill(role);
	buildUserCondition(role); 
	buildHeroPanel(role);
	buildSoldierPanel(role);
	buildCriticalPanel(role);
	buildDistanceList();
	buildSupportSkills(role);

	
	if(stage==1)
	{
		fightInfo[role]["heroLeftLife"] = fightInfo[role]["heroPanel"]["life"];
		fightInfo[role]["soldierLeftLife"] = fightInfo[role]["soldierPanel"]["life"];
		refreshLife(role);
	}

}

function buildHeroPanel(role) {
	$("#" + role + "Attack").attr("value",
			fightInfo[role]["heroPanel"]["attack"]);
	$("#" + role + "Intel")
			.attr("value", fightInfo[role]["heroPanel"]["intel"]);
	$("#" + role + "PhysicDef").attr("value",
			fightInfo[role]["heroPanel"]["physic"]);
	$("#" + role + "MagicDef").attr("value",
			fightInfo[role]["heroPanel"]["magic"]);
	$("#" + role + "Life").attr("value", fightInfo[role]["heroPanel"]["life"]);
	var detail = "<p>";
	if (fightInfo[role]["action"]) {
		var co =  fightInfo[role]["action"]["coefficient"];
		if(!fightInfo[role]["action"]["simpleAttack"])
		{
			co =  fightInfo[role]["action"]["coefficient"]*(1+fightInfo["attacker"]["heroPanel"]["skillDamage"]/100.0);
			co = co.toFixed(2);
		}
		detail += "<b>" + fightInfo[role]["action"]["name"]
				+ "</b>&nbsp;&nbsp;&nbsp;<b>"
				+co + "倍</b></p>";
	} else {
		detail += ("-----距离：" + fightInfo["distance"] + "-----</p>");
	}

	detail += "<p><b>增伤：" + fightInfo[role]["heroPanel"]["damageInc"]
			+ "&nbsp;&nbsp;&nbsp;物理减伤："
			+ fightInfo[role]["heroPanel"]["physicDamageDec"]
			+ "&nbsp;&nbsp;&nbsp;魔法减伤："
			+ fightInfo[role]["heroPanel"]["magicDamageDec"] + "</b></p>"
			+ "<p>技巧：" + fightInfo[role]["heroPanel"]["tech"]
			+ "&nbsp;&nbsp;&nbsp;暴击："
			+ fightInfo[role]["heroPanel"]["criticalProbInc"]
			+ "&nbsp;&nbsp;&nbsp;暴伤："
			+ fightInfo[role]["heroPanel"]["criticalDamageInc"]
			+ "&nbsp;&nbsp;&nbsp;防爆:"
			+ fightInfo[role]["heroPanel"]["criticalProbDec"]
			+ "&nbsp;&nbsp;&nbsp;减爆伤："
			+ fightInfo[role]["heroPanel"]["criticalDamageDec"] + "</p>";
	$("#" + role + "Detail").html(detail);
	
	$("#" + role + "-land-information").html(
			"<p><b>地形:" + lands[fightInfo[role]["land"]] + "</b></p>");

}

function buildSoldierPanel(role) {
	$("#" + role + "-soldier-information").html(
			"<p><b>" + fightInfo[role]["soldier"]["name"] + "</b></p><p>攻击："
					+ fightInfo[role]["soldierPanel"]["attack"]
					+ "&nbsp;&nbsp;&nbsp;防御："
					+ fightInfo[role]["soldierPanel"]["physic"]
					+ "&nbsp;&nbsp;&nbsp;魔防："
					+ fightInfo[role]["soldierPanel"]["magic"]
					+ "&nbsp;&nbsp;&nbsp;生命："
					+ fightInfo[role]["soldierPanel"]["life"] + "</p><p><b>增伤:"
					+ fightInfo[role]["soldierPanel"]["damageInc"]
					+ "&nbsp;&nbsp;&nbsp;物理减伤:"
					+ fightInfo[role]["soldierPanel"]["physicDamageDec"]
					+ "&nbsp;&nbsp;&nbsp;魔法减伤:"
					+ fightInfo[role]["soldierPanel"]["magicDamageDec"]
					+ "</b></p><p>" + "暴击："
					+ fightInfo[role]["soldierPanel"]["criticalProbInc"]
					+ "&nbsp;&nbsp;&nbsp;暴伤："
					+ fightInfo[role]["soldierPanel"]["criticalDamageInc"]
					+ "&nbsp;&nbsp;&nbsp;防爆："
					+ fightInfo[role]["soldierPanel"]["criticalProbDec"]
					+ "&nbsp;&nbsp;&nbsp;减爆伤："
					+ fightInfo[role]["soldierPanel"]["criticalDamageDec"]
					+ "</p>");

}

function buildSkill(role) {
	$("#" + role + "-buf-list").children("li").remove();
	if (sessionInfo[role]["checkedSkills"]) {
		for (var i = 0; i < sessionInfo[role]["checkedSkills"].length; i++) {
			var valid = sessionInfo[role]["checkedSkills"][i].valid ? "valid-skill"
					: "not-valid-skill";
			var buff = $("<li class=\"list-group-item " + valid + "\">"
					+ sessionInfo[role]["checkedSkills"][i]["skill"]["desc"]
					+ "</li>");
			buff.appendTo($("#" + role + "-buf-list"));
		}

	}
}

function buildCounter(role, name, max, def)
{
	if(!fightInfo[role]["buffCounts"][name])
	{
		fightInfo[role]["buffCounts"][name] = def;
	}
	
	var counter = $("<div id=\"counter_"+name+"\" class=\"dropdown\">" +
			"<button class=\"btn btn-default dropdown-toggle\" type=\"button\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"true\">" +
			"当前层数："+fightInfo[role]["buffCounts"][name]+"层"+
			"<span class=\"caret\"></span></button>" +
			"</div>");
	var ul = $("<ul class=\"dropdown-menu\" aria-labelledby=\"counter_"+name+"\"></ul>");
	for(var i=1; i<=max; i++)
	{
		var item= $("<li class=\"list-group-item\">"+i+"层</li>");
		item.attr("buff_count", i);
		item.click(function(e){
			fightInfo[role]["buffCounts"][name]=$(this).attr("buff_count");
			$("#counter_"+name+" > button").text("当前层数："+fightInfo[role]["buffCounts"][name]+"层");
			sync(false);
		});
		item.appendTo(ul);
	}
	ul.appendTo(counter);
	return counter;
}

function buildUserCondition(role) {
	$("#" + role + "-user-condition-list").children("li").remove();
	if (sessionInfo[role]["userConditions"]) {
		for ( var name in sessionInfo[role]["userConditions"]) {
			var checked = fightInfo[role]["userConditionChecked"][name];
			var condition = sessionInfo[role]["userConditions"][name];
			if(condition["support"] && !checked)
			{
				continue;
			}
			
			if (checked == undefined) {
				checked = sessionInfo[role]["userConditions"][name]["defaultValid"];
			}
			var buff = $("<li class=\"list-group-item\">"
					+ sessionInfo[role]["userConditions"][name]["desc"]
					+ "</li>");

			if(sessionInfo[role]["userConditions"][name]["maxCount"])
			{
				var counter = "";
				counter = buildCounter(role, name, sessionInfo[role]["userConditions"][name]["maxCount"], sessionInfo[role]["userConditions"][name]["defaultCount"]);
				counter.appendTo(buff);				
			}
			
			var buttonClass = checked ? "btn-success" : "btn-default";
			var button = $("<button type=\"button\" class=\"btn " + buttonClass
					+ "\">选择</button>");
			button.attr("uc-name", name);
			button.click(function(e) {

				if ($(this).hasClass("btn-success")) {
					$(this).removeClass("btn-success");
					$(this).addClass("btn-default");
					fightInfo[role]["userConditionChecked"][$(this).attr(
							"uc-name")] = false;
				} else {
					$(this).removeClass("btn-default");
					$(this).addClass("btn-success");
					fightInfo[role]["userConditionChecked"][$(this).attr(
							"uc-name")] = true;
					checkUserConditionGroup(role, $(this).attr("uc-name"));
				}
				sync(false);
			});
			button.appendTo(buff);
			buff.appendTo($("#" + role + "-user-condition-list"));
		}
	}
}

function checkUserConditionGroup(role, name) {
	var userCondition = sessionInfo[role]["userConditions"][name];
	//alert("userCondition: "+userCondition);
	if (userCondition
			&& userCondition["groupName"]
			&& sessionInfo[role]["userConditionGroups"][userCondition["groupName"]]) {
		var g = sessionInfo[role]["userConditionGroups"][userCondition["groupName"]];
		for (var i = 0; i < g.length; i++) {
			if (g[i] != name) {
				//alert("canceled "+g[i]);
				fightInfo[role]["userConditionChecked"][g[i]] = false;
			} else
			{
				//alert(g[i]+" is ok");
			}
		}
	}
}

function buildCriticalPanel(role) {
	$("#" + role + "-critical").children("li").remove();
	if (fightInfo[role]["heroCriticalChecked"] == undefined) {
		fightInfo[role]["heroCriticalChecked"] = fightInfo[role]["heroPanel"]["criticalProbInc"]
				+ fightInfo[role]["heroPanel"]["tech"] / 10 > 65;
	}
	var buttonClass = fightInfo[role]["heroCriticalChecked"] ? "btn-warning"
			: "btn-default";
	var buff = $("<li class=\"list-group-item\">英雄暴击</li>");
	var button = $("<button type=\"button\" class=\"btn " + buttonClass
			+ "\">暴击</button>");
	button.attr("uc-name", name);
	button.click(function(e) {

		if (fightInfo[role]["heroCriticalChecked"]) {
			$(this).removeClass("btn-warning");
			$(this).addClass("btn-default");
			fightInfo[role]["heroCriticalChecked"] = false;
		} else {
			$(this).removeClass("btn-default");
			$(this).addClass("btn-warning");
			fightInfo[role]["heroCriticalChecked"] = true;
		}
	});
	button.appendTo(buff);
	buff.appendTo($("#" + role + "-critical"));

	if (fightInfo[role]["soldierCriticalChecked"] == undefined) {
		fightInfo[role]["soldierCriticalChecked"] = fightInfo[role]["soldierPanel"]["criticalProbInc"] > 65;
	}
	var buff = $("<li class=\"list-group-item\">士兵暴击</li>");
	if (fightInfo[role]["soldierPanel"]["criticalProbInc"] > 0) {
		var buttonClass = (fightInfo[role]["soldierCriticalChecked"]) ? "btn-warning"
				: "btn-default";

		var button = $("<button type=\"button\" class=\"btn " + buttonClass
				+ "\">暴击</button>");
		button.attr("uc-name", name);
		button.click(function(e) {

			if (fightInfo[role]["soldierCriticalChecked"]) {
				$(this).removeClass("btn-warning");
				$(this).addClass("btn-default");
				fightInfo[role]["soldierCriticalChecked"] = false;
			} else {
				$(this).removeClass("btn-default");
				$(this).addClass("btn-warning");
				fightInfo[role]["soldierCriticalChecked"] = true;
			}
			// loadSkillData();
		});
		button.appendTo(buff);
	}
	buff.appendTo($("#" + role + "-critical"));
}

function buildDistanceList()
{
	$("#distance-list").children("li").remove();
	
	var range = fightInfo["attacker"]["heroPanel"]["range"];
	
	if(range)
	{
		for(var i=1; i<=range; i++)
		{
			var buff = $("<li class=\"list-group-item\">"+i+"格</li>");
			buff.attr("r", i);
			
			buff.appendTo($("#distance-list"));
			buff.click(function(event) {
				var r = $(this).attr("r");
				fightInfo["distance"] = r;
				
				sync(false);

			})
		}
	}
}

function buildSupportSkills(role)
{
	$("#"+role+"-support-list").children("li").remove();
	if (sessionInfo[role]["supportSkills"]) {
		for (var i = 0; i < sessionInfo[role]["supportSkills"].length; i++) {
			var skill = sessionInfo[role]["supportSkills"][i];
			var name = skill["condition"]["name"];
			var valid = fightInfo[role]["userConditionChecked"][name] ? "valid-skill"
					: "not-valid-skill";
			var buff = $("<li class=\"list-group-item " + valid + "\">" 
				+ "<img src=\"/fight/image/action_" + skill["id"]
				+ ".png\" alt=\"\" width=\"40\" height=\"40\"></img>"
					+ skill["desc"]
					+ "</li>");
			buff.attr("name", name);
			buff.click(function(e){
				fightInfo[role]["userConditionChecked"][$(this).attr("name")] = true;
				checkUserConditionGroup(role, $(this).attr("name"));
				sync(false);
			});
			buff.appendTo($("#"+role+"-support-list"));
		}

	}
	
}

