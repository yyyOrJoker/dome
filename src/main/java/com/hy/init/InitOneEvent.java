package com.hy.init;

import com.hy.model.domain.Catalog;
import com.hy.model.domain.Project;
import com.hy.service.jpa.CatalogRepostory;
import com.hy.service.jpa.ProjectRepostory;
import com.hy.service.jpa.TimesheetRepostory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

/**
 * Created by yyy1867 on 2016/12/21.
 */
@Component
@Order(value = 1)
public class InitOneEvent implements CommandLineRunner {

    @Autowired
    TimesheetRepostory timesheetRepostory;
    @Autowired
    ProjectRepostory projectRepostory;
    @Autowired
    CatalogRepostory catalogRepostory;

    @Override
    public void run(String... strings) throws Exception {
        if (catalogRepostory.count() == 0 && projectRepostory.count() == 0 && timesheetRepostory.count() == 0) {
            addDataToDataBase();
        }
    }

    @Transactional
    private void addDataToDataBase() {
        Catalog[] catalog = new Catalog[10];
        Project[] projects = new Project[3];
        for (int i = 0; i < catalog.length; i++) {
            catalog[i] = new Catalog();
        }
        catalog[0].setName("产品项目");
        catalog[1].setName("产品规划");
        catalog[2].setName("产品设计");
        catalog[3].setName("产品研发");
        catalog[4].setName("产品测试");
        catalog[1].setParent(catalog[0]);
        catalog[2].setParent(catalog[0]);
        catalog[3].setParent(catalog[0]);
        catalog[4].setParent(catalog[0]);
        catalog[5].setName("编码/单元测试");
        catalog[6].setName("继承测试");
        catalog[7].setName("问题修复");
        catalog[5].setParent(catalog[3]);
        catalog[6].setParent(catalog[3]);
        catalog[7].setParent(catalog[3]);
        catalog[8].setName("产品功能测试");
        catalog[9].setName("产品性能测试");
        catalog[8].setParent(catalog[4]);
        catalog[9].setParent(catalog[4]);
        projects[0] = new Project();
        projects[0].setName("DGP资源稽核系统");
        projects[0].setCatalog(catalogRepostory.findOne(1));
        projects[1] = new Project();
        projects[1].setName("网管系统");
        projects[1].setCatalog(catalogRepostory.findOne(1));
        projects[2] = new Project();
        projects[2].setName("广播系统");
        projects[2].setCatalog(catalogRepostory.findOne(1));
        catalogRepostory.save(Arrays.asList(catalog));
        projectRepostory.save(Arrays.asList(projects));
    }
}
