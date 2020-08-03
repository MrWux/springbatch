package com.wxx.batch.itemwriter;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建于:2020/8/2
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
    @Qualifier("myWriter")
    private MyWriter myWriter;

    @Bean
    public Job itemWriterDemoJob(){
        return jobBuilderFactory.get("itemWriterDemoJob")
                .start(itemWriterDemoStep())
                .build();
    }

    @Bean
    public Step itemWriterDemoStep() {
        return stepBuilderFactory.get("itemWriterDemoStep")
                .<String,String>chunk(5)
                .reader(myRead())
                .writer(myWriter)
                .build();
    }


    @Bean
    public ItemReader<String> myRead() {
        List<String> list = new ArrayList<>();
        for (int i=1;i<50;i++){
            list.add("java"+i);
        }
        return new ListItemReader<String>(list);
    }


}
