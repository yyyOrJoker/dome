/**
 * Created by yyy1867 on 2016/12/30.
 */
$(function () {

    var table = $("table")[0];
    console.info(table);

    $.getJSON("/table", {}, function (data) {
        console.info(data);
        $(table).datagrid(data);
    });
});