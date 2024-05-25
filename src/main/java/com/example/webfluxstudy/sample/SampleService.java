package com.example.webfluxstudy.sample;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class SampleService {

  public Mono<String> getSample() {
    return Mono.just("han");
  }
}
