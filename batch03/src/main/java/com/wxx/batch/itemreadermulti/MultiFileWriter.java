package com.wxx.batch.itemreadermulti;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 创建于:2020/8/2
 *
 * @author wuxixin
 */
@Component("multiFileWriter")
public class MultiFileWriter implements ItemWriter<Customer> {
    @Override
    public void write(List<? extends Customer> items) throws Exception {
        items.forEach(System.out::println);
    }
}
