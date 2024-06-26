package com.example.webfluxstudy.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Table("user")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

  @Column("num")
  @Id private Long num;

  @Column("name")
  private String name;

  @Builder
  private User(Long num, String name) {
    this.num = num;
    this.name = name;
  }
}
