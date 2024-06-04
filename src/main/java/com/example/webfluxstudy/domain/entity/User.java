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

  @Id private Long id;

  @Column("name")
  private String name;

  @Builder
  private User(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public User update(String name) {
    this.name += "," + name;
    return this;
  }
}
