package com.hy.service.jpa;

import com.hy.model.domain.Timesheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by yyy1867 on 2016/12/30.
 */
@Service
public interface TimesheetRepostory extends JpaRepository<Timesheet, Integer> {

    @Query(value = "select max(ordinal) from Timesheet where userId=?1")
    Integer getMaxToOrdinal(Integer userId);

    @Query(value = "select ordinal from Timesheet where userId=?1 and editDate>=?2 and editDate < ?3 group by ordinal", nativeQuery = false)
    List<Object> getOrdinalGroupByOrdinalByUserId(Integer userId, Date start, Date end);

    List<Timesheet> findByUserIdAndOrdinalOrderByEditDate(int userId, int ordinal);

    List<Timesheet> findByEditDateAndUserId(Date editDate, int userId);

    List<Timesheet> findByIdIn(List<Integer> ids);

    List<Timesheet> findByIdInAndIscommit(List<Integer> ids, Boolean iscommit);
}
