package servlet;
import bos.ISystemEJB;
import bos.ITripEJB;
import bos.IUserEJB;
import dtos.TripDTO;
import dtos.UserDTO;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/secured")
public class MainServlet extends HttpServlet {
    private static final long serialVersionUID = -8859262637321641782L;

    @EJB
    private IUserEJB userEJB;

    @EJB
    private ITripEJB tripEJB;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        UserDTO userDTO = (UserDTO) request.getSession(false).getAttribute("user");
        if (!userDTO.isManager()) {
            List<TripDTO> result = tripEJB.getUserTrips(userDTO.getId());
            request.setAttribute("userTrips", result);
        }
        else
        {
            List<UserDTO> result = tripEJB.getUserTripsTop5();
            request.setAttribute("userstop", result);
        }
        request.getRequestDispatcher("/secured/display.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("logout") != null) {
            request.getSession().removeAttribute("user");
            response.sendRedirect(request.getContextPath() + "/login");
        }
        if (request.getParameter("editinfo") != null)
        {
            response.sendRedirect(request.getContextPath() + "/editinfo");
        }

        if (request.getParameter("wallet") != null)
        {
            response.sendRedirect(request.getContextPath() + "/wallet");
        }

        if (request.getParameter("accountremove") != null)
        {

             UserDTO userDTO = (UserDTO) request.getSession().getAttribute("user");
             userEJB.removeuserbyemail(userDTO.getEmailuser());
             request.getSession().removeAttribute("user");
             response.sendRedirect(request.getContextPath() + "/login");
        }

        if (request.getParameter("deleteUserTrip") != null) {
            long userTripId = Long.parseLong(request.getParameter("userTripId"));
            tripEJB.removeUserTrip(userTripId);
        }

        request.getRequestDispatcher("/secured/display.jsp").forward(request, response);
    }

}