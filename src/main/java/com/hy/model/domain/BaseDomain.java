package com.hy.model.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by yyy1867 on 2016/12/30.
 */
@MappedSuperclass
public class BaseDomain implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Integer id;
    protected String code;
    protected String name;
    protected String spedId;
    protected String notes;
    protected Integer creatorId;
    @Temporal(TemporalType.TIMESTAMP)
    protected Date createDate;
    @Temporal(TemporalType.TIMESTAMP)
    protected Date version;
    protected Integer shardingId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpedId() {
        return spedId;
    }

    public void setSpedId(String spedId) {
        this.spedId = spedId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getVersion() {
        return version;
    }

    public void setVersion(Date version) {
        this.version = version;
    }

    public Integer getShardingId() {
        return shardingId;
    }

    public void setShardingId(Integer shardingId) {
        this.shardingId = shardingId;
    }
}
