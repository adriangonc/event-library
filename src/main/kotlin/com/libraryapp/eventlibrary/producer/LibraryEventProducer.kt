package com.libraryapp.eventlibrary.producer

import com.libraryapp.eventlibrary.domain.LibraryEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import java.util.*

@Component
class LibraryEventProducer {
    @Autowired
    constructor(kafkaTemplate: KafkaTemplate<Int, String>) {
        this.kafkaTemplate = kafkaTemplate
    }

    var kafkaTemplate: KafkaTemplate<Int, String>? = null;
    val topic: String = "library-events"

    public fun sendLibraryEvent(libraryEvent: LibraryEvent): Unit? {
        var key = libraryEvent.libraryEventId
        var value = libraryEvent.toString()

        val msg = Optional.of(value)
        return if (msg.isPresent) {
            kafkaTemplate?.sendDefault(key, value)?.addCallback({
                handleSuccess(value, it)
            }, {
                handleError(value, it)
            })

        } else {
            println("Error, invalid message!")
        }

    }

    private fun handleError(value: String, it: Throwable) {
        println(
            "Unable to send message=["
                    + value + "] due to : " + it.message
        )
    }

    private fun handleSuccess(
        value: String,
        it: SendResult<Int, String>?
    ) {
        println(
            "Sent message=[" + value +
                    "] with offset=[" + it!!.recordMetadata.offset() + "]"
        )
    }

}