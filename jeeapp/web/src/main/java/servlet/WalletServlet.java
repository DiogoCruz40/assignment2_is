package servlet;

import bos.IUserEJB;
import dtos.UserDTO;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/wallet")

public class WalletServlet extends HttpServlet{

    private static final long serialVersionUID = -8859262637321641782L;


    @EJB
    private IUserEJB userEJB;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDTO userDTO = (UserDTO) request.getSession(false).getAttribute("user");

        if (!userDTO.isManager()) {
            request.setAttribute("valorwallet" , userEJB.finduserbyemail(userDTO.getEmailuser()).getWallet());
            request.getRequestDispatcher("/secured/wallet.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/secured/display.jsp").forward(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        UserDTO userDTO = (UserDTO) request.getSession().getAttribute("user");

        if (request.getParameter("editwallet") != null && !request.getParameter("wallet").isEmpty())
        {

             userEJB.updatewallet(userEJB.finduserbyemail(userDTO.getEmailuser()).getId(),
                    Double.parseDouble(request.getParameter("wallet")) + userEJB.finduserbyemail(userDTO.getEmailuser()).getWallet());
        }

        if (request.getParameter("back") != null)
        {
            response.sendRedirect(request.getContextPath() + "/secured");
        }

        request.setAttribute("valorwallet" , userEJB.finduserbyemail(userDTO.getEmailuser()).getWallet());
        request.getRequestDispatcher("/secured/wallet.jsp").forward(request, response);
    }

}
