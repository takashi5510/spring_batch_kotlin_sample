package com.example.chunk.csv

import java.io.IOException
import java.io.Writer
import org.springframework.batch.item.file.FlatFileHeaderCallback

class CsvHeader(header: String) : FlatFileHeaderCallback {

    private var header: String = ""

    init {
        this.header = header
    }

    override fun writeHeader(writer: Writer) {
        writer.write(header)
    }
}