package com.wxx.batch.itemreadedb;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 输出处理类 创建于:2020/8/2
 *
 * @author wuxixin
 */
@Component("dbJbdcWriter")
public class DbJbdcWriter implements ItemWriter<User> {
    @Override
    public void write(List<? extends User> list) throws Exception {
        list.forEach(System.out::println);
    }
}
