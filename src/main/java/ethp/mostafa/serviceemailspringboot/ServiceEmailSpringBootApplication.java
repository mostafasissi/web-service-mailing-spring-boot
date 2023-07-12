package ethp.mostafa.serviceemailspringboot;

import ethp.mostafa.serviceemailspringboot.entities.EmailDetails;
import ethp.mostafa.serviceemailspringboot.services.EmailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ServiceEmailSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceEmailSpringBootApplication.class, args);
    }


    @Bean
    CommandLineRunner start(EmailServiceImpl emailService){
        return args -> {
            EmailDetails emailDetails = EmailDetails.
                    builder()
                    .recipient("sissiessaadia667@gmail.com")
                    .subject("Test Email")
                    .msgBody("Hello fom service mailing")
                    .build();
            System.out.println(emailService.sendSimpleMail(emailDetails));

        };
    }

}
