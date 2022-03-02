package com.bank.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bank.entity.Yanki;
import com.bank.servicedb.YankiServicedb;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accountyunki")
public class YankiController {

	private final YankiServicedb serviceYunki;

	@PostMapping
	public Mono<Yanki> save(@RequestBody Yanki yanki) {
		return serviceYunki.create(yanki);
	}

	/*
	 * @PostMapping("/probando/{yunki}") public void saveprueba (@PathVariable
	 * String yunki){ serviceYunki.createssss(yunki); }
	 */

	@PutMapping("/{idyunki}")
	public Mono<Yanki> update(@RequestBody Yanki yanki, @PathVariable String idyunki) {

		return serviceYunki.update(yanki, idyunki);
	}

	@DeleteMapping("/{idyunki}")
	public Mono<Void> delete(@PathVariable String idyunki) {

		return serviceYunki.deleteById(idyunki);
	}

	@GetMapping("/{idyunki}")
	public Mono<Yanki> findById(@PathVariable String idyunki) {

		return serviceYunki.findById(idyunki);
	}

	@GetMapping
	public Flux<Yanki> findAll() {

		return serviceYunki.findAll();
	}

	/**
	 * Pruebas con redis
	 */
	@GetMapping("/redis")
	public Flux<Yanki> findAllRedis() {
		return serviceYunki.findAll();
	}

	@GetMapping("/redis/{key}")
	public Mono<Yanki> findByKey(@PathVariable("key") String key) {
		return serviceYunki.findByKey(key);
	}

	@PostMapping("/redis/{key}")
	public Mono<Boolean> save(@PathVariable("key") String key, @RequestBody Yanki yanki) {
		return serviceYunki.saveRedis(key, yanki);

	}

	@DeleteMapping("/redis/{key}")
	public Mono<Boolean> deleteRedis(String key) {
		return serviceYunki.delete(key);
	}

}
