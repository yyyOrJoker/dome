package com.hy.model.domain;

import javax.persistence.*;

/**
 * Created by yyy1867 on 2016/12/30.
 */
@Entity
public class CmCatalog extends BaseDomain {
    @ManyToOne(cascade = CascadeType.ALL, targetEntity = CmCatalog.class)
    @JoinColumn(name = "parentId")
    public CmCatalog parent;

    public CmCatalog getParent() {
        return parent;
    }

    public void setParent(CmCatalog parent) {
        this.parent = parent;
    }
}
