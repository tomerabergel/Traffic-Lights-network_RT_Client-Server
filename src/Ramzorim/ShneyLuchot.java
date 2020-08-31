package Ramzorim;
import java.awt.Color;

import javax.swing.JPanel;



class ShneyLuchot extends Thread
{
    int date;
    Ramzor ramzor;
    JPanel panel;
    private Event64 evGreen,evRed,evShabat,evHol,evAck;
    private Event64 evEndGreen,evEndRed,evEndShabat,evEndHol,evEndAck;
    enum OutState {Hol,Shabat};
    OutState outState;
    enum InState {Red,Green};
    InState intState;
    private boolean history=false;
    public boolean finish=false;
    public boolean out=false;
    public boolean temp_shabat=false;
    public int i;

    // public BuildTrafficLight buildTrafficLight=new BuildTrafficLight();


    public ShneyLuchot(Ramzor ramzor, JPanel panel) {
        this.ramzor = ramzor;
        this.panel = panel;
        this.start();
    }

    public ShneyLuchot( Ramzor ramzor,JPanel panel, Event64 evGreen, Event64 evRed, Event64 evHol, Event64 evShabat,Event64  evEndGreen,Event64 evEndRed,Event64 evEndShabat,Event64 evEndHol,Event64 evEndAck)
    {
        this.ramzor = ramzor;
        this.panel = panel;
        this.evGreen = evGreen;
        this.evRed = evRed;
        this.evHol = evHol;
        this.evShabat = evShabat;
        this.ramzor=ramzor;
        this.panel=panel;
        this.evEndGreen=evEndGreen;
        this.evEndRed=evEndRed;
        this.evEndShabat=evEndShabat;
        this.evEndHol=evEndHol;
        this.evEndAck=evEndAck;
        start();

    }

    public void run()
    {
        //init();
        outState = OutState.Hol;
        intState = InState.Green;

        try {

            while (!finish)

            {

                switch (outState)
                {
                    case Hol:



                        while (!out)
                        {

                            switch (intState)

                            {
                                case Red:
                                    //  while(true) {


                                    if (evGreen.arrivedEvent()) {


                                        evGreen.waitEvent();
                                        sleep(1000);
                                        setLight(2, Color.green);
                                        setLight(1, Color.GRAY);
                                        intState = InState.Green;


                                        break;

                                    }

                                    else if (evShabat.arrivedEvent()) {
                                        evShabat.waitEvent();
                                        System.out.println("אנחנו במצב שבת-רמזורי הולכי רגל");
                                        outState = OutState.Shabat;
                                        out = true;
                                        break;
                                    }

                                    else
                                        yield();
                                    break;


                                case Green:
                                    //  while(true) {

                                    if (evRed.arrivedEvent()) {

                                        evRed.waitEvent();
                                        setLight(1, Color.red);
                                        setLight(2, Color.GRAY);
                                        evEndAck.sendEvent();
                                        intState = InState.Red;
                                        break;
                                    }


                                    else if (evShabat.arrivedEvent()) {
                                        System.out.println("אנחנו במצב שבת-רמזורי הולכי רגל");
                                        evShabat.waitEvent();
                                        outState = OutState.Shabat;
                                        out = true;
                                        break;
                                    }
                                    //   }

                                    else
                                        yield();
                                    break;       //}
                            }//switch
                        }//while-out
                    case Shabat:

                        // evEndHol.sendEvent();
                        //while(true)
                        // {
                        //   while(!temp_shabat)


                        //        {


                        // setLight(2, Color.gray);
                        //    setLight(1, Color.red);
                        // sleep(5000);

                        setLight(2, Color.gray);
                        setLight(1, Color.gray);



                        // System.out.println("אני בשני לוחות  " );
                        if (evHol.arrivedEvent()) {

                            date = (int) evHol.waitEvent();
                            evEndShabat.sendEvent();
                            outState = OutState.Hol;
                            intState=InState.Green;
                            out = false;
                            temp_shabat = true;
                            System.out.println("כרגע אנחנו יוצאים ממצב שבת לחול-הולכי רגל");
                            break;
                        }


                        // }
                        else
                            yield();

                        // }
                        break;
                }

            }
        } catch (InterruptedException e) { }

    }
    public void setLight(int place, Color color)
    {
        ramzor.colorLight[place-1]=color;
        panel.repaint();
    }
}
