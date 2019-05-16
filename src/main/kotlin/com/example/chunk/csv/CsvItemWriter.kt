package com.example.chunk.csv

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.ItemWriter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import jp.co.fujisan.entity.csvUpload.JppostCsv
import jp.co.fujisan.repository.csvUpload.JppostCsvRepository

@Component("csvItemWriter")
class CsvUploadItemWriter : ItemWriter<JppostCsv> {

    public var log = LoggerFactory.getLogger(CsvUploadItemWriter::class.java)

    @Autowired
    lateinit var repository: JppostCsvRepository

    override fun write(items: MutableList<out JppostCsv>) {
        items.forEach {
            if (repository.countByExistsRecords(it.shipmentID, it.shipmentDate) == 0) {
                repository.insert(it)
                log.info("CsvUploadItemWriter inserted record. shipmentID: [${it.shipmentID}, shipmentDate: [${it.shipmentDate}]]")
            } else {
                log.info("CsvUploadItemWriter skipping record. shipmentID: [${it.shipmentID}, shipmentDate: [${it.shipmentDate}]]")
            }
        }
    }
}
