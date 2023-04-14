package com.AirplaneTracer.AirplaneTracer_WebApp_Middleware;

import com.AirplaneTracer.AirplaneTracer_WebApp_Middleware.model.Flight;
import com.AirplaneTracer.AirplaneTracer_WebApp_Middleware.model.Waypoint;
import com.AirplaneTracer.AirplaneTracer_WebApp_Middleware.repository.FlightRepository;
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

		assertEquals(flightList,flightList2);

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

		assertEquals(flightList,flightList2);
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

		assertEquals(flightList,flightList2);
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

		assertEquals(flightList,flightList2);
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

		assertEquals(flightList,flightList2);
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

		assertEquals(flightList,flightList2);
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

		assertEquals(flightList,flightList2);
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

		assertEquals(flightList,flightList2);
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

		assertEquals(flightList,flightList2);
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

		assertEquals(flightList,flightList2);
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

		assertEquals(flightList,flightList2);
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

		assertEquals(flightList,flightList2);
	}

	// A departure airport is submitted in the bottom text field with a date
	@Test
	void departureBottomWithDate() throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Flight WHERE arrival_datetime <= '2023-03-30 23:59:59'" +
				"AND arrival_datetime >= '2023-03-30 00:00:00' AND" +
				" departure_airport = 'Eppley Airfield'");
		ResultSet rs = stmt.executeQuery();
		List<Flight> flightList = processResults(rs);
		Query query = new Query("","","","","2023-03-30","",
				"Eppley Airfield","Departure","");

		List<Flight> flightList2 = flightRepository.getFlights(query);

		assertEquals(flightList,flightList2);
	}

	// An arrival airport and a departure airport is submitted in the bottom text field with a date
	@Test
	void arrivalTopDepartureBottomWithDate() throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Flight WHERE" +
				" departure_airport = 'Edinburgh Airport' AND arrival_airport = 'Dublin Airport'" +
				" AND arrival_datetime <= '2023-03-30 23:59:59' AND arrival_datetime >= '2023-03-30 00:00:00'");
		ResultSet rs = stmt.executeQuery();
		List<Flight> flightList = processResults(rs);
		Query query = new Query("Dublin Airport","","Arrival","","2023-03-30","",
				"Edinburgh Airport","Departure","");

		List<Flight> flightList2 = flightRepository.getFlights(query);

		assertEquals(flightList,flightList2);
	}

	// An arrival airport is submitted in the bottom slot and a departure airport is submitted in the top text field with a date
	@Test
	void arrivalBottomDepartureTopWithDate() throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Flight WHERE" +
				" departure_airport = 'Edinburgh Airport' AND arrival_airport = 'Dublin Airport'" +
				" AND departure_datetime <= '2023-03-30 23:59:59' AND departure_datetime >= '2023-03-30 00:00:00'");
		ResultSet rs = stmt.executeQuery();
		List<Flight> flightList = processResults(rs);
		Query query = new Query("Edinburgh Airport","Departure","","","2023-03-30","",
				"Dublin Airport","","Arrival");

		List<Flight> flightList2 = flightRepository.getFlights(query);

		assertEquals(flightList,flightList2);
	}

	// A both airfield is submitted in the top text field with a date and time
	@Test
	void bothWithDateAndTime() throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Flight WHERE" +
				" (departure_airport = 'Dublin Airport' OR arrival_airport = 'Dublin Airport')" +
				" AND departure_datetime <= '2023-03-30 08:00:00' AND arrival_datetime >= '2023-03-30 08:00:00'");
		ResultSet rs = stmt.executeQuery();
		List<Flight> flightList = processResults(rs);
		Query query = new Query("Dublin Airport","","","Both","2023-03-30","08:00:00",
				"","","");

		List<Flight> flightList2 = flightRepository.getFlights(query);

		assertEquals(flightList,flightList2);
	}

	// An arrival airfield is submitted in the top text field with a date and time
	@Test
	void arrivalTopWithDateAndTime() throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Flight WHERE" +
				" arrival_airport = 'Dublin Airport'" +
				" AND departure_datetime <= '2023-03-30 08:00:00' AND arrival_datetime >= '2023-03-30 08:00:00'");
		ResultSet rs = stmt.executeQuery();
		List<Flight> flightList = processResults(rs);
		Query query = new Query("Dublin Airport","","Arrival","","2023-03-30","08:00:00",
				"","","");

		List<Flight> flightList2 = flightRepository.getFlights(query);

		assertEquals(flightList,flightList2);
	}

	// A departure airfield is submitted in the top text field with a date and time
	@Test
	void departureTopWithDateAndTime() throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Flight WHERE" +
				" departure_airport = 'Dublin Airport'" +
				" AND departure_datetime <= '2023-03-30 08:00:00' AND arrival_datetime >= '2023-03-30 08:00:00'");
		ResultSet rs = stmt.executeQuery();
		List<Flight> flightList = processResults(rs);
		Query query = new Query("Dublin Airport","Departure","","","2023-03-30","08:00:00",
				"","","");

		List<Flight> flightList2 = flightRepository.getFlights(query);
		assertEquals(flightList,flightList2);
	}

	// An arrival airport is submitted in the bottom text field along with a date and time
	@Test
	void arrivalBottomWithDateAndTime() throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Flight WHERE" +
				" arrival_airport = 'Dublin Airport'" +
				" AND departure_datetime <= '2023-03-30 08:00:00' AND arrival_datetime >= '2023-03-30 08:00:00'");
		ResultSet rs = stmt.executeQuery();
		List<Flight> flightList = processResults(rs);
		Query query = new Query("","","","","2023-03-30","08:00:00",
				"Dublin Airport","","Arrival");

		List<Flight> flightList2 = flightRepository.getFlights(query);
		assertEquals(flightList,flightList2);
	}

	// A departure airport is submitted in the bottom text field along with a date and time
	@Test
	void departureBottomWithDateAndTime() throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Flight WHERE" +
				" departure_airport = 'Dublin Airport'" +
				" AND departure_datetime <= '2023-03-30 08:00:00' AND arrival_datetime >= '2023-03-30 08:00:00'");
		ResultSet rs = stmt.executeQuery();
		List<Flight> flightList = processResults(rs);
		Query query = new Query("","","","","2023-03-30","08:00:00",
				"Dublin Airport","Departure","");

		List<Flight> flightList2 = flightRepository.getFlights(query);
		assertEquals(flightList,flightList2);
	}

	// An arrival airport is submitted in the top text field and a departure airport is submitted in the bottom text field along with a date and time
	@Test
	void arrivalTopDepartureBottomWithDateAndTime() throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Flight WHERE" +
				" departure_airport = 'Milan Bergamo Airport' and arrival_airport = 'Dublin Airport'" +
				" AND departure_datetime <= '2023-03-30 08:00:00' AND arrival_datetime >= '2023-03-30 08:00:00'");
		ResultSet rs = stmt.executeQuery();
		List<Flight> flightList = processResults(rs);
		Query query = new Query("Dublin Airport","","Arrival","","2023-03-30","08:00:00",
				"Milan Bergamo Airport","Departure","");

		List<Flight> flightList2 = flightRepository.getFlights(query);
		assertEquals(flightList,flightList2);
	}

	// A departure airport is submitted in the top text field and an arrival airport is submitted in the bottom text field along with a date and time
	@Test
	void departureTopArrivalBottomWithDateAndTime() throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Flight WHERE" +
				" departure_airport = 'Milan Bergamo Airport' and arrival_airport = 'Dublin Airport'" +
				" AND departure_datetime <= '2023-03-30 08:00:00' AND arrival_datetime >= '2023-03-30 08:00:00'");
		ResultSet rs = stmt.executeQuery();
		List<Flight> flightList = processResults(rs);
		Query query = new Query("Milan Bergamo Airport","Departure","","","2023-03-30","08:00:00",
				"Dublin Airport","","Arrival");

		List<Flight> flightList2 = flightRepository.getFlights(query);
		assertEquals(flightList,flightList2);
	}

	//Checks to ensure no waypoints were sent back if no flight ids are sent.
	@Test
	void noFlightIds(){
		Assertions.assertEquals(flightRepository.getWaypoints(new ArrayList<>()).size(),0);
	}

	// Tests that the expected waypoints were received and that no more than one flight was retrieved
	@Test
	void oneFlightId() throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Waypoint WHERE flight_id = ? ORDER BY offset_ms");
		stmt.setInt(1, 1);
		ResultSet rs = stmt.executeQuery();

		List<Waypoint> waypoints = new ArrayList<>();
		while(rs.next()){
			float latitude = rs.getFloat(2);
			float longitude = rs.getFloat(3);
			waypoints.add(new Waypoint(latitude,longitude));
		}

		List<Integer> flightIds = new ArrayList<>();
		flightIds.add(1);
		List<List<Waypoint>> waypointListFromRepository = flightRepository.getWaypoints(flightIds);

		Assertions.assertEquals(waypointListFromRepository.size(),1);

		for (int i = 0; i < waypoints.size(); i++){
			Assertions.assertEquals(waypoints.get(i).getLatitude(),waypointListFromRepository.get(0).get(i).getLatitude());
			Assertions.assertEquals(waypoints.get(i).getLongitude(),waypointListFromRepository.get(0).get(i).getLongitude());
		}

	}


	// Tests to see if 50 flights are retrieved properly
	@Test
	void retrieveFiftyFlightIds() throws SQLException {
		List<List<Waypoint>> expectedFlightWaypoints = new ArrayList<>();
		for (int i = 1; i <= 50; i++) {
			PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Waypoint WHERE flight_id = ? ORDER BY offset_ms");
			stmt.setInt(1, i);
			ResultSet rs = stmt.executeQuery();

			List<Waypoint> waypoints = new ArrayList<>();
			while (rs.next()) {
				float latitude = rs.getFloat(2);
				float longitude = rs.getFloat(3);
				waypoints.add(new Waypoint(latitude, longitude));
			}

			expectedFlightWaypoints.add(waypoints);
		}

		List<Integer> flightIds = new ArrayList<>();
		for (int i = 1; i <= 50; i++) {
			flightIds.add(i);
		}
		List<List<Waypoint>> waypointListFromRepository = flightRepository.getWaypoints(flightIds);

		Assertions.assertEquals(waypointListFromRepository.size(),expectedFlightWaypoints.size());

		for (int flightId = 1; flightId <= 50; flightId++) {
			for (int i = 0; i < expectedFlightWaypoints.get(flightId-1).size(); i++) {
				Assertions.assertEquals(expectedFlightWaypoints.get(flightId-1).get(i).getLatitude(),
						waypointListFromRepository.get(flightId-1).get(i).getLatitude());

				Assertions.assertEquals(expectedFlightWaypoints.get(flightId-1).get(i).getLongitude(),
						waypointListFromRepository.get(flightId-1).get(i).getLongitude());
			}
		}
	}

	List<Flight> processResults(ResultSet rs) throws SQLException {
		List<Flight> flightList = new ArrayList<>();
		while(rs.next()){
			int flightId = rs.getInt(1);
			String icao24 = rs.getString(2);
			String callsign = rs.getString(3);
			String departureAirport = rs.getString(6);
			String arrivalAirport = rs.getString(7);
			String departureDateTime = rs.getString(8);
			String arrivalDateTime = rs.getString(9);
			Long duration = rs.getLong(10);
			Long hours = duration / 3600;
			Long minutes = (duration % 3600) / 60;
			Long seconds = duration % 60;
			String durationString = String.format("%02d:%02d:%02d",hours,minutes,seconds);
			Flight flight = new Flight(flightId, icao24, callsign, departureAirport, departureDateTime, arrivalDateTime, arrivalAirport,durationString);
			flightList.add(flight);
		}
		return flightList;
	}

	public void assertEquals(List<Flight> flightList, List<Flight> flightList2){
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



}
