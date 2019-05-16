package com.example.conf

import org.springframework.stereotype.Component
import org.springframework.boot.context.properties.ConfigurationProperties

@Component("csvProperties")
@ConfigurationProperties(prefix = "csv")
class CsvProperties {
	lateinit var directory: String
}