package com.raman.flightcheckin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
@EnableHystrixDashboard
public class FlightcheckinApplication {
	
	@Bean
	@LoadBalanced
	//  To create Singleton RestTemplate object, which is autowired and consumed/used 
	//  in ReservationRestClientImpl REST calls
	public RestTemplate getRestTemplate() {
		
		 // Added timeout if the Service Request thread takes more than 3 secs to respond, this is to improve performance
		/*
		 * HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new
		 * HttpComponentsClientHttpRequestFactory();
		 * clientHttpRequestFactory.setConnectTimeout(3000);  
		 * return new RestTemplate(clientHttpRequestFactory);
		 */
		
		return new RestTemplate();
	}
	
	// As RestTemplate is going to get deprecated, use the WebClient.Builder way of getting resources
	/*
	 * @Bean
	 * @LoadBalanced 
	 * public WebClient.Builder getWebClientBuilder() { 
	 * return WebClient.builder(); 
	 * }
	 */

	public static void main(String[] args) {
		SpringApplication.run(FlightcheckinApplication.class, args);
	}

}

