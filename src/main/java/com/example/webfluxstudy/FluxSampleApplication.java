package com.example.webfluxstudy;

import java.util.List;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@Log4j2
public class FluxSampleApplication {

  public static void main(String[] args) {

    convertMonoListToFluxMapTest();
  }

  private static void convertMonoListToFluxMapTest() {
    Mono.just(List.of("tommy", "hyungki", "yansoon"))
        .flatMapIterable(name -> name)
        .map(name -> name.toUpperCase())
        .doOnNext(data -> log.info(data))
        .subscribe();
  }
}
