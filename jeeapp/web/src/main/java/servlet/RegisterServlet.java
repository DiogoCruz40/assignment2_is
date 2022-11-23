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

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1478408680017042864L;

    @EJB
    private IUserEJB userEJB;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String emailuser = request.getParameter("email");
        String nome = request.getParameter("nome");
        String password = request.getParameter("psw");
        String password_repeat = request.getParameter("psw-repeat");
        boolean isManager = request.getParameter("isManager") != null;
        System.out.println(request.getParameter("isManager"));

        Map<String, String> messages = new HashMap<String, String>();

        if (emailuser == null || emailuser.trim().isEmpty()) {
            messages.put("emailuser", "Please enter email");
        }
        if (nome == null || nome.trim().isEmpty()) {
            messages.put("nome", "Please enter nome");
        }
        if (password == null || password.trim().isEmpty()) {
            messages.put("password", "Please enter password");
        }

        if (password_repeat == null || password_repeat.trim().isEmpty()) {
            messages.put("password_repeat", "Please enter password repeat");
        }

        if (messages.isEmpty()) {
            boolean checkmail = userEJB.checkuseremail(emailuser);

            if (!checkmail && password.equals(password_repeat)) {
                UserDTO user = new UserDTO(emailuser, nome, password, isManager);

                try {
                    userEJB.insert(user);
                } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                    e.printStackTrace();
                }

                response.sendRedirect(request.getContextPath() + "/login");
                return;
            } else {
                if(!password.equals(password_repeat))
                {
                    messages.put("register", "As passwords não são iguais!");
                }
                else  if (checkmail)
                {
                    messages.put("register", "Esse email já se encontra registado!");
                }
                else
                {
                    messages.put("register", "Erro ao registar-se!");
                }

            }
        }

        request.setAttribute("messages", messages);
        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }
}
