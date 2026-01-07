/**
 * 
 */
package deo.raghav.course_project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * @author raghav
 *
 */

@Service
public class MailService {
	
	@Autowired
	private JavaMailSender mail_sender;
	
	public void send_mail(String email_id, String subject, String body) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(Constants.ADMIN_EMAIL_ID);
		message.setTo(email_id);
		message.setSubject(subject);
		message.setText(body);
		mail_sender.send(message);
	}
}
