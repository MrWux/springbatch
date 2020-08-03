package com.wxx.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.JobStepBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * 嵌套Job创建于:2020/8/2
 *
 * @author wuxixin
 */
//@Configuration
public class nestedJobDemo_06 {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private Job childJobOne;

    @Autowired
    private Job childJobTwo;

    // 启动对象
    @Autowired
    private JobLauncher jobLauncher;

    @Bean
    // 在yml中配置启动job的名称   batch.job.names:parentJob
    public Job parentJob(JobRepository repository, PlatformTransactionManager transactionManager){
        return jobBuilderFactory.get("parentJob")
                .start(childJob1(repository,transactionManager))
                .next(childJob2(repository,transactionManager))
                .build();
    }

    // 返回的是Job类型的Step,特殊的step
    private Step childJob1(JobRepository repository, PlatformTransactionManager transactionManager) {
        return new JobStepBuilder(new StepBuilder("childJob1"))
                .job(childJobOne)
                // 使用启动父Job的启动对象
                .launcher(jobLauncher)
                // 持久化
                .repository(repository)
                // 事务
                .transactionManager(transactionManager)
                .build();
    }

    private Step childJob2(JobRepository repository, PlatformTransactionManager transactionManager) {
        return new JobStepBuilder(new StepBuilder("childJob2"))
                .job(childJobTwo)
                // 使用启动父Job的启动对象
                .launcher(jobLauncher)
                // 持久化
                .repository(repository)
                // 事务
                .transactionManager(transactionManager)
                .build();
    }

}
