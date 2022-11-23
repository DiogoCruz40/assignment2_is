package servlet;

import bos.ITripEJB;
import dtos.PurchaseTripDTO;
import dtos.TripDTO;
import dtos.UserDTO;

import javax.ejb.EJB;
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
import java.util.Map;

@WebServlet("/purchase")
public class PurchaseServlet extends HttpServlet {
    private static final long serialVersionUID = -5833143051551469285L;

    @EJB
    private ITripEJB tripEJB;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        boolean isManager = ((UserDTO) session.getAttribute("user")).isManager();
        if (!isManager) {
            request.getRequestDispatcher("/secured/purchase.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/secured/display.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            long tripId = Long.parseLong(request.getParameter("tripId"));
            long place = Long.parseLong(request.getParameter("place"));
            double price = Double.parseDouble(request.getParameter("price"));
            long userId = ((UserDTO) request.getSession().getAttribute("user")).getId();
            Date purchaseDate = new Date();

            PurchaseTripDTO dto = new PurchaseTripDTO(tripId, place, price, userId, purchaseDate);
            tripEJB.purchase(dto);

            response.sendRedirect(request.getContextPath() + "/secured");
            return;

        } catch (Exception e) {

        }
    }
}