package comgithub.tobi6112.springbootkafkaexample;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootTest
class KafkaListenersTest {

  @Autowired
  KafkaListeners kafkaListeners;

  @Autowired
  KafkaTemplate<Object, Object> kafkaTemplate;

  @BeforeEach
  void setUp() {
    kafkaListeners.users.clear();
    kafkaListeners.userIds.clear();
  }

  @Test
  void userIdListener() {
    // Given
    var userId = UUID.randomUUID().toString();

    // When
    kafkaTemplate.send("user-id", userId);

    // Then
    await().atMost(15, TimeUnit.SECONDS).until(() -> kafkaListeners.userIds.size() > 0);
    assertThat(kafkaListeners.userIds).containsExactlyInAnyOrder(userId);
  }

  @Test
  void userListener() {
    // Given
    var user = """
        {
          "name": "Carl",
          "birthday": "2000-01-01",
          "hobbies": [
            "football",
            "coding"
          ]
        }
        """;

    // When
    kafkaTemplate.send("user", user);

    // Then
    var expectedUser = new User("Carl",
        LocalDate.of(2000, 1, 1),
        Set.of("football", "coding"));

    await().atMost(15, TimeUnit.SECONDS).until(() -> kafkaListeners.users.size() > 0);
    assertThat(kafkaListeners.users).containsExactlyInAnyOrder(expectedUser);
  }
}