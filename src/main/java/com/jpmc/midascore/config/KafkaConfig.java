package com.jpmc.midascore.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.ProducerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

//    @Value("${kafka.default.bootstrap-servers}")
//    private String defaultBootstrapServers;
//
//    @Bean
//    public ProducerFactory<String, String> producerFactory() {
//        String topic = System.getProperty("kafka.topic", "default-topic");
//        String bootstrapServers = resolveBootstrapServers(topic);
//
//        Map<String, Object> configProps = new HashMap<>();
//        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
//        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//
//        return new DefaultKafkaProducerFactory<>(configProps);
//    }
//
//    private String resolveBootstrapServers(String topic) {
//        if (topic.toLowerCase().contains("test") && isLocalKafkaRunning("localhost", 9092)) {
//            return "localhost:9092";
//        }
//        return defaultBootstrapServers;
//    }
//
//    private boolean isLocalKafkaRunning(String host, int port) {
//        try (Socket socket = new Socket()) {
//            socket.connect(new InetSocketAddress(host, port), 500);
//            return true;
//        } catch (IOException e) {
//            return false;
//        }
//    }
}