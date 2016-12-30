package com.hy.model.easyui;

import java.util.List;

/**
 * Created by yyy1867 on 2016/12/30.
 */
public class EasyUIDatagrid {

    private String title;
    private List<EasyUIDatagridCloumn> columns;
    private List<EasyUIDatagridCloumn> frozenColumns;
    private String toolbar;
    private Boolean striped;
    private String url;
    private List<Object> data;
    private Boolean singleSelect;
    private Boolean rownumbers;
    private Boolean checkOnSelect;
    private Boolean selectOnCheck;
    private Boolean pagination;

    public EasyUIDatagrid() {
    }

    public EasyUIDatagrid(String title, List<EasyUIDatagridCloumn> columns, List<Object> data) {
        this.title = title;
        this.columns = columns;
        this.data = data;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<EasyUIDatagridCloumn> getColumns() {
        return columns;
    }

    public void setColumns(List<EasyUIDatagridCloumn> columns) {
        this.columns = columns;
    }

    public List<EasyUIDatagridCloumn> getFrozenColumns() {
        return frozenColumns;
    }

    public void setFrozenColumns(List<EasyUIDatagridCloumn> frozenColumns) {
        this.frozenColumns = frozenColumns;
    }

    public String getToolbar() {
        return toolbar;
    }

    public void setToolbar(String toolbar) {
        this.toolbar = toolbar;
    }

    public Boolean getStriped() {
        return striped;
    }

    public void setStriped(Boolean striped) {
        this.striped = striped;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }

    public Boolean getSingleSelect() {
        return singleSelect;
    }

    public void setSingleSelect(Boolean singleSelect) {
        this.singleSelect = singleSelect;
    }

    public Boolean getRownumbers() {
        return rownumbers;
    }

    public void setRownumbers(Boolean rownumbers) {
        this.rownumbers = rownumbers;
    }

    public Boolean getCheckOnSelect() {
        return checkOnSelect;
    }

    public void setCheckOnSelect(Boolean checkOnSelect) {
        this.checkOnSelect = checkOnSelect;
    }

    public Boolean getSelectOnCheck() {
        return selectOnCheck;
    }

    public void setSelectOnCheck(Boolean selectOnCheck) {
        this.selectOnCheck = selectOnCheck;
    }

    public Boolean getPagination() {
        return pagination;
    }

    public void setPagination(Boolean pagination) {
        this.pagination = pagination;
    }
}
