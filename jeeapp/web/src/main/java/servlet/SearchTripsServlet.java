package servlet;

import bos.ITripEJB;
import dtos.SearchTripsDTO;
import dtos.TripDTO;
import dtos.UserDTO;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/searchtrips")
public class SearchTripsServlet extends HttpServlet {
    private static final long serialVersionUID = -5833143051551469285L;

    @EJB
    private ITripEJB tripEJB;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date fromDate = sdf.parse(request.getParameter("fromDate"));

            Date toDate = null;
            String toDateString = request.getParameter("toDate");
            if (toDateString != null && toDateString != "" ) {
                toDate = sdf.parse(toDateString);
            }

            SearchTripsDTO dto = new SearchTripsDTO(fromDate, toDate);
            List<TripDTO> result = tripEJB.search(dto);

            request.setAttribute("printSearchResults", true);
            request.setAttribute("tripsList", result);

            //#region Get user trips

            UserDTO userDTO = (UserDTO) request.getSession(false).getAttribute("user");
            List<TripDTO> userTrips = tripEJB.getUserTrips(userDTO.getId());
            request.setAttribute("userTrips", userTrips);

            List<UserDTO> topusers = tripEJB.getUserTripsTop5();
            request.setAttribute("userstop", topusers);

            //#endregion Get user trips

            request.getRequestDispatcher("/secured/display.jsp").forward(request, response);
            return;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}