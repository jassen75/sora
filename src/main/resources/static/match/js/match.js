$(document).ready(function() {
	$.ajax({
		type : "GET",
		url : "/record/1/score-board",
		dataType : "json",
		success : function(data) {
			buildScoreTable2(data);
			loadRecordList();
		},
		error : function(jqXHR) {
			// alert("Error: "+jqXHR.status);
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
	$("#hint-dialog").modal('hide');
	$("#error-dialog").modal('hide');
});

function buildFooter(data) {
	$("#footer").html('黑豆的个人空间 版本:' + data['version'] + ' <br>苏ICP备20025024号');
}

function switchToDashboard() {
	window.location.href = '/';
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

function loadRecordList() {
	$.ajax({
		type : "GET",
		url : "/record/1",
		dataType : "json",
		success : function(data) {
			buildRecordList2(data);
		},
		error : function(jqXHR) {
			// alert("Error: "+jqXHR.status);
		}
	});
}

function buildScoreTable(data) {
	var list = $("#score-board");

	list.children("table").remove();
	var childdiv = $('<table><tr><th class="nm">选手</th><th class="sf">胜</th><th class="sf">平</th><th class="sf">负</th><th class="sc">分数</th></tr></table>');

	for (var j = 0; j < data.length; j++) {
		var tr = $('<tr><td>' + data[j]['name'] + '</td><td>' + data[j]['win']
				+ '</td><td>' + data[j]['draw'] + '</td><td>' + data[j]['lose']
				+ '</td><td class="sc">' + data[j]['score'] + '</td></tr>');
		tr.appendTo(childdiv);
	}

	childdiv.appendTo(list);

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
		}
	})

}

function buildRecordList(data) {

	var list = $("#this-week");

	list.children("div").remove();
	list.children("br").remove();

	var currentTime;
	var lc = 1;
	var region;
	var panelBody;
	for (var j = 0; j < data.length; j++) {
		var score;
		if (data[j]['score1'] == -1 || data[j]['score2'] == -1) {
			score = "- : -";
		} else {
			score = data[j]['score1'] + " : " + data[j]['score2'];
		}

		if (currentTime != data[j]['matchTime']) {
			currentTime = data[j]['matchTime'];
			region = $('<div class="panel panel-default"><div class="panel-heading"><h3 class="panel-title">第'
					+ lc
					+ '轮</h3><div class="time_label">'
					+ currentTime
					+ ' 10:00PM</div></div></div>');
			panelBody = $('<div class="panel-body"><table class="table table-striped"></table></div>');

			lc++;
			region.appendTo(list);
			panelBody.appendTo(region);
			$('<br><br>').appendTo(list);
		}
		var div = $('<div class="panel-body"><div class="players">'
				+ data[j]['player1']['name'] + ' vs '
				+ data[j]['player2']['name'] + '</div><div class="map">'
				+ getMap(data[j]['map']) + '</div><div class="score">' + score
				+ '</div></div>');
		div.appendTo(panelBody);

	}

}

function getMap(map) {
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
