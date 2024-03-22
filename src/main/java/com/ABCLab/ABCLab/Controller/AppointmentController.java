package com.ABCLab.ABCLab.Controller;



import com.ABCLab.ABCLab.Model.Appointment;
import com.ABCLab.ABCLab.Repository.AppointmentRepo;
import com.ABCLab.ABCLab.email.SendEmailEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;


@RestController
@CrossOrigin(origins = "http://localhost:63342")
public class AppointmentController {

    private final AppointmentRepo appointmentRepo;
    private final ApplicationEventPublisher eventPublisher;
    @Autowired
    public AppointmentController(AppointmentRepo appointmentRepo, ApplicationEventPublisher eventPublisher) {
        this.appointmentRepo = appointmentRepo;
        this.eventPublisher = eventPublisher;
    }

    @GetMapping("/allAppointments")
    public ResponseEntity<List<Appointment>> getAllAppointments(){
        List<Appointment> appointment = appointmentRepo.findAll();

        if(appointment.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(appointment, HttpStatus.OK);
    }

    /*@PostMapping("/appointments")
    public Appointment addAppointment(@RequestBody Appointment appointment) {
        // Generate the next 'no'
        Integer nextNo = appointmentService.generateNextNo();

        // Set the 'no' attribute and save the appointment
        appointment.setNo(nextNo);
        Appointment savedAppointment = AppointmentRepo.save(appointment);

        // Send the confirmation email
        sendConfirmationEmail(savedAppointment);

        return savedAppointment;
    }*/



    @PostMapping("/appointments")
    public Appointment addAppointment(@RequestBody Appointment appointment) {
        // Generate the lastDigit and paymentId
        String aid = String.valueOf(appointment.getAid());
        String lastDigit = String.valueOf(aid.charAt(aid.length() - 1));
        String paymentId = aid.substring(Math.max(0, aid.length() - 4));

        // Add logs
        System.out.println("lastDigit: " + lastDigit);
        System.out.println("paymentId: " + paymentId);

        // Set the values in the Appointment entity
        appointment.setLastDigit(lastDigit);
        appointment.setPaymentId(paymentId);

        // Save the appointment, MongoDB will auto-generate the aid
        Appointment savedAppointment = appointmentRepo.save(appointment);

        // Send the confirmation email
        sendConfirmationEmail(savedAppointment);

        return savedAppointment;
    }

    private void sendConfirmationEmail(Appointment appointment) {

        String aid = String.valueOf(appointment.getAid());
        char lastDigit = aid.charAt(aid.length() - 1);
        // Generate a payment ID (last four digits of the appointment ID)
        String paymentId = aid.substring(Math.max(0, aid.length() - 4));

        String toEmail = appointment.getEmail();
        String subject = "ABC Laboratory - Appointment Confirmation";
        String body = "Hi " + appointment.getName() + ",\n\n"
                + "Thank you for booking an appointment with\nABC Laboratory.\n"
                + "The appointment details are as follows:\n\n"
                + "Payment ID: " + paymentId + "\n\n"  // Include the payment ID
                + "Test: " + appointment.getTest() + "\n"
                + "Doctor: " + appointment.getDoctor() + "\n"
                + "Datetime: " + appointment.getDatetime() + "\n"
                + "Number: " + lastDigit + "\n\n"
                + "Location: ABC Laboratory\n"
                + "Address: 182/1 Senanayake Mawatha Manavilla Walahanduwa Galle Sri Lanka\n"
                + "Contact: 091 222 2835\n                071 213 4131\n\n"
                + "Please feel free to contact us if you have any questions or need further assistance.\n\n"
                + "We look forward to seeing you!\n\n"
                + "Best regards,\n"
                + "ABC Laboratory Team..!\n";

        eventPublisher.publishEvent(new SendEmailEvent(this, toEmail, subject, body));

    }

    @DeleteMapping("/appointments/{name}")
    public ResponseEntity<String> deleteAppointment(@PathVariable String name) {
        try {
            // Check if the appointment with the given aid exists
            if (appointmentRepo.existsById(name)) {
                // Delete the appointment
                appointmentRepo.deleteById(name);
                return new ResponseEntity<>("Appointment with ID " + name + " deleted successfully.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Appointment with ID " + name + " not found.", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete appointment with ID " + name + ": " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}



