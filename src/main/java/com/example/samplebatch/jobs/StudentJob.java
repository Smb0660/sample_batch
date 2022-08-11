package com.example.samplebatch.jobs;

import com.example.samplebatch.model.StudentInfo;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.batch.item.xml.builder.StaxEventItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;

@Component
public class StudentJob {

    /**
     * Student load userJobBean from XML file to in memory database
     */
    @Bean
    public Job studentJobBean(JobBuilderFactory jobBuilderFactory,
                              StepBuilderFactory stepBuilderFactory,
                              ItemStreamReader<StudentInfo> itemReader,
                              ItemProcessor<StudentInfo, StudentInfo> itemProcessor,
                              ItemWriter<StudentInfo> studentItemWriter) {
        Step step = stepBuilderFactory.get("Student-file-load")
                .<StudentInfo, StudentInfo>chunk(100)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(studentItemWriter)
                .build();

        return jobBuilderFactory.get("Student-Load")
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();
    }

    /**
     * Batch item reader for reading data from XML by given file path
     */
    @Bean
    public StaxEventItemReader<StudentInfo> xmlFileItemReader(@Value("${student}") Resource resource) {
        Jaxb2Marshaller studentMarshaller = new Jaxb2Marshaller();
        studentMarshaller.setClassesToBeBound(StudentInfo.class);

        return new StaxEventItemReaderBuilder<StudentInfo>()
                .name("studentReader")
                .resource(resource)
                .addFragmentRootElements("student")
                .unmarshaller(studentMarshaller)
                .build();
    }
}