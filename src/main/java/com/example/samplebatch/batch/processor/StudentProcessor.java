package com.example.samplebatch.batch.processor;

import com.example.samplebatch.model.StudentInfo;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class StudentProcessor implements ItemProcessor<StudentInfo, StudentInfo> {

    @Override
    public StudentInfo process(StudentInfo student) throws Exception {
        student.setTime(LocalDate.now());
        return student;
    }
}