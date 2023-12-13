package com.example.demo1.app;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PasswordParser {
    private static List<User> users;

    private static List<User> getUsers() throws IOException {
        String passwords = Files.readString(Paths.get("/Users/patryk/Desktop/FinalTPO/passwords.txt"));

        String[] passwordEntries = passwords.split("\\*#\\*");

        return Arrays.stream(passwordEntries).map(entry -> entry.split("=")).filter(entry -> entry.length == 3)
                .map(entry -> new User(entry[0], entry[1], entry[2])).collect(Collectors.toList());
    }

    public static void addFilesToUsers(){
        if(users !=null){
            users.forEach(user -> {
                            File dir = new File(FileUtils.getFolderPath());
                            File[] files = dir.listFiles();
                            user.getUserFiles().clear();
                            System.out.println(Arrays.toString(files));
                            for(File file: files){
                                UserFile userFile = UserFile.load(file.getName(), user.getLogin());
                                if(userFile!=null){
                                    user.addFile(userFile);
                                }
                            }
                    });
        }
    }

    public static void loadUsersToContext() throws IOException {
        users = getUsers();
    }

    public static List<User> getContextUsers(){
        return PasswordParser.users;
    }



    public static Optional<User> getUserByLogin(String login) throws IOException {
        loadUsersToContext();
        addFilesToUsers();
        return getContextUsers().stream().filter(u -> u.getLogin().equals(login))
                .findFirst();
    }


}
