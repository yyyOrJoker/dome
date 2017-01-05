package com.hy.service;

import com.hy.model.WorkFrom;
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

    public static final String DAY_PREFIX = "day";
    public static final String EDITDAY_PREFIX = "editDays";

    @Autowired
    CmTimesheetRepostory cmTimesheetRepostory;
    @Autowired
    CmProjectRepostory cmProjectRepostory;
    @Autowired
    CmCatalogRepostory cmCatalogRepostory;

    @Transactional
    @Override
    public boolean saveDays(List<CmTimesheet> cms) {
        if (isExistsDays(cms)) {
            for (CmTimesheet cm : cms) {
                if (cm.getIscommit() != null && cm.getIscommit()) continue;
                cmTimesheetRepostory.saveAndFlush(cm);
            }
            return true;
        }
        return false;
    }

    @Transactional
    @Override
    public boolean submitDays(List<CmTimesheet> cms) {
        if (isExistsDays(cms)) {
            for (CmTimesheet cm : cms) {
                if (cm.getIscommit() != null && cm.getIscommit()) continue;
                if (cm.getWorktime() != null && cm.getWorktime() > 0) {
                    cm.setIscommit(true);
                }
                cmTimesheetRepostory.saveAndFlush(cm);
            }
            return true;
        }
        return false;
    }

    private boolean isExistsDays(List<CmTimesheet> cms) {
        if (cms.size() == 7) {
            List<Integer> ids = new ArrayList<Integer>();
            for (CmTimesheet t : cms) {
                if (t.getId() == null) return false;
                ids.add(t.getId());
            }
            return cmTimesheetRepostory.findByIdIn(ids).size() == 7;
        }
        return false;
    }

    @Transactional
    @Override
    public Boolean delDays(List<Integer> ids) {
        List<CmTimesheet> list = cmTimesheetRepostory.findByIdInAndIscommit(ids, null);
        if (list.size() == 7) {
            for (CmTimesheet cm : list) {
                cmTimesheetRepostory.delete(cm);
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
        List<CmTimesheet> data = new LinkedList<CmTimesheet>();
        Integer ordinal = cmTimesheetRepostory.getMaxToOrdinal(userId);
        if (ordinal == null) {
            ordinal = 0;
        }
        ordinal++;
        for (int i = 0; i < 7; i++) {
            CmTimesheet cmTimesheet = new CmTimesheet();
            cmTimesheet.setUserId(userId);
            cmTimesheet.setProject(cmProjectRepostory.findOne(frm.getProject()));
            cmTimesheet.setCatalog(cmCatalogRepostory.findOne(frm.getCatalog()));
            cmTimesheet.setAddress(frm.getAddress());
            cmTimesheet.setNetpriceId(frm.getNetprice());
            cmTimesheet.setSettleId(frm.getSettle());
            cmTimesheet.setOrdinal(ordinal);
            cmTimesheet.setEditDate(calendar.getTime());
            cmTimesheet.setWorktime(0);
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            data.add(cmTimesheet);
        }
        cmTimesheetRepostory.save(data);
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
        List<CmTimesheet> ordinalIds = cmTimesheetRepostory.findByEditDateAndUserId(str, userId);
        List<Map<String, Object>> list = new LinkedList<Map<String, Object>>();
        for (int i = 0; i < ordinalIds.size(); i++) {
            List<CmTimesheet> timesheets = cmTimesheetRepostory.findByUserIdAndOrdinalOrderByEditDate(userId, ordinalIds.get(i).getOrdinal());
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
        map.put("netprice", timesheet.getNetpriceId());
        for (CmTimesheet t : data) {
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
