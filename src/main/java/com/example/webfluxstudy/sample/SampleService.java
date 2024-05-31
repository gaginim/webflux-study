package com.example.webfluxstudy.sample;

import com.example.webfluxstudy.domain.entity.User;
import com.example.webfluxstudy.domain.repository.UserRepository;
import java.time.Duration;
import java.util.Arrays;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.function.TupleUtils;

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

  // https://d2.naver.com/helloworld/2771091
  public Mono<String> tupleUtilsTest() {

    return Mono.just("tommy")
        .flatMap(
            t ->
                Mono.just(t)
                    .log()
                    .zipWith(userRepository.findAll().map(User::getName).collectList())
                    .log())
        .log()
        .flatMap(
            TupleUtils.function(
                (name, users) -> {
                  System.out.println("name => " + name);
                  users.forEach(user -> System.out.println("user memer => " + user));
                  return Mono.just(name);
                }))
        .log();
  }

  public Flux<Integer> switchIfEmptyTest() {
    return Flux.just(1, 2, 3)
        .filter(i -> i > 1)
        .doOnNext(data -> System.out.println("data => " + data))
        .doOnError(error -> System.out.println("error => " + error.toString()))
        .map(i -> i * i)
        .switchIfEmpty(Mono.error(new RuntimeException("there is no data")))
        .log();
  }

  public Flux<Integer> switchIfEmptyTest2() {
    return Flux.just(1, 2, 3)
        .filterWhen(i -> getName(i).map(name -> name.startsWith("tommy")).log())
        .map(i -> i * i)
        .log();
  }

  public Mono<Boolean> mainTest() {
    return Mono.empty()
        .then(Mono.defer(() -> subTest2()))
        .log()
        .then(Mono.defer(() -> subTest3()))
        .log()
        .then(Mono.defer(() -> subTest4()))
        .log();
  }

  public Mono<Boolean> subTest1() {
    return Mono.just(Boolean.TRUE).log();
  }

  public Mono<Boolean> subTest2() {
    return Mono.just(Boolean.TRUE).log();
  }

  public Mono<Boolean> subTest3() {
    return Mono.error(new RuntimeException("there is no data"));
  }

  public Mono<Boolean> subTest4() {
    return Mono.just(Boolean.TRUE).log();
  }

  public Mono<String> getName(Integer i) {
    if (i > 1) {
      return Mono.just("tommy_ " + i);
    } else {
      return Mono.just("hyunki_ " + i);
    }
  }
}
