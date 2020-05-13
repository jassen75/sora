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
	currentSeason = season['number'];
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