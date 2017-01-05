/**
 * Created by yyy1867 on 2016/12/30.
 */
$(function () {
        var table = $("#workEditTable");
        var addDaysUrl = "add";
        var tableUrl = "table";
        var tableOptionsUrl = "workedit.json";
        var saveListUrl = "save";
        var submitListUrl = "submit";
        var delListUrl = "del";

        var specialFiledName = "text";
        var specialFiledData = "data";
        var pmstatusFiledData = "pmstatus";
        var dmstatusFiledData = "dmstatus";
        var iscommitFiledData = "iscommit";

        var EDITDAY_PREFIX = "editDays"
        var DAY_PREFIX = "day";


        var editIndex = undefined;
        var editField = undefined;
        var editStatu = false;

        laodWorkEdit();
        // laodWorkEditToolbar();
        // refreshDatagrid();

        function laodWorkEdit() {

            jQuery.getJSON(tableOptionsUrl, undefined, function (data) {
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
                        var isSubmit = value[specialFiledData][iscommitFiledData];
                        var pmStatu = value[specialFiledData][pmstatusFiledData];
                        var dmStatu = value[specialFiledData][dmstatusFiledData];
                        if (dmStatu == true) {
                            return "background-color:#449d44;color:#fff";
                        } else if (pmStatu == false || dmStatu == false) {
                            return "background-color:#d9534f;color:#fff";
                        }
                        if (isSubmit == true) {
                            return "background-color:#e6e6e6;color:#333";
                        }
                    }
                }
            }
            //选中单元格时编辑该单元格内容
            data.onClickCell = function (rowIndex, field, value) {
                if (value.hasOwnProperty(specialFiledData)) {
                    saveEdit();
                    editIndex = rowIndex;
                    editField = field;
                    var data = table.datagrid('getRows')[editIndex];
                    setWorkEditDialogFrm(data);
                    $("#workEditDialog").dialog("open");
                }
            };
            return data;
        }

        function saveEdit() {
            if (editIndex != undefined || editField != undefined) {
                var data = table.datagrid('getRows')[editIndex];
                data = getWorkEditDialogFrm(data);
                table.datagrid("updateRow", {
                    index: editIndex,
                    row: data
                });
                editStatu = true;
            }
        }

        function setWorkEditDialogFrm(data) {
            console.info($("#workEditDialogFrm .easyui-textbox[textboxname^='editDate']"));
            $("#workEditDialogFrm .easyui-textbox").textbox("enable");
            $("#workEditDialogFrm .easyui-numberbox").numberbox("enable");
            if (data[editField][specialFiledData]["iscommit"]) {
                $("#workEditDialogFrm .easyui-textbox").textbox("disable");
                $("#workEditDialogFrm .easyui-numberbox").numberbox("disable");
            }
            $("#editDate").textbox("disable");
            $("#workEditDialogFrm").form("load", {
                worktime: data[editField][specialFiledData]["worktime"],
                detail: data[editField][specialFiledData]["detail"],
                problem: data[editField][specialFiledData]["problem"],
                solution: data[editField][specialFiledData]["solution"],
                editDate: data[editField][EDITDAY_PREFIX]
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

        function delList() {
            var list = new Array();
            var row = table.datagrid("getChecked");
            if (row.length == 0) {
                jQuery.messager.alert("提示", "请先选择需要删除的工时!!!");
                return;
            }
            for (var i in row) {
                var is = new Array();
                for (var j = 1; j <= 7; j++) {
                    is.push(row[i][DAY_PREFIX + j][specialFiledData]["id"]);
                }
                list.push(is);
            }
            postBody(delListUrl, list, function (data) {
                console.info(typeof data);
                if (typeof data === "number" && data != 0) {
                    $.messager.alert("提示", "成功移除工时信息!");
                    return;
                }
                $.messager.alert("提示", "移除失败,工时一旦提交将不能移除!!!");
            });
            refreshDatagrid();
        }

        function saveList() {
            saveEdit();
            var list = new Array();
            var row = table.datagrid("getRows");
            for (var i in row) {
                var is = new Array();
                for (var j = 1; j <= 7; j++) {
                    is.push(row[i][DAY_PREFIX + j][specialFiledData]);
                }
                list.push(is);
            }
            postBody(saveListUrl, list, function (data) {
                console.info(typeof data);
                if (typeof data === "number") {
                    $.messager.alert("提示", "成功更新工时信息!");
                    return;
                }
                $.messager.alert("提示", "保存信息出错,请与管理员联系!!!");
            });
            refreshDatagrid();
        }

        function submitList() {
            saveEdit();
            var list = new Array();
            var row = table.datagrid("getChecked");
            if (row.length == 0) {
                jQuery.messager.alert("提示", "请先选择需要提交的工时!!!");
                return;
            }
            for (var i in row) {
                var is = new Array();
                for (var j = 1; j <= 7; j++) {
                    is.push(row[i][DAY_PREFIX + j][specialFiledData]);
                }
                list.push(is);
            }
            postBody(submitListUrl, list, function (data) {
                console.info(typeof data);
                if (typeof data === "number") {
                    $.messager.alert("提示", "成功提交工时信息!");
                    return;
                }
                $.messager.alert("提示", "提交信息出错,请与管理员联系!!!");
            });
            refreshDatagrid();
        }

        function postBody(url, data, func) {
            jQuery.ajax({
                url: url,
                data: JSON.stringify(data),
                dataType: "json",
                contentType: "application/json",
                type: "post",
                success: func
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

    }
);