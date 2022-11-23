package servlet;

import bos.ITripEJB;
import dtos.TripDTO;
import dtos.UserDTO;
import entities.Trip;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/trip")
public class TripServlet extends HttpServlet {
    private static final long serialVersionUID = -5833143051551469285L;

    @EJB
    private ITripEJB tripEJB;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        boolean isManager = ((UserDTO) session.getAttribute("user")).isManager();
        if (isManager) {
            request.getRequestDispatcher("/secured/trip.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/secured/display.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Map<String, String> messages = new HashMap<String, String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

        try {
            Date departureDate = sdf.parse(request.getParameter("departureDate"));
            String departurePoint = request.getParameter("departurePoint");
            String destinationPoint = request.getParameter("destinationPoint");
            long capacity = Long.parseLong(request.getParameter("capacity"));
            double price =  Double.parseDouble(request.getParameter("price"));

            TripDTO dto = new TripDTO(departureDate,departurePoint,destinationPoint,capacity,price);
            tripEJB.insert(dto);

            response.sendRedirect(request.getContextPath() + "/secured");
            return;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}