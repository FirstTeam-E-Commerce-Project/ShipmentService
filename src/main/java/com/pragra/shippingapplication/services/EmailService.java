package com.pragra.shippingapplication.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Data
@Service
public class EmailService {

    private final JavaMailSender mailSender; // Us Spring Boot’s JavaMailSender to send an email.

    // Inject Gmail username from application.properties
    @Value("${spring.mail.username}")
    private String fromEmail;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /*
     * Sends a shipment confirmation email.
     * @param toEmail        The recipient's email
     * @param trackingNumber The shipment tracking number
     * @param deliveryDate   Estimated delivery date
     */

    //This method creates an HTML email with shipment details.
    public void sendShipmentEmail(String toEmail, String trackingNumber, String deliveryDate) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject("Your Shipment is on the Way! 🚚");

            // Email body (HTML formatted)
            String body = "<h2>Shipment Confirmation</h2>"
                    + "<p>Your order has been shipped! 🎉</p>"
                    + "<p><strong>Tracking Number:</strong> " + trackingNumber + "</p>"
                    + "<p><strong>Estimated Delivery:</strong> " + deliveryDate + "</p>"
                    + "<p>Track your shipment using our website.</p>";

            helper.setText(body, true); // Set HTML content

            mailSender.send(message); // Send the email

            System.out.println("✔ Shipment email sent to: " + toEmail);

        } catch (MessagingException e) {
            System.err.println("❌ Error sending email: " + e.getMessage());
        }
    }
}
