package com.wxx.batch.itemwriter;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * ItemWriter的实现 创建于:2020/8/2
 *
 * @author wuxixin
 */
@Component("myWriter")
public class MyWriter implements ItemWriter<String> {


    @Override
    public void write(List<? extends String> items) throws Exception {
        System.out.println(items.size());
        items.forEach(System.out::println);
    }
}
