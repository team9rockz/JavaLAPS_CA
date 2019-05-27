package sg.edu.nus.javalapsteam9.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import sg.edu.nus.javalapsteam9.util.EmailUtil;

@Component	
public class EmailService implements EmailUtil {

    public JavaMailSender emailSender;

	@Autowired
	public void setEmailSender(JavaMailSender emailSender) {
		this.emailSender = emailSender;
	}
 
    public void sendSimpleMessage(String to, String subject, String text) {

		ExecutorService executorService = Executors.newFixedThreadPool(10);
		executorService.execute(new Runnable() {
			
			@Override
			public void run() {
		        SimpleMailMessage message = new SimpleMailMessage(); 
		        message.setTo(to); 
		        message.setSubject(subject); 
		        message.setText(text);
		        emailSender.send(message);
			}
		});
		executorService.shutdown();
    }

}
