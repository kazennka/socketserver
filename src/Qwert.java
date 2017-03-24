import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Qwert {
    public static void main(String[] args) throws IOException {
        ServerSocket ss=new ServerSocket(80);
        while (true){
            Socket s=ss.accept();
            Runnable action = new Runnable() {
                public void run() {
                     ;handle(s);
                }
            };
            new Thread(action).start();
        }
    }

    static void handle(Socket s)  {
        try{
            try{
                BufferedReader rdr = new BufferedReader( new InputStreamReader(s.getInputStream(),"US-ASCII"));
                Pattern p= Pattern.compile("get\\s+(\\S+)");
                while (true) {
                    String line = rdr.readLine();
                    if (line == null || line.isEmpty()) break;
                    Matcher m=p.matcher(line);
                    if (m.find()){
                        String resource = m.group(1);
                        System.out.println(resource);
                    }
                    System.out.println(">>" + line);
                }
                OutputStream os= s.getOutputStream();
                os.write("Hello world".getBytes("US-ASCII"));
            }finally {
                s.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


}
