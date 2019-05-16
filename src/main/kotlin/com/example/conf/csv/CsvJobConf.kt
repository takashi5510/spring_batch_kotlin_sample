package com.example.conf

import com.example.chunk.csv.CsvJobListener
import com.example.entity.csv.InputCsv
import com.example.entity.csv.OutputCsv
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.item.file.FlatFileItemReader
import org.springframework.batch.item.file.FlatFileItemWriter
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableBatchProcessing
class CsvJobConf {

	@Autowired
    lateinit var jobBuilderFactory: JobBuilderFactory

    @Autowired
    lateinit var steps: StepBuilderFactory

    @Autowired
    lateinit var jobListener: CsvJobListener

    @Autowired
    lateinit var csvItemReader: FlatFileItemReader<InputCsv>

    @Autowired
    lateinit var csvItemWriter: FlatFileItemWriter

    @Bean
    open fun csvJob(): Job {
        return jobBuilderFactory.get("csvJob")
            .listener(jobListener)
            .start(csvStep())
            .build()
    }
    @Bean(name=arrayOf("csvStep"))
    open fun csvStep(): Step {
        return steps.get("csvStep")
            .chunk<InputCsv, OutputCsv>(1)
            .reader(csvItemReader)
            .writer(csvItemWriter)
            .build()
    }

}