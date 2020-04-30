

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
	var childdiv = $('<table><tr><th>选手</th><th>胜</th><th>平</th><th>负</th><th>分数</th></tr></table>');
	
	for (var j = 0; j < data.length; j++) {
		var tr = $('<tr><td>'+data[j]['name']+'</td><td>'+data[j]['win']+'</td><td>'+data[j]['draw']+'</td><td>'+data[j]['lose']+'</td><td>'+data[j]['score']+'</td></tr>');
		tr.appendTo(childdiv);
	}
	
	childdiv.appendTo(list);


}


function buildRecordList(data) {
	
	var list = $("#this-week");
	
	list.children("div").remove();
	
	for (var j = 0; j < data.length; j++) {
		var score;
		if(data[j]['score1']==-1 || data[j]['score2']==-1)
		{
			score="- : -";
		} else
		{
			score=data[j]['score1']+" : "+data[j]['score2'];
		}
		var div = $('<div class="panel">'+data[j]['player1']['name']+' vs '+data[j]['player2']['name']+'   '+score+'</div>');
		div.appendTo(list);
	}
	
}
