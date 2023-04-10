package com.AirplaneTracer.AirplaneTracer_WebApp_Middleware;

import com.AirplaneTracer.AirplaneTracer_WebApp_Middleware.model.Flight;
import com.AirplaneTracer.AirplaneTracer_WebApp_Middleware.repository.FlightRepository;
import com.AirplaneTracer.AirplaneTracer_WebApp_Middleware.repository.FlightRepositoryImplementation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AirplaneTracerWebAppMiddlewareApplicationTests {


	@Autowired
	private FlightRepository flightRepository;

	Connection connection;

	@BeforeAll
	void beforeAll() throws SQLException {
		connection = DBUtil.getConnection();
	}

	// Nothing is entered in the search menu
	@Test
	void noInformationProvidedQuery() throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Flight");
		ResultSet rs = stmt.executeQuery();
		List<Flight> flightList = processResults(rs);

		Query query = new Query("","","","","","",
				"","","");

		List<Flight> flightList2 = flightRepository.getFlights(query);

		int flightNum = 0;
		for (Flight flight: flightList){
			Assertions.assertEquals(flight.getFlightId(),flightList2.get(flightNum).getFlightId());
			Assertions.assertEquals(flight.getIcao24(),flightList2.get(flightNum).getIcao24());
			Assertions.assertEquals(flight.getDepartureAirport(),flightList2.get(flightNum).getDepartureAirport());
			Assertions.assertEquals(flight.getArrivalAirport(),flightList2.get(flightNum).getArrivalAirport());
			Assertions.assertEquals(flight.getDepartureDateTime(),flightList2.get(flightNum).getDepartureDateTime());
			Assertions.assertEquals(flight.getArrivalDateTime(),flightList2.get(flightNum).getArrivalDateTime());

			flightNum++;
		}

	}

	// An airfield is specified with the Both radio button selected (No date provided)
	@Test
	void bothAirfieldQuery() throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Flight WHERE" +
				" arrival_airport = 'Bob Hope Airport' OR departure_airport = 'Bob Hope Airport'");
		ResultSet rs = stmt.executeQuery();
		List<Flight> flightList = processResults(rs);

		Query query = new Query("Bob Hope Airport","","","Both","","",
				"","","");

		List<Flight> flightList2 = flightRepository.getFlights(query);

		int flightNum = 0;
		for (Flight flight: flightList){
			Assertions.assertEquals(flight.getFlightId(),flightList2.get(flightNum).getFlightId());
			Assertions.assertEquals(flight.getIcao24(),flightList2.get(flightNum).getIcao24());
			Assertions.assertEquals(flight.getDepartureAirport(),flightList2.get(flightNum).getDepartureAirport());
			Assertions.assertEquals(flight.getArrivalAirport(),flightList2.get(flightNum).getArrivalAirport());
			Assertions.assertEquals(flight.getDepartureDateTime(),flightList2.get(flightNum).getDepartureDateTime());
			Assertions.assertEquals(flight.getArrivalDateTime(),flightList2.get(flightNum).getArrivalDateTime());

			flightNum++;
		}
	}

	// Only an arrival airport is submitted in the top text field
	@Test
	void arrivalTop() throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Flight WHERE" +
				" arrival_airport = 'Edo Seaplane Base'");
		ResultSet rs = stmt.executeQuery();
		List<Flight> flightList = processResults(rs);
		Query query = new Query("Edo Seaplane Base","","Arrival","","","",
				"","","");

		List<Flight> flightList2 = flightRepository.getFlights(query);

		int flightNum = 0;
		for (Flight flight: flightList){
			Assertions.assertEquals(flight.getFlightId(),flightList2.get(flightNum).getFlightId());
			Assertions.assertEquals(flight.getIcao24(),flightList2.get(flightNum).getIcao24());
			Assertions.assertEquals(flight.getDepartureAirport(),flightList2.get(flightNum).getDepartureAirport());
			Assertions.assertEquals(flight.getArrivalAirport(),flightList2.get(flightNum).getArrivalAirport());
			Assertions.assertEquals(flight.getDepartureDateTime(),flightList2.get(flightNum).getDepartureDateTime());
			Assertions.assertEquals(flight.getArrivalDateTime(),flightList2.get(flightNum).getArrivalDateTime());

			flightNum++;
		}
	}

	// Only a departure airfield is specified in the top text field
	@Test
	void departureTop() throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Flight WHERE" +
				" departure_airport = 'Eppley Airfield'");
		ResultSet rs = stmt.executeQuery();
		List<Flight> flightList = processResults(rs);
		Query query = new Query("Eppley Airfield","Departure","","","","",
				"","","");

		List<Flight> flightList2 = flightRepository.getFlights(query);

		int flightNum = 0;
		for (Flight flight: flightList){
			Assertions.assertEquals(flight.getFlightId(),flightList2.get(flightNum).getFlightId());
			Assertions.assertEquals(flight.getIcao24(),flightList2.get(flightNum).getIcao24());
			Assertions.assertEquals(flight.getDepartureAirport(),flightList2.get(flightNum).getDepartureAirport());
			Assertions.assertEquals(flight.getArrivalAirport(),flightList2.get(flightNum).getArrivalAirport());
			Assertions.assertEquals(flight.getDepartureDateTime(),flightList2.get(flightNum).getDepartureDateTime());
			Assertions.assertEquals(flight.getArrivalDateTime(),flightList2.get(flightNum).getArrivalDateTime());

			flightNum++;
		}
	}

	// Only an arrival airport is submitted in the bottom text field
	@Test
	void arrivalBottom() throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Flight WHERE" +
				" arrival_airport = 'Edo Seaplane Base'");
		ResultSet rs = stmt.executeQuery();
		List<Flight> flightList = processResults(rs);
		Query query = new Query("","","","","","",
				"Edo Seaplane Base","","Arrival");

		List<Flight> flightList2 = flightRepository.getFlights(query);

		int flightNum = 0;
		for (Flight flight: flightList){
			Assertions.assertEquals(flight.getFlightId(),flightList2.get(flightNum).getFlightId());
			Assertions.assertEquals(flight.getIcao24(),flightList2.get(flightNum).getIcao24());
			Assertions.assertEquals(flight.getDepartureAirport(),flightList2.get(flightNum).getDepartureAirport());
			Assertions.assertEquals(flight.getArrivalAirport(),flightList2.get(flightNum).getArrivalAirport());
			Assertions.assertEquals(flight.getDepartureDateTime(),flightList2.get(flightNum).getDepartureDateTime());
			Assertions.assertEquals(flight.getArrivalDateTime(),flightList2.get(flightNum).getArrivalDateTime());

			flightNum++;
		}
	}

	// Only a departure airport is submitted in the bottom text field
	@Test
	void departureBottom() throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Flight WHERE" +
				" arrival_airport = 'Eppley Airfield'");
		ResultSet rs = stmt.executeQuery();
		List<Flight> flightList = processResults(rs);
		Query query = new Query("","","","","","",
				"Eppley Airfield","Departure","");

		List<Flight> flightList2 = flightRepository.getFlights(query);

		int flightNum = 0;
		for (Flight flight: flightList){
			Assertions.assertEquals(flight.getFlightId(),flightList2.get(flightNum).getFlightId());
			Assertions.assertEquals(flight.getIcao24(),flightList2.get(flightNum).getIcao24());
			Assertions.assertEquals(flight.getDepartureAirport(),flightList2.get(flightNum).getDepartureAirport());
			Assertions.assertEquals(flight.getArrivalAirport(),flightList2.get(flightNum).getArrivalAirport());
			Assertions.assertEquals(flight.getDepartureDateTime(),flightList2.get(flightNum).getDepartureDateTime());
			Assertions.assertEquals(flight.getArrivalDateTime(),flightList2.get(flightNum).getArrivalDateTime());

			flightNum++;
		}
	}

	// Only an arrival airfield is submitted in the top slot and a departure airfield in the bottom slot
	@Test
	void arrivalTopDepartureBottom() throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Flight WHERE" +
				" arrival_airport = 'Helsinki Vantaa Airport' AND departure_airport = 'Oulu Airport'");
		ResultSet rs = stmt.executeQuery();
		List<Flight> flightList = processResults(rs);

		Query query = new Query("Helsinki Vantaa Airport","","Arrival","","","",
				"Oulu Airport","Departure","");

		List<Flight> flightList2 = flightRepository.getFlights(query);

		int flightNum = 0;
		for (Flight flight: flightList){
			Assertions.assertEquals(flight.getFlightId(),flightList2.get(flightNum).getFlightId());
			Assertions.assertEquals(flight.getIcao24(),flightList2.get(flightNum).getIcao24());
			Assertions.assertEquals(flight.getDepartureAirport(),flightList2.get(flightNum).getDepartureAirport());
			Assertions.assertEquals(flight.getArrivalAirport(),flightList2.get(flightNum).getArrivalAirport());
			Assertions.assertEquals(flight.getDepartureDateTime(),flightList2.get(flightNum).getDepartureDateTime());
			Assertions.assertEquals(flight.getArrivalDateTime(),flightList2.get(flightNum).getArrivalDateTime());

			flightNum++;
		}
	}

	// A departure airfield is submitted in the top slot and an arrival airfield is submitted in the bottom slot
	@Test
	void arrivalBottomDepartureTop() throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Flight WHERE" +
				" arrival_airport = 'Helsinki Vantaa Airport' AND departure_airport = 'Oulu Airport'");
		ResultSet rs = stmt.executeQuery();
		List<Flight> flightList = processResults(rs);

		Query query = new Query("Oulu Airport","Departure","","","","",
				"Helsinki Vantaa Airport","","Arrival");

		List<Flight> flightList2 = flightRepository.getFlights(query);

		int flightNum = 0;
		for (Flight flight: flightList){
			Assertions.assertEquals(flight.getFlightId(),flightList2.get(flightNum).getFlightId());
			Assertions.assertEquals(flight.getIcao24(),flightList2.get(flightNum).getIcao24());
			Assertions.assertEquals(flight.getDepartureAirport(),flightList2.get(flightNum).getDepartureAirport());
			Assertions.assertEquals(flight.getArrivalAirport(),flightList2.get(flightNum).getArrivalAirport());
			Assertions.assertEquals(flight.getDepartureDateTime(),flightList2.get(flightNum).getDepartureDateTime());
			Assertions.assertEquals(flight.getArrivalDateTime(),flightList2.get(flightNum).getArrivalDateTime());

			flightNum++;
		}
	}

	// An airfield is specified with the Both radio button selected and a date is provided
	@Test
	void bothAirfieldWithDateQuery() throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Flight WHERE departure_datetime <= '2023-03-29 23:59:59'" +
				"AND arrival_datetime >= '2023-03-29 00:00:00' AND (arrival_airport = 'Bob Hope Airport' OR departure_airport = 'Bob Hope Airport')");
		ResultSet rs = stmt.executeQuery();
		List<Flight> flightList = processResults(rs);

		Query query = new Query("Bob Hope Airport","","","Both","2023-03-29","",
				"","","");

		List<Flight> flightList2 = flightRepository.getFlights(query);

		int flightNum = 0;
		for (Flight flight: flightList){
			Assertions.assertEquals(flight.getFlightId(),flightList2.get(flightNum).getFlightId());
			Assertions.assertEquals(flight.getIcao24(),flightList2.get(flightNum).getIcao24());
			Assertions.assertEquals(flight.getDepartureAirport(),flightList2.get(flightNum).getDepartureAirport());
			Assertions.assertEquals(flight.getArrivalAirport(),flightList2.get(flightNum).getArrivalAirport());
			Assertions.assertEquals(flight.getDepartureDateTime(),flightList2.get(flightNum).getDepartureDateTime());
			Assertions.assertEquals(flight.getArrivalDateTime(),flightList2.get(flightNum).getArrivalDateTime());

			flightNum++;
		}
	}

	// An arrival airport is submitted in the top text field with a date
	@Test
	void arrivalTopWithDate() throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Flight WHERE arrival_datetime <= '2023-03-30 23:59:59'" +
				"AND arrival_datetime >= '2023-03-30 00:00:00' AND" +
				" arrival_airport = 'Edo Seaplane Base'");
		ResultSet rs = stmt.executeQuery();
		List<Flight> flightList = processResults(rs);
		Query query = new Query("Edo Seaplane Base","","Arrival","","2023-03-30","",
				"","","");

		List<Flight> flightList2 = flightRepository.getFlights(query);

		int flightNum = 0;
		for (Flight flight: flightList){
			Assertions.assertEquals(flight.getFlightId(),flightList2.get(flightNum).getFlightId());
			Assertions.assertEquals(flight.getIcao24(),flightList2.get(flightNum).getIcao24());
			Assertions.assertEquals(flight.getDepartureAirport(),flightList2.get(flightNum).getDepartureAirport());
			Assertions.assertEquals(flight.getArrivalAirport(),flightList2.get(flightNum).getArrivalAirport());
			Assertions.assertEquals(flight.getDepartureDateTime(),flightList2.get(flightNum).getDepartureDateTime());
			Assertions.assertEquals(flight.getArrivalDateTime(),flightList2.get(flightNum).getArrivalDateTime());

			flightNum++;
		}
	}

	// A departure airfield is submitted in the top slot with a date
	@Test
	void departureTopWithDate() throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Flight WHERE departure_datetime <= '2023-03-30 23:59:59'" +
				"AND departure_datetime >= '2023-03-30 00:00:00' AND" +
				" departure_airport = 'Eppley Airfield'");
		ResultSet rs = stmt.executeQuery();
		List<Flight> flightList = processResults(rs);
		Query query = new Query("Eppley Airfield","Departure","","","2023-03-30","",
				"","","");

		List<Flight> flightList2 = flightRepository.getFlights(query);

		int flightNum = 0;
		for (Flight flight: flightList){
			Assertions.assertEquals(flight.getFlightId(),flightList2.get(flightNum).getFlightId());
			Assertions.assertEquals(flight.getIcao24(),flightList2.get(flightNum).getIcao24());
			Assertions.assertEquals(flight.getDepartureAirport(),flightList2.get(flightNum).getDepartureAirport());
			Assertions.assertEquals(flight.getArrivalAirport(),flightList2.get(flightNum).getArrivalAirport());
			Assertions.assertEquals(flight.getDepartureDateTime(),flightList2.get(flightNum).getDepartureDateTime());
			Assertions.assertEquals(flight.getArrivalDateTime(),flightList2.get(flightNum).getArrivalDateTime());

			flightNum++;
		}
	}

	// An arrival airport is submitted in the bottom text field with a date
	@Test
	void arrivalBottomWithDate() throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Flight WHERE arrival_datetime <= '2023-03-30 23:59:59'" +
				"AND arrival_datetime >= '2023-03-30 00:00:00' AND" +
				" arrival_airport = 'Turin Airport'");
		ResultSet rs = stmt.executeQuery();
		List<Flight> flightList = processResults(rs);
		Query query = new Query("","","","","2023-03-30","",
				"Turin Airport","","Arrival");

		List<Flight> flightList2 = flightRepository.getFlights(query);

		int flightNum = 0;
		for (Flight flight: flightList){
			Assertions.assertEquals(flight.getFlightId(),flightList2.get(flightNum).getFlightId());
			Assertions.assertEquals(flight.getIcao24(),flightList2.get(flightNum).getIcao24());
			Assertions.assertEquals(flight.getDepartureAirport(),flightList2.get(flightNum).getDepartureAirport());
			Assertions.assertEquals(flight.getArrivalAirport(),flightList2.get(flightNum).getArrivalAirport());
			Assertions.assertEquals(flight.getDepartureDateTime(),flightList2.get(flightNum).getDepartureDateTime());
			Assertions.assertEquals(flight.getArrivalDateTime(),flightList2.get(flightNum).getArrivalDateTime());

			flightNum++;
		}
	}

	List<Flight> processResults(ResultSet rs) throws SQLException {
		List<Flight> flightList = new ArrayList<>();
		while(rs.next()){
			int flightId = rs.getInt(1);
			String icao24 = rs.getString(2);
			String departureAirport = rs.getString(6);
			String arrivalAirport = rs.getString(7);
			String departureDateTime = rs.getString(8);
			String arrivalDateTime = rs.getString(9);

			Flight flight = new Flight(flightId, icao24, departureAirport, departureDateTime, arrivalDateTime, arrivalAirport);
			flightList.add(flight);
		}
		return flightList;
	}



}
