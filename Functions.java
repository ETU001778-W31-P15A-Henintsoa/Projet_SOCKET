package tools;

import java.io.*;

public class Functions {

    public String getUrl(String bytes) {
        String[] answer = bytes.split(" ");
        return answer[1];
    }

    public String[] directories_files(String my_directory) {
        File file = new File(my_directory);
        String[] all_file = new String[file.list().length];
        for (int i = 0; i < all_file.length; i++) {
            all_file[i] = file.list()[i];
        }
        return all_file;
    }

    public String displaying(String[] data) {
        String returning = "<table><tr><th>All files</th><th>Last modified</th></tr>";
        for (int i = 0; i < data.length; i++) {
            returning = returning + "<tr><td><a href='" + data[i] + "'>" + data[i]
                    + "<a></td><td>Not avalaible</td></tr>";
        }
        returning = returning + "</table>";
        return "returning";
    }

}
