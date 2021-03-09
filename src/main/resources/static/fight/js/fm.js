

function importFm() {
	
	$("#csvFileInput").val("");
	$("#csvFileInput").click();
}

function exportFm() {
    var aEle = document.createElement("a");// 创建a标签
    aEle.download = "test.csv";
    var text = createFmData();
    var uri = 'data:text/csv;charset=utf-8,\ufeff' + encodeURI(text);
    aEle.href = uri;
    aEle.click();
}

function createFmData()
{
	var fm = userData["fm"]
	var result = "";
	for(var i in fm)
	{
		result+=i;
		result+=",";
		result+=fm[i]["name"];
		result+=",";
		for(var e in parts)
		{
			if(fm[i][parts[e]])
			{
				result+=fm[i][parts[e]]["fm_type"];
				result+=",";
				result+=printFm(fm[i][parts[e]]["fm_info"]);
				result+=",";
			}
		}
		result+=userData["jt"][i]["life"]+"|";
		result+=userData["jt"][i]["attack"]+"|";
		result+=userData["jt"][i]["intel"]+"|";
		result+=userData["jt"][i]["physic"]+"|";
		result+=userData["jt"][i]["magic"]+"|";
		result+=userData["jt"][i]["tech"]+",";
		
		result+=userData["jjc"][i]["life"]+"|";
		result+=userData["jjc"][i]["attack"]+"|";
		result+=userData["jjc"][i]["intel"]+"|";
		result+=userData["jjc"][i]["physic"]+"|";
		result+=userData["jjc"][i]["magic"]+"|";
		result+=userData["jjc"][i]["tech"]+",";
		
		result+=userData["jjc"][i]["criticalProbInc"]+"|";
		result+=userData["jjc"][i]["criticalDamageInc"]+"|";
		result+=userData["jjc"][i]["criticalProbDec"]+"|";
		result+=userData["jjc"][i]["criticalDamageDec"]+",";
		
		var j=0;
		for(var e in parts)
		{
			result+=userData["equip"][i][parts[e]];
			j++;
			if(j!=parts.length)
			{
				result+=",";
			}
		}
		result+="\n";
			
	}
	
	return result;
}

function printFm(fm)
{
	var result = [];
	var i=0;
	for(var key in fm)
	{
		result[i]=key+"="+fm[key];
		i++;
	}
	return result.join(";");
}

function readCSVFile(obj) {
	var reader = new FileReader();
	reader.readAsText(obj.files[0]);
	reader.onload = function() {

		var data = this.result;
		parseCSV(data);
	}

}

var fm_type = [];
fm_type[1]="轻风"; 
fm_type[2]="满月"; 
fm_type[3]="魔术"; 
fm_type[4]="时钟"; 
fm_type[5]="怒涛"; 
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
		var weapon = userData["equip"][i]["weapon"] ? userData["equip"][i]["weapon"] : 0 ;
		var armor = userData["equip"][i]["armor"] ? userData["equip"][i]["armor"] : 0 ;
		var helmet = userData["equip"][i]["helmet"] ? userData["equip"][i]["helmet"] : 0 ;
		var jewelry = userData["equip"][i]["jewelry"] ? userData["equip"][i]["jewelry"] : 0 ;
		var tr = $("<tr><td>"+userData["fm"][i]["name"]+"</td><td><div><img src=\"/fight/image/equip_"+weapon+".png\" alt=\"\" width=\"40\" height=\"40\"></img>"+
							displayFM(userData["fm"][i]["weapon"])+"</div></td><td><div><img src=\"/fight/image/equip_"+armor+".png\" alt=\"\" width=\"40\" height=\"40\"></img>"+
							displayFM(userData["fm"][i]["armor"])+"</div></td><td><div><img src=\"/fight/image/equip_"+helmet+".png\" alt=\"\" width=\"40\" height=\"40\"></img>"+
							displayFM(userData["fm"][i]["helmet"])+"</div></td><td><div><img src=\"/fight/image/equip_"+jewelry+".png\" alt=\"\" width=\"40\" height=\"40\"></img>"+
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
	fmType = parseInt(fmType);
	switch(fmType)
	{		
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
			return "fm_type_red";
		case 6:
		case 7:
			return "fm_type_yellow";
		case 8:
		case 9:	
		case 10:
			return "fm_type_blue";
		case 11:
		case 12:
		case 13:
			return "fm_type_green";
	}
}

function displayFM(fm)
{
	var result = "";
	if(!fm || !fm["fm_type"] || !fm["fm_info"])
	{
		return result;
	}
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
		
		if(!fm || !fm[i])
		{
			continue;
		}
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
	//alert("lifep=="+lifep);
	heroInc["lifeInc"]=Math.round(hero["life"]*lifep/100)+lifes+(jt?jt["life"]:0);
	heroInc["attackInc"]=Math.ceil(hero["attack"]*attackp/100)+attacks+(jt?jt["attack"]:0);
	heroInc["intelInc"]=Math.ceil(hero["intel"]*intelp/100)+intels+(jt?jt["intel"]:0);
	heroInc["physicInc"]=Math.ceil(hero["physic"]*physicp/100)+physics+(jt?jt["physic"]:0);
	heroInc["magicInc"]=Math.ceil(hero["magic"]*magicp/100)+magics+(jt?jt["magic"]:0);
	heroInc["techInc"]=Math.ceil(hero["tech"]*techp/100)+techs+(jt?jt["tech"]:0);
	
	heroInc["lifeJJC"] = (jjc ? jjc["life"] : 0);
	heroInc["attackJJC"] = (jjc ? jjc["attack"]:0);
	heroInc["intelJJC"] = (jjc ? jjc["intel"] : 0 );
	heroInc["physicJJC"] = (jjc ? jjc["physic"] : 0);
	heroInc["magicJJC"] = (jjc? jjc["magic"] : 0 );
	heroInc["techJJC"] = (jjc ? jjc["tech"] : 0);
	
	heroInc["criticalProbIncSkill"] += (jjc ? jjc["criticalProbInc"] : 0);
	heroInc["criticalDamageIncSkill"] += (jjc ? jjc["criticalDamageInc"] : 0);
	heroInc["criticalProbDecSkill"] += (jjc ? jjc["criticalProbDec"] : 0 );
	heroInc["criticalDamageDecSkill"] += (jjc ? jjc["criticalDamageDec"] : 0 );

	return heroInc;
}

function showFmEditor()
{
  	if(editingEquipPart!="jewelry")
	{
		$("#fm-edit-critical").hide();
	} else
	{
		$("#fm-edit-critical").show();
	}

	//$("#equip-editor").find(":text").attr("value", "");
	$("#equip-editor").find(":text").val("");
	$("#fm-edit-desc").html("");
	$("#fm-edit-type").text("选择附魔类型");
	$("#fm-edit-type").attr("value", 0);
	
	var heroId = fightInfo[editingRole]["hero"]["id"];
	if(!userData["fm"][heroId])
	{
		//alert(heroId+" not exist!");
		return;
	}
	
	var fmInfo = userData["fm"][heroId][editingEquipPart];
	//alert(JSON.stringify(fmInfo));
	if(fmInfo)
	{	
		for(var i in fmInfo["fm_info"])
		{
			//alert("i=="+i+", fminfo=="+fmInfo["fm_info"][i]);
			//$("#fm-edit-"+i).attr("value", fmInfo["fm_info"][i]);
			$("#fm-edit-"+i).val(fmInfo["fm_info"][i]);
		}
		$("#fm-edit-desc").html(displayFM(fmInfo));
		$("#fm-edit-type").attr("value", fmInfo["fm_type"]);
		//$("#fm-edit-type").val(fmInfo["fm_type"]);
		$("#fm-edit-type").text(fm_type[fmInfo["fm_type"]]);
	}	
}

function fmEditorSave()
{
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
	
	if(!userData["fm"][heroId])
	{
		userData["fm"][heroId] = {};
	} 
	if(!userData["equip"][heroId])
	{
		userData["equip"][heroId] = {};
	}
	
	userData["fm"][heroId]["id"] = heroId;
	userData["fm"][heroId]["name"] = fightInfo[editingRole]["hero"]["name"];

	if(!userData["fm"][heroId][editingEquipPart])
	{
		userData["fm"][heroId][editingEquipPart] = {};
	}
	userData["fm"][heroId][editingEquipPart]["fm_info"] = fm;
	userData["fm"][heroId][editingEquipPart]["fm_type"] = parseInt($("#fm-edit-type").attr("value"));
	$("#fm-edit-desc").html(displayFM(userData["fm"][heroId][editingEquipPart]));
	
	if(fightInfo[editingRole]["equip"][editingEquipPart])
	{
		userData["equip"][heroId]["id"] = heroId;
		userData["equip"][heroId][editingEquipPart] = fightInfo[editingRole]["equip"][editingEquipPart]["id"];
	}
	loadComplete(editingRole);
	refreshTable();
}


function showJtEditor()
{
	var heroId = fightInfo[editingRole]["hero"]["id"];
	var jt = userData["jt"][heroId];
	var jjc = userData["jjc"][heroId];
	$("#hero-editor").find(":text").val("");
	if(jt)
	{
		for(var i in jt)
		{
			//$("#jt-edit-"+i).attr("value", jt[i]);
			if(jt[i])
			{
				$("#jt-edit-"+i).val(jt[i]);
			}
		}
	}
	
	if(jjc)
	{
		for(var i in jjc)
		{
			//$("#jjc-edit-"+i).attr("value", jjc[i]);
			if(jjc[i])
			{
				$("#jjc-edit-"+i).val(jjc[i]);
			}
		}
	}
}

function jtEditorSave()
{
	var jtKey = ["life", "attack", "intel","physic","magic","tech"];
	var jjcKey = ["life", "attack", "intel","physic","magic","tech","criticalProbInc","criticalDamageInc","criticalProbDec","criticalDamageDec"];

	var heroId = fightInfo[editingRole]["hero"]["id"];
	if(!userData["jt"][heroId])
	{
		userData["jt"][heroId] = {};
	}
	if(!userData["jjc"][heroId])
	{
		userData["jjc"][heroId] = {};
	}
	userData["jt"][heroId]["id"] = heroId;
	userData["jt"][heroId]["name"] = fightInfo[editingRole]["hero"]["name"];
	
	userData["jjc"][heroId]["id"] = heroId;
	userData["jjc"][heroId]["name"] = fightInfo[editingRole]["hero"]["name"];
	for(var i in jtKey)
	{
		var value = $("#jt-edit-"+jtKey[i]).val();
		if(value)
		{
			userData["jt"][heroId][jtKey[i]] = parseInt(value);
		} else
		{
			userData["jt"][heroId][jtKey[i]] = 0 ;
		}
	}
	for(var i in jjcKey)
	{
		var value = $("#jjc-edit-"+jjcKey[i]).val();
		if(value)
		{
			userData["jjc"][heroId][jjcKey[i]] = parseInt(value);
		} else
		{
			userData["jjc"][heroId][jjcKey[i]] = 0 ;
		}
	}
	loadComplete(editingRole);
	refreshTable();
}








