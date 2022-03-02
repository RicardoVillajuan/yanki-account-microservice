package com.bank.servicedb;

import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.bank.entity.Yanki;
import com.bank.model.Operation;
import com.bank.repository.YankiRepository;
import com.bank.service.IYankiService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class YankiServicedb implements IYankiService {

	private final ReactiveRedisOperations<String, Yanki> yankiOpps;
	private final KafkaTemplate<String, Yanki> kafkaTemplate;

	private final YankiRepository repoYunki;

	@Override
	public Mono<Yanki> create(Yanki yanki) {
		// TODO Auto-generated method stub
		return repoYunki.save(yanki);
	}

	@Override
	public Flux<Yanki> findAll() {
		// TODO Auto-generated method stub
		return repoYunki.findAll();
	}

	@Override
	public Mono<Yanki> update(Yanki yanki, String idyunki) {
		// TODO Auto-generated method stub
		return repoYunki.findById(idyunki).flatMap(e -> {
			yanki.setId(e.getId());
			return repoYunki.save(yanki);
		});
	}

	@Override
	public Mono<Void> deleteById(String idyunki) {
		// TODO Auto-generated method stub
		return repoYunki.deleteById(idyunki);
	}

	@Override
	public Mono<Yanki> findById(String idyunki) {
		// TODO Auto-generated method stub
		return repoYunki.findById(idyunki);
	}

	// Entidad accountYunki
	// recibimos(consumer)
	@KafkaListener(topics = "yunki")
	public void consumeMessage(Operation operation) {
		System.out.println("consumidor Yunki :" + operation.toString());
		creates(operation);
		// kafkaTemplate.send("yunkisubmit", "Enviado desde el account");
	}

	// envio
	public void creates(Operation operation) {
		Mono<Yanki> yunkiaccountsub = repoYunki.findByPhonenumber(operation.getPhonenumbersubmit());
		Mono<Yanki> yunkiaccountrec = repoYunki.findByPhonenumber(operation.getPhonenumberreceive());
		yunkiaccountsub.doOnNext(yunkisubmit -> {
			if (yunkisubmit.getBalance() < operation.getBalance()) {
				throw new RuntimeException("No cuenta con el saldo suficiente");
			}
		}).flatMap(yunkisubmit -> {
			return yunkiaccountrec.map(yunkireceive -> {
				yunkisubmit.setBalance(yunkisubmit.getBalance() - operation.getBalance());
				yunkireceive.setBalance(yunkireceive.getBalance() + operation.getBalance());

				update(yunkisubmit, yunkisubmit.getId()).subscribe();
				update(yunkireceive, yunkireceive.getId()).subscribe();
				/*
				 * try { kafkaTemplate.send("enviocuatro", yunki).get(); } catch
				 * (InterruptedException | ExecutionException e) { // TODO Auto-generated catch
				 * block e.printStackTrace(); }
				 */
				 System.out.println("========================="+yunkireceive);

				return yunkireceive;
			});
		}).subscribe();
	}

	
	
	
	
	
	public Flux<Yanki> findAllRedis() {
		return yankiOpps.keys("*").flatMap(yankiOpps.opsForValue()::get);
	}

	
	public Mono<Boolean> saveRedis(String key, Yanki yanki) {
		return yankiOpps.opsForValue().set(key, yanki);
	}

	
	public Mono<Yanki> findByKey(String key) {
		return yankiOpps.opsForValue().get(key);
	}

	
	public Mono<Boolean> delete(String key) {
		return yankiOpps.opsForValue().delete(key);
	}

}
