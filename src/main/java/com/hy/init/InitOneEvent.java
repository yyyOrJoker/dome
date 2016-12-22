package com.hy.init;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.DeploymentBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created by yyy1867 on 2016/12/21.
 */
@Component
@Order(value = 1)
public class InitOneEvent implements CommandLineRunner {

    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private RepositoryService repositoryService;


    @Override
    public void run(String... strings) throws Exception {
        DeploymentBuilder deployment = repositoryService.createDeployment();
        deployment.addClasspathResource("processes\\MyProcess.bpmn.xml");
        deployment.deploy();
    }
}
