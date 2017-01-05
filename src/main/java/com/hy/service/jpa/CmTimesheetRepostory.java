package com.hy.service.jpa;

import com.hy.model.domain.CmTimesheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by yyy1867 on 2016/12/30.
 */
@Service
public interface CmTimesheetRepostory extends JpaRepository<CmTimesheet, Integer> {

    @Query(value = "select max(ordinal) from CmTimesheet where userId=?1")
    Integer getMaxToOrdinal(Integer userId);

    @Query(value = "select ordinal from CmTimesheet where userId=?1 and editDate>=?2 and editDate < ?3 group by ordinal", nativeQuery = false)
    List<Object> getOrdinalGroupByOrdinalByUserId(Integer userId, Date start, Date end);

    List<CmTimesheet> findByUserIdAndOrdinalOrderByEditDate(int userId, int ordinal);

    List<CmTimesheet> findByEditDateAndUserId(Date editDate, int userId);

    List<CmTimesheet> findByIdIn(List<Integer> ids);

    List<CmTimesheet> findByIdInAndIscommit(List<Integer> ids, Boolean iscommit);
}
