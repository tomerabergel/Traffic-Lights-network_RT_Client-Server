package Ramzorim;

import java.awt.Color;

import javax.swing.JPanel;

/*
 * Created on Mimuna 5767  upDate on Tevet 5770
 */

/**
 * @author
 */

public class ShloshaAvot extends Thread
{
    int q=0;
    Ramzor ramzor;
    JPanel panel;
    private boolean stop=true;
    private Event64 evGreen,evRed,evShabat,evHol,evAck,evRedCarITCHOUL,evRedCarITCHOULAck;
    private Event64 evEndGreen,evEndRed,evEndShabat,evEndHol,evEndAck;
    enum OutState {Hol,Shabat};
    ShloshaAvot.OutState outState;
    enum InState {Itchoul,Red, Red_Orange, Green, Gray, Green_flickering, Orange};
    ShloshaAvot.InState intState;
    private boolean history=false;
    public boolean finish=false;
    public boolean out=false;
    public boolean temp_shabat=false;
    public  int date;


    public boolean temp=true;

    public ShloshaAvot( Ramzor ramzor,JPanel panel,int key)
    {
        this.ramzor=ramzor;
        this.panel=panel;
        new CarsMaker(panel,this,key);
        start();
    }

    public ShloshaAvot(Ramzor ramzor, JPanel panel, Event64 evGreen, Event64 evRed,Event64 evHol, Event64 evShabat, Event64 evEndShabat, Event64 evEndHol, Event64 evEndAck,Event64 evRedCarITCHOUL, Event64 evRedCarITCHOULAck,int key)
    {
        this.evEndHol=evEndHol;
        this.evGreen = evGreen;
        this.evRed = evRed;
        this.evHol = evHol;
        this.evShabat = evShabat;
        this.ramzor=ramzor;
        this.panel=panel;
        this.evEndAck=evEndAck;
        this.evRedCarITCHOUL=evRedCarITCHOUL;
        this.evRedCarITCHOULAck=evRedCarITCHOULAck;
        this.evEndShabat=evEndShabat;
        new CarsMaker(panel,this,key);
        start();

    }

    public void run()
    {


        try {
            outState = OutState.Hol;
            intState = InState.Itchoul;

            while (!finish)
            {

                switch (outState) {
                    case Hol:


                        while (!out) {

                            switch (intState)

                            {

                                case Itchoul:
                                    //evRed.arrivedEvent()&&
                                    if (evRedCarITCHOUL.arrivedEvent()) {
                                        // date=(int)evRed.waitEvent();
                                        //   if (date==1) {
                                        evRedCarITCHOUL.waitEvent();
                                        setLight(3, Color.gray);
                                        setLight(2, Color.gray);
                                        setLight(1, Color.red);

                                        evRedCarITCHOULAck.sendEvent();

                                        intState = InState.Red;
                                        break;

                                        // }

                                    } else if (evShabat.arrivedEvent()) {
                                        evShabat.waitEvent();
                                        outState = ShloshaAvot.OutState.Shabat;
                                        out = true;
                                        break;
                                    } else
                                        yield();
                                    break;


                                case Red:
                                    if (evGreen.arrivedEvent()) {
                                        evGreen.waitEvent();
                                        setLight(1, Color.red);
                                        setLight(2, Color.orange);
                                        setLight(3, Color.gray);
                                        sleep(1000);
                                        intState = InState.Red_Orange;
                                        break;

                                    } else if (evShabat.arrivedEvent()) {
                                        evShabat.waitEvent();
                                        outState = ShloshaAvot.OutState.Shabat;
                                        out = true;
                                        break;
                                    } else
                                        yield();
                                    break;


                                case Red_Orange:

                                    stop = false;
                                    setLight(1, Color.gray);
                                    setLight(2, Color.gray);
                                    setLight(3, Color.green);

                                    intState = InState.Green;

                                    if (evShabat.arrivedEvent()) {
                                        evShabat.waitEvent();
                                        evEndHol.sendEvent();
                                        outState = ShloshaAvot.OutState.Shabat;
                                        out = true;
                                        break;
                                    } else
                                        yield();
                                    break;

                                case Green:

                                    if (evRed.arrivedEvent()) {

                                        evRed.waitEvent();

                                        intState = InState.Gray;

                                    } else
                                        yield();

                                    break;


                                case Gray:
                                    //sleep(500);
                                    setLight(1, Color.gray);
                                    setLight(2, Color.gray);
                                    setLight(3, Color.green);


                                    intState = InState.Green_flickering;
                                    if (evShabat.arrivedEvent()) {
                                        evShabat.waitEvent();
                                        outState = ShloshaAvot.OutState.Shabat;
                                        out = true;
                                        break;

                                    } else
                                        yield();
                                    break;

                                case Green_flickering:


                                    sleep(500);
                                    setLight(1, Color.gray);
                                    setLight(2, Color.gray);
                                    setLight(3, Color.gray);
                                    sleep(500);
                                    setLight(3, Color.green);
                                    setLight(1, Color.gray);
                                    setLight(2, Color.gray);
                                    sleep(500);
                                    setLight(1, Color.gray);
                                    setLight(2, Color.gray);
                                    setLight(3, Color.gray);
                                    sleep(500);
                                    setLight(3, Color.green);
                                    setLight(1, Color.gray);
                                    setLight(2, Color.gray);
                                    sleep(500);
                                    setLight(1, Color.gray);
                                    setLight(2, Color.gray);
                                    setLight(3, Color.gray);
                                    sleep(500);

                                    setLight(1, Color.gray);
                                    setLight(2, Color.orange);
                                    setLight(3, Color.gray);
                                    intState = InState.Orange;


                                    if (evShabat.arrivedEvent()) {
                                        evShabat.waitEvent();
                                        outState = ShloshaAvot.OutState.Shabat;
                                        out = true;
                                        break;
                                    } else
                                        yield();

                                    break;

                                case Orange:
                                    stop = true;
                                    sleep(1000);
                                    setLight(3, Color.gray);
                                    setLight(2, Color.gray);
                                    setLight(1, Color.red);
                                    evEndAck.sendEvent();
                                    ;

                                    intState = InState.Red;
                                    if (evShabat.arrivedEvent()) {
                                        evShabat.waitEvent();
                                        outState = ShloshaAvot.OutState.Shabat;
                                        out = true;
                                        break;
                                    } else
                                        yield();
                                    break;
                            }
                        }

                    case Shabat:
                        System.out.println("אנחנו במצב שבת-רכבים");
                        // evEndHol.sendEvent();
                        // while (!temp_shabat) {
                        //  {

                        //   setLight(3,Color.gray);
                        // setLight(2,Color.gray);
                        //setLight(1, Color.red);
                        //sleep(5000);
                        //  evEndAck.sendEvent(" Ack Red");;

                        //  while(temp) {
                        // sleep(500);


                        // sleep(500);
                        if (true) {
                            while (temp_shabat == false) {

                                sleep(500);
                                setLight(1, Color.GRAY);
                                setLight(2, Color.YELLOW);
                                setLight(3, Color.GRAY);
                                sleep(500);

                                setLight(1, Color.GRAY);
                                setLight(2, Color.GRAY);
                                setLight(3, Color.GRAY);
                                if (evHol.arrivedEvent())
                                {
                                    date = (int) evHol.waitEvent();
                                    temp_shabat = true;}
                            }

                            evEndShabat.sendEvent();
                            outState = ShloshaAvot.OutState.Hol;
                            intState = InState.Itchoul;
                            out = false;
                            System.out.println("כרגע אנחנו יוצאים ממצב שבת לחול-רכבים");
                            //temp_shabat = true;
                            // temp = false;

                        }
                        else
                            yield();

                        break;

                    //evEndAck.sendEvent();

                    //  System.out.println("אני בשלושה אבות  " );
                    // if (evHol.arrivedEvent()) {
                    //date=(int)
                    // date = (int) evHol.waitEvent();


                }



                //  }

                //}
            }
            //   break;
            // }

       /* try
        {
          /*  while (true)
            {
                sleep(1000);
                setLight(2,Color.YELLOW);
                sleep(1000);
                setLight(1,Color.LIGHT_GRAY);
                setLight(2,Color.LIGHT_GRAY);
                setLight(3,Color.GREEN);
                stop=false;
                sleep(3000);
                stop=true;
                setLight(1,Color.LIGHT_GRAY);
                setLight(2,Color.YELLOW);
                setLight(3,Color.LIGHT_GRAY);
                sleep(1000);
                setLight(1,Color.RED);
                setLight(2,Color.LIGHT_GRAY);
                setLight(3,Color.LIGHT_GRAY);
            }
*/        }
        catch (InterruptedException e) {}

    }
    public void setLight(int place, Color color)
    {
        ramzor.colorLight[place-1]=color;
        panel.repaint();
    }

    public boolean isStop()
    {
        return stop;
    }
}


