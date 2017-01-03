var work_edit_table_data = {
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
        {'title': '选择', 'field': 'projectName', 'width': 100, 'align': 'center', 'halign': 'center', 'checkbox': true},
        {'title': '项目名称', 'field': 'projectName', 'width': 100, 'align': 'center', 'halign': 'center'},
        {'title': '任务名称', 'field': 'catalogName', 'width': 100, 'align': 'center', 'halign': 'center'},
        {'title': '服务地点', 'field': 'address', 'width': 100, 'align': 'center', 'halign': 'center'},
        {'title': '服务地点', 'field': 'settle', 'width': 100, 'align': 'center', 'halign': 'center'},
        {'title': '星期一', 'field': 'monday', 'width': 100, 'align': 'center', 'halign': 'center'},
        {'title': '星期二', 'field': 'tuesday', 'width': 100, 'align': 'center', 'halign': 'center'},
        {'title': '星期三', 'field': 'wednesday', 'width': 100, 'align': 'center', 'halign': 'center'},
        {'title': '星期四', 'field': 'thursday', 'width': 100, 'align': 'center', 'halign': 'center'},
        {'title': '星期五', 'field': 'friday', 'width': 100, 'align': 'center', 'halign': 'center'},
        {'title': '星期六', 'field': 'saturday', 'width': 100, 'align': 'center', 'halign': 'center'},
        {'title': '星期日', 'field': 'sunday', 'width': 100, 'align': 'center', 'halign': 'center'}
    ]]
};
var work_edit_select_projects = {
    panelWidth: 500,
    panelMinWidth: '50%',
    loadMsg: '数据加载中...',
    mode: 'remote',
    url: 'findAllprojects',
    idField: 'id',
    textField: 'name',
    method: 'get',
    cloumns: [[
        {'title': '项目ID', 'field': 'id', 'width': 100},
        {'title': '项目名称', 'field': 'name', 'width': 100}
    ]],
    fitColumns: true
}