$(document).ready(function() {
	
	$.ajax({
		type : "GET",
		url : "/admin/jbb/currentSeason",
		dataType : "json",
		success : function(season) {

			loadSeasonData(season) ;

			$.ajax({
				type : "GET",
				url : "/record/jbb/"+season['number']+"/score-board",
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
var currentSeason;

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
		url : "/record/jbb/"+season+"/stage/1",
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


