$(document).ready(function() {


	loadSeasonData();

	$("#set-start-time").click(setTime);
	
	$("#add-player").click(addPlayer);
	
	$("#add-challenger").click(addChallenger);
	
	$("#start-season").click(startSeason);
	
	$("#cancle-season").click(cancelSeason);

});

function loadSeasonData() {
	$.ajax({
		type : "GET",
		url : "/admin/"+matchType+"/currentSeason",
		dataType : "json",
		success : function(season) {

			$("#start-time").val(season['matchTime']);

			$.ajax({
				type : "GET",
				url : "/player",
				dataType : "json",
				success : function(player) {
					buildPlayerList(season, player);
					toggleAll(season);
					
					if(matchType=='kkb') {
						loadRoleList(season);
					}

				},
				error : function(jqXHR) {
					hintError('导入队员列表失败');
				}
			});
			

		},
		error : function(jqXHR) {
			hintError('导入赛季数据失败');
		}
	});
}

function loadRoleList(season) {
	$.ajax({
		type : "GET",
		url : "/admin/"+matchType+"/challengers",
		dataType : "json",
		success : function(challengers) {


			buildRoleList(season, challengers);

		},
		error : function(jqXHR) {
			hintError('导入赛季数据失败');
		}
	});
}

function toggleAll(season) {
	if(season['status']=='PLANNING') 
	{
		$('#set-start-time').attr("disabled",false);
		$('#add-player').attr("disabled",false);
		$('.destory').attr("disabled",false);
		
		$('#player-panel').removeClass('panel-default');
		$('#season-panel').removeClass('panel-default');
		$('#player-panel').addClass('panel-info');
		$('#season-panel').addClass('panel-info');
	}else
	{
		hintInfo('第'+season['number']+'届比赛正在进行中，无法更改赛季设定');
		$('#set-start-time').attr("disabled",true);
		$('#add-player').attr("disabled",true);
		$('.destory').attr("disabled",true);
		
		$('#player-panel').removeClass('panel-info');
		$('#season-panel').removeClass('panel-info');
		$('#player-panel').addClass('panel-default');
		$('#season-panel').addClass('panel-default');
	}

}
function buildRoleList(season, challengers) {
	var list = $("#role-list");
	
	list.children("table").remove();
	
	var data = challengers;
	var number = season['number'];
	var table = $('<table></table>');
	for (var j = 0; j < data.length; j++) {
		var tr = $('<tr></tr>');
		var name = data[j]['name'];
		var td1= $('<td><div class="enroll"><div>'+name+'('+data[j]['server']+')</div></td>');
		var td2=$('<td></td>');
		var destory = $('<div class="destory" value="'+name+'"></div>');
		destory.click(data[j], function(e){
			if($(this).attr("disabled"))
			{
				hintInfo("赛季进行时无法调整队员名单");
				return;
			}
			$.ajax({
				type : "POST",
				url : "/admin/"+matchType+"/removeChallenger",
				dataType : "json",
				data: {
					name:e.data['name'],
					server:e.data['server']
				},
		
				success : function(data) {
					if(data['success']) {
						hintSuccess('该队员将再是擂主');
						loadSeasonData();
					} else {
						hintError(data['message']);
					}
				},
				error : function(jqXHR) {
					hintError('删除失败');
				}
			});
		});
		destory.appendTo(td2);
		td1.appendTo(tr);
		td2.appendTo(tr);
		tr.appendTo(table);
	}
	table.appendTo(list);
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
			if($(this).attr("disabled"))
			{
				hintInfo("赛季进行时无法调整队员名单");
				return;
			}
			$.ajax({
				type : "POST",
				url : "/admin/"+matchType+"/removePlayer",
				dataType : "json",
				data: {
					name:e.data['name'],
					server:e.data['server']
				},
		
				success : function(data) {
					if(data['success']) {
						hintSuccess('该队员将不参加本赛季比赛');
						loadSeasonData();
					} else {
						hintError(data['message']);
					}
				},
				error : function(jqXHR) {
					hintError('删除失败');
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
			url : "/admin/"+matchType+"/setPlanningTime",
			dataType : "json",
			data: {
				date: date,
			},
	
			success : function(data) {
				if(data['success']) {
					hintSuccess('新赛季起始时间设定成功');
				} else {
					hintError(data['message']);
				}
				
	
			},
			error : function(jqXHR) {
				//$("#error-dialog div").html("新赛季起始时间设定失败");
				//$("#error-dialog").model();
			}
		});
		
	}
	else
	{
		alert('还没有设置时间');
	}
}

function addChallenger() {
	var name = $("#new-challenger").val();
	var server = $("#new-challenger-server").val();
	if(!name) {
		hintError('队员名还没填');
	}
	if(!server) {
		hintError('请选择服务器');
	}
	$.ajax({
		type : "POST",
		url : "/admin/"+matchType+"/addChallenger",
		dataType : "json",
		data: {
			name: name,
			server: server
			
		},

		success : function(data) {
			if(data['success']) {
				hintSuccess('添加成员成功');
				loadSeasonData();
			} else {
				hintError(data['message']);		
			}


		},
		error : function(jqXHR) {
			alert('添加失败');
		}
	});
	
}
function addPlayer() {
	var name = $("#new-player").val();
	var server = $("#new-player-server").val();
	if(!name) {
		hintError('队员名还没填');
	}
	if(!server) {
		hintError('请选择服务器');
	}
	$.ajax({
		type : "POST",
		url : "/admin/"+matchType+"/addPlayer",
		dataType : "json",
		data: {
			name: name,
			server: server
			
		},

		success : function(data) {
			if(data['success']) {
				hintSuccess('添加成员成功');
				loadSeasonData();
			} else {
				hintError(data['message']);		
			}


		},
		error : function(jqXHR) {
			alert('添加失败');
		}
	});
}


function startSeason() {
	
	$.ajax({
		type : "POST",
		url : "/admin/"+matchType+"/startSeason",
		dataType : "json",

		success : function(data) {	
			if(data['success']) {
				hintSuccess('新赛季成功开启');
				switchToDashboard();
			} else {
				hintError(data['message']);
			}
		},
		error : function(jqXHR) {
			hintError("开始赛季失败了");
		}
	});
}

function stopSeason() {
	
	$.ajax({
		type : "POST",
		url : "/admin/"+matchType+"/stopSeason",
		dataType : "json",

		success : function(data) {	
			if(data['success']) {
				hintSuccess('当前赛季顺利结束');
			} else {
				hintError(data['message']);
			}
		},
		error : function(jqXHR) {
			hintError("结束赛季失败了");
		}
	});
}

function cancelSeason() {
	
	$.ajax({
		type : "POST",
		url : "/admin/"+matchType+"/cancelSeason",
		dataType : "json",

		success : function(data) {	
			if(data['success']) {
				hintSuccess('中止当前赛季成功');
				loadSeasonData();

			} else {
				hintError(data['message']);
			}
		},
		error : function(jqXHR) {
			hintError('中止当前赛季失败');
		}
	});
}