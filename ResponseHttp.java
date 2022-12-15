package tools;

import file.*;

import java.io.*;
import java.net.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class ResponseHttp {

    /// TRaitement path: voir Readme.md pour plus de specification
    public String deleteSlash(String path) {
        String answer = "";
        answer = answer + String.valueOf(path.toCharArray(), 1, path.toCharArray().length - 1);
        return answer;
    }

    /// GET FILE AND DIRECTORIES IN A DIRECTORY
    public String[] directories_files(String my_directory) {
        System.out.println(my_directory);
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

    /// VERIFICATION
    // Verify if its a directory or a file
    public int verifyfileordirectorie(String path) {
        File f = new File(path);
        if (f.isFile()) {
            return 0;
        } else if (f.isDirectory()) {
            return 1;
        }
        return -1;
    }

    // Verify if the file or the directory exixts
    public int verifyExisting(String path) {
        File f = new File(path);
        if (f.exists()) {
            return 1;
        } else {
            return 0;
        }
    }

    /// CONFIGURATION
    public String readConfigurations(String libelle) throws Exception {
        Acces f = new Acces("henintsoaConfig.ini");
        String[] block = f.reader().split("///");
        for (int i = 0; i < block.length; i++) {
            String[] blocks = block[i].split(":");
            if (blocks.length != 0 && blocks[0].equalsIgnoreCase(libelle)) {
                return blocks[1];
            }
        }
        return " ";
    }

    /// HEADERS
    public void headers(PrintWriter out, String chiffre, int len, String path) throws Exception {
        Acces f = new Acces("henintsoaConfig.ini");
        Acces file = new Acces(path);

        if (f.exists() == true) {
            out.write("HTTP/1.0 " + chiffre + " YES\r\n");
            out.write(
                    "Date:" + String.valueOf(LocalDate.now()) + " " + String.valueOf(LocalTime.now()) + " "
                            + "GMT\r\n");
            out.write("Server: " + readConfigurations("NOM_APACHE") + "\r\n");
            out.write("Content-Type: text/html\r\n");
            out.write("Content-Length: " + String.valueOf(len) + "\r\n");
            out.write("Expires: None\r\n");
            out.write("Last-modified: " + file.lastModified() + " GMT\r\n");
            out.write("\r\n");
        }
    }

    /// ERROR
    public void error1778(PrintWriter out) throws Exception {
        String error = "<center><h3 style='color=red;'> Error 1778 </h3><p>The file extention must be .html or .php </p><enter>";
        headers(out, "1778", error.length(), "error.ini");
        out.write("<title>Error</title>\n");
        out.write(error);
        out.flush();
    }

    public void error031(PrintWriter out) throws Exception {
        String error = "<center><h3 style='color:red;'> Error 031 </h3><p>File not found</p><enter>";
        headers(out, "031", error.length(), "error.ini");
        out.write("<title>Error</title>\n");
        out.write(error);
        out.flush();
    }

    /// TRAITEMENT DONNES
    // Pour la fonction verifyString() voit ReadMe.md pour plus de specification
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

    /// CONTENTS OF THE FILE(READING FILES)
    public String readingfile(String path) throws Exception {
        File f = new File(path);
        String answer = "";
        Scanner scanner = new Scanner(f);
        while (scanner.hasNextLine()) {
            answer = answer.concat(scanner.nextLine() + "\n");
        }
        return answer;
    }

    public File writeInaFile(String path) throws Exception {
        Acces file = new Acces("withscript.php");
        PrintWriter out = null;
        FileWriter writer = new FileWriter(file);
        out = new PrintWriter(writer);
        file.writer(readingfile("script.php") + readingfile(path));
        return file;
    }

    /// READING A PHP FILE
    public String readphpfile(String path, String[] data) throws Exception {
        Runtime execution = Runtime.getRuntime();
        Acces file = new Acces(path);
        String readerfile = file.reader();
        String datas = " ";
        Process process;
        if (data != null) {
            datas = gettdata(data);
            process = execution.exec("php-cgi " + writeInaFile(path).getPath() + " " + datas);
        } else {
            process = execution.exec("php-cgi " + path);
        }
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
