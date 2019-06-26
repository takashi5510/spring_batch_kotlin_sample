package com.example.chunk.csv

import com.example.entity.csv.InputCsv
import com.example.chunk.csv.CsvJobListener
import com.example.conf.CsvProperties
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.file.FlatFileItemReader
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper
import org.springframework.batch.item.file.mapping.DefaultLineMapper
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.FileSystemResource
import org.springframework.stereotype.Component

@Configuration
@EnableBatchProcessing
@ComponentScan("com.example")
class CsvItemReader {

    public var log = LoggerFactory.getLogger(CsvItemReader::class.java)

    @Autowired
    lateinit var listener: CsvJobListener

    @Autowired
    lateinit var properties: CsvProperties

    @Bean
    @StepScope
    fun csvReader(): FlatFileItemReader<InputCsv> {

        var reader = FlatFileItemReader<InputCsv>()
        reader.setLinesToSkip(1)
        reader.setResource(FileSystemResource(properties.directory + listener.targetFile))
        reader.setEncoding("windows-31j")

        var delimitedLineTokenizer = DelimitedLineTokenizer()
        val header = arrayOf(
            "id",
            "name",
            "email",
            "gender"
        )
        delimitedLineTokenizer.setNames(*header)

        var beanWrapperFieldeSetMapper = BeanWrapperFieldSetMapper<InputCsv>()
        beanWrapperFieldeSetMapper.setTargetType(InputCsv::class.java)
        // 項目名を識別するためのマッチング文字数を設定（デフォルトが5文字）
        // 例えば、「trackingNumber」と「trackID」という項目名がある場合、「tracki」まで合致している
        // そのため、当項目値は「7」としないと両項目が識別できない
        beanWrapperFieldeSetMapper.setDistanceLimit(5)

        var defaultLineMapper = DefaultLineMapper<InputCsv>()
        defaultLineMapper.setLineTokenizer(delimitedLineTokenizer)
        defaultLineMapper.setFieldSetMapper(beanWrapperFieldeSetMapper)

        reader.setLineMapper(defaultLineMapper)

        return reader
    }
}
