package com.example.webfluxstudy;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@Log4j2
public class ZipSampleApplication {

  public static void main(String[] args) {

    zipWhenTest();
  }

  public static void zipWhenTest() {
    Mono.empty()
        .defaultIfEmpty("han")
        .zipWhen(newName -> Mono.just(newName + "_sub"))
        .log()
        .doOnNext(data -> log.info("data => " + data))
        .map(tuple -> String.format("%s , %s", tuple.getT1(), tuple.getT2()))
        .doOnNext(data -> log.info("data => " + data))
        .subscribe();
  }
}
