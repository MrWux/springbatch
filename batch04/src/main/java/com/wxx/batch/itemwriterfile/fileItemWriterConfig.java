package com.wxx.batch.itemwriterfile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

/**
 * 创建于:2020/8/2
 *
 * @author wuxixin
 */
@Configuration
public class fileItemWriterConfig {

    @Bean
    public FlatFileItemWriter<Customer> fileItemWriter() throws Exception {
        // 把Customer对象转成字符串输出到文件
        FlatFileItemWriter<Customer> writer = new FlatFileItemWriter<Customer>();
        String path  = "E:\\1.txt";
        writer.setResource(new FileSystemResource(path));

        // 把Customer对象转成字符串
        writer.setLineAggregator(new LineAggregator<Customer>() {

            ObjectMapper mapper = new ObjectMapper();

            @Override
            public String aggregate(Customer item) {
                String str = null;
                try {
                    str = mapper.writeValueAsString(item);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                return str;
            }
        });

        writer.afterPropertiesSet();
        return writer;
    }

}
