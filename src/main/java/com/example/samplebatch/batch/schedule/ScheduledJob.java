package com.example.samplebatch.batch.schedule;

import com.example.samplebatch.controller.LoadController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@EnableScheduling
public class ScheduledJob {

    private final JobLauncher jobLauncher;
    private final Job userJobBean;
    private final Job studentJobBean;
    private final Job employeeJobBean;
    private final Job customerJobBean;
    private Logger logger = LoggerFactory.getLogger(LoadController.class);

    public ScheduledJob(JobLauncher jobLauncher,
                        Job userJobBean,
                        Job studentJobBean,
                        Job employeeJobBean,
                        Job customerJobBean) {
        this.jobLauncher = jobLauncher;
        this.userJobBean = userJobBean;
        this.studentJobBean = studentJobBean;
        this.employeeJobBean = employeeJobBean;
        this.customerJobBean = customerJobBean;
    }

    /**
     * Scheduled userJobBean runner , in params can be passed time patterns of cron
     *
     */

    // @Scheduled(cron= "*/10 * * * * *") for every 10 seconds
    // @Scheduled(cron= "0 0 * * * *") for top of every hour of every day

    @Scheduled(cron= "*/10 * * * * *")
    public BatchStatus loadEmployees() throws
            JobInstanceAlreadyCompleteException,
            JobExecutionAlreadyRunningException,
            JobParametersInvalidException,
            JobRestartException {
        Map<String, JobParameter> params = new HashMap<>();
        params.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters paramaters = new JobParameters(params);
        JobExecution jobExecution = jobLauncher.run(employeeJobBean, paramaters);
        logger.info("Job execution " + jobExecution.getStatus());
        return jobExecution.getStatus();
    }
}