package com.example.samplebatch.jobs;

import com.example.samplebatch.model.EmployeeInfo;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class EmployeeJob {

    /**
     * Employee load userJobBean from CSV file to in memory database
     */
    @Bean
    public Job employeeJobBean(JobBuilderFactory jobBuilderFactory,
                               StepBuilderFactory stepBuilderFactory,
                               ItemStreamReader<EmployeeInfo> itemReader,
                               ItemProcessor<EmployeeInfo, EmployeeInfo> itemProcessor,
                               ItemWriter<EmployeeInfo> employeeInfoItemWriter) {
        Step step = stepBuilderFactory.get("Student-file-load")
                .<EmployeeInfo, EmployeeInfo>chunk(100)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(employeeInfoItemWriter)
                .build();

        return jobBuilderFactory.get("Employee-Load")
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();
    }

    /**
     * Batch item reader for reading data from CSV file by given file path for EmployeeInfo entity
     */
    @Bean
    public FlatFileItemReader<EmployeeInfo> employeeInfoFlatFileItemReader(@Value("${employee}") Resource resource) {
        FlatFileItemReader<EmployeeInfo> employeeInfoFlatFileItemReader = new FlatFileItemReader<>();
        employeeInfoFlatFileItemReader.setResource(resource);
        employeeInfoFlatFileItemReader.setName("Employee-CSV-Reader");
        employeeInfoFlatFileItemReader.setLinesToSkip(1);
        employeeInfoFlatFileItemReader.setLineMapper(employeeLineMapper());
        return employeeInfoFlatFileItemReader;
    }

    /**
     * Mapper for mapping Csv file for EmployeeInfo entity
     */
    @Bean
    public LineMapper<EmployeeInfo> employeeLineMapper() {
        DefaultLineMapper<EmployeeInfo> defaultLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames(new String[]{"id", "name", "department", "salary"});

        BeanWrapperFieldSetMapper<EmployeeInfo> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(EmployeeInfo.class);

        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(fieldSetMapper);

        return defaultLineMapper;
    }
}
