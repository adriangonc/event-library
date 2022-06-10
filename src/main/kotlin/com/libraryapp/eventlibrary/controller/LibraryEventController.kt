package com.libraryapp.eventlibrary.controller

import com.libraryapp.eventlibrary.domain.LibraryEvent
import com.libraryapp.eventlibrary.producer.LibraryEventProducer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class LibraryEventController {
    private var library: LibraryEventProducer

    @Autowired
    constructor(library: LibraryEventProducer){
        this.library = library
    }

    @PostMapping("v1/libraryevent")
    fun libraryEvent(@RequestBody libraryEvent: LibraryEvent): ResponseEntity<LibraryEvent> {
        //chamar kafka producer
        return try {
            library.sendLibraryEvent(libraryEvent)
            ResponseEntity.status(HttpStatus.CREATED).body(libraryEvent)
        } catch (e: Exception){
            println(e.message)
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)
        }
    }


}