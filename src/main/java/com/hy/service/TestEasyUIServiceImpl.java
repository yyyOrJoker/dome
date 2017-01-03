package com.hy.service;

import com.hy.model.domain.CmCatalog;
import com.hy.model.domain.CmProject;
import com.hy.model.domain.CmTimesheet;
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
public class TestEasyUIServiceImpl implements TestEasyUIService {

    public static final Logger log = Logger.getLogger(TestEasyUIServiceImpl.class);

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
    @Override
    public List<CmTimesheet> addWorkSheet(int userId, int pojectId, int catalogId, String addr, int netprice, int settle, int yyyy, int e) throws ParseException {
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
    @Override
    public List<Map<String, Object>> loadAllWorkSheet(int userId, int yyyy, int e) {
        String str = "";
        String end = "";
        try {
            str = yyyyMMdd.format(getWeekByOneDay(yyyy, e));
            end = yyyyMMdd.format(getWeekByOneDay(yyyy, e + 1));
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        List<Object> ordinalIds = cmTimesheetRepostory.getOrdinalGroupByOrdinalByUserId(userId, str, end);
        List<Map<String, Object>> list = new LinkedList<Map<String, Object>>();
        for (int i = 0; i < ordinalIds.size(); i++) {
            List<CmTimesheet> timesheets = cmTimesheetRepostory.findByUserIdAndOrdinalOrderByEditDate(userId, (Integer) ordinalIds.get(i));
            list.add(parseMap(timesheets));
        }
        return list;
    }

    //获取全部项目
    @Override
    public List<Map<String, Object>> loadAllprojects(String p) {
        List<Map<String, Object>> list = new LinkedList<Map<String, Object>>();
        List<CmProject> data = cmProjectRepostory.findAll();
        for (int i = 0; i < data.size(); i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", data.get(i).getId());
            map.put("name", data.get(i).getName());
            if (i == 0) {
                map.put("selected", true);
            }
            list.add(map);
        }
        return list;
    }

    //获取全部任务目录
    @Override
    public List<Map<String, Object>> loadAllCatalogs(int projectId, String p) {
        List<Map<String, Object>> list = new LinkedList<Map<String, Object>>();
        List<CmCatalog> data = cmCatalogRepostory.findAll();
        for (int i = 0; i < data.size(); i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", data.get(i).getId());
            map.put("name", data.get(i).getName());
            if (i == 0) {
                map.put("selected", true);
            }
            list.add(map);
        }
        return list;
    }

    @Override
    public Date getWeekByOneDay(int year, int week) throws ParseException {
        Calendar c = Calendar.getInstance();
        c.setTime(yyyyMMdd.parse(year + "-01-01"));
        c.add(Calendar.DAY_OF_YEAR, (week - 1) * 7);
        int day = c.get(Calendar.DAY_OF_WEEK);
        if (day == 1) {
            c.add(Calendar.DAY_OF_YEAR, -6);
        } else {
            c.add(Calendar.DAY_OF_YEAR, 2 - day);
        }
        return c.getTime();
    }

    @Override
    public int getDayByDate(Date date) throws ParseException {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int year = c.get(Calendar.YEAR);
        int day = c.get(Calendar.DAY_OF_WEEK);
        if (day == 1) {
            c.add(Calendar.DAY_OF_YEAR, -6);
        } else {
            c.add(Calendar.DAY_OF_YEAR, 2 - day);
        }
        String str = yyyyMMdd.format(date);
        for (int i = 1; i <= 53; i++) {
            if (str.equals(yyyyMMdd.format(getWeekByOneDay(year, i)))) {
                return i;
            }
        }
        return 0;
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
            map.put(DAY_PREFIX + day, t);
        }
        return map;
    }


}
