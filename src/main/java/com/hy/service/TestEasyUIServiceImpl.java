package com.hy.service;

import com.hy.model.WorkFrom;
import com.hy.model.domain.Catalog;
import com.hy.model.domain.Project;
import com.hy.model.domain.Timesheet;
import com.hy.service.jpa.CatalogRepostory;
import com.hy.service.jpa.ProjectRepostory;
import com.hy.service.jpa.TimesheetRepostory;
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

    public static final String DAY_PREFIX = "day";
    public static final String EDITDAY_PREFIX = "editDays";

    @Autowired
    TimesheetRepostory timesheetRepostory;
    @Autowired
    ProjectRepostory projectRepostory;
    @Autowired
    CatalogRepostory catalogRepostory;

    @Transactional
    @Override
    public boolean saveDays(List<Timesheet> cms) {
        if (isExistsDays(cms)) {
            for (Timesheet cm : cms) {
                if (cm.getIscommit() != null && cm.getIscommit()) continue;
                timesheetRepostory.saveAndFlush(cm);
            }
            return true;
        }
        return false;
    }

    @Transactional
    @Override
    public boolean submitDays(List<Timesheet> cms) {
        if (isExistsDays(cms)) {
            for (Timesheet cm : cms) {
                if (cm.getIscommit() != null && cm.getIscommit()) continue;
                if (cm.getWorktime() != null && cm.getWorktime() > 0) {
                    cm.setIscommit(true);
                }
                timesheetRepostory.saveAndFlush(cm);
            }
            return true;
        }
        return false;
    }

    private boolean isExistsDays(List<Timesheet> cms) {
        if (cms.size() == 7) {
            List<Integer> ids = new ArrayList<Integer>();
            for (Timesheet t : cms) {
                if (t.getId() == null) return false;
                ids.add(t.getId());
            }
            return timesheetRepostory.findByIdIn(ids).size() == 7;
        }
        return false;
    }

    @Transactional
    @Override
    public Boolean delDays(List<Integer> ids) {
        List<Timesheet> list = timesheetRepostory.findByIdInAndIscommit(ids, null);
        if (list.size() == 7) {
            for (Timesheet cm : list) {
                timesheetRepostory.delete(cm);
            }
            return true;
        }
        return false;
    }

    //添加一周的工时
    @Transactional
    @Override
    public Map<String, Object> addWorkSheet(int userId, WorkFrom frm) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getWeekByOneDay(frm.getYear(), frm.getDay()));
        List<Timesheet> data = new LinkedList<Timesheet>();
        Integer ordinal = timesheetRepostory.getMaxToOrdinal(userId);
        if (ordinal == null) {
            ordinal = 0;
        }
        ordinal++;
        for (int i = 0; i < 7; i++) {
            Timesheet timesheet = new Timesheet();
            timesheet.setUserId(userId);
            timesheet.setProject(projectRepostory.findOne(frm.getProject()));
            timesheet.setCatalog(catalogRepostory.findOne(frm.getCatalog()));
            timesheet.setAddress(frm.getAddress());
            timesheet.setNetpriceId(frm.getNetprice());
            timesheet.setSettleId(frm.getSettle());
            timesheet.setOrdinal(ordinal);
            timesheet.setEditDate(calendar.getTime());
            timesheet.setWorktime(0);
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            data.add(timesheet);
        }
        timesheetRepostory.save(data);
        return parseMap(data);
    }

    //获取该用户的所有工时
    @Override
    public List<Map<String, Object>> loadAllWorkSheet(int userId, int yyyy, int e) {
        Date str = null;
        try {
            str = getWeekByOneDay(yyyy, e);
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        List<Timesheet> ordinalIds = timesheetRepostory.findByEditDateAndUserId(str, userId);
        List<Map<String, Object>> list = new LinkedList<Map<String, Object>>();
        for (int i = 0; i < ordinalIds.size(); i++) {
            List<Timesheet> timesheets = timesheetRepostory.findByUserIdAndOrdinalOrderByEditDate(userId, ordinalIds.get(i).getOrdinal());
            list.add(parseMap(timesheets));
        }
        return list;
    }

    //获取全部项目
    @Override
    public List<Map<String, Object>> loadAllprojects(String p) {
        List<Map<String, Object>> list = new LinkedList<Map<String, Object>>();
        List<Project> data = projectRepostory.findAll();
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
        List<Catalog> data = catalogRepostory.findAll();
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
    private Map<String, Object> parseMap(List<Timesheet> data) {
        Map<String, Object> map = new HashMap<String, Object>();
        Timesheet timesheet = data.get(0);
        map.put("ordinal", timesheet.getOrdinal());
        map.put("projectId", timesheet.getProject().getId());
        map.put("projectName", timesheet.getProject().getName());
        map.put("catalogId", timesheet.getCatalog().getId());
        map.put("catalogName", timesheet.getCatalog().getName());
        map.put("address", timesheet.getAddress());
        map.put("settle", timesheet.getSettleId());
        map.put("netprice", timesheet.getNetpriceId());
        for (Timesheet t : data) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(t.getEditDate());
            int day = calendar.get(Calendar.DAY_OF_WEEK);
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("data", t);
            param.put("text", t.getWorktime());
            param.put(EDITDAY_PREFIX, yyyyMMddE.format(t.getEditDate()));
            map.put(DAY_PREFIX + day, param);
        }
        return map;
    }


}
