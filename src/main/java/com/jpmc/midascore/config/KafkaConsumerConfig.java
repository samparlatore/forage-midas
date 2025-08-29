package com.jpmc.midascore.config;

import com.jpmc.midascore.foundation.Transaction;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers:localhost:9092}")
    private String bootstrapServers;

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerConfig.class);

    private boolean isValidKafkaAddress(String address) {
        return address.matches("^[a-zA-Z0-9._-]+:\\d{2,5}$");
    }

    @Bean
    public ConsumerFactory<String, Transaction> consumerFactory() {
        JsonDeserializer<Transaction> deserializer = new JsonDeserializer<>(Transaction.class);
        deserializer.addTrustedPackages("*");

        Map<String, Object> props = new HashMap<>();
//        props.put("bootstrap.servers", Objects.requireNonNullElse(bootstrapServers, "localhost:9092"));
        if (isValidKafkaAddress(bootstrapServers))
            props.put("bootstrap.servers", bootstrapServers);
        else
            props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "midas-core-group");
        props.put("key.deserializer", StringDeserializer.class);
        props.put("value.deserializer", deserializer);

        return new org.springframework.kafka.core.DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), deserializer);
    }

//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, Transaction> kafkaListenerContainerFactory() {
//        if (!bootstrapServers.matches("^[a-zA-Z0-9._-]+:\\d{2,5}$")) {
//            LoggerFactory.getLogger(KafkaConsumerConfig.class).info("Kafka not available — skipping listener container factory setup.");
//            return null; // or throw new BeanNotAvailableException if you want to be explicit
//        }
//
//        ConcurrentKafkaListenerContainerFactory<String, Transaction> factory = new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory());
//        return factory;
//    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Transaction> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Transaction> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory()); // This now refers to the bean above
        return factory;
    }

//    String bootstrap = "localhost:" + findLocalKafkaPort().orElse(9092)"localhost:" + findLocalKafkaPort().orElse(9092);
//    private Optional<Integer> findLocalKafkaPort() {
//        for (int port = 9092; port <= 70000; port++) {
//            try (Socket socket = new Socket()) {
//                socket.connect(new InetSocketAddress("localhost", port), 200);
//                return Optional.of(port);
//            } catch (IOException ignored) {}
//        }
//        return Optional.empty();
//    }

    private boolean isKafkaAvailable() {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress("localhost", 9092), 200);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

}