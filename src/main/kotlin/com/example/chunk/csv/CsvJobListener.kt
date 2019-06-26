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

    public var log = LoggerFactory.getLogger(CsvJobListener::class.java)

    override fun beforeJob(jobExecution: JobExecution) {
        var tempFile = jobExecution.getJobParameters().getString("targetFile")
        if (tempFile.isNullOrEmpty()) {
            this.targetFile = "test.csv"
        } else {
            this.targetFile = tempFile
        }
        log.info("${jobExecution.getJobInstance().getJobName()} Start, targetFile: ${this.targetFile}")
    }
    override fun afterJob(jobExecution: JobExecution) {
        log.info("${jobExecution.getJobInstance().getJobName()} Done, status: ${jobExecution.getStatus()}")
    }
}
