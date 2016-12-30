package com.hy.service.jpa;

import com.hy.model.domain.CmProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Created by yyy1867 on 2016/12/30.
 */
@Service
public interface CmProjectRepostory extends JpaRepository<CmProject, Integer> {
}
