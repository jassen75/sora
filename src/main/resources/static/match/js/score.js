$(document).ready(function() {

	$.ajax({
		type : "GET",
		url : "/admin/players",
		dataType : "json",
		success : function(data) {
			buildPlayerList(data);

		},
		error : function(jqXHR) {
			// alert("Error: "+jqXHR.status);
		}
	});

	$("#change-score").click(changeScore);

});

function buildPlayerList(data) {	
	var player1_sel = $("#player1-sel");
	player1_sel.children("select").remove();
	
	var sel1 = $("<select class='players'></select>");
	for (var j = 0; j < data.length; j++) {
		var tr = $('<option>'+data[j]['name']+'</option>');
		tr.appendTo(sel1);
	}
	sel1.appendTo(player1_sel);
	
	var sel2 = $("<select class='players'></select>");
	for (var j = 0; j < data.length; j++) {
		var tr = $('<option>'+data[j]['name']+'</option>');
		tr.appendTo(sel2);
	}
	var player2_sel = $("#player2-sel");
	player2_sel.children("select").remove();
	sel2.appendTo(player2_sel);
	
	
}

function changeScore()
{
	var player1 = $("#player1-sel > select").val();
	var player2 = $("#player2-sel > select").val();
	var score = $("#score-input").val();
	

	if(player1==player2)
	{
		alert("相同的选手");
		return;
	}
	
	var patt = /^\d:\d$/;
	if(!patt.test(score))
	{
		if(/^\d：\d$/.test(score))
		{
			score = score.replace("：", ":");
		} else
		{
			hintError("不合法的比分")
			return;
		}
	}
	$.ajax({
		type : "POST",
		url : "/admin/setScore",
		dataType : "json",
		data: {
			player1:player1,
			player2:player2,
			score:score
		},

		success : function(data) {
			if(data['code'] != 0) {
				hintError(data['message']);
			} else {
				hintSuccess('修改比分成功');
			}

		},
		error : function(jqXHR) {
			hintError('修改比分失败');
		}
	});
	
}