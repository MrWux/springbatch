package com.wxx.batch.restart;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.*;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

/**
 * 输入异常处理类和重启,需要实现 ItemStreamReader接口创建于:2020/8/2
 *
 * @author wuxixin
 */
@Component("restartReader")
public class RestartReader implements ItemStreamReader<Customer> {

    public FlatFileItemReader<Customer> reader = new FlatFileItemReader<>();
    public Long curLine = 0L;
    public boolean restart = false;
    public ExecutionContext executionContext;


    @StepScope
    public RestartReader(){
        reader.setResource(new ClassPathResource("customer.txt"));
        // 跳过第一行
        reader.setLinesToSkip(1);
        // 解析数据
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames("id","firstName","lastName","birthday");
        // 把解析出的一行数据映射为Customer对象
        DefaultLineMapper<Customer> mapper = new DefaultLineMapper<>();
        mapper.setLineTokenizer(tokenizer);
        mapper.setFieldSetMapper(fieldSet -> {
            Customer customer = new Customer();
            customer.setId(fieldSet.readLong("id"));
            customer.setFirstName(fieldSet.readString("firstName"));
            customer.setLastName(fieldSet.readString("lastName"));
            customer.setBirthday(fieldSet.readString("birthday"));
            return customer;
        });
        mapper.afterPropertiesSet();
        reader.setLineMapper(mapper);
    }


    @Override
    public Customer read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        Customer customer = null;
        this.curLine++;

        if(restart){
            reader.setLinesToSkip(this.curLine.intValue()-1);
            restart = false;
        }

        reader.open(this.executionContext);
        customer = reader.read();

        if(customer != null && customer.getFirstName().equals("WrongNmae")){
            throw new RuntimeException("Something wrong Customer id: " + customer.getId());
        }

        return customer;
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        this.executionContext = executionContext;
        if(executionContext.containsKey("curLine")){
            this.curLine = executionContext.getLong("curLine");
            this.restart = true;
        }else{
            this.curLine = 0L;
            executionContext.put("curLine",this.curLine);
            System.out.println("Start reading from line: "+this.curLine + 1);
        }
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        executionContext.put("curLine",this.curLine);
        System.out.println("currentLine:"+this.curLine);
    }

    @Override
    public void close() throws ItemStreamException {

    }
}
