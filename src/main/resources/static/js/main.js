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
	window.location.href = '/';
}