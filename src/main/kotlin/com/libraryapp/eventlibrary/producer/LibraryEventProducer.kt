package com.libraryapp.eventlibrary.producer

import com.examplekafka.libraryeventsproducer.domain.LibraryEvent
import org.apache.kafka.clients.producer.RecordMetadata
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.util.concurrent.ListenableFuture
import org.springframework.util.concurrent.ListenableFutureCallback
import reactor.core.publisher.Mono
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Future

@Component
class LibraryEventProducer {
    @Autowired
    constructor(kafkaTemplate: KafkaTemplate<Integer, String>) {
        this.kafkaTemplate = kafkaTemplate
    }

    var kafkaTemplate: KafkaTemplate<Integer, String>? = null;
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
        it: SendResult<Integer, String>?
    ) {
        println(
            "Sent message=[" + value +
                    "] with offset=[" + it!!.recordMetadata.offset() + "]"
        )
    }

}