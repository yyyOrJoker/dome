package com.hy.service;

import com.hy.model.domain.CmCatalog;
import com.hy.model.domain.CmProject;
import com.hy.model.domain.CmTimesheet;
import com.hy.model.easyui.EasyUIDatagrid;
import com.hy.model.easyui.EasyUIDatagridCloumn;
import com.hy.service.jpa.CmCatalogRepostory;
import com.hy.service.jpa.CmProjectRepostory;
import com.hy.service.jpa.CmTimesheetRepostory;
import org.apache.log4j.Logger;
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

    public static final Logger log = Logger.getLogger(TestEasyUIService.class);

    public static final SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
    public static final SimpleDateFormat yyyyMMddE = new SimpleDateFormat("yyyy-MM-dd E", Locale.CHINA);

    public static final String DAY_TEXT_PREFIX = "dayText";
    public static final String DAY_PREFIX = "day";

    @Autowired
    CmTimesheetRepostory cmTimesheetRepostory;
    @Autowired
    CmProjectRepostory cmProjectRepostory;
    @Autowired
    CmCatalogRepostory cmCatalogRepostory;

    //添加一周的工时
    @Transactional
    public List<CmTimesheet> addWorkSheet(int userId, int pojectId, int catalogId, String addr, int netprice, int settle, String yyyy, String e) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getWeekByOneDay(yyyy, e));
        List<CmTimesheet> data = new LinkedList<CmTimesheet>();
        Integer ordinal = cmTimesheetRepostory.getMaxToOrdinal(userId);
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
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            data.add(cmTimesheet);
        }
        cmTimesheetRepostory.save(data);
        return data;
    }

    //获取该用户的所有工时
    public List<Map<String, Object>> loadAllWorkSheet(int userId) {
        List<Integer> ordinalIds = cmTimesheetRepostory.getOrdinalGroupByOrdinalByUserId(userId);
        List<Map<String, Object>> list = new LinkedList<Map<String, Object>>();
        for (int i = 0; i < ordinalIds.size(); i++) {
            List<CmTimesheet> timesheets = cmTimesheetRepostory.findByUserIdAndOrdinalOrderByEditDate(userId, ordinalIds.get(i));
            list.add(parseMap(timesheets));
        }
        return list;
    }

    //获取全部项目
    public List<Map<String, Object>> loadAllprojects() {
        List<Map<String, Object>> list = new LinkedList<Map<String, Object>>();
        List<CmProject> data = cmProjectRepostory.findAll();
        for (int i = 0; i < data.size(); i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", data.get(i).getId());
            map.put("name", data.get(i).getName());
            list.add(map);
        }
        return list;
    }

    //获取全部任务目录
    public List<Map<String, Object>> loadAllCatalogs() {
        List<Map<String, Object>> list = new LinkedList<Map<String, Object>>();
        List<CmCatalog> data = cmCatalogRepostory.findAll();
        for (int i = 0; i < data.size(); i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", data.get(i).getId());
            map.put("name", data.get(i).getName());
            list.add(map);
        }
        return list;
    }

    //获取yyyy年第e周的第一天的日期
    private Date getWeekByOneDay(String yyyy, String e) throws ParseException {
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

    //将一周的工时转换为一个对象
    private Map<String, Object> parseMap(List<CmTimesheet> data) {
        Map<String, Object> map = new HashMap<String, Object>();
        CmTimesheet timesheet = data.get(0);
        map.put("ordinal", timesheet.getOrdinal());
        map.put("projectId", timesheet.getProject().getId());
        map.put("projectName", timesheet.getProject().getName());
        map.put("catalogId", timesheet.getCatalog().getId());
        map.put("catalogName", timesheet.getCatalog().getName());
        map.put("address", timesheet.getAddress());
        map.put("settle", timesheet.getSettleId());
        map.put("netprice", timesheet.getSettleId());
        for (CmTimesheet t : data) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(t.getEditDate());
            int day = calendar.get(Calendar.DAY_OF_WEEK);
            map.put(DAY_TEXT_PREFIX + day, t.getWorktime());
            map.put(DAY_PREFIX + day, t.getWorktime());
        }
        return map;
    }


}
