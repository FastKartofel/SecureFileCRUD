package com.example.demo1.app;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

public class HTMLUtils {
    public static String getTableOfFiles(HttpServletRequest req){
        List<User> users = PasswordParser.getContextUsers();
        List<UserFile> files = users.stream()
                .map(
                        User::getUserFiles
                ).flatMap(List::stream).collect(Collectors.toList());
        System.out.println(files);
        String tableHead = "<tr> <th>file name</th> <th>login</th> <th>actions</th></tr>";
        HttpSession session = req.getSession();
        String login = (String)session.getAttribute("login");
        String tole = (String) session.getAttribute("role");
        return "<table>" + tableHead + files.stream()
                .map(file -> {
                    String rowData = "<td>" + file.getFileName() + "</td>";
                    String rowDataOwner = "<td>" + file.getUserLogin() + "</td>";
                    String actions = "<td>" + ((file.getUserLogin().equals(login) || tole.equals("admin")) ? (getDeleteButton(file.getFileName()) + getEditButton(file.getFileName())) : "") + "</td>";
                    return "<tr> " +rowData + rowDataOwner + actions + "</tr>";
                }).collect(Collectors.joining()) + "</table>";
    }

    private static String getAction(String fileName, String action){
        String btn = "<button type=\"submit\">"+action+"</button>";
        String actionInput = "<input type=\"hidden\" name=\"action\" value=\""+action+"\"/>";
        String fileNameInput = "<input type=\"hidden\" name=\"filename\" value=\""+fileName+"\"/>";
        return "<form action=\"filelist\" method=\"post\">" + btn + fileNameInput +actionInput+ "</form>";
    }

    public static String editFile(String content, String fileName){
        String textArea = "<textarea name=\"content\">"+content+" </textarea>";
        String hidden = "<input type=\"hidden\" name=\"filename\" value=\"" + fileName + "\"/>";
        String hiddenAction = "<input type=\"hidden\" name=\"action\" value=\"save\"/>";
        String label = "<label>" + fileName +  "</label>";
        String btn = "<button type=\"submit\">save</button>";
        return "<form action=\"filelist\" method = \"post\">" + hiddenAction + hidden + label + textArea + btn + "</form>";
    }

    public static String getAddFrom(){
        return "<form method=\"post\" action=\"createfile\">\n" +
                "    <input type=\"hidden\" name=\"action\" value=\"save\" />\n" +
                "    <label>filename</label>\n" +
                "    <input name=\"filename\" />\n" +
                "    <label>Content</label>\n" +
                "    <textarea name=\"content\"> </textarea>\n" +
                "    <button type=\"submit\">save</button>\n" +
                "</form>";
    }

    private static String getEditButton(String fileName){
        return getAction(fileName, "edit");
    }
    private static String getDeleteButton(String fileName){
        return getAction(fileName, "delete");
    }
}
