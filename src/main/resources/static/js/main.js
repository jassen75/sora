

$(document).ready(function() {
	$.ajax({
		type : "GET",
		url : "/record/1/score-board",
		dataType : "json",
		success : function(data) {
			buildScoreTable(data);
			loadRecordList();
		},
		error : function(jqXHR) {
			// alert("Error: "+jqXHR.status);
		}
	});

	



});

function loadRecordList()
{
	$.ajax({
		type : "GET",
		url : "/record/1",
		dataType : "json",
		success : function(data) {
			buildRecordList(data);

		},
		error : function(jqXHR) {
			// alert("Error: "+jqXHR.status);
		}
	});
}

function buildScoreTable(data)
{
	var list = $("#score-board");
	
	list.children("table").remove();
	var childdiv = $('<table><tr><th class="nm">选手</th><th class="sf">胜</th><th class="sf">平</th><th class="sf">负</th><th class="sc">分数</th></tr></table>');
	
	for (var j = 0; j < data.length; j++) {
		var tr = $('<tr><td>'+data[j]['name']+'</td><td>'+data[j]['win']+'</td><td>'+data[j]['draw']+'</td><td>'+data[j]['lose']+'</td><td class="sc">'+data[j]['score']+'</td></tr>');
		tr.appendTo(childdiv);
	}
	
	childdiv.appendTo(list);


}


function buildRecordList(data) {
	
	var list = $("#this-week");
	
	list.children("div").remove();
	list.children("br").remove();
	
	var currentTime;
	var lc = 1;
	var region;
	for (var j = 0; j < data.length; j++) {
		var score;
		if(data[j]['score1']==-1 || data[j]['score2']==-1)
		{
			score="- : -";
		} else
		{
			score=data[j]['score1']+" : "+data[j]['score2'];
		}
		
		if(currentTime != data[j]['matchTime'])
		{
			currentTime=data[j]['matchTime'];
			region= $('<div class="region">第'+lc+'轮</div>');
			lc++;
			region.appendTo(list);
			$('<br><br>').appendTo(list);
		}
		var div = $('<div class="panel"><div class="label">'+data[j]['player1']['name']+' vs '+data[j]['player2']['name']+'</div><div class="score">'+score+'</div></div>');
		div.appendTo(region);
		
		
		
	}
	
}
