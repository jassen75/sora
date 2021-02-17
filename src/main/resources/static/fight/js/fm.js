

function importFm() {
	
	$("#csvFileInput").val("");
	$("#csvFileInput").click();
}

function readCSVFile(obj) {
	var reader = new FileReader();
	reader.readAsText(obj.files[0]);
	reader.onload = function() {

		var data = this.result;
		parseCSV(data);// data为csv转换后的对象
	}

}

var fm_type = [];
fm_type[1]="轻风"; 
fm_type[2]="满月"; 
fm_type[3]="魔术"; 
fm_type[5]="怒涛"; 
fm_type[4]="时钟"; 
fm_type[6]="烈日"; 
fm_type[7]="流星"; 
fm_type[8]="顽石"; 
fm_type[9]="水晶"; 
fm_type[10]="寒冰"; 
fm_type[11]="大树"; 
fm_type[12]="荆棘"; 
fm_type[13]="钢铁"; 

var fm_inc_key = {};
fm_inc_key["attackInc"] = "攻击";
fm_inc_key["intelInc"] = "智力";
fm_inc_key["lifeInc"] = "生命";
fm_inc_key["physicDefInc"] = "防御";
fm_inc_key["magicDefInc"] = "魔防";
fm_inc_key["criticalInc"] = "暴击概率";

var fm_key = {};
fm_key["attack"] = "攻击";
fm_key["intel"] = "智力";
fm_key["life"] = "生命";
fm_key["physicDef"] = "防御";
fm_key["magicDef"] = "魔防";

function parseCSV(data)
{
	gameData["fm"] = {};
	gameData["jt"] = {};
	gameData["jjc"] = {};
	gameData["equip"] = {};
	var csvarry = data.split("\r\n");
     for(var i = 0; i<csvarry.length;i++){
     	
        var temp = csvarry[i].split(",");

        if(temp.length==17)
        {
        	var id =  temp[0];
        	gameData["fm"][id] = {};
        	gameData["fm"][id]["id"] = temp[0];
        	gameData["fm"][id]["name"] = temp[1];
        	
        	gameData["fm"][id]["weapon"] = {};
        	gameData["fm"][id]["weapon"]["fm_type"] =temp[2];
        	gameData["fm"][id]["weapon"]["fm_info"] = parseFM(temp[3]);
        	
        	gameData["fm"][id]["armor"] = {};
        	gameData["fm"][id]["armor"]["fm_type"] = temp[4];
        	gameData["fm"][id]["armor"]["fm_info"] = parseFM(temp[5]);
        	
        	gameData["fm"][id]["helmet"]  = {};
        	gameData["fm"][id]["helmet"]["fm_type"] = temp[6];
        	gameData["fm"][id]["helmet"]["fm_info"] = parseFM(temp[7]);
        	
        	gameData["fm"][id]["jewelry"]  = {};
        	gameData["fm"][id]["jewelry"]["fm_type"] = temp[8];
        	gameData["fm"][id]["jewelry"]["fm_info"] = parseFM(temp[9]);
        	
        	gameData["jt"][id]  = {};

        	gameData["jt"][id] = parseJT(temp[10]);
        	gameData["jt"][id]["id"] = temp[0];
        	gameData["jt"][id]["name"] = temp[1];
        	
        	gameData["jjc"][id]  = {};
        	gameData["jjc"][id] = parseJJC(temp[11], temp[12]);
        	gameData["jjc"][id]["id"] = temp[0];
        	gameData["jjc"][id]["name"] = temp[1];
        	
        	gameData["equip"][id]  = {};
        	gameData["equip"][id]["id"]  = temp[0];
        	gameData["equip"][id]["name"]  = temp[1];
        	gameData["equip"][id]["weapon"]  = temp[13];
        	gameData["equip"][id]["armor"]  = temp[14];
        	gameData["equip"][id]["helmet"]  = temp[15];
        	gameData["equip"][id]["jewelry"]  = temp[16];
        }
    }
    refreshTable();
}

function parseFM(obj)
{
	var result = {};
	var temp = obj.split(";");
	
	 for(var i = 0; i<temp.length;i++){
	 	var kv = temp[i].split("=");
	 	result[kv[0]] = parseInt(kv[1]);
	 }
	 return result;
}

function parseJT(obj)
{
	var result = {};
	var temp = obj.split("|");
	
	 result["life"] = parseInt(temp[0]);
	 result["attack"] = parseInt(temp[1]);
	 result["intel"] = parseInt(temp[2]);
	 result["physicDef"] = parseInt(temp[3]);
	 result["magicDef"] = parseInt(temp[4]);
	 result["tech"] = parseInt(temp[5]);
	 return result;
}

function parseJJC(obj1, obj2)
{
	var result = {};
	var temp1 = obj1.split("|");
	var temp2 = obj2.split("|");
	
	 result["life"] = parseInt(temp1[0]);
	 result["attack"] = parseInt(temp1[1]);
	 result["intel"] = parseInt(temp1[2]);
	 result["physicDef"] = parseInt(temp1[3]);
	 result["magicDef"] =parseInt( temp1[4]);
	 result["tech"] = parseInt(temp1[5]);
	 
	 result["criticalProbInc"] = parseInt(temp2[0]);
	 result["criticalDamageInc"] = parseInt(temp2[1]);
	 result["criticalProbDec"] = parseInt(temp2[2]);
	 result["criticalDamageDec"] = parseInt(temp2[3]);
	 return result;
}

function refreshTable()
{
	$("#fm_info > tbody").children("tr").remove();
	
	for(var i in gameData["fm"]){
		
		var tr = $("<tr><td>"+gameData["fm"][i]["name"]+"</td><td>"+
							displayFM(gameData["fm"][i]["weapon"])+"</td><td>"+
							displayFM(gameData["fm"][i]["armor"])+"</td><td>"+
							displayFM(gameData["fm"][i]["helmet"])+"</td><td>"+
							displayFM(gameData["fm"][i]["jewelry"])+"</td></tr>");
		
	  	tr.appendTo($("#fm_info > tbody"));
    }
    
    $("#jt_info > tbody").children("tr").remove();
	
	for(var i in gameData["jt"]){
		
		var tr = $("<tr><td>"+gameData["jt"][i]["name"]+"</td><td>"+
							gameData["jt"][i]["life"]+"</td><td>"+
							gameData["jt"][i]["attack"]+"</td><td>"+
							gameData["jt"][i]["intel"]+"</td><td>"+
							gameData["jt"][i]["physicDef"]+"</td><td>"+
							gameData["jt"][i]["magicDef"]+"</td><td>"+
							gameData["jt"][i]["tech"]+"</td><td>"+
							gameData["jjc"][i]["life"]+"</td><td>"+
							gameData["jjc"][i]["attack"]+"</td><td>"+
							gameData["jjc"][i]["intel"]+"</td><td>"+
							gameData["jjc"][i]["physicDef"]+"</td><td>"+
							gameData["jjc"][i]["magicDef"]+"</td><td>"+
							gameData["jjc"][i]["tech"]+"</td><td>"+
							gameData["jjc"][i]["criticalProbInc"]+"</td><td>"+
							gameData["jjc"][i]["criticalDamageInc"]+"</td><td>"+
							gameData["jjc"][i]["criticalProbDec"]+"</td><td>"+
							gameData["jjc"][i]["criticalDamageDec"]+"</td></tr>");
		
	  	tr.appendTo($("#jt_info > tbody"));
    }
}

function fmClass(fmType)
{
	switch(fmType)
	{		
		case "1":
		case "2":
		case "3":
		case "4":
		case "5":
			return "fm_type_red";
		case "6":
		case "7":
			return "fm_type_yellow";
		case "8":
		case "9":	
		case "10":
			return "fm_type_blue";
		case "11":
		case "12":
		case "13":
			return "fm_type_green";
	}
}

function displayFM(fm)
{
	var result = "";
	var fm_class = fmClass(fm["fm_type"]);
	result += "<div class=\""+fm_class+"\">" +fm_type[fm["fm_type"]]+"</div>";
	for(var i in fm["fm_info"])
	{
		if( fm_inc_key[i])
		{
			result += fm_inc_key[i]+"+"+fm["fm_info"][i]+"%";
		}
		
		if(fm_key[i])
		{
			result += fm_key[i]+"+"+fm["fm_info"][i];
		}
		result += "&nbsp;&nbsp;";
	}
	
	return result;
}


var parts = ["weapon", "armor", "helmet", "jewelry"];

function generateHeroInc(hero, equip)
{
	var heroInc = {};
	var id = hero["id"]
	var fm = gameData["fm"][id];
	var jt = gameData["jt"][id];
	var jjc = gameData["jjc"][id];
	
	if(!fm || !jt)
	{
		return defaultHeroInc(hero);
	}
	heroInc["lifeSkill"] = 0;
	heroInc["attackSkill"] = 0;
	heroInc["intelSkill"] = 0;
	heroInc["physicDefSkill"] = 0;
	heroInc["magicDefSkill"] = 0;
	heroInc["techSkill"] = 0;
	heroInc["criticalProbInc"]=0;
	heroInc["criticalDamageInc"]=0;
	heroInc["criticalProbDec"]=0;
	heroInc["criticalDamageDec"]=0;
	var lifep = 0;  // percent 
	var lifes = 0;  // static
	var attackp = 0;
	var attacks = 0;
	var intelp = 0;
	var intels = 0;
	var physicp = 0;
	var physics = 0;
	var magicp = 0;
	var magics = 0;
	var techp = 0;
	var techs = 0;

	var fmTypeCount=[];
	for(var i=0; i<12; i++)
	{
		fmTypeCount[i]=0;
	}

	for(var j in parts)
	{
		var i = parts[j];
		var fmType = fm[i]["fm_type"];
		var fmInfo = fm[i]["fm_info"];
		
		fmTypeCount[fmType]++;
		if(fmInfo["lifeInc"])
		{
			lifep += fmInfo["lifeInc"];
		}
		if(fmInfo["life"])
		{
			lifes += fmInfo["life"];
		}
		if(fmInfo["attackInc"])
		{
			attackp = attackp+fmInfo["attackInc"];
		}
		if(fmInfo["attack"])
		{
			attacks = attacks+fmInfo["attack"];
		}
		if(fmInfo["intelInc"])
		{
			intelp = intelp+fmInfo["intelInc"];
		}
		if(fmInfo["intel"])
		{
			intels = intels+fmInfo["intel"];
		}
		if(fmInfo["physicDefInc"])
		{
			physicp += fmInfo["physicDefInc"];
		}
		if(fmInfo["physicDef"])
		{
			physics += fmInfo["physicDef"];
		}
		if(fmInfo["magicDefInc"])
		{
			magicp += fmInfo["magicDefInc"];
		}
		if(fmInfo["magicDef"])
		{
			magics += fmInfo["magicDef"];
		}
		if(fmInfo["techInc"])
		{
			techp += fmInfo["techInc"];
		}
		if(fmInfo["tech"])
		{
			techs += fmInfo["tech"];
		}
		
		if(fmInfo["criticalInc"])
		{
			heroInc["criticalProbInc"] += fmInfo["criticalInc"];
		}

		if(equip[i])
		{
			if(equip[i]["equipType"]["life"])
			{
				lifes+=equip[i]["equipType"]["life"];
			}
			if(equip[i]["equipType"]["attack"])
			{
				attacks+=equip[i]["equipType"]["attack"];
			}
			if(equip[i]["equipType"]["intel"])
			{
				intels+=equip[i]["equipType"]["intel"];
			}
			if(equip[i]["equipType"]["physicDef"])
			{
				physics+=equip[i]["equipType"]["physicDef"];
			}
			if(equip[i]["equipType"]["magicDef"])
			{
				magics+=equip[i]["equipType"]["magicDef"];
			}
			if(equip[i]["equipType"]["tech"])
			{
				techs+=equip[i]["equipType"]["tech"];
			}
			
			
			if(equip[i]["lifeSkill"])
			{
				heroInc["lifeSkill"] += equip[i]["lifeSkill"];
			}
			if(equip[i]["attackSkill"])
			{
				heroInc["attackSkill"] += equip[i]["attackSkill"];
			}
			if(equip[i]["intelSkill"])
			{
				heroInc["intelSkill"] += equip[i]["intelSkill"];
			}
			if(equip[i]["physicDefSkill"])
			{
				heroInc["physicDefSkill"] += equip[i]["physicDefSkill"];
			}
			if(equip[i]["magicDefSkill"])
			{
				heroInc["magicDefSkill"] += equip[i]["magicDefSkill"];
			}
			if(equip[i]["techSkill"])
			{
				heroInc["techSkill"] += equip[i]["techSkill"];
			}
		}
	}
	
	for(var i=0; i<12; i++)
	{
		if(i>=0 && i<=3 && fmTypeCount[i]>=2)
		{
			attackp+=5;
			intelp+=5;
		}
		if(i==4 && fmTypeCount[i]>=2)
		{
			attackp+=5;
		}
		if(i>=5 && i<=6 && fmTypeCount[i]>=2)
		{
			heroInc["criticalProbInc"]+=7;
		}
		if(i>=7 && i<=9 && fmTypeCount[i]>=2)
		{
			physicp+=5;
			magicp+=5;
		}
		if(i>=10 && i<=12 && fmTypeCount[i]>=2)
		{
			lifep+=5;
		}
		
		if(fmTypeCount[i]==4)
		{
			heroInc["fmSkill"] = i;
		}
	}
	
	heroInc["lifeInc"]=Math.round(hero["life"]*lifep/100)+lifes+jt["life"];
	heroInc["attackInc"]=Math.ceil(hero["attack"]*attackp/100)+attacks+jt["attack"];
	heroInc["intelInc"]=Math.ceil(hero["intel"]*intelp/100)+intels+jt["intel"];
	heroInc["physicDefInc"]=Math.ceil(hero["physicDef"]*physicp/100)+physics+jt["physicDef"];
	heroInc["magicDefInc"]=Math.ceil(hero["magicDef"]*magicp/100)+magics+jt["magicDef"];
	heroInc["techInc"]=Math.ceil(hero["tech"]*techp/100)+techs+jt["tech"];
	
	heroInc["lifeJJC"] = jjc["life"];
	heroInc["attackJJC"] = jjc["attack"];
	heroInc["intelJJC"] = jjc["intel"];
	heroInc["physicDefJJC"] = jjc["physicDef"];
	heroInc["magicDefJJC"] = jjc["magicDef"];
	heroInc["techJJC"] = jjc["tech"];
	
	heroInc["criticalProbInc"] += jjc["criticalProbInc"];
	heroInc["criticalDamageInc"] += jjc["criticalDamageInc"];
	heroInc["criticalProbDec"] += jjc["criticalProbDec"];
	heroInc["criticalDamageDec"] += jjc["criticalDamageDec"];

	return heroInc;
}

function defaultHeroInc(hero)
{
	var heroInc = {};
	heroInc["lifeInc"]=Math.round(hero["life"]*0.25)+700+2000;
	if(hero["isPhysic"])
	{
		heroInc["attackInc"]=Math.ceil(hero["attack"]*0.38)+80+182;
		heroInc["attackJJC"] = 60;
		heroInc["intelInc"] = 0;
		heroInc["intelJJC"] = 0;
	    heroInc["attackSkill"] = 18;
	}else
	{
		heroInc["intelInc"]=Math.ceil(hero["intel"]*0.38)+80+182;
		heroInc["intelJJC"] = 60;
		heroInc["attackInc"] = 0;
		heroInc["attackJJC"] = 0;
		heroInc["intelSkill"] = 18;
	}
	
	heroInc["lifeSkill"] = 10;
	heroInc["physicDefSkill"] = 10;
	heroInc["magicDefSkill"] = 0;
	heroInc["techSkill"] = 0;
	
	heroInc["physicDefInc"]=Math.ceil(hero["physicDef"]*0.15)+55+60;
	heroInc["magicDefInc"]=60;
	heroInc["techInc"]=0;
	
	heroInc["lifeJJC"] = 480;
	heroInc["physicDefJJC"] = 45;
	heroInc["magicDefJJC"] = 0;
	heroInc["techJJC"] = 0;
	
	heroInc["criticalProbInc"] = 0;
	heroInc["criticalDamageInc"] = 0;
	heroInc["criticalProbDec"] = 20;
	heroInc["criticalDamageDec"] = 30;

	return heroInc;
}