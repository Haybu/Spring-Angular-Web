/*
 * Copyright 2012-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.agilehandy.reservation.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

/**
 * @author Haytham Mohamed
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Reservation {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "confirmation_number")
	private String confirmationNumber;

	@Column(nullable = true)
	private String origin;

	@Column(nullable = true)
	private String destination;

	@Column(nullable = true)
	private LocalDate departure;

	@Column(nullable = true)
	private LocalDate arrival;

	@Column(name = "flight_number")
	private String flightNumber;

	@Column(name = "flight_id", nullable = false)
	private Long flightId;

	@OneToMany(mappedBy = "reservation", cascade = CascadeType.REFRESH, orphanRemoval = true)
	private List<Passenger> passengers;

}
