package server;

import java.net.*;
import java.nio.file.Path;
import java.lang.*;
import java.io.*;
import java.util.*;
import tools.*;

public class WebServer {
    public static void main(String[] args) {

        int port = 5678;
        ServerSocket serverSocket = null;
        BufferedReader in = null;
        PrintWriter out = null;
        Socket clientSocket = null;

        RequestHttp request = new RequestHttp();
        ResponseHttp response = new ResponseHttp();
        try {
            serverSocket = new ServerSocket(port);
            System.err.println("Serveur lancé sur le port : " + port);

            while (true) {
                clientSocket = serverSocket.accept();
                System.err.println("Nouveau client connecté");

                in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
                out = new PrintWriter(
                        new BufferedWriter(
                                new OutputStreamWriter(clientSocket.getOutputStream())),
                        true);

                String s;
                ArrayList lists = new ArrayList<String>();
                while ((s = in.readLine()) != null) {
                    System.out.println(s);
                    lists.add(s);
                    if (s.equals("")) {
                        break;
                    }
                }

                if ((request.GettingMethod(lists, "GET")).equalsIgnoreCase("GET")) {
                    if ((request.getUrl(lists, "GET")).equalsIgnoreCase("/")
                            || (request.getUrl(lists, "GET")).equalsIgnoreCase("/favicon.ico")) {
                        String[] data = response.directories_files("my_www");
                        clientSocket.shutdownInput();
                        out.write("HTTP/1.0 200 OK\r\n");
                        out.write("Date: Fri, 31 Dec 1999 23:59:59 GMT\r\n");
                        out.write("Server: Apache/0.8.4\r\n");
                        out.write("Content-Type: text/html\r\n");
                        out.write("Content-Length: 59\r\n");
                        out.write("Expires: Sat, 01 Jan 2000 00:59:59 GMT\r\n");
                        out.write("Last-modified: Fri, 09 Aug 1996 14:21:40 GMT\r\n");
                        out.write("\r\n");
                        out.write("<title>My Server</title>");
                        out.write(response.ResponseSlash(data));
                        out.flush();
                        clientSocket.shutdownOutput();
                    }
                    if ((request.getUrl(lists, "GET")).equalsIgnoreCase("/") == false
                            && (request.getUrl(lists, "GET")).equalsIgnoreCase("/favicon.ico") == false) {
                        String url = response.deleteSlash(request.getUrl(lists, "GET"));
                        System.out.println(url);
                        if (response.verifyExisting(url) == 1
                                && response.verifyfileordirectorie(url) == 1) {
                            String[] data = response.directories_files(url);
                            clientSocket.shutdownInput();
                            out.write("HTTP/1.0 200 OK\r\n");
                            out.write("Date: Fri, 31 Dec 1999 23:59:59 GMT\r\n");
                            out.write("Server: Apache/0.8.4\r\n");
                            out.write("Content-Type: text/html\r\n");
                            out.write("Content-Length: 59\r\n");
                            out.write("Expires: Sat, 01 Jan 2000 00:59:59 GMT\r\n");
                            out.write("Last-modified: Fri, 09 Aug 1996 14:21:40 GMT\r\n");
                            out.write("\r\n");
                            out.write("<title>My Server</title>");
                            out.write(response.ResponseSlash(data));
                            out.flush();
                            clientSocket.shutdownOutput();
                        }
                        if (response.verifyExisting(url) == 1
                                && response.verifyfileordirectorie(url) == 0) {
                            System.out.println(url);
                            if (url.split("/")[url.split("/").length - 1].contains(".html")) {
                                clientSocket.shutdownInput();
                                out.write("HTTP/1.0 200 OK\r\n");
                                out.write("Date: Fri, 31 Dec 1999 23:59:59 GMT\r\n");
                                out.write("Server: Apache/0.8.4\r\n");
                                out.write("Content-Type: text/html\r\n");
                                out.write("Content-Length: 59\r\n");
                                out.write("Expires: Sat, 01 Jan 2000 00:59:59 GMT\r\n");
                                out.write("Last-modified: Fri, 09 Aug 1996 14:21:40 GMT\r\n");
                                out.write("\r\n");
                                out.write("<title>My Server</title>");
                                out.write(response.readingfile(url));
                                out.flush();
                                clientSocket.shutdownOutput();
                            } else {
                                clientSocket.shutdownInput();
                                out.write("HTTP/1.0 200 OK\r\n");
                                out.write("Date: Fri, 31 Dec 1999 23:59:59 GMT\r\n");
                                out.write("Server: Apache/0.8.4\r\n");
                                out.write("Content-Type: text/html\r\n");
                                out.write("Content-Length: 59\r\n");
                                out.write("Expires: Sat, 01 Jan 2000 00:59:59 GMT\r\n");
                                out.write("Last-modified: Fri, 09 Aug 1996 14:21:40 GMT\r\n");
                                out.write("\r\n");
                                out.write("<title>My Server</title>");
                                out.write(
                                        "<center><h3 style='color=red;'> Error 1778 </h3><p>The file extention must be .html or .php </p><enter>");
                                out.flush();
                                clientSocket.shutdownOutput();
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
                in.close();
                clientSocket.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }

    }
}
