package com.wxx.batch.itemreader;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.util.Iterator;
import java.util.List;

/**
 * 创建于:2020/8/2
 *
 * @author wuxixin
 */
public class MyReader implements ItemReader<String> {

    private Iterator<String> iterator;

    public MyReader(List<String> list){
        this.iterator = list.iterator();
    }

    @Override
    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        // 一个数据一个数据的读
        if(iterator.hasNext()){
            return this.iterator.next();
        }else {
            return null;
        }
    }
}
