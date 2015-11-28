package ch.poinzofnoreturn.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableAutoConfiguration
@EntityScan("ch.poinzofnoreturn.server.model")
@EnableJpaRepositories("ch.poinzofnoreturn.server.repo")
public class PonrServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PonrServerApplication.class, args);
    }
}
