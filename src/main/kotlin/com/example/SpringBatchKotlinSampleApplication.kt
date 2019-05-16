package com.example

//import org.springframework.boot.autoconfigure.SpringBootApplication
//import org.springframework.boot.runApplication
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParameter
import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.SpringApplication
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.ExitStatus
import org.springframework.batch.core.launch.support.SimpleJobLauncher

@SpringBootApplication
class SpringBatchKotlinSampleApplication

fun main(args: Array<String>) {
    //runApplication<SpringBatchKotlinSampleApplication>(*args)
    val app = SpringApplication(SpringBatchKotlinSampleApplication::class.java)
    val ctx: ConfigurableApplicationContext = app.run(*args)
    val jobLauncher: JobLauncher = ctx.getBean(JobLauncher::class.java)
    val jobParametersBuilder = JobParametersBuilder()

    if (args.size >= 1) {
        args.forEach {
            if (it.split("=").size == 2) {
                jobParametersBuilder.addParameter(it.split("=")[0], JobParameter(it.split("=")[1]))
            }
        }
    }
    val jobParameters: JobParameters = jobParametersBuilder.toJobParameters()

    val jobExec: JobExecution = jobLauncher.run(ctx.getBean(args[0], Job::class.java), jobParameters)

    if (ExitStatus.COMPLETED.equals(jobExec.getExitStatus())) {
        System.exit(0)
    } else {
        System.exit(1)
    }

}
