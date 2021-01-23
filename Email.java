

import java.time.LocalDateTime;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class Email {

   private static String formatTime(LocalDateTime now){
      return "" + now.getHour() + ":" + now.getMinute() + ":" + now.getSecond() +  "  -  " + now.getDayOfMonth() + "/" + now.getMonthValue() + "/" + now.getYear();
   }

   private static void createMessage(MimeMessage message) throws MessagingException{
      LocalDateTime now = LocalDateTime.now();
      String textMessage = "<h1>Somone tryed to login into the Password Manager app on your computer at:<br>" + formatTime(now) + "</h1><h2>Please check your device.<br>Do not send your credentials to anyone.<br>We won't ask them.</h2><br><p>This message is send automatically after failing login 5 times<br>Please do not reply to this email.<br>The Password Manager Team</p>";

      message.setSubject("Security Allert!");
      //message.setText(textMessage);   
      message.setContent(textMessage, "text/html; charset=utf-8");
   }

   public static void sendEmail(String to){
      final String from = "noreply.pswmanager@yahoo.com";
      
      final String username = "noreply.pswmanager";
      final String password = "****************";
      
      String host = "smtp.mail.yahoo.com";
      Properties properties = System.getProperties();

      properties.put("mail.smtp.host", host);
      properties.put("mail.smtp.port", "587");
      properties.put("mail.smtp.starttls.enable", "true");
      properties.put("mail.smtp.auth", "true");

      Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
         protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(username, password);
         }
      });

      try {
         MimeMessage message = new MimeMessage(session);

         message.setFrom(new InternetAddress(from));
         message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
         createMessage(message);
         //System.out.println("sending...");
         //session.setDebug(true);
         Transport.send(message);
         //System.out.println("Sent message successfully....");
      } catch (MessagingException mex) {
         //mex.printStackTrace();
      }
   }
   /*

   public static void main(String [] args) {
      final String to = "raviz.ale@gmail.com";
      sendEmail(to);
   }*/
}
