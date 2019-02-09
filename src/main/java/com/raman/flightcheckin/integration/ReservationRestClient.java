package com.raman.flightcheckin.integration;

import com.raman.flightcheckin.integration.dto.Reservation;
import com.raman.flightcheckin.integration.dto.ReservationUpdateRequest;

public interface ReservationRestClient {

	public Reservation findReservation(Long id);
	
	public Reservation updateReservation(ReservationUpdateRequest request);
	
}
