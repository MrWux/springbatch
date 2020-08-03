package com.wxx.batch.restart;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 创建于:2020/8/2
 *
 * @author wuxixin
 */
@Component("restartWriter")
public class RestartWriter implements ItemWriter<Customer> {
    @Override
    public void write(List<? extends Customer> list){
        list.forEach(System.out::println);
    }
}
