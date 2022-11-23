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

@WebServlet("/editinfo")

public class EditInfoServlet extends HttpServlet{

    private static final long serialVersionUID = -8859262637321641782L;

    @EJB
    private IUserEJB userEJB;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/secured/editinfo.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        UserDTO userDTO = (UserDTO) request.getSession().getAttribute("user");

        if (request.getParameter("editname") != null && !request.getParameter("nome").isEmpty())
        {
            userEJB.updateusername(userEJB.finduserbyemail(userDTO.getEmailuser()).getId(),request.getParameter("nome"));
        }

        if (request.getParameter("editpass") != null)
        {
            try {
                userDTO = userEJB.checkusercredentials(userDTO.getEmailuser(),request.getParameter("psw-repeat"));
                if(userDTO != null && !request.getParameter("psw").isEmpty())
                {
                    userEJB.updatepassword(userEJB.finduserbyemail(userDTO.getEmailuser()).getId(),request.getParameter("psw"));
                }
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                e.printStackTrace();
            }
        }

        if (request.getParameter("back") != null)
        {
            response.sendRedirect(request.getContextPath() + "/secured");
        }


        request.getRequestDispatcher("/secured/editinfo.jsp").forward(request, response);
    }

}
