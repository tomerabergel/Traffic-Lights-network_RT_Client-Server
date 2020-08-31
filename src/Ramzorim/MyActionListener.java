package Ramzorim;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JRadioButton;


public class MyActionListener implements ActionListener {

    public static boolean degel=false;
    public int count=2;
    public Event64 evENDpressed;
    public Event64 evShabat;
    public Event64 evPressed;
    public  Event64 evHolI;
    public void MyActionListener(Event64 evShabat,Event64 evPressed,  Event64 evHolI, Event64 evENDpressed ) {
        this.evShabat = evShabat;
        this.evPressed=evPressed;
        this.evHolI=evHolI;
        this.evENDpressed=evENDpressed;

    }

    public void actionPerformed(ActionEvent e) {
        JRadioButton butt = (JRadioButton) e.getSource();
        int buttonNumber = Integer.parseInt(butt.getName());
        System.out.println("כפתור מספר:  " + "  " + buttonNumber+" נלחץ");
        int key = Integer.parseInt(butt.getName());
        if (key == 16)
            if (butt.isSelected()) {
                System.out.println("כפתור מספר 16-מצב שבת");
                evShabat.sendEvent();
            } else {
                System.out.println("כפתור מספר 16-מצב חול");
                evHolI.sendEvent();
                //  evHolI.sendEvent(5);
            }

      /*
        else if (count == 0 ) {

            evPressed.sendEvent(key);
            System.out.println("  כפתור לחוץ על דגל   " + key);
            // butt.setSelected(false);
            butt.setEnabled(false);
            count = 1;
        }
else if(evENDpressed.arrivedEvent()){
            evENDpressed.waitEvent();
            evPressed.sendEvent(key);
            System.out.println(" כפתור לחוץ על ידי אירוע" + key);
            // butt.setSelected(false);
            butt.setEnabled(false);
            count = 1;

        }
        else {
            System.out.println("כבר לחצת");
            butt.setSelected(false);
        }

  */
        else if(evENDpressed.arrivedEvent()||count ==2){
            {

                if(count!=2)
                    evENDpressed.waitEvent();
                evPressed.sendEvent(key);
                System.out.println("כפתור מספר " + key+" יאלה לעבור מצב");
                // butt.setSelected(false);
                butt.setEnabled(false);
                count = 0;
            }

        }
        else {
            System.out.println("כבר לחצת");
            butt.setSelected(false);
        }


    }
}