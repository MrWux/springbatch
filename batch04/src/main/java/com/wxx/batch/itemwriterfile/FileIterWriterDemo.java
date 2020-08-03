package com.wxx.batch.itemwriterfile;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 输出文件到普通文件 创建于:2020/8/2
 *
 * @author wuxixin
 */
@Configuration
public class FileIterWriterDemo {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier("dbJdbcReader")
    private ItemReader<? extends Customer> dbJdbcReader;

    @Autowired
    @Qualifier("fileItemWriter")
    private ItemWriter<? super Customer> fileItemWriter;

    @Bean
    public Job fileIterWriterDemoJob(){
        return jobBuilderFactory.get("fileIterWriterDemoJob")
                .start(fileIterWriterDemoStep())
                .build();
    }

    @Bean
    public Step fileIterWriterDemoStep() {


        return stepBuilderFactory.get("fileIterWriterDemoStep")
                .<Customer,Customer>chunk(10)
                .reader(dbJdbcReader)
                .writer(fileItemWriter)
                .build();
    }


}
