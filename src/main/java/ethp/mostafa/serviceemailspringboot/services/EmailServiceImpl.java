package ethp.mostafa.serviceemailspringboot.services;

import ethp.mostafa.serviceemailspringboot.entities.EmailDetails;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private Configuration configuration ;
    @Autowired
    private JavaMailSender javaMailSender;
    @Value(value = "${spring.mail.username}")
    private String sender ;
    @Override
    public String sendSimpleMail(EmailDetails details) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            // set the infos of email
            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getRecipient());
            mailMessage.setText(details.getMsgBody());
            mailMessage.setSubject(details.getSubject());
            // send the email
            javaMailSender.send(mailMessage);
            return "Mail Sent Successfully...";

        }
        catch (Exception e) {
            return "Error while Sending Mail";
        }
    }

    @Override
    public String sendMailWithAttachment(EmailDetails details) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper ;

        try {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage , true);

            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(details.getRecipient());
            mimeMessageHelper.setText(details.getMsgBody());
            mimeMessageHelper.setSubject(details.getSubject());


            // add attachement

            FileSystemResource file = new FileSystemResource(
                    new File(details.getAttachment())
            );



            mimeMessageHelper.addAttachment(file.getFilename() , file);

            // send the message  :
            javaMailSender.send(mimeMessage);
            return "Mail Sent Successfully...";

        } catch (MessagingException e) {
            return "Error while Sending Mail";
        }


    }

    @Override
    public String sendMailTemplate(EmailDetails details) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper ;
        try {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage , true) ;
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(details.getRecipient());
            mimeMessageHelper.setText(getEmailContent(details) , true);
            mimeMessageHelper.setSubject(details.getSubject());
            javaMailSender.send(mimeMessage);

            return "Mail Sent Successfully...";

        } catch (Exception e) {
            return "Error while Sending Mail";
        }
    }
    String getEmailContent(EmailDetails details) throws IOException, TemplateException {
        StringWriter stringWriter = new StringWriter();
        Map<String, Object> model = new HashMap<>();
        model.put("details", details);
        configuration.getTemplate("email-template.ftlh").process(model, stringWriter);
        return stringWriter.getBuffer().toString();
    }
}
