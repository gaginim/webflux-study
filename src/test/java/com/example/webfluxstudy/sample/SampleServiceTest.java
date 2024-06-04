package com.example.webfluxstudy.sample;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

class SampleServiceTest {

  @Test
  void MonoThenTest() {
    first_sequence().then(second_sequence()).subscribe();
  }

  Mono<Void> first_sequence() {
    System.out.println("------------first_sequence------------");
    return Mono.create(
        sink -> {
          for (int i = 0; i < 10; i++) {
            System.out.println("first_sequence: " + i);
            if (i == 5) throw new RuntimeException("just test");
          }
        });
  }

  Mono<Void> second_sequence() {
    System.out.println("------------second_sequence------------");
    return Mono.create(
        sink -> {
          System.out.println("------second_sequence mono start------");
          //          sleep_mono().block();
          //          sink.success();
          System.out.println("------second_sequence mono end------");
        });
  }

  Mono<Void> sleep_mono() {
    System.out.println("sleep_mono");
    return Mono.empty();

    //    return Mono.delay(Duration.ofSeconds(10L));
  }
}
