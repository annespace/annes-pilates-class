package sheridancollege.annecho.controllers;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sheridancollege.annecho.beans.Appointment;
import sheridancollege.annecho.database.DatabaseAccess;

@Controller
public class AppointmentController {

	@Autowired
	private DatabaseAccess database;

	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("appointment", new Appointment());
		model.addAttribute("appointmentList", database.getAllAppointments());
		return "index";
	}

	@PostMapping("/insertAppointment")
	public String insertAppointment(Model model, @RequestParam String name, @RequestParam String classname,
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam LocalDate appointmentDate,
			@DateTimeFormat(iso = DateTimeFormat.ISO.TIME) @RequestParam LocalTime appointmentTime) {
		database.insertAppointment(name, classname, appointmentDate, appointmentTime);
		model.addAttribute("appointment", new Appointment());
		model.addAttribute("appointmentList", database.getAllAppointments());
		return "index";
	}

	@GetMapping("/deleteAppointment/{id}")
	public String deleteAppointment(Model model, @PathVariable Long id) {
		database.deleteAppointmentById(id);
		model.addAttribute("appointment", new Appointment());
		model.addAttribute("appointmentList", database.getAllAppointments());
		return "index";
	}

	@GetMapping("/editAppointment/{id}")
	public String editAppointment(Model model, @PathVariable Long id) {
		model.addAttribute("appointment", database.getAppointmentById(id).get(0));
		database.deleteAppointmentById(id);
		model.addAttribute("appointmentList", database.getAllAppointments());
		return "index";
	}

	@GetMapping("/new")
	public String newAppointment(Model model) {
		model.addAttribute("appointment", new Appointment());
		model.addAttribute("appointmentList", database.getAllAppointments());
		return "new";
	}

}
