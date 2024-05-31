package com.example.webfluxstudy.config;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.ReactiveAuditorAware;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import reactor.core.publisher.Mono;

@Configuration(proxyBeanMethods = false)
@EnableTransactionManagement
@EnableR2dbcAuditing
@EnableR2dbcRepositories(basePackages = {"com.example.webfluxstudy.**.repository"})
public class R2dbcConfig {

  @Bean
  public ConnectionFactory connectionFactory() {
    String url =
        "r2dbc:mysql://admin:password@localhost/cloud_billing_dev?tcpKeepAlive=true&useServerPrepareStatement=true&tcpNoDelay=true&ssl=false";
    return ConnectionFactories.get(url);
  }

  @Bean
  ReactiveAuditorAware<String> auditorAware() {
    return () -> Mono.just("system");
  }
}
