package com.example.demo1;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/protected/*"})
public
    class AuthenticationFilter
    implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpReq = (HttpServletRequest) servletRequest;
        HttpServletResponse httpRes = (HttpServletResponse) servletResponse;
        HttpSession session = httpReq.getSession(false);

        boolean isLoggedIn = session != null && session.getAttribute("login") != null;

        if(isLoggedIn)
            filterChain.doFilter(servletRequest, servletResponse);
        else
            httpRes.sendRedirect("../error.html");
    }
}
