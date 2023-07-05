package app;

import org.slf4j.*;
import org.springframework.scheduling.annotation.*;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.web.client.*;
import org.springframework.boot.web.client.*;
import org.springframework.context.annotation.*;

@SpringBootApplication
@EnableScheduling
public class RestConsumerApplication {

    private static final Logger logger = LoggerFactory.getLogger(RestConsumerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(RestConsumerApplication.class, args);
	}

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Scheduled(fixedRate = 2000)
    public void getRandomQuote() {
        RestTemplate restTemplate = new RestTemplate();
        Quote quote = restTemplate.getForObject(
            "http://localhost:8080/api/random", Quote.class);
        logger.info(quote.toString());
    }

    @Bean
    public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
        return args -> {
            Quote quote = restTemplate.getForObject(
                "http://localhost:8080/api/random", Quote.class);
            logger.info(quote.toString());
        };
    }

}
