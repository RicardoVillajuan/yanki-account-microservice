package com.bank.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.bank.entity.Yanki;

import reactor.core.publisher.Mono;

public interface YankiRepository extends ReactiveMongoRepository<Yanki, String>{

	Mono<Yanki> findByPhonenumber(String numberphone);
}
