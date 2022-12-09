package tools;

import java.util.*;

public class RequestHttp {

    public String GettingMethod(ArrayList lists) {
        String url = "";
        for (int i = 0; i < lists.size(); i++) {
            String valuable = (String) lists.get(i);
            if (valuable.contains("GET /") && valuable.contains("favicon.ico") == false) {
                url = valuable;
                break;
            }
            if (valuable.contains("POST /") && valuable.contains("favicon.ico") == false) {
                url = valuable;
                break;
            }
        }
        return url.split(" ")[0];
    }

    public String getUrl(ArrayList lists) {
        String url = "";
        for (int i = 0; i < lists.size(); i++) {
            String valuable = (String) lists.get(i);
            if (valuable.contains("GET /") && valuable.contains("favicon.ico") == false) {
                url = valuable;
                break;
            }
            if (valuable.contains("POST /") && valuable.contains("favicon.ico") == false) {
                url = valuable;
                break;
            }
        }
        return url.split(" ")[1];
    }

    public String sentdata(String url) {
        if (url.contains("?")) {
            return url.split("\\?")[0];
        } else {
            return url;
        }
    }
}
