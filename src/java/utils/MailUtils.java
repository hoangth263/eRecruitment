/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Thien's
 */
public class MailUtils {



    public static void send(String to, String subject, String body) throws Exception {
        //Getting the current date
        Date dateMili = new Date();
        //This method returns the time in millis
        long timeMilli = dateMili.getTime();
        Properties props = System.getProperties();
        final String login = "nguyenhuuthien9a1nbk@gmail.com";//usermail
        final String pwd = "kdjuoojwugittneb";//”password o day”;
        
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "465");
        props.put("mail.debug", "true");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");

        Authenticator pa = null; //default: no authentication
        if (login != null && pwd != null) { //authentication required?
            props.put("mail.smtp.auth", "true");
            pa = new Authenticator() {
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(login, pwd);
                }
            };
        }//else: no authentication
        Session session = Session.getInstance(props, pa);
// — Create a new message –
        Message msg = new MimeMessage(session);
// — Set the FROM and TO fields –
        msg.setFrom(new InternetAddress(login));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));

// — Set the subject and body text –
        msg.setSubject(subject);
        msg.setText(body);
// — Set some other header information –
        msg.setHeader("X - Mailer", "LOTONtechEmail");
        msg.setSentDate(new Date());
        msg.saveChanges();
// — Send the message –
        msg.setContent(body, "text/html");
        Transport.send(msg);
        System.out.println("Message sent OK.");
        dateMili = new Date();
        long currentTime = dateMili.getTime() - timeMilli;
        System.out.println("This is time to sent a message: " + currentTime);
    }
}
