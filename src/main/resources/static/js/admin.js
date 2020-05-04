$(document).ready(function() {


	loadSeasonData();

	$("#set-start-time").click(setTime);
	
	$("#add-player").click(addPlayer);
	
	$("#start-season").click(startSeason);
});

function loadSeasonData() {
	$.ajax({
		type : "GET",
		url : "/admin/planning",
		dataType : "json",
		success : function(season) {
			$("#start-time").val(season['matchTime']);
			$.ajax({
				type : "GET",
				url : "/player",
				dataType : "json",
				success : function(player) {
					buildPlayerList(season, player);
				},
				error : function(jqXHR) {
					// alert("Error: "+jqXHR.status);
				}
			});
			

		},
		error : function(jqXHR) {
			// alert("Error: "+jqXHR.status);
		}
	});
}

function buildPlayerList(season, player) {
	var list = $("#player-list");
	
	list.children("table").remove();
	
	var data = season['players'];
	var number = season['number'];
	var table = $('<table></table>');
	for (var j = 0; j < data.length; j++) {
		var tr = $('<tr></tr>');
		var name = data[j]['name'];
		var td1= $('<td><div class="enroll"><div>'+name+'('+data[j]['server']+')</div></td>');
		var td2=$('<td></td>');
		var destory = $('<div class="destory" value="'+name+'"></div>');
		destory.click(data[j], function(e){
			$.ajax({
				type : "POST",
				url : "/admin/removePlayer",
				dataType : "text",
				data: {
					name:e.data['name'],
					server:e.data['server']
				},
		
				success : function(data) {
					loadSeasonData();
		
				},
				error : function(jqXHR) {
					alert('删除失败');
				}
			});
		});
		destory.appendTo(td2);
		td1.appendTo(tr);
		td2.appendTo(tr);
		tr.appendTo(table);

		
		
		player.forEach(function(item, index, arr) {
		    if(item['name'] === data[j]['name'] && item['server'] === data[j]['server']) {
		    	player.splice(index, 1);
		    }
		});
	}
	
	table.appendTo(list);

	var un_list = $("#unplayer-list");
	un_list.children("table").remove();
	table = $('<table></table>');
	
	for (var k = 0; k < player.length; k++) {
		var tr = $('<tr></tr>');
		var td = $('<div class="unroll">'+player[k]['name']+'('+player[k]['server']+')</div>');
		td.appendTo(tr);
		tr.appendTo(table);
	}
	
	table.appendTo(un_list);
}

function setTime(){
	var date = $("#start-time").val();
	
	if(date) 
	{	
		$.ajax({
			type : "POST",
			url : "/admin/setPlanningTime",
			dataType : "text",
			data: {
				date: date,
			},
	
			success : function(data) {
				alert('新赛季起始时间设定成功');
	
			},
			error : function(jqXHR) {
				alert('新赛季起始时间设定失败');
			}
		});
		
	}
	else
	{
		alert('还没有设置时间');
	}
}

function addPlayer() {
	var name = $("#new-player").val();
	var server = $("#new-player-server").val();
	$.ajax({
		type : "POST",
		url : "/admin/addPlayer",
		dataType : "text",
		data: {
			name: name,
			server: server
			
		},

		success : function(data) {
			loadSeasonData();

		},
		error : function(jqXHR) {
			alert('添加失败');
		}
	});
}


function startSeason() {
	
	$.ajax({
		type : "POST",
		url : "/admin/startSeason",
		dataType : "text",

		success : function(data) {	
			window.location.href = '/index.html';

		},
		error : function(jqXHR) {
			alert('启动新赛季失败');
		}
	});
	
}