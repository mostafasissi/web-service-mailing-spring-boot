package ethp.mostafa.serviceemailspringboot.services;

import ethp.mostafa.serviceemailspringboot.entities.EmailDetails;

public interface EmailService {
    // To send a simple email
    String sendSimpleMail(EmailDetails details);

    // To send an email with attachment
    String sendMailWithAttachment(EmailDetails details);

    String sendMailTemplate(EmailDetails details) ;
}
