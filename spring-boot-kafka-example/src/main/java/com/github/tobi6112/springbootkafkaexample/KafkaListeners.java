package com.github.tobi6112.springbootkafkaexample;

import java.util.HashSet;
import java.util.Set;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {
  private final static Logger log = LoggerFactory.getLogger(KafkaListeners.class);

  public Set<String> userIds = new HashSet<>();
  public Set<User> users = new HashSet<>();

  @KafkaListener(topics = "user-id")
  public void userIdListener(ConsumerRecord<String, String> record) {
    log.info("Received a Message in user-id topic: {}", record);
    this.userIds.add(record.value());
  }

  @KafkaListener(topics = "user")
  public void userListener(ConsumerRecord<String, User> record) {
    log.info("Received a Message in user topic: {}", record);
    this.users.add(record.value());
  }
}
