package com.hy.service.jpa;

import com.hy.model.domain.CmTimesheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yyy1867 on 2016/12/30.
 */
@Service
public interface CmTimesheetRepostory extends JpaRepository<CmTimesheet, Integer> {

    @Query(value = "select max(ordinal) from CmTimesheet where userId=?1", nativeQuery = true)
    Integer getMaxToOrdinal(Integer userId);

    @Query(value = "select ordinal from cm_Timesheet where user_Id=?1 and edit_date>=?2 and edit_date < ?3 group by ordinal", nativeQuery = true)
    List<Object> getOrdinalGroupByOrdinalByUserId(Integer userId, String start, String end);

    List<CmTimesheet> findByUserIdAndOrdinalOrderByEditDate(int userId, int ordinal);

}
