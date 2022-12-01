package server;

import java.net.*;
import java.nio.file.Path;
import java.lang.*;
import java.io.*;
import java.util.*;
import tools.*;

public class WebServer {
    /**
     * Start a Server Socket to monitor client requests and dispatches the http
     * request to HttpWorkers.
     */
    public static void main(String[] args) throws Exception {
        Functions f = new Functions();

        // création de la socket
        int port = 1989;
        ServerSocket serverSocket = new ServerSocket(port);
        System.err.println("Serveur lancé sur le port : " + port);

        // repeatedly wait for connections, and process
        while (true) {
            // on reste bloqué sur l'attente d'une demande client
            Socket clientSocket = serverSocket.accept();
            System.err.println("Nouveau client connecté");
            // on ouvre un flux de conversation

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(
                    new BufferedWriter(
                            new OutputStreamWriter(clientSocket.getOutputStream())),
                    true);

            // chaque fois qu'une donnée est lue sur le réseau on la renvoi sur le flux
            // d'écriture.
            // la donnée lue est donc retournée exactement au même client.
            String s = new String();
            while ((s = in.readLine()) != null) {
                // System.out.println(s);
                if (s.equalsIgnoreCase("GET / HTTP/1.1")) {
                    System.out.println(f.getUrl(s));
                    String url = f.getUrl(s);
                    if (url.equals("/")) {
                        System.out.println(url.split("/")[0] + "oui");
                        String[] data = f.directories_files(url);
                        System.out.println("moi");
                        String outing = f.displaying(data);
                        out.println(outing);
                    }
                }

                // try (Socket server = serverSocket.accept()) {
                // Date today = new Date();
                /*
                 * if (s.equalsIgnoreCase("GET / HTTP/1.1")) {
                 * File f = new File("my_www");
                 * String[] all_file = new String[f.list().length];
                 * for (int i = 0; i < all_file.length; i++) {
                 * all_file[i] = f.list()[i];
                 * }
                 * String httpResponse = "HTTP/1.1 200 OK\r\n\r\n" + all_file[0];
                 * for (int i = 1; i < all_file.length; i++) {
                 * httpResponse = httpResponse + "\n" + all_file[i];
                 * }
                 * clientSocket.getOutputStream()
                 * .write(httpResponse.getBytes("UTF-8"));
                 * }
                 * // }
                 */

            }

            // on ferme les flux.
            System.err.println("Connexion avec le client terminée");
            out.close();
            in.close();
            clientSocket.close();
        }
    }
}
