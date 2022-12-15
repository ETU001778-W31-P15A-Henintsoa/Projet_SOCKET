package server;

import java.net.*;
import java.net.http.HttpRequest;
import java.nio.file.Path;
import java.lang.*;
import java.io.*;
import java.util.*;
import tools.*;

public class WebServer {
    public static void main(String[] args) {

        ServerSocket serverSocket = null;
        BufferedReader in = null;
        PrintWriter out = null;
        Socket clientSocket = null;

        try {
            // MES CLASSES
            RequestHttp request = new RequestHttp();
            ResponseHttp response = new ResponseHttp();
            String ressource = response.readConfigurations("DEFAULT");
            int port = Integer.valueOf(response.readConfigurations("PORT"));

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
                String averina = "";
                String error = "";
                int valeur = 0;
                ArrayList lists = new ArrayList<String>();

                while ((s = in.readLine()) != null) {
                    System.out.println(s);
                    lists.add(s);
                    if (s.contains("Content-Length")) {
                        valeur = Integer.valueOf(s.split(":")[1].trim());
                    }
                    if (s.isEmpty()) {
                        break;
                    }
                }

                if (valeur > 0) {
                    char[] data = new char[valeur];
                    in.read(data, 0, valeur);
                    averina = new String(data);
                }

                if ((request.GettingMethod(lists)).equalsIgnoreCase("GET")) {
                    if ((request.getUrl(lists)).equalsIgnoreCase("/")
                            || (request.getUrl(lists)).equalsIgnoreCase("/favicon.ico")) {
                        String[] data = response.directories_files(ressource);
                        clientSocket.shutdownInput();
                        response.headers(out, "200", response.ResponseSlash(data).length(), ressource);
                        out.write("<title>My Server</title>");
                        out.write(response.ResponseSlash(data));
                        out.flush();
                        clientSocket.shutdownOutput();
                    }
                    if ((request.getUrl(lists)).equalsIgnoreCase("/") == false
                            && (request.getUrl(lists)).equalsIgnoreCase("/favicon.ico") == false) {
                        String url = response.deleteSlash(request.getUrl(lists));
                        String vrai = url;
                        url = request.sentdata(url);
                        if (response.verifyExisting(url) == 1) {
                            if (response.verifyfileordirectorie(url) == 1) {
                                String[] data = response.directories_files(url);
                                clientSocket.shutdownInput();
                                response.headers(out, "200", response.ResponseSlash(data).length(), url);
                                out.write("<title>My Server</title>\n");
                                out.write(response.ResponseSlash(data));
                                out.flush();
                                clientSocket.shutdownOutput();
                            }
                            if (response.verifyfileordirectorie(url) == 0) {
                                if (url.split("/")[url.split("/").length - 1].contains(".html")) {
                                    clientSocket.shutdownInput();
                                    response.headers(out, "200", response.readingfile(url).length(), url);
                                    out.write(response.readingfile(url));
                                    out.flush();
                                    clientSocket.shutdownOutput();
                                } else if (url.split("/")[url.split("/").length - 1].contains(".php")) {
                                    url = request.sentdata(vrai);
                                    clientSocket.shutdownInput();
                                    if (vrai.contains("?")) {
                                        response.headers(out, "200",
                                                response.readphpfile(url, response.datas(vrai, "&")).length(), url);
                                        out.write(response.readphpfile(url, response.datas(vrai, "&")));
                                    } else {
                                        response.headers(out, "200",
                                                response.readphpfile(url, null).length(), url);
                                        out.write(response.readphpfile(url, null));
                                    }
                                    out.flush();
                                    clientSocket.shutdownOutput();
                                } else {
                                    clientSocket.shutdownInput();
                                    response.error1778(out);
                                    clientSocket.shutdownOutput();
                                }
                            }
                        } else {
                            clientSocket.shutdownInput();
                            response.error031(out);
                            clientSocket.shutdownOutput();
                        }
                    }
                }

                if ((request.GettingMethod(lists)).equalsIgnoreCase("POST")) {
                    if ((request.getUrl(lists)).equalsIgnoreCase("/") == false
                            && (request.getUrl(lists)).equalsIgnoreCase("/favicon.ico") == false) {
                        String url = response.deleteSlash(request.getUrl(lists));
                        if (response.verifyExisting(url) == 1) {
                            if (response.verifyfileordirectorie(url) == 1) {
                                String[] data = response.directories_files(url);
                                clientSocket.shutdownInput();
                                response.headers(out, "555", 29, "error.ini");
                                out.write("<title>Error</title>\n");
                                out.write("<center>IS A DIRECTORY</center>");
                                out.flush();
                                clientSocket.shutdownOutput();
                            }
                            if (response.verifyfileordirectorie(url) == 0) {
                                if (url.split("/")[url.split("/").length - 1].contains(".html")) {
                                    clientSocket.shutdownInput();
                                    response.headers(out, "200", response.readingfile(url).length(), url);
                                    out.write(response.readingfile(url));
                                    out.flush();
                                    clientSocket.shutdownOutput();
                                } else if (url.split("/")[url.split("/").length - 1].contains(".php")) {
                                    String vrai = url;
                                    if (averina != null && averina.isEmpty() == false) {
                                        vrai = vrai + "\\?" + averina;
                                    }
                                    clientSocket.shutdownInput();
                                    response.headers(out, "200",
                                            response.readphpfile(url, response.datas(vrai, "&")).length(), url);
                                    if (vrai.contains("\\?")) {
                                        out.write(response.readphpfile(url, response.datas(vrai, "&")));
                                    } else {
                                        out.write(response.readphpfile(vrai, null));
                                    }
                                    out.flush();
                                    clientSocket.shutdownOutput();
                                } else {
                                    clientSocket.shutdownInput();
                                    response.error1778(out);
                                    clientSocket.shutdownOutput();
                                }
                            }
                        } else {
                            clientSocket.shutdownInput();
                            response.error031(out);
                            clientSocket.shutdownOutput();
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
