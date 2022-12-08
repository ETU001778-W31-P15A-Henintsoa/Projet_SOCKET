package tools;

import java.io.*;
import java.util.*;

public class ResponseHttp {
    public String deleteSlash(String path) {
        String answer = "";
        answer = answer + String.valueOf(path.toCharArray(), 1, path.toCharArray().length - 1);
        return answer;
    }

    public String[] directories_files(String my_directory) {
        File file = new File(my_directory);
        String[] all_file = new String[file.list().length];
        for (int i = 0; i < all_file.length; i++) {
            all_file[i] = my_directory.split("/")[my_directory.split("/").length - 1] + "/" + file.list()[i];
            System.out.println(my_directory);
        }
        return all_file;
    }

    public String ResponseSlash(String[] data) {
        if (data.length != 0) {
            String returning = "<table><tr><th>All files</th><th>Last modified</th></tr>";
            for (int i = 0; i < data.length; i++) {
                System.out.println(data[i].split("/")[data[i].split("/").length - 1]);
                returning = returning + "<tr><td><a href='" + data[i] + "'>"
                        + data[i].split("/")[data[i].split("/").length - 1]
                        + "<a></td><td>Not avalaible</td></tr>";
            }
            returning = returning + "</table>";
            return returning;
        } else {
            return "<center><p>No file or directorie</p></center>";
        }
    }

    public int verifyfileordirectorie(String path) {
        File f = new File(path);
        if (f.isFile()) {
            return 0;
        } else if (f.isDirectory()) {
            return 1;
        }
        return -1;
    }

    public int verifyExisting(String path) {
        File f = new File(path);
        if (f.exists()) {
            return 1;
        } else {
            return 0;
        }
    }

    public String readingfile(String path) throws Exception {
        File f = new File(path);
        String answer = "";
        Scanner scanner = new Scanner(f);
        while (scanner.hasNextLine()) {
            answer = answer.concat(scanner.nextLine() + "\n");
        }
        return answer;
    }

}
