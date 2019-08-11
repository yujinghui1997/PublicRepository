package com.shixun.Job;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class MyJob implements Job {
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private Scheduler scheduler = null;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		System.out.println("现在是——"+sdf.format(new Date()));
	}
	public MyJob() {
		try {
			//初始化调度器
			scheduler = StdSchedulerFactory.getDefaultScheduler();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addJob(String className,String name,String group,String cron) throws Exception {
		//任务详情
		Class jobClass = Class.forName(className);
		JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(name, group).build();
		//触发器
		CronScheduleBuilder schedBuilder = CronScheduleBuilder.cronSchedule(cron);
		Trigger trigger =TriggerBuilder.newTrigger().startNow().withSchedule(schedBuilder).build();
		//将触发器和任务添加到调度器中
		scheduler.scheduleJob(jobDetail, trigger);
		scheduler.start();
	}
	
	public static void main(String[] args) {
		MyJob job = new MyJob();
		try {
			job.addJob("com.shixun.Job.MyJob", "yjh", "123", "0/2 * * * * ?");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
