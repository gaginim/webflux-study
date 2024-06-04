package com.example.webfluxstudy;

import com.example.webfluxstudy.sample.SampleService;
import java.util.Arrays;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@SpringBootApplication
public class WebfluxStudyApplication {

  public static void main(String[] args) {
    SpringApplication.run(WebfluxStudyApplication.class, args);
  }

  private static void threadTest() {
    Flux.fromIterable(Arrays.asList(1, 2, 3, 4))
        .map(it -> it * it)
        .log()
        .publishOn(Schedulers.boundedElastic())
        .map(it -> it * it)
        .log()
        .subscribe();
  }

  @Bean
  public ApplicationRunner runner(SampleService sampleService) {
    return args -> {
      //      String name = sampleService.getSample().block();
      //      System.out.println("name => " + name);

      //      sampleService.tupleUtilsTest().subscribe();

      // sampleService.switchIfEmptyTest().subscribe();

      //      sampleService.mainTest().subscribe();

      //      sampleService.thenManyTest1().subscribe();

      sampleService.isValid().subscribe();

      //      //      threadTest();
      //
      //      Mono.just("tommy")
      //          .log()
      //          .flatMap(t ->
      //                  Mono.just(t).zipWith(Flux.fromStream(Stream.of("yansoon",
      // "hyungki")).collectList())
      //              )
      //          .log()
      //          .
    };
  }
}
