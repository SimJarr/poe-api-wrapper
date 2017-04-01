package se.simjarr;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import se.simjarr.model.ApiDataFetch;

@Controller
@SpringBootApplication
public class PoeApiWrapperApplication {

	@RequestMapping("/")
	@ResponseBody
	String home() {
		return "Hello World!";
	}


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
