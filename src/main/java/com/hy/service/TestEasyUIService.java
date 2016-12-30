package com.hy.service;

import com.hy.model.domain.CmCatalog;
import com.hy.model.domain.CmProject;
import com.hy.model.domain.CmTimesheet;
import com.hy.model.easyui.EasyUIDatagrid;
import com.hy.model.easyui.EasyUIDatagridCloumn;
import com.hy.service.jpa.CmCatalogRepostory;
import com.hy.service.jpa.CmProjectRepostory;
import com.hy.service.jpa.CmTimesheetRepostory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by yyy1867 on 2016/12/30.
 */
@Service
public class TestEasyUIService {

    public static final SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
    public static final SimpleDateFormat yyyyMMddE = new SimpleDateFormat("yyyy-MM-dd E", Locale.CHINA);

    @Autowired
    CmTimesheetRepostory cmTimesheetRepostory;
    @Autowired
    CmProjectRepostory cmProjectRepostory;
    @Autowired
    CmCatalogRepostory cmCatalogRepostory;

    public EasyUIDatagrid loadEasyuiDataGrid() {
        List<EasyUIDatagridCloumn> cloumns = loadWorkTimeCloumns();
        List<CmTimesheet> data = new LinkedList<CmTimesheet>();
        EasyUIDatagrid datagrid = new EasyUIDatagrid("工时填报", cloumns, data);
        return datagrid;
    }

    @Transactional
    public List<CmTimesheet> addWorkSheet(int userId, int pojectId, int catalogId, String addr, int netprice, int settle, String yyyy, String e) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getWeekByOneDay(yyyy, e));
        List<CmTimesheet> data = new LinkedList<CmTimesheet>();
        Integer ordinal = cmTimesheetRepostory.getMaxToOrdinal();
        if (ordinal == null) {
            ordinal = 0;
        }
        ordinal++;
        for (int i = 0; i < 7; i++) {
            CmTimesheet cmTimesheet = new CmTimesheet();
            cmTimesheet.setUserId(userId);
            cmTimesheet.setProject(cmProjectRepostory.findOne(pojectId));
            cmTimesheet.setCatalog(cmCatalogRepostory.findOne(catalogId));
            cmTimesheet.setAddress(addr);
            cmTimesheet.setNetpriceId(netprice);
            cmTimesheet.setSettleId(settle);
            cmTimesheet.setOrdinal(ordinal);
            cmTimesheet.setEditDate(calendar.getTime());
//            cmTimesheet.setNotes(yyyyMMddE.format(calendar.getTime()));
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            data.add(cmTimesheet);
        }
        cmTimesheetRepostory.save(data);
        return data;
    }

    private static Date getWeekByOneDay(String yyyy, String e) throws ParseException {
        int ee = Integer.parseInt(e);
        if (ee < 1 || ee > 53) {
            throw new RuntimeException("周的范围取值不正确...");
        }
        Calendar c = Calendar.getInstance();
        c.setTime(yyyyMMdd.parse(yyyy + "-01-01"));
        c.add(Calendar.DAY_OF_YEAR, ee * 7);
        int i = c.get(Calendar.DAY_OF_WEEK);
        if (i != 1) {
            c.add(Calendar.DAY_OF_YEAR, 0 - i + 2);
        } else {
            c.add(Calendar.DAY_OF_YEAR, -6);
        }
        return c.getTime();
    }

    private List<EasyUIDatagridCloumn> loadWorkTimeCloumns() {
        List<EasyUIDatagridCloumn> cloumns = new LinkedList<EasyUIDatagridCloumn>();
        cloumns.add(new EasyUIDatagridCloumn("项目名称", "projectName", 100));
        cloumns.add(new EasyUIDatagridCloumn("任务名称", "catalogId", 100));
        cloumns.add(new EasyUIDatagridCloumn("服务地点", "address", 100));
        cloumns.add(new EasyUIDatagridCloumn("服务地点", "settle", 100));
        cloumns.add(new EasyUIDatagridCloumn("星期一", "monday", 100));
        cloumns.add(new EasyUIDatagridCloumn("星期二", "tuesday", 100));
        cloumns.add(new EasyUIDatagridCloumn("星期三", "wednesday", 100));
        cloumns.add(new EasyUIDatagridCloumn("星期四", "thursday", 100));
        cloumns.add(new EasyUIDatagridCloumn("星期五", "friday", 100));
        cloumns.add(new EasyUIDatagridCloumn("星期六", "saturday", 100));
        cloumns.add(new EasyUIDatagridCloumn("星期日", "sunday", 100));
        return cloumns;
    }

    private List<Map<String, Object>> parseMap(List<CmTimesheet> data) {
        List<Map<String, Object>> list = new LinkedList<Map<String, Object>>();
        for (CmTimesheet timesheet : data) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("projectId", timesheet.getProject().getId());
            map.put("projectName", timesheet.getProject().getName());
            map.put("catalogId", timesheet.getCatalog().getId());
            map.put("catalogName", timesheet.getCatalog().getName());
            map.put("address", timesheet.getAddress());
            map.put("settle", timesheet.getSettleId());
            list.add(map);
        }
        return list;
    }


}
