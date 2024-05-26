package com.example.webfluxstudy.sample;

import ch.qos.logback.core.util.TimeUtil;
import io.netty.util.internal.ConstantTimeUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Arrays;
import java.util.stream.Stream;

@Service
public class SampleService {

  public Mono<String> getSample() {
    return Mono.just("han");
  }

  public void HotSequenceTest() throws InterruptedException {

    System.out.println("-- HotSequenceTest --");

    Flux<String> concertProcess =
        Flux.fromStream(Stream.of("1 part", "2 part", "3 part", "4 part", "5 part"))
            .delayElements(Duration.ofSeconds(1))
            .share();

    concertProcess.subscribe(data -> System.out.println("jitoon is " + data));

    Thread.sleep(2500);

    concertProcess.subscribe(data -> System.out.println("hyunki is " + data));

    Thread.sleep(2500);
  }

  public void ColdSequenceTest() throws InterruptedException {

    System.out.println("-- ColdSequenceTest --");

    Flux<String> concertProcess =
        Flux.fromIterable(Arrays.asList("1 part", "2 part", "3 part")).map(String::toUpperCase);

    concertProcess.subscribe(data -> System.out.println("jitoon is " + data));
    concertProcess.subscribe(data -> System.out.println("hyunki is " + data));
  }
}
