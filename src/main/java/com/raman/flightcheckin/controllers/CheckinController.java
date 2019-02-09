package com.raman.flightcheckin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.raman.flightcheckin.integration.ReservationRestClient;
import com.raman.flightcheckin.integration.dto.Reservation;
import com.raman.flightcheckin.integration.dto.ReservationUpdateRequest;

@Controller
public class CheckinController {

	@Autowired
	ReservationRestClient restClient;
	
	@RequestMapping("/showStartCheckin")
	public String showStartCheckin() {
		return "startCheckin";
	}

	@RequestMapping("/startCheckin")
	public String startCheckin(@RequestParam("reservationId") Long reservationId, ModelMap modelMap) {
		Reservation reservation = restClient.findReservation(reservationId);
		modelMap.addAttribute("reservation", reservation);
		return "displayReservationDetails";
	}
	
	@RequestMapping("/completeCheckin")
	public String completeCheckIn(@RequestParam("reservationId") Long reservationId, @RequestParam("noOfBags") int noOfBags) {
		ReservationUpdateRequest request = new ReservationUpdateRequest();
		request.setCheckedIn(true);
		request.setId(reservationId);
		request.setNoOfBags(noOfBags);
		restClient.updateReservation(request);
		return "checkinConfirmation";
	}
	
}
