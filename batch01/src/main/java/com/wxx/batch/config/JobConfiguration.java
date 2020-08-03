package com.wxx.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 任务配置类创建于:2020/8/1
 *
 * @author wuxixin
 */
@Configuration
@EnableBatchProcessing
public class JobConfiguration {

    // 注入创建任务对象的对象
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    // 任务的执行由Step决定, 注入创建Step对象
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    // 创建任务对象
    @Bean
    public Job helloWorldJob(){
        return jobBuilderFactory.get("helloWorldJob")
                .start(step1())
                .next(step1())
                .build();
    }

    // 要做的事情
    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("Hello World!");
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }

}
