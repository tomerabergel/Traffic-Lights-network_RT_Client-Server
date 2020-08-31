package Ramzorim;

import java.awt.Color;

import javax.swing.JPanel;






class Echad extends Thread
{
    Ramzor ramzor;
    JPanel panel;
    String state="Yellow";
    MyTimer72 timer;
    Event64   evTimer;
    Event64   evTime=new Event64();
    public Echad( Ramzor ramzor,JPanel panel)
    {

        this.ramzor=ramzor;
        this.panel=panel;

        start();
    }

    public void run()
    {
        try
        {
            setLight(1,Color.yellow);

            while (true)

            {
                switch (state) {
                    case "Yellow" :


                        //MyTimer72 mytimer1 = new MyTimer72(500, evTime);
                        // evTime.waitEvent();
                        sleep(500);
                        setLight(1, Color.yellow);
                        state="Gray";


                        break;


                    case "Gray" :

                        // MyTimer72 mytimer2 = new MyTimer72(500, evTime);
                        //evTime.waitEvent();
                        sleep(500);
                        setLight(1, Color.gray);
                        state="Yellow";


                        break;


                }
            }
        } catch (InterruptedException e) {}

    }
    public void setLight(int place, Color color)
    {
        ramzor.colorLight[place-1]=color;
        panel.repaint();
    }
}



