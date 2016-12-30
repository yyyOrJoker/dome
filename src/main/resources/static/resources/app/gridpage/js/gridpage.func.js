function switchOp(e) {
	var $text = $(e.data.target);
	var $op = $(e.target);
	 if ($op.hasClass('icon-eq')) {
	 	$op.removeClass('icon-eq').addClass('icon-like');
	 	$text.data('op', Like);
	 } else {
	 	$op.removeClass('icon-like').addClass('icon-eq') 
	 	$text.data('op', Equal);
	 }
}

function openRefDialog(e) {
	var $ref = $(e.data.target);
	var attributeTemplateId = $ref.attr('data-attributeTemplateId'), title = $ref.attr('data-title');
	var $win = $('#win_' + attributeTemplateId);
	if ($win.length == 0) {
		$win = $('<div id="win_' + attributeTemplateId + '">').appendTo("body");
		$win.window({
			title: title,
			width: 650,
			height: 500,
			minimizable: false,
			maximizable: false,
			collapsible: false,
			modal: true,
			closed: true,
			href: 'loadReferenceQueryGridpage.action?attributeTemplateId=' + attributeTemplateId
		})
	}
	$win.data('opener', $ref);
	$win.window('open');
}

Date.prototype.format = function(fmt) {
  var o = {   
    "M+" : this.getMonth()+1,                 //月份
    "d+" : this.getDate(),                    //日   
    "h+" : this.getHours(),                   //小时   
    "m+" : this.getMinutes(),                 //分   
    "s+" : this.getSeconds(),                 //秒   
    "q+" : Math.floor((this.getMonth()+3)/3), //季度   
    "S"  : this.getMilliseconds()             //毫秒
  };   
  if(/(y+)/.test(fmt))   
    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
  for(var k in o)   
    if(new RegExp("("+ k +")").test(fmt))   
  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
  return fmt;
}

function query(btn, gridpageId) {
	var values = [];
	var $btn = $(btn), $datagrid = $btn.closest('.datagrid').find('.my-datagrid');
	
	$btn.closest('.datagrid-toolbar').find('.criteria').each(function() {
		var $this = $(this);
		var name = $this.attr('data-code'); 
		var op = $this.data('op');
		var dataType = $this.attr('data-type');
		var value = $this.textbox('getValue');
		if ($this.hasClass('my-date')) {
			if (op == Between) {
				var startAndEnd = value.split(',');
				values.push({attribute: name, dataType: dataType, criteriaType: GreaterthanOrEqual, value: startAndEnd[0]})
				op = LessthanOrEqual;
				value = startAndEnd[1];
			}
		}
		if (value && op) {
			values.push({attribute: name, dataType: dataType, criteriaType: op, value: value})
		}
	});
	$datagrid.datagrid('options').url = 'loadGridpageData.action?gridpage.id=' + gridpageId;
	$datagrid.datagrid('load',  {paramJson: JSON.stringify(values)} );
}

function clean(btn) {
	$(btn).closest('.datagrid-toolbar').find('.criteria').textbox('clear');
	$('.my-date-dropdown').find('.after,.before').textbox('clear');
}

function _export() {
	alert('功能未实现......');
}

function okDlg(btn) {
	var $win = $(btn).closest('.window-body');
	var $dg = $win.find('.my-datagrid');
	var $ref = $win.data('opener');
	//console.log($win.data('opener'));
	
	var values = [], texts = [];
	var selectedRows = $dg.datagrid('getSelections');
	if (selectedRows.length == 0) {
		alert('未选中记录');
		return;
	}
	
	$.each(selectedRows, function(i, e) {
		values.push(e["entityId"]);
		texts.push(e["name"]);
	});
	
	$ref.textbox('setValue', values.join(',')).textbox('setText', texts.join(','));
	if (values.length > 1) {
		$ref.data('op', In);
	} else {
		$ref.data('op', Equal);
	}
	$win.window('close');
}

function closeDlg(btn) {
	$(btn).closest('.window-body').window('close');
}

function doAction(btn, entityTypeId) {
	var $btn = $(btn), url = $btn.attr('data-url'), urlType = $btn.attr('data-urltype'), selectionConstraint = $btn.attr('data-selectionConstraint');
	var selectedRows = $mainDataGrid.datagrid('getSelections');
	if ((selectionConstraint == 1 && selectedRows.length != 1) ||
		(selectionConstraint == 2 && selectedRows.length == 0)) {
		alert('请选中' + (selectionConstraint == 1 ? '1条' : '') + '记录!');
		return;
	}
	var entityIds = [];
	$.each(selectedRows, function(i, e) {
		entityIds.push(e["entityId"]);
	});
	if (urlType == 1) { //html
		url += '?entityTypeId=' + entityTypeId;
		if (selectionConstraint != 0) {
			url += '&entityIds=' + entityIds.join(',');
		}
		open(url);
	} else if (urlType == 2) { //js
		var param = {entityTypeId: entityTypeId};
		if (selectionConstraint != 0) {
			param.entityIds = entityIds.join(',');
		}
		$.post(
			url,
			param,
			function() {},
			'script'
		);
	}
}

function save() {
	
    $.messager.progress({
        title: '保存',
        msg: '正在保存，请稍候...'
    });
    
	var attributes = {};
	var extension = {attributes: attributes};
	var jsonEntity = {
		id: $('#id').val(),
		//entityTypeId: $('#entityTypeId').val(),
		specId: $('#specId').val(),
		version: $('#version').val(),
		extension: extension
	};
	$('.attr').each(function() {
		var $this = $(this);
		var name = $this.attr('code');
		var value = $this.textbox('getValue');
		if (name && value) {
			if ($this.attr('data-extension')) {
				attributes[name] = value;
			} else {
				jsonEntity[name] = value;
			}
		}
	})
	/*
	$('.attr').each(function() {
		jsonEntity[$(this).attr('name')] = $(this).textbox('getValue');
	})
	*/
	
	$.post(
		'commonSave.action',
		{
			entityTypeId: $('#entityTypeId').val(),
			paramJson: JSON.stringify(jsonEntity)
		},
		function(response) {
			$.messager.progress('close');
			$.messager.alert('保存', response.result);
		}
	);
}
