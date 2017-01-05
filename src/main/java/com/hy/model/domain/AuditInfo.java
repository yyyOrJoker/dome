package com.hy.model.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by yyy1867 on 2017/1/5.
 */
@Entity
public class AuditInfo extends BaseDomain {

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "timesheet_id")
    private Timesheet timesheet;

    private int auditPresonId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    private String resultl;
    private String advice;

    public Timesheet getTimesheet() {
        return timesheet;
    }

    public void setTimesheet(Timesheet timesheet) {
        this.timesheet = timesheet;
    }

    public int getAuditPresonId() {
        return auditPresonId;
    }

    public void setAuditPresonId(int auditPresonId) {
        this.auditPresonId = auditPresonId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getResultl() {
        return resultl;
    }

    public void setResultl(String resultl) {
        this.resultl = resultl;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }
}
