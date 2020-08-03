package com.wxx.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

/**
 * split 并发 创建于:2020/8/2
 *
 * @author wuxixin
 */
//@Configuration
//@EnableBatchProcessing
public class SplitDemo_04 {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Step splitDemoSplit1(){
        return stepBuilderFactory.get("splitDemoSplit1")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("splitDemoSplit1");
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }

    @Bean
    public Step splitDemoSplit2(){
        return stepBuilderFactory.get("splitDemoSplit2")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("splitDemoSplit2");
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }

    @Bean
    public Step splitDemoSplit3(){
        return stepBuilderFactory.get("splitDemoSplit3")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("splitDemoSplit3");
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }

    // 创建Flow
    @Bean
    public Flow splitFlowDemo1(){
        return new FlowBuilder<Flow>("splitFlowDemo1")
                .start(splitDemoSplit1())
                .build();
    }

    // 创建Flow
    @Bean
    public Flow splitFlowDemo2(){
        return new FlowBuilder<Flow>("splitFlowDemo2")
                .start(splitDemoSplit2())
                .next(splitDemoSplit3())
                .build();
    }

    // 创建Job
    @Bean
    public Job splitDemoJob(){
        return jobBuilderFactory.get("splitDemoJob")
                .start(splitFlowDemo1())
                .split(new SimpleAsyncTaskExecutor()).add(splitFlowDemo2())
                .end()
                .build();
    }
}
