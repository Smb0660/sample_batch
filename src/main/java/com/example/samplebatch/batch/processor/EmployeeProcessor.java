package com.example.samplebatch.batch.processor;


import com.example.samplebatch.model.EmployeeInfo;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class EmployeeProcessor implements ItemProcessor<EmployeeInfo, EmployeeInfo> {
    @Override
    public EmployeeInfo process(EmployeeInfo employeeInfo) throws Exception {
        employeeInfo.setTime(LocalDate.now());
        return employeeInfo;
    }
}