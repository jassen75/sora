$(document).ready(function() {
	matchType = 'kkb';
	$.ajax({
		type : "GET",
		url : "/admin/"+matchType+"/currentSeason",
		dataType : "json",
		success : function(season) {
			loadSeasonData(season) ;
			loadRecordList(season) ;

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

var matchType = 'kkb';
var currentSeason;


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

function switchToDashboard() {
	window.location.href = '/lyb';
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
		url : "/record/kkb/"+season['number'],
		dataType : "json",
		success : function(data) {
			buildRecordList2(data);
		},
		error : function(jqXHR) {
			// alert("Error: "+jqXHR.status);
		}
	});
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

		if (currentTime != data[j]['group']) {
			currentTime = data[j]['group'];
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

