package com.minejava.collectservice.message;

import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.minejava.utilservice.event.DataEvent;
import com.minejava.utilservice.event.EventType;
import com.minejava.utilservice.payload.UserPayload;


@Configuration
public class MessageSupplier {
    private final Logger LOG = LoggerFactory.getLogger(MessageSupplier.class);

    private final Boolean produce;

    public MessageSupplier(@Value("${spring.cloud.stream.producer.produce}") Boolean produce) {
        this.produce = produce;
    }

    @Bean
    public Supplier<DataEvent<String, UserPayload>> userProducer() {
        return () -> {
            if(produce) {
                return getUserPayload();
                //return MessageBuilder.withPayload(getUserPayload()).build();
            }
            return null;
        };
    }

    private DataEvent<String, UserPayload> getUserPayload() {
        UserPayload user = new UserPayload("Crease", "namodel@gmail.com", "Maestro");
        LOG.info("Logging from gerUserPayLoad function...");
        return new DataEvent<>(EventType.CREATE, null, user);
    }
}
