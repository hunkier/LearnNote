package cn.hunkier.spring.reactor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@SpringBootApplication
public class ReactorApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(ReactorApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Flux.range(1,6)
                .doOnRequest(n -> log.info(" Request {} number", n)) // 注意顺序造成的区别
                .publishOn(Schedulers.elastic())
                .doOnComplete(()->log.info("Publisher COMPLETE 1"))
                .map(i->{
                    log.info("Public {}, {}", Thread.currentThread(), i);
//                    return 10 / (i-3);
                    return i;
                })
                .doOnComplete(()-> log.info("Publisher COMPLETE 2"))
//                .subscribeOn(Schedulers.single())
                .onErrorResume(e->{
                    log.error("Exceptoin {}", e.toString());
                    return Mono.just(-1);
                })
                .onErrorReturn(-1)
                .subscribe(i -> {
                    log.info("Subscribe {}:{}", Thread.currentThread(), i);
                },
                        e->log.error("error {}", e.toString()),
                        () -> log.info("Subscriber COMPLETE"),
                        s -> s.request(4)

                );

        Thread.sleep(2000);
    }
}
