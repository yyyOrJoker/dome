<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>工时填报</title>
    <link rel="stylesheet" type="text/css"
          href="resources/jquery-easyui-1.4/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css"
          href="resources/jquery-easyui-1.4/themes/icon.css">
</head>
<body>

<table id="workEditTable" class="easyui-datagrid" data-options="
    'title': '工时填报',
    'fit': true,
    'fitColumns': true,
    'striped': true,
    'loadMsg': '数据加载中...',
    'pagination': true,
    'rownumbers': true,
    'singleSelect': true,
    'checkOnSelect': false,
    'selectOnCheck': false,
    'toolbar': '#workEditToolbar',
    'columns': [[
        {'title': '选择', 'field': 'Id', 'width': 100, 'align': 'center', 'halign': 'center', 'checkbox': true},
        {'title': '项目名称', 'field': 'projectName', 'width': 100, 'align': 'center', 'halign': 'center'},
        {'title': '任务名称', 'field': 'catalogName', 'width': 100, 'align': 'center', 'halign': 'center'},
        {'title': '服务地点', 'field': 'address', 'width': 100, 'align': 'center', 'halign': 'center'},
        {'title': '服务地点', 'field': 'settle', 'width': 100, 'align': 'center', 'halign': 'center'},
        {'title': '星期一', 'field': 'dayText2', 'width': 100, 'align': 'center', 'halign': 'center'},
        {'title': '星期二', 'field': 'dayText3', 'width': 100, 'align': 'center', 'halign': 'center'},
        {'title': '星期三', 'field': 'dayText4', 'width': 100, 'align': 'center', 'halign': 'center'},
        {'title': '星期四', 'field': 'dayText5', 'width': 100, 'align': 'center', 'halign': 'center'},
        {'title': '星期五', 'field': 'dayText6', 'width': 100, 'align': 'center', 'halign': 'center'},
        {'title': '星期六', 'field': 'dayText7', 'width': 100, 'align': 'center', 'halign': 'center'},
        {'title': '星期日', 'field': 'dayText1', 'width': 100, 'align': 'center', 'halign': 'center'}
    ]]
"></table>

<div id="workEditToolbar">
    <div>
        <label>年: <input type="text" class="easyui-numberbox" name="year" data-options="min:1970,max:2100,width:75">
        </label>
        <label>周: <input type="text" class="easyui-numberbox" name="day" data-options="min:1,max:53,width:75"> </label>
        <label><a type="button" class="easyui-linkbutton">查询</a></label>
        <a type="button" class="easyui-linkbutton">上周</a>
        <a type="button" class="easyui-linkbutton">下周</a>
        <a type="button" class="easyui-linkbutton">保存</a>
        <a type="button" class="easyui-linkbutton">提交</a>
        <a type="button" class="easyui-linkbutton">被拒绝的工时信息</a>
        <a type="button" class="easyui-linkbutton">添加工时</a>
    </div>
    <div>
        <label>项目:
            <select class="easyui-combobox" data-options="
                    width:100,
                    panelHeight:'auto',
                    mode: 'remote',
                    url: 'findAllprojects',
                    idField: 'id',
                    textField: 'name',
                    method: 'get'
            "></select>
        </label>
        <label>
            任务:
            <select class="easyui-combobox" data-options="
                    width:100,
                    panelHeight:'auto',
                    url:'findAllcatalogs',
                    mode: 'remote',
                    idField: 'id',
                    textField: 'name',
                    method: 'get'
            "></select>
        </label>
        <label>服务地点:<input type="text" class="easyui-textbox" data-options="width:100"></label>
        <label>结算方式:<input type="text" class="easyui-textbox" data-options="width:100"></label>
        <label>是否净价:<input type="text" class="easyui-textbox" data-options="width:100"></label>
    </div>
</div>

</div>

<script type="text/javascript"
        src="resources/jquery-easyui-1.4/jquery.min.js"></script>
<script type="text/javascript"
        src="resources/jquery-easyui-1.4/jquery.easyui.min.js"></script>
<script type="text/javascript"
        src="resources/jquery-easyui-1.4/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"
        src="js/work_data.js"></script>
<script type="text/javascript"
        src="js/work.js"></script>
</body>
</html>