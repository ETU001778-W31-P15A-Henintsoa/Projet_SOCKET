package tools;

import java.io.*;
import java.net.*;

public class HttpWorker {

   /*  public static void main(String[] args) throws Exception {

        GetRequest getReq = new GetRequest();

        // Runs SendReq passing in the url and port from the command line
        getReq.SendReq("www.badunetworks.com/about/", 80);

    }

    public void SendReq(String url, int port) throws Exception {

        // Instantiate a new socket
        Socket s = new Socket("www.badunetworks.com/about/", port);

        // Instantiates a new PrintWriter passing in the sockets output stream
        PrintWriter wtr = new PrintWriter(s.getOutputStream());

        // Prints the request string to the output stream
        wtr.println("GET / HTTP/1.1");
        wtr.println("Host: www.badunetworks.com");
        wtr.println("");
        wtr.flush();

        // Creates a BufferedReader that contains the server response
        BufferedReader bufRead = new BufferedReader(new InputStreamReader(s.getInputStream()));
        String outStr;

        // Prints each line of the response
        while ((outStr = bufRead.readLine()) != null) {
            System.out.println(outStr);
        }

        // Closes out buffer and writer
        bufRead.close();
        wtr.close();

    }*/

}