package sheridancollege.annecho.database;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import sheridancollege.annecho.beans.Appointment;

@Repository
public class DatabaseAccess {

	@Autowired
	protected NamedParameterJdbcTemplate jdbc;

	public void insertAppointment(String name, String classname, LocalDate appointmentDate, LocalTime appointmentTime) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query="INSERT INTO appointment(name, classname, appointmentDate, appointmentTime) VALUES(:name, :classname, :appointmentDate, :appointmentTime)";
		namedParameters.addValue("name", name);
		namedParameters.addValue("classname", classname);
		namedParameters.addValue("appointmentDate", appointmentDate);
		namedParameters.addValue("appointmentTime", appointmentTime);
		int rowsAffected = jdbc.update(query, namedParameters);
		if (rowsAffected > 0) 
			System.out.println("Inserted appointment into database.");
	}
	
	public List<Appointment> getAllAppointments() {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "SELECT * FROM appointment";
		return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Appointment>(Appointment.class));
	}
	
	public void deleteAppointmentById(Long id) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "DELETE FROM appointment WHERE id = :id";
		namedParameters.addValue("id", id);
		int rowsAffected = jdbc.update(query, namedParameters);
		if (rowsAffected > 0)
			System.out.println("Deleted appointment " + id + " from database");
	}
	
	public List<Appointment> getAppointmentById(Long id) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "SELECT * FROM appointment WHERE id = :id";
		namedParameters.addValue("id", id);
		return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Appointment>(Appointment.class));
	}

}
