package com.raman.flightcheckin.controllers;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import com.raman.flightcheckin.integration.ReservationRestClient;
import com.raman.flightcheckin.integration.dto.Flight;
import com.raman.flightcheckin.integration.dto.Passenger;
import com.raman.flightcheckin.integration.dto.Reservation;
import com.raman.flightcheckin.integration.dto.ReservationUpdateRequest;

@Controller
public class CheckinController {

	@Autowired
	ReservationRestClient restClient;
	
	@RequestMapping(value = "/showStartCheckin", method = RequestMethod.GET)
	public String showStartCheckin() {
		return "startCheckin";
	}

	@RequestMapping(value = "/startCheckin", method = RequestMethod.GET)
	@HystrixCommand(fallbackMethod = "startFallbackCheckin",
		commandProperties = {
				@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
				@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
				@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
				@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
		}
	)
	public String startCheckin(@RequestParam("reservationId") Long reservationId, ModelMap modelMap) {
		Reservation reservation = restClient.findReservation(reservationId);
		modelMap.addAttribute("reservation", reservation);
		return "displayReservationDetails";
	}
	
	public String startFallbackCheckin(@RequestParam("reservationId") Long reservationId, ModelMap modelMap) {
		
		Flight flight = new Flight();
		
		flight.setId(0L);
		flight.setFlightNumber("No Flight found");
		flight.setOperatingAirlines("No Airlines found");
		flight.setDepartureCity("Departure City not found");
		flight.setArrivalCity("Arrival City not found");
		flight.setDateOfDeparture(new Date(0));
		flight.setEstimatedDepartureTime(new Timestamp(0));
		
		Passenger passenger = new Passenger();
		
		passenger.setId(0L);
		passenger.setFirstName("First name not available");
		passenger.setLastName("Last name not available");
		passenger.setMiddleName("Middle name not available");
		passenger.setEmail("Email not available");
		passenger.setPhone("Phone number not available");
		
		Reservation reservation = new Reservation();
		
		reservation.setId(0L);
		reservation.setCheckedIn(false);
		reservation.setNoOfBags(0);
		reservation.setPassenger(passenger);
		reservation.setFlight(flight);
		
		modelMap.addAttribute("reservation", reservation);
		return "displayReservationDetails";
	}
	
	@RequestMapping(value = "/completeCheckin", method = RequestMethod.POST)
	public String completeCheckIn(@RequestParam("reservationId") Long reservationId, @RequestParam("noOfBags") int noOfBags) {
		ReservationUpdateRequest request = new ReservationUpdateRequest();
		request.setCheckedIn(true);
		request.setId(reservationId);
		request.setNoOfBags(noOfBags);
		restClient.updateReservation(request);
		return "checkinConfirmation";
	}
	
}
