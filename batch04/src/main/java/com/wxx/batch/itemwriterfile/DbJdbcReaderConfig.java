package com.wxx.batch.itemwriterfile;

import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.batch.item.database.support.OraclePagingQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
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
@Configuration
public class DbJdbcReaderConfig {

    @Autowired
    private DataSource dataSource;

    @Bean
    public JdbcPagingItemReader<Customer> dbJdbcReader(){
        JdbcPagingItemReader<Customer> reader = new JdbcPagingItemReader<>();
        reader.setDataSource(dataSource);
        reader.setFetchSize(10);
        // 把读取的数据转换成Customer对象
        reader.setRowMapper(new RowMapper<Customer>() {
            @Override
            public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
                Customer customer = new Customer();
                customer.setId(rs.getLong(1));
                customer.setFirstName(rs.getString(2));
                customer.setLastName(rs.getString(3));
                customer.setBirthday(rs.getString(4));
                return customer;
            }
        });
        // 指定sql语句
        MySqlPagingQueryProvider provider = new MySqlPagingQueryProvider();
        provider.setSelectClause("id,firstName,lastName,birthday");
        provider.setFromClause("from customer");

        // 指定根据那个字段来排序
        Map<String, Order> sort = new HashMap<>(1);
        sort.put("id",Order.ASCENDING);
        provider.setSortKeys(sort);

        reader.setQueryProvider(provider);
        return reader;
    }


}
