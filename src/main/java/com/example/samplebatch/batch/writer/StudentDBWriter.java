package com.example.samplebatch.batch.writer;


import com.example.samplebatch.model.StudentInfo;
import com.example.samplebatch.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StudentDBWriter implements ItemWriter<StudentInfo> {
    @Autowired
    private final StudentRepository studentRepository;
    Logger logger = LoggerFactory.getLogger(StudentDBWriter.class);

    public StudentDBWriter(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public void write(List<? extends StudentInfo> students) throws Exception {
        logger.info("Attempt to save date for student: " + students);
        studentRepository.saveAll(students);
    }
}