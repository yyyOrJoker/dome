package com.hy.service;

import com.hy.model.WorkFrom;
import com.hy.model.domain.CmTimesheet;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by yyy1867 on 2017/1/3.
 */
public interface TestEasyUIService {

    //添加一周的工时
    Map<String, Object> addWorkSheet(int userId, WorkFrom frm) throws ParseException;

    //查询所有项目
    List<Map<String, Object>> loadAllprojects(String p);

    //查询任务目录
    List<Map<String, Object>> loadAllCatalogs(int projectId, String p);

    //查询该用户yyyy年e周的工时
    List<Map<String, Object>> loadAllWorkSheet(int userId, int yyyy, int e);

    //获取year年的第week周的第一天
    Date getWeekByOneDay(int year, int week) throws ParseException;

    //获取指定时间是当年的第几周
    int getDayByDate(Date date) throws ParseException;

    //保存一周的工时
    void saveDays(List<CmTimesheet> cms);
}
