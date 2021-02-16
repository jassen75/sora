

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
fm_type[0]="轻风"; 
fm_type[1]="满月"; 
fm_type[2]="魔术"; 
fm_type[3]="怒涛"; 
fm_type[4]="时钟"; 
fm_type[5]="烈日"; 
fm_type[6]="流星"; 
fm_type[7]="顽石"; 
fm_type[8]="水晶"; 
fm_type[9]="寒冰"; 
fm_type[10]="大树"; 
fm_type[11]="荆棘"; 
fm_type[12]="钢铁"; 

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
	fightInfo["fm"] = {};
	fightInfo["jt"] = {};
	fightInfo["jjc"] = {};
	fightInfo["equip"] = {};
	var csvarry = data.split("\r\n");
     for(var i = 0; i<csvarry.length;i++){
     	
        var temp = csvarry[i].split(",");

        if(temp.length==17)
        {
        	var id =  temp[0];
        	fightInfo["fm"][id] = {};
        	fightInfo["fm"][id]["id"] = temp[0];
        	fightInfo["fm"][id]["name"] = temp[1];
        	
        	fightInfo["fm"][id]["weapon"] = {};
        	fightInfo["fm"][id]["weapon"]["fm_type"] =temp[2];
        	fightInfo["fm"][id]["weapon"]["fm_info"] = parseFM(temp[3]);
        	
        	fightInfo["fm"][id]["armor"] = {};
        	fightInfo["fm"][id]["armor"]["fm_type"] = temp[4];
        	fightInfo["fm"][id]["armor"]["fm_info"] = parseFM(temp[5]);
        	
        	fightInfo["fm"][id]["helmet"]  = {};
        	fightInfo["fm"][id]["helmet"]["fm_type"] = temp[6];
        	fightInfo["fm"][id]["helmet"]["fm_info"] = parseFM(temp[7]);
        	
        	fightInfo["fm"][id]["jewelry"]  = {};
        	fightInfo["fm"][id]["jewelry"]["fm_type"] = temp[8];
        	fightInfo["fm"][id]["jewelry"]["fm_info"] = parseFM(temp[9]);
        	
        	fightInfo["jt"][id]  = {};

        	fightInfo["jt"][id] = parseJT(temp[10]);
        	fightInfo["jt"][id]["id"] = temp[0];
        	fightInfo["jt"][id]["name"] = temp[1];
        	
        	fightInfo["jjc"][id]  = {};
        	fightInfo["jjc"][id] = parseJJC(temp[11], temp[12]);
        	fightInfo["jjc"][id]["id"] = temp[0];
        	fightInfo["jjc"][id]["name"] = temp[1];
        	
        	fightInfo["equip"][id]  = {};
        	fightInfo["equip"][id]["id"]  = temp[0];
        	fightInfo["equip"][id]["name"]  = temp[1];
        	fightInfo["equip"][id]["weapon"]  = temp[13];
        	fightInfo["equip"][id]["armor"]  = temp[14];
        	fightInfo["equip"][id]["helmet"]  = temp[15];
        	fightInfo["equip"][id]["jewelry"]  = temp[16];
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
	 	result[kv[0]] = kv[1];
	 }
	 return result;
}

function parseJT(obj)
{
	var result = {};
	var temp = obj.split("|");
	
	 result["life"] = temp[0];
	 result["attack"] = temp[1];
	 result["intel"] = temp[2];
	 result["physicDef"] = temp[3];
	 result["magicDef"] = temp[4];
	 result["tech"] = temp[5];
	 return result;
}

function parseJJC(obj1, obj2)
{
	var result = {};
	var temp1 = obj1.split("|");
	var temp2 = obj2.split("|");
	
	 result["life"] = temp1[0];
	 result["attack"] = temp1[1];
	 result["intel"] = temp1[2];
	 result["physicDef"] = temp1[3];
	 result["magicDef"] = temp1[4];
	 result["tech"] = temp1[5];
	 
	 result["criticalPropInc"] = temp2[0];
	 result["criticalDamageInc"] = temp2[1];
	 result["criticalPropDec"] = temp2[2];
	 result["criticalDamageDec"] = temp2[3];
	 return result;
}

function refreshTable()
{
	$("#fm_info > tbody").children("tr").remove();
	
	for(var i in fightInfo["fm"]){
		
		var tr = $("<tr><td>"+fightInfo["fm"][i]["name"]+"</td><td>"+
							displayFM(fightInfo["fm"][i]["weapon"])+"</td><td>"+
							displayFM(fightInfo["fm"][i]["armor"])+"</td><td>"+
							displayFM(fightInfo["fm"][i]["helmet"])+"</td><td>"+
							displayFM(fightInfo["fm"][i]["jewelry"])+"</td></tr>");
		
	  	tr.appendTo($("#fm_info > tbody"));
    }
    
    $("#jt_info > tbody").children("tr").remove();
	
	for(var i in fightInfo["jt"]){
		
		var tr = $("<tr><td>"+fightInfo["jt"][i]["name"]+"</td><td>"+
							fightInfo["jt"][i]["life"]+"</td><td>"+
							fightInfo["jt"][i]["attack"]+"</td><td>"+
							fightInfo["jt"][i]["intel"]+"</td><td>"+
							fightInfo["jt"][i]["physicDef"]+"</td><td>"+
							fightInfo["jt"][i]["magicDef"]+"</td><td>"+
							fightInfo["jt"][i]["tech"]+"</td><td>"+
							fightInfo["jjc"][i]["life"]+"</td><td>"+
							fightInfo["jjc"][i]["attack"]+"</td><td>"+
							fightInfo["jjc"][i]["intel"]+"</td><td>"+
							fightInfo["jjc"][i]["physicDef"]+"</td><td>"+
							fightInfo["jjc"][i]["magicDef"]+"</td><td>"+
							fightInfo["jjc"][i]["tech"]+"</td><td>"+
							fightInfo["jjc"][i]["criticalPropInc"]+"</td><td>"+
							fightInfo["jjc"][i]["criticalDamageInc"]+"</td><td>"+
							fightInfo["jjc"][i]["criticalPropDec"]+"</td><td>"+
							fightInfo["jjc"][i]["criticalDamageDec"]+"</td></tr>");
		
	  	tr.appendTo($("#jt_info > tbody"));
    }
}

function fmClass(fmType)
{
	switch(fmType)
	{
		case "0":
		case 1:
		case 2:
		case 3:
		case 4:
			return "fm_type_red";
		case 5:
		case 6:
			return "fm_type_yellow";
		case 7:
		case 8:
		case 9:
			return "fm_type_blue";
		case 10:
		case 11:
		case 12:
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
