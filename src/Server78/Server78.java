package Server78;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

class Server78 extends Thread 	   //the parallel server
{
int i;
    int DEFAULT_PORT = 5000;
    ServerSocket listenSocket;
    Socket clientSockets;
    public ServerWin78 myServerWin78;
    public List<Socket> Sockets;
    public List<Event64> evJunction;

    public Server78()   // constructor of a TCP server
    {
        try
        {
            listenSocket = new ServerSocket(DEFAULT_PORT);
        } catch (IOException e)    //error
        {
            System.out.println("Problem creating the server-socket");
            System.out.println(e.getMessage());
            System.exit(1);
        }

        System.out.println("Server starts on port " + DEFAULT_PORT);
        System.out.println("הסרבר יצר קשר");
        myServerWin78 = new ServerWin78();
        Sockets = new ArrayList<Socket>();
        evJunction = new ArrayList<Event64>();
        start();
    }

    public void run()
    {
        try
        {
            new Thread(new Runnable(){
                public void run() {
                    try {
                        while (true) {
                            if (!myServerWin78.isAlive)
                                System.exit(0);
                            sleep(3000);
                        }
                    }
                    catch (Exception e) { }
                }
            }).start();

            while (true)
            {
             i++;
                System.out.println("הקליינט וסוקט עובדים"+i);
                Event64 tmp = new Event64();
                clientSockets = listenSocket.accept();
                Sockets.add(clientSockets);
                System.out.println("הסוקטים הינם"+Sockets);
                evJunction.add(tmp);
                myServerWin78.AddSocket(clientSockets, new Dialog78(clientSockets, this, tmp));
            }

        } catch (IOException e)
        {
            System.out.println("Problem listening server-socket");
            System.exit(1);
        }

        System.out.println("end of server");
    }
}
