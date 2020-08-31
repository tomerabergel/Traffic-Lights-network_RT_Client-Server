package Server78;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Dialog78 extends Thread // parallel dialogs on the same socket
{
    Socket client;
    Server78 myServer;
    Event64 evJunction;
    BufferedReader bufferSocketIn;
    PrintWriter bufferSocketOut;

    public Dialog78(Socket clientSocket, Server78 myServer, Event64 evJunction)
    {
        client = clientSocket;
        this.myServer = myServer;
        this.evJunction = evJunction;
        try
        {
            // Init streams to read/write text in this socket
            bufferSocketIn = new BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream()));
            bufferSocketOut = new PrintWriter(
                    new BufferedWriter(
                            new OutputStreamWriter(
                                    clientSocket.getOutputStream())), true);
        } catch (IOException e)
        {
            try
            {
                client.close();
            } catch (IOException e2)
            {
            }
            System.err.println("server:Exception when opening sockets: " + e);
            return;
        }
        start();
    }

    public void run()
    {
        String line;
        boolean stop=false;
        try
        {
            while (true)
            {
                line = bufferSocketIn.readLine();
               System.out.println("אנחנו בדיאלוג+line");
                if (myServer.myServerWin78.isControlled)
                {
                    System.out.println("אנחנו בדיאלוג+line");
                    evJunction.sendEvent(line);
                }
                if (line == null)
                    break;
                if (line.equals("end"))
                    break;
            }
        } catch (IOException e)
        {
        } finally
        {
            try
            {
                client.close();
            } catch (IOException e2)
            {
            }
        }
    }

    void exit()
    {
        try
        {
            client.close();
        } catch (IOException e2)
        {
        }
    }
}
