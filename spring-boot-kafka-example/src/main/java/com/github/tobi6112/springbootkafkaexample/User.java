package com.github.tobi6112.springbootkafkaexample;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.util.Set;

public record User(
    @JsonProperty("name") String name,
    @JsonProperty("birthday") LocalDate birthday,
    @JsonProperty("hobbies") Set<String> hobbies
) {}
