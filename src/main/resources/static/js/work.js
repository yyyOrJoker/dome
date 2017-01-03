/**
 * Created by yyy1867 on 2016/12/30.
 */
$(function () {
    var table = $("#workEditTable");

    laodWorkEdit();
    laodWorkEditToolbar();

    function laodWorkEdit() {
        $("#workEditToolbar a:contains('查询')").linkbutton({
            onClick: function () {
                table.datagrid({
                    queryParams: {
                        "year": $(":input[name='year']").val(),
                        "day": $(":input[name='day']").val()
                    }
                });
            }
        });
    }

    function laodWorkEditToolbar() {

    }

});