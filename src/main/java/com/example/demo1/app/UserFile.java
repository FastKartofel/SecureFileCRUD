package com.example.demo1.app;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;

public class UserFile {
    private String fileName;
    private String userLogin;
    private String fileContent;

    public UserFile(String fileName, String userLogin, String fileContent) {
        this.fileName = fileName;
        this.userLogin = userLogin;
        this.fileContent = fileContent;
    }
    public UserFile(String fileName) throws IOException {
        File file = FileUtils.getFile(fileName);
        List<String> lines = Files.readAllLines(file.toPath());
        if(lines.size()>=1){
            this.userLogin = lines.get(0).trim().split(":")[1];
            lines.remove(0);
            this.fileContent = String.join("", lines);
            this.fileName = fileName;
        }
    }

    public String getFileName() {
        return fileName;
    }


    public String getUserLogin() {
        return userLogin;
    }


    public String getFileContent() {
        return fileContent;
    }


    public boolean changeFileData(String newData){
        this.fileContent = newData;
        try {
            FileWriter writer = new FileWriter(FileUtils.getFile(this.fileName), false);
            writer.write("owner:"+this.userLogin + "\n"+ this.fileContent);
            writer.flush();
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean remove() {
        return remove(false);
    }

    public boolean remove(boolean isAdmin){
        if(Security.checkIfOwner(this.fileName) || isAdmin) {
            try {
                Files.delete(FileUtils.getFile(this.fileName).toPath());
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static UserFile load(String fileName, String login){
        File file = FileUtils.getFile(fileName);

        try {
            boolean isOwner = Security.checkIfOwner(new User(login,"", "user"),fileName);
            if(!isOwner){
                return null;
            }
            List<String> lines = Files.readAllLines(file.toPath());
            if(lines.size() > 0){
                String ownerPart = lines.get(0).trim().split(":")[1];
                lines.remove(0);
                String content = String.join("", lines);
                return new UserFile(fileName, ownerPart, content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return "UserFile{" +
                "fileName='" + fileName + '\'' +
                ", userLogin='" + userLogin + '\'' +
                '}';
    }
}
