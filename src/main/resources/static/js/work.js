/**
 * Created by yyy1867 on 2016/12/30.
 */
$(function () {
    var table = $("#workEditTable");
    var addDaysUrl = "add";
    var tableUrl = "table";
    var tableOptionsUrl = "workedit.json";
    var saveListUrl = "save";

    var specialFiledName = "text";
    var specialFiledData = "data";
    var pmstatusFiledData = "pmstatus";
    var dmstatusFiledData = "dmstatus";
    var iscommitFiledData = "iscommit";

    var DAY_PREFIX = "day";


    var editIndex = undefined;
    var editField = undefined;
    var editStatu = false;

    laodWorkEdit();
    // laodWorkEditToolbar();
    // refreshDatagrid();

    function laodWorkEdit() {

        jQuery.getJSON(tableOptionsUrl, undefined, function (data) {
            console.info(data);
            table.datagrid(loadDatagridOptions(data));
        });

        $("#workEditToolbar a").click(function (a) {
            var ev = $(this).attr("ws-click");
            if (ev) {
                eval($(this).attr("ws-click"));
            }
        });
    }

    function laodWorkEditToolbar() {

    }

    function loadDatagridOptions(data) {
        for (var i = 0; i < data.columns[0].length; i++) {
            //对象的显示方法
            data.columns[0][i].formatter = function (value, row, index) {
                if (value && value.hasOwnProperty(specialFiledName)) {
                    return value[specialFiledName];
                }
                return value;
            };
            //不同状态的背景色
            data.columns[0][i].styler = function (value, row, index) {
                if (value && value.hasOwnProperty(specialFiledData)) {
                    if (value[specialFiledData][pmstatusFiledData] && value[specialFiledData][dmstatusFiledData]) {
                        return "";
                    } else if (!value[specialFiledData][pmstatusFiledData] || !value[specialFiledData][dmstatusFiledData]) {
                        return "";
                    } else if (value[specialFiledData][iscommitFiledData]) {
                        return "";
                    }
                }
            };
        }
        //选中单元格时编辑该单元格内容
        data.onClickCell = function (rowIndex, field, value) {
            if (value.hasOwnProperty(specialFiledData)) {
                var data = table.datagrid('getRows')[editIndex];
                if (editIndex != undefined || editField != undefined) {
                    data = getWorkEditDialogFrm(data);
                    table.datagrid("updateRow", {
                        index: editIndex,
                        row: data
                    });
                    editStatu = true;
                }
                editIndex = rowIndex;
                editField = field;
                data = table.datagrid('getRows')[editIndex];
                setWorkEditDialogFrm(data);
                $("#workEditDialog").dialog("open");
            }
        };
        return data;
    }

    function setWorkEditDialogFrm(data) {
        $("#workEditDialogFrm").form("load", {
            worktime: data[editField][specialFiledData]["worktime"],
            detail: data[editField][specialFiledData]["detail"],
            problem: data[editField][specialFiledData]["problem"],
            solution: data[editField][specialFiledData]["solution"]
        });
    }

    function getWorkEditDialogFrm(data) {
        data[editField][specialFiledData]["worktime"] = $(":input[name='worktime']:eq(0)").val();
        data[editField][specialFiledName] = $(":input[name='worktime']:eq(0)").val();
        data[editField][specialFiledData]["detail"] = $(":input[name='detail']:eq(0)").val();
        data[editField][specialFiledData]["problem"] = $(":input[name='problem']:eq(0)").val();
        data[editField][specialFiledData]["solution"] = $(":input[name='solution']:eq(0)").val();
        return data;
    }

    function saveList() {
        var row = table.datagrid("getRows")[0];
        var list = new Array();
        for (var i = 1; i <= 7; i++) {
            list.push(row[DAY_PREFIX + i][specialFiledData]);
        }
        console.info(JSON.stringify(list));
        jQuery.ajax({
            url: saveListUrl,
            data: JSON.stringify(list),
            dataType: "json",
            contentType: "application/json",
            type: "post",
            success: function (data) {
                console.info(data);
            }
        });
    }


    function addDays() {
        var frm = getFrom();
        jQuery.get(addDaysUrl, frm, function (data) {
            table.datagrid("appendRow", data);
            editStatu = true;
        });
    }

    function nextDays(x) {
        checkEditStatu();
        if (editStatu)return;
        var frm = getFrom();
        frm.day = frm.day * 1 + x * 1;
        if (frm.day < 1) {
            frm.year--;
            frm.day = 53;
        } else if (frm.day > 53) {
            frm.year++;
            frm.day = 1;
        }
        setForm(frm);
        refreshDatagrid();
    }

    function checkEditStatu() {
        if (editStatu) {
            jQuery.messager.confirm("提示", "当前有为保存的数据,若要强制操作请确定后重复操作!", function (r) {
                if (r) editStatu = false;
            });
        }
    }

    function refreshDatagrid() {
        checkEditStatu();
        if (editStatu)return;
        var frm = getFrom();
        table.datagrid({
            url: tableUrl,
            queryParams: {
                "year": frm.year,
                "day": frm.day
            }
        });
    }

    function getFrom() {
        var frm = new Object();
        frm.year = $(":input[name='year']:eq(0)").val();
        frm.day = $(":input[name='day']:eq(0)").val();
        frm.project = $(":input[name='project']:eq(0)").val();
        frm.catalog = $(":input[name='catalog']:eq(0)").val();
        frm.settle = $(":input[name='settle']:eq(0)").val();
        frm.address = $(":input[name='address']:eq(0)").val();
        frm.netprice = $(":input[name='netprice']:eq(0)").val();
        return frm;
    }

    function setForm(frm) {
        $("#workEditToolbarForm").form("load", {
            "year": frm.year,
            "day": frm.day,
            "project": frm.project,
            "catalog": frm.catalog,
            "settle": frm.settle,
            "address": frm.address,
            "netprice": frm.netprice
        });
    }

});