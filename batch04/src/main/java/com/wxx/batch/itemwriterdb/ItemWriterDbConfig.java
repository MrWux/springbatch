package com.wxx.batch.itemwriterdb;

import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * 文件输出 创建于:2020/8/2
 *
 * @author wuxixin
 */
@Configuration
public class ItemWriterDbConfig{

    @Autowired
    private DataSource dataSource;

    @Bean
    public JdbcBatchItemWriter<Customer> itemWriterDb()
    {
        JdbcBatchItemWriter<Customer> writer = new JdbcBatchItemWriter<>();
        writer.setDataSource(dataSource);
        writer.setSql("insert into customer(id,firstName,lastName,birthday) values "+
                "(:id,:firstName,:lastName,:birthday)");
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider());
        return writer;
    }

}
