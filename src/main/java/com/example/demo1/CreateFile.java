package com.example.demo1;

import com.example.demo1.app.FileUtils;
import com.example.demo1.app.HTMLUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet( "/createfile")
public class CreateFile
        extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        HttpSession session = req.getSession();
        String login = (String)session.getAttribute("login");
        if(action != null && action.equals("save")){
            String content = req.getParameter("content");
            String fileName = req.getParameter("filename");
            FileUtils.add(content, fileName, login);
        }
        resp.setContentType("text/html");
        resp.getOutputStream().print(HTMLUtils.getAddFrom());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.getOutputStream().print(HTMLUtils.getAddFrom());
    }
}