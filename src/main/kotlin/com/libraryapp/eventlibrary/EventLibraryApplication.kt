package com.libraryapp.eventlibrary

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class EventLibraryApplication

fun main(args: Array<String>) {
	runApplication<EventLibraryApplication>(*args)
}
