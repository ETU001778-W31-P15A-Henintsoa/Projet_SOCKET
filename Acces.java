package file;

import javax.swing.*;
import java.io.*;

public class Acces extends File {
    public Acces(String path) throws Exception {
        super(path);
        if (!exists()) {
            this.createNewFile();
        }
    }

    public void writer(String content) throws Exception {
        FileWriter manoratra = new FileWriter(this);
        BufferedWriter soratra = new BufferedWriter(manoratra);
        soratra.write(content);
        soratra.close();
        manoratra.close();
    }

    public String reader() throws Exception {
        try (BufferedReader mamaky = new BufferedReader(new InputStreamReader(new FileInputStream(this)))) {
            String m = mamaky.readLine();
            return m;
        }
    }

}
