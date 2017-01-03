package com.hy.model.easyui;

import java.util.List;

/**
 * Created by yyy1867 on 2017/1/3.
 */
public class EasyUIData {
    private int total;
    private List rows;

    public EasyUIData() {

    }

    public EasyUIData(int total, List rows) {
        this.total = total;
        this.rows = rows;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }
}
