package servlet;

import javax.annotation.Resource;
import javax.mail.Session;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

//@WebServlet("/mail")

public class MailServlet extends HttpServlet
{
//    @Resource(name="java:jboss/mail/gmail")
//    private Session mailSession;
//
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        {
//
//            PrintWriter out=response.getWriter();
//            try    {
//                MimeMessage m = new MimeMessage(mailSession);
//                Address from = new InternetAddress("wildflycoorp@gmail.com");
//                Address[] to = new InternetAddress[] {new InternetAddress("diogo.e.cruz@hotmail.com") };
//
//                m.setFrom(from);
//                m.setRecipients(Message.RecipientType.TO, to);
//                m.setSubject("WildFly Mail");
//                        m.setSentDate(new java.util.Date());
//                m.setContent("Mail sent from WildFly","text/plain");
//                Transport.send(m);
//                out.println("Mail sent!");
//            }
//            catch (javax.mail.MessagingException e)
//            {
//                e.printStackTrace();
//                out.println("Error in Sending Mail: "+e);
//            }
//        }
//    }
}