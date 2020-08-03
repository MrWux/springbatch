package com.wxx.batch.itemwriterdb;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * 文件输入 创建于:2020/8/2
 *
 * @author wuxixin
 */
@Configuration
public class FlatFileReadConfig {

    @Bean
    public FlatFileItemReader flatFileRead(){
        FlatFileItemReader<Customer> reader = new FlatFileItemReader();
        reader.setResource(new ClassPathResource("customer.txt"));
        // 跳过第一行
        reader.setLinesToSkip(1);
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
