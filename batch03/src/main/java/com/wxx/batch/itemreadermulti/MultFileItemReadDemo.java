package com.wxx.batch.itemreadermulti;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * 创建于:2020/8/2
 *
 * @author wuxixin
 */
//@Configuration
public class MultFileItemReadDemo {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Value("classpath:/file*.txt")
    private Resource[] fileResources;

    @Autowired
    @Qualifier("multiFileWriter")
    private ItemWriter<? super Customer> multiFileWriter;

    @Bean
    public Job multFileItemReadJob(){
        return jobBuilderFactory.get("multFileItemReadJob")
                .start(multFileItemReadStep())
                .build();
    }

    @Bean
    public Step multFileItemReadStep() {

        return stepBuilderFactory.get("multFileItemReadStep")
                .<Customer,Customer>chunk(10)
                .reader(multiFileReader())
                .writer(multiFileWriter)
                .build();
    }

    @Bean
    @StepScope
    public ItemReader<? extends Customer> multiFileReader() {
        MultiResourceItemReader<Customer> reader = new MultiResourceItemReader<>();
        reader.setDelegate(flatFileItemReader());
        reader.setResources(fileResources);
        return reader;
    }

    @Bean
    @StepScope
    public FlatFileItemReader<Customer> flatFileItemReader(){
        FlatFileItemReader<Customer> reader = new FlatFileItemReader<>();
        //reader.setResource(new ClassPathResource("file1.txt"));

        // 解析数据
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames("id","firstName","lastName","birthday");
        // 把解析出的一行数据映射为Customer对象
        DefaultLineMapper<Customer> mapper = new DefaultLineMapper<>();
        mapper.setLineTokenizer(tokenizer);
        mapper.setFieldSetMapper(fieldSet -> {
           Customer customer = new Customer();
            customer.setId(fieldSet.readLong("id"));
            customer.setFirstName(fieldSet.readString("firstName"));
            customer.setLastName(fieldSet.readString("lastName"));
            customer.setBirthday(fieldSet.readString("birthday"));
            return customer;
        });
        mapper.afterPropertiesSet();
        reader.setLineMapper(mapper);
        return reader;
    }

}
