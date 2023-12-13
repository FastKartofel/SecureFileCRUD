package com.example.demo1;

import com.example.demo1.app.PasswordParser;
import com.example.demo1.app.Security;
import com.example.demo1.app.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@WebServlet( name = "basicLoginServlet", value = "/basiclogin-servlet")
public class BasicLoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("username");
        String password = req.getParameter("password");
        Optional<User> user = PasswordParser.getUserByLogin(userName);

        if (user.isPresent() && user.get().getPass().equals(password)) {
            Security.setContext(userName);
            PasswordParser.addFilesToUsers();
            String role = user.get().getRole();

            HttpSession session = req.getSession();
            session.setAttribute("login", userName);
            session.setAttribute("role", role);
            resp.sendRedirect("protected/sucess.html");
        }else
            resp.sendRedirect("error.html");
    }
}
