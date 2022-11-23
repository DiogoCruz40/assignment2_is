package servlet;
import bos.IHelperEJB;
import bos.ISystemEJB;
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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 8182693883319112782L;

    @EJB
    private IUserEJB userEJB;
    @EJB
    private IHelperEJB helperEJB;

    @EJB
    private ISystemEJB systemEJB;


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String emailuser = request.getParameter("emailuser");
        String password = request.getParameter("passworduser");

       Map<String, String> messages = new HashMap<String, String>();

        if (emailuser == null || emailuser.trim().isEmpty()) {
            messages.put("emailuser", "Please enter email");
        }
        if (password == null || password.trim().isEmpty()) {
            messages.put("password", "Please enter password");
        }

        if (messages.isEmpty()) {
            UserDTO user = new UserDTO();
            user.setEmailuser(emailuser);
            user.setPassword(password);

            try {
                user = userEJB.checkusercredentials(emailuser,password);
                if (user != null) {
                    request.getSession().setAttribute("user", user);
                    response.sendRedirect(request.getContextPath() + "/secured");
                    return;
                } else {
                    messages.put("login", "Unknown login, please try again");
                }
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                e.printStackTrace();
            }
        }

        request.setAttribute("messages", messages);
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

}