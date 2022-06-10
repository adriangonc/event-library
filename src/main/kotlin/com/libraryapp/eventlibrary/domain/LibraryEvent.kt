package com.examplekafka.libraryeventsproducer.domain

data class LibraryEvent (
    var libraryEventId: Integer,
    var book: Book
)