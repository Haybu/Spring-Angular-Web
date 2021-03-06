package io.agilehandy.reservation.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@ToString
public class ReservationRequest {

	private Long flightId;

	private List<Passenger> passengers;

	private Address address;

	private String confirmation;

}
