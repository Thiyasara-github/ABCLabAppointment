package com.ABCLab.ABCLab.Controller;

import com.ABCLab.ABCLab.Model.Payment;
import com.ABCLab.ABCLab.Repository.PaymentRepo;
import com.ABCLab.ABCLab.email.SendEmailEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:63342")
public class PaymentController {

    @Autowired
    private PaymentRepo paymentRepo;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public PaymentController(PaymentRepo paymentRepo, ApplicationEventPublisher eventPublisher) {
        this.paymentRepo = paymentRepo;
        this.eventPublisher = eventPublisher;
    }

    @PostMapping("/payment")
    public String processPayment(@RequestBody Payment payment) {
        // Save the payment details to the database
        Payment savedPayment = paymentRepo.save(payment);

        // Send payment receipt email
        sendPaymentReceiptEmail(savedPayment);

        return "Payment processed successfully!";
    }

    private void sendPaymentReceiptEmail(Payment payment) {
        String subject = "ABC Laboratory - Payment Receipt";
        String body = "Hi " + payment.getName() + ",\n\n"
                + "Thank you for your payment to ABC Laboratory.\n"
                + "Payment ID: " + payment.getPaymentid() + "\n"
                + "Amount: Rs. 2500\n\n"  // You can customize the amount based on your requirements
                + "This email serves as your payment receipt.\n\n"
                + "If you have any questions, please feel free to contact us.\n\n"
                + "Best regards,\n"
                + "ABC Laboratory Team..!\n";

        eventPublisher.publishEvent(new SendEmailEvent(this, payment.getEmail(), subject, body));
    }

}
