package com.hy.model.easyui;

import java.io.Serializable;

/**
 * Created by yyy1867 on 2016/12/30.
 */
public class EasyUIDatagridCloumn implements Serializable {
    private String title;
    private String field;
    private Integer width;
    private String align;
    private String halign;
    private Boolean sortable;
    private String order;
    private Boolean checkbox;
    private String __data;

    public EasyUIDatagridCloumn() {
        init();
    }

    public EasyUIDatagridCloumn(String title, String field, Integer width) {
        init();
        this.title = title;
        this.field = field;
        this.width = width;
    }

    private void init() {
        this.align = "center";
        this.halign = "center";
        this.sortable = false;
        this.checkbox = false;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public String getAlign() {
        return align;
    }

    public void setAlign(String align) {
        this.align = align;
    }

    public String getHalign() {
        return halign;
    }

    public void setHalign(String halign) {
        this.halign = halign;
    }

    public Boolean getSortable() {
        return sortable;
    }

    public void setSortable(Boolean sortable) {
        this.sortable = sortable;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Boolean getCheckbox() {
        return checkbox;
    }

    public void setCheckbox(Boolean checkbox) {
        this.checkbox = checkbox;
    }

    public String get__data() {
        return __data;
    }

    public void set__data(String __data) {
        this.__data = __data;
    }
}
