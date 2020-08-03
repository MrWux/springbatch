package com.wxx.batch.itemreader;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

/**
 * 创建于:2020/8/2
 *
 * @author wuxixin
 */
//@Configuration
public class ItemReaderDemo {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job itemReaderDemoJob(){
        return jobBuilderFactory.get("itemReaderDemoJob")
                .start(itemReaderStep())
                .build();
    }

    @Bean
    public Step itemReaderStep() {
        return stepBuilderFactory.get("itemReaderStep")
                // 读取多少才做处理
                .<String,String>chunk(2)
                .reader(itemReaderDemoRead())
                .writer(list -> {
                    list.forEach(str-> System.out.println(str+"..."));
                }).build();
    }

    @Bean
    public MyReader itemReaderDemoRead() {
        List<String> data = Arrays.asList("cat","dog","pig","duck");
        return new MyReader(data);
    }

}
