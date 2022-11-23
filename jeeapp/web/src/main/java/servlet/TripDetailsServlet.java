package servlet;

import bos.ITripEJB;
import dtos.TripDTO;
import dtos.UserDTO;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/tripdetails")
public class TripDetailsServlet extends HttpServlet {
    private static final long serialVersionUID = 6694970793860083573L;

    @EJB
    private ITripEJB tripEJB;

    @Resource(name="java:jboss/mail/gmail")
    private Session mailSession;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        UserDTO dto = (UserDTO) session.getAttribute("user");

        if (dto.isManager()) {

            //get trip users
            long tripId = Long.parseLong(request.getParameter("tripId"));
            List<UserDTO> result = tripEJB.getTripUsers(tripId);
            request.setAttribute("tripUsers", result);

            request.getRequestDispatcher("/secured/tripdetails.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/secured/display.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            long tripId = Long.parseLong(request.getParameter("tripId"));
            List<UserDTO> userToNotify = tripEJB.delete(tripId);
            if (userToNotify.size() > 0) {
                try {
                    MimeMessage m = new MimeMessage(mailSession);
                    Address from = new InternetAddress("wildflycoorp@gmail.com");

                    Address[] to = new InternetAddress[userToNotify.size()];
                    for (int i = 0; i < userToNotify.size(); i++) {
                        to[i] = new InternetAddress(userToNotify.get(i).getEmailuser());
                    }

                    m.setFrom(from);
                    m.setRecipients(Message.RecipientType.TO, to);
                    m.setSubject("Trip "+tripId+ "deleted");
                    m.setSentDate(new java.util.Date());
                    m.setContent("Trip with id"+tripId+" has been deleted.","text/plain");
                    Transport.send(m);
                }
                catch (javax.mail.MessagingException e)
                {
                    e.printStackTrace();
                }
            }

            response.sendRedirect(request.getContextPath() + "/secured");
            return;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}