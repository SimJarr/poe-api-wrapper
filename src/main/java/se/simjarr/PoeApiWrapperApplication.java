package se.simjarr;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import se.simjarr.model.ApiDataFetch;

@SpringBootApplication
public class PoeApiWrapperApplication {

	public static void main(String[] args) {
		SpringApplication.run(PoeApiWrapperApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(ApplicationContext context) {
		return args -> {
			ApiDataFetch apiDataFetch = context.getBean(ApiDataFetch.class);
		};
	}
}
