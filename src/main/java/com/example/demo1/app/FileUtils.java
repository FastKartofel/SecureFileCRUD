package com.example.demo1.app;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileUtils {

    public static String getFolderPath() {
        return "/Users/patryk/Desktop/FinalTPO/files";
    }

    public static File getFile(String fileName){
        return new File(getFolderPath() + "/" + fileName);
    }

    public static boolean fileExists(String fileName) {
        File file = new File(getFolderPath() + "/" + fileName);
        return file.exists();
    }

    public static boolean add(String fileContent, String fileName, String login)  {
        File file = new File(getFolderPath() + "/" + fileName);
        boolean result = false;
        if(fileExists(fileName)){
            return false;
        }
        try{
            boolean created = file.createNewFile();
            if(created) {
                Files.writeString(file.toPath(), "Owner:" + login + "\n" + fileContent);
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        return result;
    }
}
