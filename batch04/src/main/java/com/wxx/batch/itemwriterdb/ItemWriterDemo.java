package com.wxx.batch.itemwriterdb;

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
 * 输出数据持久化到数据库 创建于:2020/8/2
 *
 * @author wuxixin
 */
//@Configuration
public class ItemWriterDemo {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier("flatFileRead")
    private ItemReader<Customer> flatFileRead;

    @Autowired
    @Qualifier("itemWriterDb")
    private ItemWriter<Customer> itemWriterDb;


    @Bean
    public Job itemWriterDemoJob(){
        return jobBuilderFactory.get("itemWriterDemoJob")
                .start(itemWriterDemoStep())
                .build();
    }

    @Bean
    public Step itemWriterDemoStep() {
        return stepBuilderFactory.get("itemWriterDemoStep")
                .<Customer,Customer>chunk(10)
                .reader(flatFileRead)
                .writer(itemWriterDb)
                .build();

    }

}
