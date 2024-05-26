package com.example.webfluxstudy.sample;

import com.example.webfluxstudy.domain.entity.User;
import com.example.webfluxstudy.domain.repository.UserRepository;
import java.time.Duration;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SampleService {

  private final UserRepository userRepository;

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

  // 이렇게 쓰면 "stream has already been operated upon or closed" 오류 발생
  // Stream 은 오직 한번만 소비될 수 있음. 참고 : https://hamait.tistory.com/547
  public void ColdSequenceErrorTest() throws InterruptedException {

    System.out.println("-- ColdSequenceTest --");

    Flux<String> concertProcess =
        Flux.fromStream(Stream.of("1 part", "2 part", "3 part", "4 part", "5 part"))
            .map(String::toUpperCase);

    concertProcess.subscribe(data -> System.out.println("jitoon is " + data));
    //    concertProcess.subscribe(data -> System.out.println("hyunki is " + data)); // error
  }

  public void RepositoryTest() {

    Mono<User> byId = userRepository.findById(2L);

    System.out.println(byId.block().getNum());

    Mono<User> saved =
        userRepository.save(User.builder().name(UUID.randomUUID().toString()).build());

    System.out.println(saved.block().getNum());
  }
}
