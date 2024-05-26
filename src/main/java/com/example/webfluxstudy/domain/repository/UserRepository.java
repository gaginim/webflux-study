package com.example.webfluxstudy.domain.repository;

import com.example.webfluxstudy.domain.entity.User;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface UserRepository extends R2dbcRepository<User, Long> {}
