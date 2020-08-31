package Client78;
import Ramzorim.Event64;
import java.io.*;
import java.net.*;

public class Client78 extends Thread{

    Event64 myConnetion;
    String SERVERHOST = "Localhost";
    //String SERVERHOST = "192.168.43.205";
    int DEFAULT_PORT =5000;
    Socket clientSocket = null;
    BufferedReader bufferSocketIn;
    public PrintWriter bufferSocketOut;
    BufferedReader keyBoard;
    String line;
    static int c=0;
    public Client78(Event64 evClientConn) {
        this.myConnetion = evClientConn;

        setDaemon(true);
        c++;
        System.out.println("אני בקליינט"+c);
        start();
    }

    public void run() {
        try {
            // Request to server
            clientSocket = new Socket(SERVERHOST, DEFAULT_PORT);
            // Init streams to read/write text in this socket
            bufferSocketIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            bufferSocketOut = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())), true);

            while (true) {
                line = bufferSocketIn.readLine(); // reads a line from the server
                System.out.println( myConnetion+"    "+line +"כרגע אני נמצא client-serve    ");
                myConnetion.sendEvent(line);

            }
        } catch (IOException e) {
            System.err.println(e);
        } finally {
            try {
                if (clientSocket != null) {
                    clientSocket.close();
                }
            } catch (IOException e2) {
            }
        }
    }
}
