package com.example.webfluxstudy;

import com.example.webfluxstudy.sample.SampleService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WebfluxStudyApplication {

  public static void main(String[] args) {
    SpringApplication.run(WebfluxStudyApplication.class, args);
  }

  @Bean
  public ApplicationRunner runner(SampleService sampleService) {
    return args -> {
      String name = sampleService.getSample().block();
      System.out.println("name => " + name);

      sampleService.HotSequenceTest();
      sampleService.ColdSequenceTest();
    };
  }
}
