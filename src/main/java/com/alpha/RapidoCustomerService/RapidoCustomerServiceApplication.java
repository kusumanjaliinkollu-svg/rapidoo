package com.alpha.RapidoCustomerService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.client.RestTemplate;

import com.alpha.RapidoCustomerService.dto.FareDto;
import com.alpha.RapidoCustomerService.dto.RideRequestDto;

import aj.org.objectweb.asm.TypeReference;
import tools.jackson.databind.ObjectMapper;

@SpringBootApplication
public class RapidoCustomerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RapidoCustomerServiceApplication.class, args);
	}
	
	@Autowired
	private RedisConnectionFactory redisConnectionFactory;
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
	@Bean
	public RedisTemplate<String, Map<String, Object>> getRedisTemplate(){
		 RedisTemplate<String, Map<String, Object>> redisTemplate = new RedisTemplate<>();
		 redisTemplate.setConnectionFactory(redisConnectionFactory);
		 redisTemplate.setKeySerializer(new StringRedisSerializer());
		 
		 redisTemplate.setKeySerializer(RedisSerializer.string());
		 redisTemplate.setValueSerializer(RedisSerializer.json());
		 redisTemplate.setHashKeySerializer(RedisSerializer.string());
		 redisTemplate.setHashValueSerializer(RedisSerializer.json());
		 redisTemplate.afterPropertiesSet();
		 
		return redisTemplate ;
	}
	
	@Bean
	public RedisTemplate<String, String> geoRedisTemplate() {

	    RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();

	    redisTemplate.setConnectionFactory(redisConnectionFactory);

	    StringRedisSerializer serializer = new StringRedisSerializer();

	    redisTemplate.setKeySerializer(serializer);
	    redisTemplate.setValueSerializer(serializer);
	    redisTemplate.setHashKeySerializer(serializer);
	    redisTemplate.setHashValueSerializer(serializer);

	    redisTemplate.afterPropertiesSet();

	    return redisTemplate;
	}
	
	@Bean
	public RedisTemplate<String, RideRequestDto> rideRequestRedisTemplate() {

	    RedisTemplate<String, RideRequestDto> template = new RedisTemplate<>();

	    template.setConnectionFactory(redisConnectionFactory);

	    template.setKeySerializer(new StringRedisSerializer());
	    template.setValueSerializer(RedisSerializer.json());

	    template.afterPropertiesSet();

	    return template;
	}
}
