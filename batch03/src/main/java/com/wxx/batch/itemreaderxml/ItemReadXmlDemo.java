package com.wxx.batch.itemreaderxml;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.xstream.XStreamMarshaller;

import java.util.HashMap;
import java.util.Map;

/**
 * 创建于:2020/8/2
 *
 * @author wuxixin
 */
//@Configuration
public class ItemReadXmlDemo {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier("xmlFileWriter")
    private  ItemWriter<? super Customer> xmlFileWriter;

    @Bean
    public Job itemReadXmlJob(){
        return jobBuilderFactory.get("itemReadXmlJob")
                .start(itemReadXmlStep())
                .build();
    }

    @Bean
    public Step itemReadXmlStep() {
        return stepBuilderFactory.get("itemReadXmlStep")
                .<Customer,Customer>chunk(1)
                .reader(xmlFileReader())
                .writer(xmlFileWriter)
                .build();
    }

    @Bean
    @StepScope
    public StaxEventItemReader<Customer> xmlFileReader() {
        StaxEventItemReader<Customer> reader = new StaxEventItemReader<>();
        reader.setResource(new ClassPathResource("customer.xml"));

        //指定需要处理的根标签
        reader.setFragmentRootElementName("customer");
        //把xml转成对象
        XStreamMarshaller xStreamMarshaller = new XStreamMarshaller();

        Map<String,Class> map = new HashMap<>();
        map.put("customer",Customer.class);
        xStreamMarshaller.setAliases(map);

        reader.setUnmarshaller(xStreamMarshaller);
        return reader;
    }


}
