$(document).ready(function() {


	loadHeroData();

	$("#aa").show();
	$("#ai").hide();
	$("#da").show();
	$("#di").hide();
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

var isAttacker = 1;
function displayHero(id, hero, heroEquip)
{
	if(isAttacker==1)
	{
		$("#attackerPic").children("div").remove();
		var pic = $("<div><img src=\"/fight/image/"+id+".png\" alt=\"\" width=\"60\" height=\"86\"></img>"+hero["name"]+"</div>");
		pic.appendTo($("#attackerPic"));
		
		if(hero["isPhysic"])
		{
			$("#aa").show();
			$("#ai").hide();
		} else
		{
			$("#ai").show();
			$("#aa").hide();
		}
		isAttacker=0;
	} else
	{
		$("#defenderPic").children("div").remove();
		var pic = $("<div><img src=\"/fight/image/"+id+".png\" alt=\"\" width=\"60\" height=\"86\"></img>"+hero["name"]+"</div>");
		pic.appendTo($("#defenderPic"));
		
		if(hero["isPhysic"])
		{
			$("#da").show();
			$("#di").hide();
		} else
		{
			$("#da").hide();
			$("#di").show();
		}
		
		isAttacker=1;
	}
	
}



