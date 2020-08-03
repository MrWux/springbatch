package com.wxx.batch.itemreadedb;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 从数据库中读取数据 创建于:2020/8/2
 *
 * @author wuxixin
 */
//@Configuration
public class ItemReaderDbDemo {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private DataSource dataSource;

    @Autowired
    @Qualifier("dbJbdcWriter")
    private ItemWriter<? super User> dbJbdcWriter;

    @Bean
    public Job iterReaderDBJob(){
        return jobBuilderFactory.get("iterReaderDBJob")
                .start(iterReaderDBStep())
                .build();
    }

    @Bean
    public Step iterReaderDBStep() {
        return stepBuilderFactory.get("iterReaderDBStep")
                .<User,User>chunk(2)
                .reader(dbJbdcReader())
                .writer(dbJbdcWriter)
                .build();
    }

    @Bean
    @StepScope
    public JdbcPagingItemReader<User> dbJbdcReader() {
        // 创建jdbc
        JdbcPagingItemReader<User> reader = new JdbcPagingItemReader<>();
        // 使用的数据源
        reader.setDataSource(dataSource);
        // 查询数量
        reader.setFetchSize(2);
        // 把读取到的记录转换成user对象
        reader.setRowMapper(new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User u = new User();
                u.setId(rs.getInt(1));
                u.setUsername(rs.getString(2));
                u.setPassword(rs.getString(3));
                u.setAge(rs.getInt(4));
                return u;
            }
        });
        // 指定sql语句
        MySqlPagingQueryProvider provider = new MySqlPagingQueryProvider();
        provider.setSelectClause("id,username,password,age");
        provider.setFromClause("from user");

        // 指定根据那个字段进行排序
        Map<String, Order> sort = new HashMap<>(1);
        sort.put("id", Order.ASCENDING);
        provider.setSortKeys(sort);

        reader.setQueryProvider(provider);
        return reader;
    }


}
