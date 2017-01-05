package com.hy.model.domain;

import javax.persistence.*;

/**
 * Created by yyy1867 on 2016/12/30.
 */
@Entity
@Table(name = "cm_catalog")
public class Catalog extends BaseDomain {
    @ManyToOne(cascade = CascadeType.ALL, targetEntity = Catalog.class)
    @JoinColumn(name = "parentId")
    public Catalog parent;

    public Catalog getParent() {
        return parent;
    }

    public void setParent(Catalog parent) {
        this.parent = parent;
    }
}
