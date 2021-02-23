

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
fm_inc_key["physicInc"] = "防御";
fm_inc_key["magicInc"] = "魔防";
fm_inc_key["criticalInc"] = "暴击概率";

var fm_key = {};
fm_key["attack"] = "攻击";
fm_key["intel"] = "智力";
fm_key["life"] = "生命";
fm_key["physic"] = "防御";
fm_key["magic"] = "魔防";

function parseCSV(data)
{

	userData["fm"] = {};
	userData["jt"] = {};
	userData["jjc"] = {};
	userData["equip"] = {};
	var csvarry = data.split("\n");
     for(var i = 0; i<csvarry.length;i++){
     	
        var temp = csvarry[i].split(",");

        if(temp.length==17)
        {
        	var id =  temp[0];
        	userData["fm"][id] = {};
        	userData["fm"][id]["id"] = temp[0];
        	userData["fm"][id]["name"] = temp[1];
        	
        	userData["fm"][id]["weapon"] = {};
        	userData["fm"][id]["weapon"]["fm_type"] =temp[2];
        	userData["fm"][id]["weapon"]["fm_info"] = parseFM(temp[3]);
        	
        	userData["fm"][id]["armor"] = {};
        	userData["fm"][id]["armor"]["fm_type"] = temp[4];
        	userData["fm"][id]["armor"]["fm_info"] = parseFM(temp[5]);
        	
        	userData["fm"][id]["helmet"]  = {};
        	userData["fm"][id]["helmet"]["fm_type"] = temp[6];
        	userData["fm"][id]["helmet"]["fm_info"] = parseFM(temp[7]);
        	
        	userData["fm"][id]["jewelry"]  = {};
        	userData["fm"][id]["jewelry"]["fm_type"] = temp[8];
        	userData["fm"][id]["jewelry"]["fm_info"] = parseFM(temp[9]);
        	
        	userData["jt"][id]  = {};

        	userData["jt"][id] = parseJT(temp[10]);
        	userData["jt"][id]["id"] = temp[0];
        	userData["jt"][id]["name"] = temp[1];
        	
        	userData["jjc"][id]  = {};
        	userData["jjc"][id] = parseJJC(temp[11], temp[12]);
        	userData["jjc"][id]["id"] = temp[0];
        	userData["jjc"][id]["name"] = temp[1];
        	
        	userData["equip"][id]  = {};
        	userData["equip"][id]["id"]  = temp[0];
        	userData["equip"][id]["name"]  = temp[1];
        	userData["equip"][id]["weapon"]  = temp[13];
        	userData["equip"][id]["armor"]  = temp[14];
        	userData["equip"][id]["helmet"]  = temp[15];
        	userData["equip"][id]["jewelry"]  = temp[16];
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
	 result["physic"] = parseInt(temp[3]);
	 result["magic"] = parseInt(temp[4]);
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
	 result["physic"] = parseInt(temp1[3]);
	 result["magic"] =parseInt( temp1[4]);
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
	
	for(var i in userData["fm"]){
		
		var tr = $("<tr><td>"+userData["fm"][i]["name"]+"</td><td><div><img src=\"/fight/image/equip_"+userData["equip"][i]["weapon"]+".png\" alt=\"\" width=\"40\" height=\"40\"></img>"+
							displayFM(userData["fm"][i]["weapon"])+"</div></td><td><div><img src=\"/fight/image/equip_"+userData["equip"][i]["armor"]+".png\" alt=\"\" width=\"40\" height=\"40\"></img>"+
							displayFM(userData["fm"][i]["armor"])+"</div></td><td><div><img src=\"/fight/image/equip_"+userData["equip"][i]["helmet"]+".png\" alt=\"\" width=\"40\" height=\"40\"></img>"+
							displayFM(userData["fm"][i]["helmet"])+"</div></td><td><div><img src=\"/fight/image/equip_"+userData["equip"][i]["jewelry"]+".png\" alt=\"\" width=\"40\" height=\"40\"></img>"+
							displayFM(userData["fm"][i]["jewelry"])+"</div></td></tr>");
		
	  	tr.appendTo($("#fm_info > tbody"));
    }
    
    $("#jt_info > tbody").children("tr").remove();
	
	for(var i in userData["jt"]){
		
		var tr = $("<tr><td>"+userData["jt"][i]["name"]+"</td><td>"+
							userData["jt"][i]["life"]+"</td><td>"+
							userData["jt"][i]["attack"]+"</td><td>"+
							userData["jt"][i]["intel"]+"</td><td>"+
							userData["jt"][i]["physic"]+"</td><td>"+
							userData["jt"][i]["magic"]+"</td><td>"+
							userData["jt"][i]["tech"]+"</td><td>"+
							userData["jjc"][i]["life"]+"</td><td>"+
							userData["jjc"][i]["attack"]+"</td><td>"+
							userData["jjc"][i]["intel"]+"</td><td>"+
							userData["jjc"][i]["physic"]+"</td><td>"+
							userData["jjc"][i]["magic"]+"</td><td>"+
							userData["jjc"][i]["tech"]+"</td><td>"+
							userData["jjc"][i]["criticalProbInc"]+"</td><td>"+
							userData["jjc"][i]["criticalDamageInc"]+"</td><td>"+
							userData["jjc"][i]["criticalProbDec"]+"</td><td>"+
							userData["jjc"][i]["criticalDamageDec"]+"</td></tr>");
		
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
	//result+="</b>"
	return result;
}


var parts = ["weapon", "armor", "helmet", "jewelry"];

function generateHeroInc(hero, equip)
{
	var heroInc = {};
	var id = hero["id"]
	var fm = userData["fm"][id];
	var jt = userData["jt"][id];
	var jjc = userData["jjc"][id];
	
	if(!fm || !jt)
	{
		return defaultHeroInc(hero);
	}
	heroInc["lifeSkill"] = 0;
	heroInc["attackSkill"] = 0;
	heroInc["intelSkill"] = 0;
	heroInc["physicSkill"] = 0;
	heroInc["magicSkill"] = 0;
	heroInc["techSkill"] = 0;
	heroInc["criticalProbIncSkill"]=0;
	heroInc["criticalDamageIncSkill"]=0;
	heroInc["criticalProbDecSkill"]=0;
	heroInc["criticalDamageDecSkill"]=0;
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
	for(var i=0; i<=13; i++)
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
			//lifes += Math.round(hero["life"]*fmInfo["lifeInc"]/100);
		}
		if(fmInfo["life"])
		{
			lifes += fmInfo["life"];
		}
		if(fmInfo["attackInc"])
		{
			attackp = attackp+fmInfo["attackInc"];
			//attacks += Math.round(hero["attack"]*fmInfo["attackInc"]/100);
		}
		if(fmInfo["attack"])
		{
			attacks = attacks+fmInfo["attack"];
		}
		if(fmInfo["intelInc"])
		{
			intelp = intelp+fmInfo["intelInc"];
			//intels += Math.round(hero["intel"]*fmInfo["intelInc"]/100);
		}
		if(fmInfo["intel"])
		{
			intels = intels+fmInfo["intel"];
		}
		if(fmInfo["physicInc"])
		{
			physicp += fmInfo["physicInc"];
			//physics += Math.round(hero["physicDef"]*fmInfo["physicInc"]/100);
		}
		if(fmInfo["physic"])
		{
			physics += fmInfo["physic"];
		}
		if(fmInfo["magicInc"])
		{
			magicp += fmInfo["magicInc"];
			//magics += Math.round(hero["magicDef"]*fmInfo["magicInc"]/100);
		}
		if(fmInfo["magic"])
		{
			magics += fmInfo["magic"];
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
			heroInc["criticalProbIncSkill"] += fmInfo["criticalInc"];
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
			if(equip[i]["equipType"]["physic"])
			{
				physics+=equip[i]["equipType"]["physic"];
			}
			if(equip[i]["equipType"]["magic"])
			{
				magics+=equip[i]["equipType"]["magic"];
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
			if(equip[i]["physicSkill"])
			{
				heroInc["physicSkill"] += equip[i]["physicSkill"];
			}
			if(equip[i]["magicSkill"])
			{
				heroInc["magicSkill"] += equip[i]["magicSkill"];
			}
			if(equip[i]["techSkill"])
			{
				heroInc["techSkill"] += equip[i]["techSkill"];
			}
		}
	}
	
	for(var i=1; i<=13; i++)
	{
		if(i>=1 && i<=5 && fmTypeCount[i]>=2)
		{
			attackp+=5;
			intelp+=5;
			//attacks += Math.floor(hero["attack"]*5/100);
			//intels += Math.floor(hero["intel"]*5/100);
		}
		if(i>=6 && i<=7 && fmTypeCount[i]>=2)
		{
			heroInc["criticalProbIncSkill"]+=7;
		}
		if(i>=8 && i<=10 && fmTypeCount[i]>=2)
		{
			physicp+=5;
			magicp+=5;
			//physics += Math.floor(hero["physicDef"]*5/100);
			//magics += Math.floor(hero["magicDef"]*5/100);
		}
		if(i>=11 && i<=13 && fmTypeCount[i]>=2)
		{
			lifep+=10;
			//lifes += Math.floor(hero["life"]*10/100);
			
		}
		
		if(fmTypeCount[i]==4)
		{
			heroInc["fmSkill"] = i;
		}
	}
	heroInc["lifeInc"]=Math.round(hero["life"]*lifep/100)+lifes+jt["life"];
	heroInc["attackInc"]=Math.ceil(hero["attack"]*attackp/100)+attacks+jt["attack"];
	heroInc["intelInc"]=Math.ceil(hero["intel"]*intelp/100)+intels+jt["intel"];
	heroInc["physicInc"]=Math.ceil(hero["physic"]*physicp/100)+physics+jt["physic"];
	heroInc["magicInc"]=Math.ceil(hero["magic"]*magicp/100)+magics+jt["magic"];
	heroInc["techInc"]=Math.ceil(hero["tech"]*techp/100)+techs+jt["tech"];
	
	heroInc["lifeJJC"] = jjc["life"];
	heroInc["attackJJC"] = jjc["attack"];
	heroInc["intelJJC"] = jjc["intel"];
	heroInc["physicJJC"] = jjc["physic"];
	heroInc["magicJJC"] = jjc["magic"];
	heroInc["techJJC"] = jjc["tech"];
	
	heroInc["criticalProbIncSkill"] += jjc["criticalProbInc"];
	heroInc["criticalDamageIncSkill"] += jjc["criticalDamageInc"];
	heroInc["criticalProbDecSkill"] += jjc["criticalProbDec"];
	heroInc["criticalDamageDecSkill"] += jjc["criticalDamageDec"];

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
	heroInc["physicSkill"] = 10;
	heroInc["magicSkill"] = 0;
	heroInc["techSkill"] = 0;
	
	heroInc["physicInc"]=Math.ceil(hero["physic"]*0.15)+55+60;
	heroInc["magicInc"]=60;
	heroInc["techInc"]=0;
	
	heroInc["lifeJJC"] = 480;
	heroInc["physicJJC"] = 45;
	heroInc["magicJJC"] = 0;
	heroInc["techJJC"] = 0;
	
	heroInc["criticalProbIncSkill"] = 0;
	heroInc["criticalDamageIncSkill"] = 0;
	heroInc["criticalProbDecSkill"] = 20;
	heroInc["criticalDamageDecSkill"] = 30;

	return heroInc;
}