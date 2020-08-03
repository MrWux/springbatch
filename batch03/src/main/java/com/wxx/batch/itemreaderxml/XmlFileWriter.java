package com.wxx.batch.itemreaderxml;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 创建于:2020/8/2
 *
 * @author wuxixin
 */
@Component("xmlFileWriter")
public class XmlFileWriter implements ItemWriter<Customer> {
    @Override
    public void write(List<? extends Customer> list) throws Exception {
        list.forEach(System.out::println);

    }
}
