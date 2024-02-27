package com.rt.pot.emailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Component
public class EmailServiceProvider {

	@Autowired
	private JavaMailSender javaMailSender;
              
	@Value("${spring.mail.username}")
	private String senderMail;

	public void sendMail(String[] to, String text, String subject) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

		helper.setFrom(senderMail);
		helper.setSubject(subject);
		helper.setText(text, true);
		helper.setTo(to);
		 javaMailSender.send(mimeMessage);
		System.out.println("Mail Sended Successfully");
	}

}
