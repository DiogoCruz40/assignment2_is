package filter;

import java.io.IOException;
import        javax.servlet.Filter;
import javax.servlet.FilterChain;
import     javax.servlet.ServletException;
import      javax.servlet.ServletRequest;
import     javax.servlet.ServletResponse;
import     javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("/*")

public class SecurityFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);
        String loginURI = request.getContextPath() + "/login";
        String registerURI = request.getContextPath() + "/register";
        boolean loggedIn = session != null && session.getAttribute("user") != null;
        boolean loginRequest = request.getRequestURI().equals(loginURI);
        boolean registerRequest = request.getRequestURI().equals(registerURI);

        if (loggedIn || loginRequest || registerRequest) {
            chain.doFilter(request, response);
        } else {
            response.sendRedirect(loginURI);
        }
    }
}