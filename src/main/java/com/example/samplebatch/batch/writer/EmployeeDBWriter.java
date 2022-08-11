package com.example.samplebatch.batch.writer;


import com.example.samplebatch.model.EmployeeInfo;
import com.example.samplebatch.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmployeeDBWriter implements ItemWriter<EmployeeInfo> {
    private final EmployeeRepository employeeRepository;
    Logger logger = LoggerFactory.getLogger(EmployeeDBWriter.class);

    public EmployeeDBWriter(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void write(List<? extends EmployeeInfo> employees) throws Exception {
        logger.info("Attempt to save date for employee: " + employees);
        employeeRepository.saveAll(employees);
    }
}