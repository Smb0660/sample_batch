package com.example.samplebatch.jobs;

import com.example.samplebatch.model.Customer;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CustomerJob {

    private final DataSource dataSource;

    public CustomerJob(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Batch item reader for reading data from in memory database for Customer entity
     */
    @Bean
    public JdbcCursorItemReader<Customer> customerReader() {
        JdbcCursorItemReader<Customer> reader = new JdbcCursorItemReader<>();
        reader.setDataSource(dataSource);
        reader.setSql("SELECT id, name, department, salary from customer");
        reader.setRowMapper(new RowMapper<Customer>() {
            @Override
            public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
                Customer Customer = new Customer();
                Customer.setId(rs.getLong("id"));
                Customer.setName(rs.getString("name"));
                Customer.setDepartment(rs.getString("department"));
                Customer.setSalary(rs.getString("salary"));
                return Customer;
            }
        });
        return reader;
    }

    /**
     * Customer writer userJobBean, for rewriting data from database to CSV file
     */
    @Bean
    public Job customerJobBean(JobBuilderFactory jobBuilderFactory,
                               StepBuilderFactory stepBuilderFactory,
                               @Value("${customer}") String path) {

        Step step = stepBuilderFactory.get("executeStep")
                .<Customer, Customer>chunk(100)
                .reader(customerReader())
                .writer(writer(path))
                .build();

        return jobBuilderFactory.get("Customer-writer")
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();
    }

    /**
     * Batch item writer for writing data to CSV file by given path
     */
    @Bean
    public FlatFileItemWriter<Customer> writer(@Value("${customer}") String path) {
        FlatFileItemWriter<Customer> flatFileItemWriter = new FlatFileItemWriter<>();
        flatFileItemWriter.setResource(new FileSystemResource(path));
        DelimitedLineAggregator<Customer> aggregator = new DelimitedLineAggregator<>();
        BeanWrapperFieldExtractor<Customer> extractor = new BeanWrapperFieldExtractor<>();
        extractor.setNames(new String[]{"id", "name", "department", "salary"});
        aggregator.setFieldExtractor(extractor);
        flatFileItemWriter.setLineAggregator(aggregator);
        return flatFileItemWriter;
    }
}