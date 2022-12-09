package tools;

import java.io.*;
import java.net.*;
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
            String returning = "<center><h3>All Disponible ressources</h3><table style='text-align:center;'><tr><th width='300px'>All files</th><th width='300px'>Type</th></tr>";
            for (int i = 0; i < data.length; i++) {
                returning = returning + "<tr><td><a href='" + data[i] + "'>"
                        + data[i].split("/")[data[i].split("/").length - 1]
                        + "<a></td>";
                if (verifyfileordirectorie(data[i]) == 0) {
                    returning = returning + "<td>File</td></tr>";
                } else {
                    returning = returning + "<td>Directory</td></tr>";
                }
            }
            returning = returning + "</table><center>";
            return returning;
        } else {
            return "<center><p>No file(s) or directorie(s)</p></center>";
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

    public String verifyString(String string) {
        if (string.contains("%27")) {
            String ans = string.split("%27")[1];
            // ans = "'" + String.valueOf(ans.toCharArray(), 0, ans.toCharArray().length -
            // 3) + "'";
            return ans;
        } else {
            return string;
        }
    }

    public String gettdata(String[] datas) {
        String all = "";
        for (int i = 0; i < datas.length; i++) {
            String libelle = datas[i].split("=")[0] + "=" + verifyString(datas[i].split("=")[1]);
            all = all.concat(libelle);
        }
        System.out.println(all);
        return all;
    }

    public String[] datas(String sent, String regex) {
        String[] data = null;
        if (sent.isEmpty() == false && sent != null) {
            data = sent.split("\\?")[1].split("&");
            for (int i = 0; i < data.length; i++) {
                System.out.println(data[i]);
            }
        }
        return data;
    }

    public String readphpfile(String path, String[] data) throws Exception {
        Runtime execution = Runtime.getRuntime();
        String datas = " ";
        if (data != null) {
            datas = gettdata(data);
        }
        Process process = execution.exec("php-cgi " + path + " " + datas);
        InputStream stream = process.getInputStream();
        InputStreamReader streamreader = new InputStreamReader(stream);
        BufferedReader reader = new BufferedReader(streamreader);
        String answer = "";
        String s = "";
        while ((s = reader.readLine()) != null) {
            if (s.equals("")) {
                while ((s = reader.readLine()) != null) {
                    answer = answer.concat(s);
                }
            }
        }
        stream.close();
        streamreader.close();
        reader.close();
        return answer;
    }
}
