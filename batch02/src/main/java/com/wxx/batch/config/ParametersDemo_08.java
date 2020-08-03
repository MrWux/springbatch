package com.wxx.batch.config;

import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * job参数的实现 创建于:2020/8/2
 *
 * @author wuxixin
 */
@Configuration
public class ParametersDemo_08 implements StepExecutionListener {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    private Map<String,JobParameter> parameters;

    @Bean
    public Job ParametersDemo(){
        return jobBuilderFactory.get("ParametersDemo")
                .start(parameterStep())
                .build();
    }

    // Job执行的是step,Job使用的数据肯定是在step中使用
    // 那只要给step传递数据，如果给step传递参数
    // 使用监听 使用Step级别的监听来传递数据
    @Bean
    public Step parameterStep() {
        return stepBuilderFactory.get("parameterStep")
                .listener(this)
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        // 输出接收到的参数值
                        System.out.println("接收到的参数值"+parameters.get("info"));
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }


    @Override
    public void beforeStep(StepExecution stepExecution) {
        parameters = stepExecution.getJobParameters().getParameters();
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        System.out.println("step结束！");
        return null;
    }
}
