package com.bank.service;

import com.bank.entity.Yanki;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IYankiService {

	Mono<Yanki> create(Yanki yanki);
	
	Flux<Yanki> findAll();
	
	Mono<Yanki> update(Yanki yanki,String idyunki);
	
	Mono<Void> deleteById(String idyunki);
	
	Mono<Yanki> findById(String idyunki);
}
