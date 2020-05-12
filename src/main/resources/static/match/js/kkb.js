$(document).ready(function() {
	
	$.ajax({
		type : "GET",
		url : "/admin/currentSeason",
		dataType : "json",
		success : function(season) {


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
		url : "/match/kkb_admin.html",
		dataType : "text",
		success : function(data) {
			$("#content").children("div").remove();
			$("#content").html(data);
		},
		error : function(jqXHR) {
			// alert("Error: "+jqXHR.status);
		}
	});
}
