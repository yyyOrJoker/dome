/**
 * Created by yyy1867 on 2016/12/30.
 */
$(function () {
    var table = $("#workEditTable");
    var addDaysUrl = "add";


    laodWorkEdit();
    laodWorkEditToolbar();

    function laodWorkEdit() {
        $("#workEditToolbar a").click(function (a) {
            var ev = $(this).attr("ws-click");
            if (ev) {
                eval($(this).attr("ws-click"));
            }
        });
    }

    function laodWorkEditToolbar() {

    }

    function addDays() {
        var frm = getFrom();
        jQuery.get(addDaysUrl, frm, function (data) {
            console.info(data);
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
        console.info(frm);
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