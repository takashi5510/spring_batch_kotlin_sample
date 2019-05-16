package com.example.chunk.csv

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.batch.core.BatchStatus
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.JobExecutionListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
public class CsvJobListener : JobExecutionListener {

    public var targetFile: String = ""

    public var log = LoggerFactory.getLogger(CsvUploadJobListener::class.java)

    override fun beforeJob(jobExecution: JobExecution) {
        targetFile = jobExecution.getJobParameters().getString("targetFile")
        log.info("${jobExecution.getJobInstance().getJobName()} Start, targetFile: ${targetFile}")
    }
    override fun afterJob(jobExecution: JobExecution) {
        log.info("${jobExecution.getJobInstance().getJobName()} Done, status: ${jobExecution.getStatus()}")
    }
}
