package com.hy.model.domain;

import javax.persistence.*;
import java.util.List;

/**
 * Created by yyy1867 on 2016/12/30.
 */
@Entity
public class CmProject extends BaseDomain {

    protected Integer userId;
    protected Integer groupId;

    @ManyToOne(cascade = CascadeType.ALL, targetEntity = CmCatalog.class)
    @JoinColumn(name = "catalogId")
    protected CmCatalog catalog;
    protected Integer businessId;
    protected Integer costumerId;
    protected String partner;
    protected Integer issignId;
    protected Integer signuserId;
    protected Integer signotherId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public CmCatalog getCatalog() {
        return catalog;
    }

    public void setCatalog(CmCatalog catalog) {
        this.catalog = catalog;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public Integer getCostumerId() {
        return costumerId;
    }

    public void setCostumerId(Integer costumerId) {
        this.costumerId = costumerId;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public Integer getIssignId() {
        return issignId;
    }

    public void setIssignId(Integer issignId) {
        this.issignId = issignId;
    }

    public Integer getSignuserId() {
        return signuserId;
    }

    public void setSignuserId(Integer signuserId) {
        this.signuserId = signuserId;
    }

    public Integer getSignotherId() {
        return signotherId;
    }

    public void setSignotherId(Integer signotherId) {
        this.signotherId = signotherId;
    }

}
