package com.example.demo1.app;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class Security {
    private static User userFromRequest;

    public static void setContext(String login){
        userFromRequest = new User(login,"", "user");
    }

    public static User getContext(){
        return userFromRequest;
    }

    public static boolean checkIfOwner(User user, String fileName){
        File file = FileUtils.getFile(fileName);
        try {
            List<String> lines = Files.readAllLines(file.toPath());
            if(lines.size() > 0){
                String authorLine = lines.get(0);
                String[] headers = authorLine.split(":");

                return headers.length == 2 && headers[1].equals(user.getLogin());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean checkIfOwner(String fileName){
        return checkIfOwner(Security.getContext(), fileName);
    }
}
