$(document).ready(function() {
	
	$.ajax({
		type : "GET",
		url : "/admin/currentSeason",
		dataType : "json",
		success : function(season) {

			loadSeasonData(season) ;

			$.ajax({
				type : "GET",
				url : "/record/"+season['number']+"/score-board",
				dataType : "json",
				success : function(data) {
					buildScoreTable2(data);
					loadRecordList(season['number']);
				},
				error : function(jqXHR) {
					hintError('获取排行榜数据失败');
				}
			});

		},
		error : function(jqXHR) {
			hintError('导入赛季数据失败');
		}
	});
	


	$.ajax({
		type : "GET",
		url : "/systemInfo",
		dataType : "json",
		success : function(data) {
			buildFooter(data);
		},
		error : function(jqXHR) {
			// alert("Error: "+jqXHR.status);
		}
	});
	
	$("#season-control").click(switchToAdmin);
	$("#home").click(switchToDashboard);
	$("#save-score").click(switchToScore);
});

var matchType = 'jbb';
function loadSeasonData(season) {
	var hint;
	if(season['status']=='PLANNING') 
	{
		hint='第'+season['number']+'届比赛正在筹备中';
	} else if(season['status']=='RUNNING') 
	{
		hint='第'+season['number']+'届比赛火热进行中';
	} else	
	{
		hint='unknown status for season '+season['number'];
	}
	$("#season-hint").html(hint);
}

function hintInfo(message) 
{
	$("#hint-dialog").children('div').remove();
	$('<div class="alert alert-info">'+message+'</div>').appendTo($("#hint-dialog"));
	$("#hint-dialog").modal('show');
}

function hintError(message) 
{
	$("#hint-dialog").children('div').remove();
	$('<div class="alert alert-danger">'+message+'</div>').appendTo($("#hint-dialog"));
	$("#hint-dialog").modal('show');
}

function hintSuccess(message) 
{
	$("#hint-dialog").children('div').remove();
	$('<div class="alert alert-success">'+message+'</div>').appendTo($("#hint-dialog"));
	$("#hint-dialog").modal('show');
}

function buildFooter(data) {
	$("#footer").html('黑豆的个人空间 版本:' + data['version'] + ' <br>苏ICP备20025024号');
}

function switchToDashboard() {
	window.location.href = '/match';
}

function switchToScore() {
	$.ajax({
		type : "GET",
		url : "/match/score.html",
		dataType : "text",
		success : function(data) {
			$("#content").children("div").remove();
			$("#content").html(data);
			$.getScript("/match/js/score.js", function(){
			});
		},
		error : function(jqXHR) {
			// alert("Error: "+jqXHR.status);
		}
	});
}

function switchToAdmin() {
	$.ajax({
		type : "GET",
		url : "/match/admin.html",
		dataType : "text",
		success : function(data) {
			$("#content").children("div").remove();
			$("#content").html(data);
			$.getScript("/match/js/admin.js", function(){
			});
		},
		error : function(jqXHR) {
			// alert("Error: "+jqXHR.status);
		}
	});
}

function loadRecordList(season) {
	$.ajax({
		type : "GET",
		url : "/record/"+season,
		dataType : "json",
		success : function(data) {
			buildRecordList2(data);
		},
		error : function(jqXHR) {
			// alert("Error: "+jqXHR.status);
		}
	});
}

function buildScoreTable2(data) {
	
	new Vue({
		el : '#score-board',
		data : {
			scores : data
		}
	})


}

function buildRecordList2(data) {
	var result = new Array();
	var currentTime;
	var list;
	for (var j = 0; j < data.length; j++) {
		var score;
		if (data[j]['score1'] == -1 || data[j]['score2'] == -1) {
			score = "- : -";
		} else {
			score = data[j]['score1'] + " : " + data[j]['score2'];
		}

		if (currentTime != data[j]['matchTime']) {
			currentTime = data[j]['matchTime'];
			list = new Array();
			result.push(list);
		}
		
		data[j]['score']=score;

		list.push(data[j]);

	}

	new Vue({
		el : '#schedule-list',
		data : {
			records : result
		},
		methods: {
			getMap: function (map) {
				return mapName(map);
			}
		}
	})

}

function mapName(map) {
	switch (map) {
	case 1:
		return '纷争平原(1)';
	case 2:
		return '西风哨岗(2)';
	case 3:
		return '海角之泪(3)';
	case 4:
		return '沙漠之眼(4)';
	case 5:
		return '空中庭院(5)';
	case 6:
		return '双桥逆波(6)';
	case 7:
		return '凛冬栖地(7)';
	}

}
