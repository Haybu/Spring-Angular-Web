package io.springframework.flights;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(FlightController.class)
@TestPropertySource(properties = {
        "eureka.client.register-with-eureka=false",
        "eureka.client.fetch-registry=false",
        "spring.cloud.config.enabled=false"
})
public class FlightControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private FlightRepository repository;

    Flight flight;
    Date arrivalDate = new Date();
    Date departureDate = new Date();

    @Before
    public void setup() {
        flight = new Flight();
        flight.setAirline("Delta");
        flight.setCapacity(150);
        flight.setOrigin("X");
        flight.setDestination("Y");
        flight.setNbr("FL000");
        flight.setDeparture(departureDate);
        flight.setArrival(arrivalDate);
    }

    @Test
    public void testDatedLegs() throws Exception {
        given(repository.findFlightsByCustomQueryDated(anyString(), anyString(), (Date)any(), (Date)any()))
                .willReturn(Arrays.asList(flight));

        mockMvc.perform(MockMvcRequestBuilders.get("/datedlegs")
                  .param("origin", "X")
                  .param("destination", "Y")
                  .param("mindate", "2018-05-05")
                  .param("maxdate", "2018-05-20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].airline").value("Delta"))
                .andExpect(jsonPath("$.[0].capacity").value(150))
                .andExpect(jsonPath("$.[0].origin").value("X"))
                .andExpect(jsonPath("$.[0].destination").value("Y"))
        ;
    }

    @Test
    public void testLegs() throws Exception {
        given(repository.findByOriginAndDestination(anyString(), anyString()))
                .willReturn(Arrays.asList(flight));

        mockMvc.perform(MockMvcRequestBuilders.get("/legs")
                .param("origin", "X")
                .param("destination", "Y"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].airline").value("Delta"))
                .andExpect(jsonPath("$.[0].capacity").value(150))
                .andExpect(jsonPath("$.[0].origin").value("X"))
                .andExpect(jsonPath("$.[0].destination").value("Y"))
        ;
    }

}