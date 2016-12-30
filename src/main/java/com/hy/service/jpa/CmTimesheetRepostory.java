package com.hy.service.jpa;

import com.hy.model.domain.CmTimesheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

/**
 * Created by yyy1867 on 2016/12/30.
 */
@Service
public interface CmTimesheetRepostory extends JpaRepository<CmTimesheet, Integer> {

    @Query(value="select max(ordinal) from CmTimesheet")
    Integer getMaxToOrdinal();
}
