$(document).ready(function() {

	$.ajax({
		type : "GET",
		url : "/player",
		dataType : "json",
		success : function(data) {
			buildPlayerList(data);

		},
		error : function(jqXHR) {
			// alert("Error: "+jqXHR.status);
		}
	});


});

function buildPlayerList(data) {
	var list = $("#player-list");
	
	list.children("div").remove();
	
	for (var j = 0; j < data.length; j++) {
		var tr = $('<div>'+data[j]['name']+'('+data[j]['server']+')</div>');
		tr.appendTo(list);
	}
	
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