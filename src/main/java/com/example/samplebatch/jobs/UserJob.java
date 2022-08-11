package com.example.samplebatch.jobs;

import com.example.samplebatch.model.UserInfo;
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
public class UserJob {

    /**
     * User load userJobBean from CSV file to in memory database
     */
    @Bean
    public Job userJobBean(JobBuilderFactory jobBuilderFactory,
                           StepBuilderFactory stepBuilderFactory,
                           ItemStreamReader<UserInfo> itemReader,
                           ItemProcessor<UserInfo, UserInfo> itemProcessor,
                           ItemWriter<UserInfo> itemWriter) {

        Step step = stepBuilderFactory.get("User-file-load")
                .<UserInfo, UserInfo>chunk(100)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();

        return jobBuilderFactory.get("User-Load")
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();
    }

    /**
     * Batch item reader for reading data from CSV file by given file path for UserInfo entity
     */
    @Bean
    public FlatFileItemReader<UserInfo> flatFileItemReader(@Value("${input}") Resource resource) {
        FlatFileItemReader<UserInfo> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(resource);
        flatFileItemReader.setName("CSV-Reader");
        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setLineMapper(lineMapper());
        return flatFileItemReader;
    }

    /**
     * Mapper for mapping Csv file for UserInfo entity
     */
    @Bean
    public LineMapper<UserInfo> lineMapper() {
        DefaultLineMapper<UserInfo> defaultLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();

        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("id", "name", "department", "salary");

        BeanWrapperFieldSetMapper<UserInfo> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(UserInfo.class);

        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(fieldSetMapper);

        return defaultLineMapper;
    }
}