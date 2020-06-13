package ru.kphu.bot;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.var;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
public class BotApplication {
	@Bean
	public ObjectMapper myObjectMapper() {
		var objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return objectMapper;
	}

	@Bean
	@Profile({"dis","tel"})
	public RestOperations restOperations() {
		RestTemplate rest = new RestTemplate();
		rest.getMessageConverters().add(0, mappingJacksonHttpMessageConverter());
		return rest;
	}

	@Bean
	public MappingJackson2HttpMessageConverter mappingJacksonHttpMessageConverter() {
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setObjectMapper(myObjectMapper());
		return converter;
	}

	public static void main(String[] args) {
		ApiContextInitializer.init();
		SpringApplication.run(BotApplication.class, args);

	}

}
