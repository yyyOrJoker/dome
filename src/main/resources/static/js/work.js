/**
 * Created by yyy1867 on 2016/12/30.
 */
$(function () {
    var table = $("#workEditTable");
    var addDaysUrl = "add";
    var tableUrl = "table";
    var tableOptionsUrl = "workedit.json";
    var specialFiledName = "text";
    var specialFiledData = "text";
    var pmstatusFiledData = "pmstatus";
    var dmstatusFiledData = "dmstatus";
    var iscommitFiledData = "iscommit";


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
                console.info(value)
            }
        };
        return data;
    }

    function addDays() {
        var frm = getFrom();
        jQuery.get(addDaysUrl, frm, function (data) {
            table.datagrid("appendRow", data);
        });
    }

    function nextDays(x) {
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

    function refreshDatagrid() {
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