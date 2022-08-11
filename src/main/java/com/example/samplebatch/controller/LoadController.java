package com.example.samplebatch.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/load")
public class LoadController {

    private final JobLauncher jobLauncher;
    private final Job job;
    private final Job studentJob;
    private final Job employeeJob;
    private final Job customerJob;
    private final Logger logger = LoggerFactory.getLogger(LoadController.class);

    public LoadController(JobLauncher jobLauncher,
                          Job userJobBean,
                          Job studentJobBean,
                          Job employeeJobBean,
                          Job customerJobBean) {
        this.jobLauncher = jobLauncher;
        this.job = userJobBean;
        this.studentJob = studentJobBean;
        this.employeeJob = employeeJobBean;
        this.customerJob = customerJobBean;
    }


    @GetMapping("/users")
    public BatchStatus load() throws
            JobInstanceAlreadyCompleteException,
            JobExecutionAlreadyRunningException,
            JobParametersInvalidException, JobRestartException {
        Map<String, JobParameter> params = new HashMap<>();
        params.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters paramaters = new JobParameters(params);
        JobExecution jobExecution = jobLauncher.run(job, paramaters);
        logger.info("Job execution " + jobExecution.getStatus());
        return jobExecution.getStatus();
    }

    @GetMapping("/students")
    public BatchStatus loadStudents() throws
            JobInstanceAlreadyCompleteException,
            JobExecutionAlreadyRunningException,
            JobParametersInvalidException,
            JobRestartException {
        Map<String, JobParameter> params = new HashMap<>();
        params.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters paramaters = new JobParameters(params);
        JobExecution jobExecution = jobLauncher.run(studentJob, paramaters);
        logger.info("Job execution " + jobExecution.getStatus());
        return jobExecution.getStatus();
    }

    @GetMapping("/employees")
    public BatchStatus loadEmployees() throws
            JobInstanceAlreadyCompleteException,
            JobExecutionAlreadyRunningException,
            JobParametersInvalidException,
            JobRestartException {
        Map<String, JobParameter> params = new HashMap<>();
        params.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters paramaters = new JobParameters(params);
        JobExecution jobExecution = jobLauncher.run(employeeJob, paramaters);
        logger.info("Job execution " + jobExecution.getStatus());
        return jobExecution.getStatus();
    }

    @GetMapping("/customers")
    public BatchStatus writeCustomers() throws
            JobInstanceAlreadyCompleteException,
            JobExecutionAlreadyRunningException,
            JobParametersInvalidException,
            JobRestartException {
        Map<String, JobParameter> params = new HashMap<>();
        params.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters paramaters = new JobParameters(params);
        JobExecution jobExecution = jobLauncher.run(customerJob, paramaters);
        logger.info("Job execution " + jobExecution.getStatus());
        return jobExecution.getStatus();
    }
}