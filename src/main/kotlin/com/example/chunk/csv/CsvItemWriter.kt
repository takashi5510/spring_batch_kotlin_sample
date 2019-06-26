package com.example.chunk.csv

import com.example.entity.csv.OutputCsv
import com.example.chunk.csv.CsvJobListener
import com.example.conf.CsvProperties
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.file.FlatFileItemWriter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.FileSystemResource
import org.springframework.stereotype.Component

import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor
import org.springframework.batch.item.file.transform.DelimitedLineAggregator
import org.springframework.batch.item.file.transform.FieldExtractor
import org.springframework.batch.item.file.transform.LineAggregator

@Configuration
@EnableBatchProcessing
@ComponentScan("com.example")
class CsvItemWriter {

    public var log = LoggerFactory.getLogger(CsvItemWriter::class.java)

    @Autowired
    lateinit var listener: CsvJobListener

    @Autowired
    lateinit var properties: CsvProperties

    @Bean
    @StepScope
    fun csvWriter(): FlatFileItemWriter<OutputCsv> {

        var writer = FlatFileItemWriter<OutputCsv>()
        writer.setResource(FileSystemResource(properties.directory + "output_" + listener.targetFile))
        writer.setAppendAllowed(true)
        writer.setEncoding("windows-31j")

        var headerWriter = CsvHeader("id,name,email,gender,userType")
        writer.setHeaderCallback(headerWriter)

        var lineAggregator: LineAggregator<OutputCsv> = createOutputCsvAggregator()
        writer.setLineAggregator(lineAggregator)

        return writer
    }

    private fun createOutputCsvAggregator(): LineAggregator<OutputCsv> {
        var lineAggregator = DelimitedLineAggregator<OutputCsv>()
        lineAggregator.setDelimiter(",")

        var fiedExtractor: FieldExtractor<OutputCsv> = createOutputCsvFieldExtractor()
        lineAggregator.setFieldExtractor(fiedExtractor)

        return lineAggregator
    }

    private fun createOutputCsvFieldExtractor(): FieldExtractor<OutputCsv> {
        var extractor = BeanWrapperFieldExtractor<OutputCsv>()
        extractor.setNames(arrayOf("id", "name", "email", "gender", "userType"))
        return extractor
    }
}
