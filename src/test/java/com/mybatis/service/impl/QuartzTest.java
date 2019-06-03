package com.mybatis.service.impl;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

public class QuartzTest {

    public static void main(String[] args) throws Exception {
        // define the job and tie it to our HelloJob class
        JobDetail job = newJob(MyJob.class)
                .withIdentity("job1", "group1")
                .build();


        // Trigger the job to run now, and then repeat every 40 seconds
        Trigger trigger = newTrigger()
                .withIdentity("trigger1", "group1")
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule("53/3 59 23 * * ? "))
                .build();


        // Tell quartz to schedule the job using our trigger
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.scheduleJob(job, trigger);
        scheduler.start();
    }

}
