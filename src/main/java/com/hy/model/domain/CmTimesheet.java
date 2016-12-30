package com.hy.model.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by yyy1867 on 2016/12/30.
 */
@Entity
public class CmTimesheet extends BaseDomain {

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "project_id")
    protected CmProject project;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "catalog_id")
    protected CmCatalog catalog;
    protected String address;
    protected Integer settleId;
    protected Integer netpriceId;
    protected Integer userId;
    protected Integer ordinal;
    protected Integer worktime;
    @Temporal(TemporalType.TIMESTAMP)
    protected Date editDate;
    protected String detail;
    protected String problem;
    protected String solution;
    protected Boolean iscommit;
    protected Boolean pmstatus;
    protected Boolean dmstatus;

    public CmProject getProject() {
        return project;
    }

    public void setProject(CmProject project) {
        this.project = project;
    }

    public CmCatalog getCatalog() {
        return catalog;
    }

    public void setCatalog(CmCatalog catalog) {
        this.catalog = catalog;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getSettleId() {
        return settleId;
    }

    public void setSettleId(Integer settleId) {
        this.settleId = settleId;
    }

    public Integer getNetpriceId() {
        return netpriceId;
    }

    public void setNetpriceId(Integer netpriceId) {
        this.netpriceId = netpriceId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(Integer ordinal) {
        this.ordinal = ordinal;
    }

    public Integer getWorktime() {
        return worktime;
    }

    public void setWorktime(Integer worktime) {
        this.worktime = worktime;
    }

    public Date getEditDate() {
        return editDate;
    }

    public void setEditDate(Date editDate) {
        this.editDate = editDate;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public Boolean getIscommit() {
        return iscommit;
    }

    public void setIscommit(Boolean iscommit) {
        this.iscommit = iscommit;
    }

    public Boolean getPmstatus() {
        return pmstatus;
    }

    public void setPmstatus(Boolean pmstatus) {
        this.pmstatus = pmstatus;
    }

    public Boolean getDmstatus() {
        return dmstatus;
    }

    public void setDmstatus(Boolean dmstatus) {
        this.dmstatus = dmstatus;
    }
}
