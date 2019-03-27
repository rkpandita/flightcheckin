package com.raman.flightcheckin.integration;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.raman.flightcheckin.integration.dto.Reservation;
import com.raman.flightcheckin.integration.dto.ReservationUpdateRequest;

@Service
public class ReservationRestClientImpl implements ReservationRestClient {

	private static final String RESERVATION_REST_URL = "http://localhost:9090/flightreservation/reservations/";

	@Override
	public Reservation findReservation(Long id) {
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(RESERVATION_REST_URL + id, Reservation.class);
	}

	@Override
	public Reservation updateReservation(ReservationUpdateRequest request) {
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.postForObject(RESERVATION_REST_URL, request, Reservation.class);
	}

}
