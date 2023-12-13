package com.example.demo1;

import com.example.demo1.app.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/filelist")
public class filelist extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        String fileName = req.getParameter("filename");
        HttpSession session = req.getSession();

        String login = (String)session.getAttribute("login");
        boolean isAdmin = ((String)session.getAttribute("role")).equals("admin");
        resp.setContentType("text/html");
        if(action.equals("edit")){
            if(isAdmin){
                UserFile uf = new UserFile(fileName);
                resp.getOutputStream().print(HTMLUtils.editFile(uf.getFileContent(), fileName));
            }else{
                Optional<User> user = PasswordParser.getUserByLogin(login);

                if(user.isPresent()) {
                    Optional<UserFile> file = user.get()
                            .getUserFiles().stream()
                            .filter(f -> f.getFileName().equals(fileName)).findFirst();
                    if(file.isPresent()){
                        UserFile f = file.get();
                        resp.getOutputStream().print(HTMLUtils.editFile(f.getFileContent(),fileName));
                    }
                }
            }

            }else if(action.equals("delete")){
                if(isAdmin){
                    UserFile uf = new UserFile(fileName);
                    uf.remove(true);
                }
                PasswordParser.getUserByLogin(login).ifPresent(u -> {
                            u.getUserFiles().stream()
                                    .filter(file -> {
                                        return file.getFileName().equals(fileName);
                                    }).findFirst().ifPresent(UserFile::remove);
                        });

            }else if(action.equals("save")){
            String newFileContent = req.getParameter("content");
            if(isAdmin){
                UserFile uf = new UserFile(fileName);
                uf.changeFileData(newFileContent);
            }else{
                PasswordParser.getUserByLogin(login)
                        .ifPresent(u -> {
                            u.getUserFiles().stream()
                                    .filter(f -> f.getFileName().equals(fileName))
                                    .findFirst()
                                    .ifPresent(ff -> {
                                        ff.changeFileData(newFileContent);
                                    });
                        });
            }

        }
        PasswordParser.loadUsersToContext();
        PasswordParser.addFilesToUsers();
        resp.getOutputStream().print(HTMLUtils.getTableOfFiles(req));
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getOutputStream().print(HTMLUtils.getTableOfFiles(req));
    }

}