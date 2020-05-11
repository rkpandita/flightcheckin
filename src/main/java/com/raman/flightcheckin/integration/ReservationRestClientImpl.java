package com.raman.flightcheckin.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.raman.flightcheckin.integration.dto.Reservation;
import com.raman.flightcheckin.integration.dto.ReservationUpdateRequest;

@Service
public class ReservationRestClientImpl implements ReservationRestClient {

	private static final String RESERVATION_REST_URL = "http://flight-reservation-service/flightreservation/reservations/";
	// private static final String RESERVATION_REST_URL = "http://localhost:9090/flightreservation/reservations/";
	
	@Autowired
	private RestTemplate restTemplate;
	
	/*
	 * @Autowired private WebClient.Builder webClientBuilder;
	 */ 
	
	@Override
	public Reservation findReservation(Long id) {
		
		// Creates unnecessary objects every time this method findReservation() is called. (Avoid this)
		// RestTemplate restTemplate = new RestTemplate();
		
		// Singleton object 'RestTemplate' created using @Bean in the Main() class and used here using @Autowired
		return restTemplate.getForObject(RESERVATION_REST_URL + id, Reservation.class);

		/*
		 * As of 5.0, the non-blocking, reactive {@code * org.springframework.web.reactive.client.WebClient} 
		 * offers a modern alternative to the {@code RestTemplate} with efficient support for both sync
		 * and async, as well as streaming scenarios. The {@code RestTemplate} will be deprecated in a future 
		 * version and will not have major new features added going forward.
		 */
		// return webClientBuilder.build()
		//				.get()
		//				.uri(RESERVATION_REST_URL + id)
		//				.retrieve()
		//				.bodyToMono(Reservation.class) 
		//				.block(); // Async call, wait further execution till the object is fetched
	}

	@Override
	public Reservation updateReservation(ReservationUpdateRequest request) {
		return restTemplate.postForObject(RESERVATION_REST_URL, request, Reservation.class);
	}

}
