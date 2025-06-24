package com.flowreserve.demo1.service.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
 @Autowired
    private JavaMailSender mailSender;

    public void enviarCorreo(String to, String subject, String body) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(to);              // destinatario
        mensaje.setSubject(subject);    // asunto
        mensaje.setText(body);          // contenido
        mensaje.setFrom("info.flowreserve@gmail.com"); // debe coincidir con spring.mail.username
        mailSender.send(mensaje);       // se envía el correo
    }


}
