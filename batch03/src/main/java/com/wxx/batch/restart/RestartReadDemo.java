package com.wxx.batch.restart;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * 创建于:2020/8/2
 *
 * @author wuxixin
 */
@Configuration
public class RestartReadDemo {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier("restartReader")
    private RestartReader restartReader;


    @Autowired
    @Qualifier("restartWriter")
    private ItemWriter<? super Customer> restartWriter;



    @Bean
    public Job itemReaderFileJob()
    {
        return jobBuilderFactory.get("itemReaderFileJob")
                .start(itemReaderFileStep())
                .build();
    }

    @Bean
    public Step itemReaderFileStep() {
        return stepBuilderFactory.get("itemReaderFileStep")
                .<Customer, Customer>chunk(10)
                .reader(restartReader)
                .writer(restartWriter)
                .build();
    }

}
