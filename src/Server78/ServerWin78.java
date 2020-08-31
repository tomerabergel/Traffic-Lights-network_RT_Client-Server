package Server78;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class ServerWin78 extends JFrame
{
    private List<Soketim> mySockets;
    private List<Event64> myJunctions;
    public Boolean isControlled = false;
    public Boolean isAlive;

    Integer currIndex;
    Soketim currSocket;

    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JComboBox comboboxSockets;
    private JButton btnShabbat;
    private JButton btnHol;
    private JButton btnDisconnect;;
    private JButton btnConnect;


//------Background Image-----------
    JLabel back;
    {
        try
        {
            back = new JLabel(new ImageIcon(ImageIO.read(new File("Images/Background.jpg"))));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        back.setLayout(new BorderLayout());
        back.setLayout(new FlowLayout(getWidth()));
        back.setLayout(new FlowLayout(getHeight()));
        this.setContentPane(back);
    }


    public ServerWin78()
    {
        initComponents();
        pack();
        setVisible(true);
        mySockets = new ArrayList<>();
        myJunctions = new ArrayList<>();
        currSocket = null;
        lockButtons();
        btnDisconnect.setEnabled(false);
        isAlive = true;
        setResizable(false);
    }

    @SuppressWarnings({ "unchecked" })
    public void AddSocket(Socket newSocket, Dialog78 newDialog)
    {
        Soketim newExSocket = new Soketim("Crossroads " + mySockets.size(), newSocket, newDialog);
        this.mySockets.add(newExSocket);
        this.myJunctions.add(newDialog.evJunction);
        comboboxSockets.addItem(newExSocket);
        comboboxSockets.setBackground(new Color(240,255,240));
        ((JLabel)comboboxSockets.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        if (this.mySockets.size() >= 2)
            btnConnect.setEnabled(true);
        else if (this.mySockets.size() >= 1)
        {
            btnShabbat.setEnabled(true);
            btnHol.setEnabled(false);
      btnConnect.setEnabled(true);
        }
    }

    private void cboSocketsActionPerformed(ActionEvent e)
    {
        currIndex = comboboxSockets.getSelectedIndex();
        currSocket = mySockets.get(currIndex);
    }


    private void btnShabbosActionPerformed(ActionEvent e) {


        isControlled=true;
        for(int p=0; p<mySockets.size();p++)
            mySockets.get(p).dialog.bufferSocketOut.println("evShabat");
        btnShabbat.setEnabled(false);
        btnHol.setEnabled(true);

    }

    private void btnHolActionPerformed(ActionEvent e) {


        isControlled=true;
        for(int p=0; p<mySockets.size();p++)
            mySockets.get(p).dialog.bufferSocketOut.println("evHol-Server");
        btnHol.setEnabled(false);
        btnShabbat.setEnabled(true);

    }

    private void lockButtons_Conection() {
        btnShabbat.setEnabled(false);
        btnDisconnect.setEnabled(true);
        btnConnect.setEnabled(false);
        btnHol.setEnabled(false);
    }


    private void btnConnectActionPerformed(ActionEvent e)
    {

        lockButtons_Conection();
        isControlled=true;
        for (int i = 0; i < mySockets.size() ; i++)
            mySockets.get(i).dialog.bufferSocketOut.println("evConection");


    }

    private void btnDisconnectActionPerformed(ActionEvent e)
    {
        isControlled = true;
        unlockButtons();
    }

    private void lockButtons()
    {
        btnShabbat.setEnabled(false);
        btnHol.setEnabled(false);
        btnConnect.setEnabled(false);
        btnDisconnect.setEnabled(true);
    }

    private void unlockButtons()
    {
        btnShabbat.setEnabled(true);
        btnHol.setEnabled(false);;
        btnConnect.setEnabled(true);
        btnDisconnect.setEnabled(false);
    }

    private void thisWindowClosing(WindowEvent e)
    {
        isAlive = false;
    }

    private void initComponents()
    {
        label1 = new JLabel();
        label2 = new JLabel();
        label3 = new JLabel();
        comboboxSockets = new JComboBox();
        btnShabbat = new JButton();
        btnDisconnect = new JButton();
        btnConnect = new JButton();
        btnHol = new JButton();

        //======== Main ========
        setTitle(" Traffic-Light ControllerTrafficLight ");
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                thisWindowClosing(e);
            }
        });
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //---- label1 ----
        label1.setText("Traffic-Light Control ");
        label1.setFont(new Font("IMPACT", Font.BOLD, 30));
        label1.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(label1);
        label1.setBounds(50, 15, 395, 55);
        label1.setForeground(Color.RED);

        //---- label2 ----
        label2.setText("Crossroads:");
        contentPane.add(label2);
        label2.setBounds(70, 140, 150, 45);
        label2.setFont(new Font("NARKISIM", Font.BOLD, 18));
        label2.setBackground(new Color(255,215,0));
        //---- label3 ----
        label3.setText("Mode:");
        label3.setFont(new Font("NARKISIM", Font.BOLD, 18));
        contentPane.add(label3);
        label3.setBounds(70, 85, 90, 45);
        label3.setBackground(new Color(255,215,0));
        //---- cboSockets ----
        comboboxSockets.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cboSocketsActionPerformed(e);
            }
        });
        contentPane.add(comboboxSockets);
        comboboxSockets.setBounds(200, 150, 150, 25);

        //---- btnShabbos ----
        btnShabbat.setText("Shabbat");
        btnShabbat.setBackground(new Color(240,248,222));
        btnShabbat.setFont(new Font("", Font.CENTER_BASELINE,15));
        btnShabbat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnShabbosActionPerformed(e);
            }
        });
        contentPane.add(btnShabbat);
        btnShabbat.setBounds(275, 90, 100, 35);

        //---- btnHol ----
        btnHol.setText("Hol");
        btnHol.setBackground(new Color(240,248,222));
        btnHol.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnHolActionPerformed(e);
            }
        });
        contentPane.add(btnHol);
        btnHol.setBounds(150, 90, 100, 35);
        btnHol.setFont(new Font("",Font.BOLD,15));

        //---- btnDisconnect ----
        btnDisconnect.setText("Disconnect");
        btnDisconnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnDisconnectActionPerformed(e);
            }
        });
        contentPane.add(btnDisconnect);
        btnDisconnect.setBounds(250, 200, 150, 40);
        btnDisconnect.setBackground(new Color(240,248,222));
        btnDisconnect.setFont(new Font("",Font.BOLD,15));

        //---- btnConnect ----
        btnConnect.setText("Connect");
        btnConnect.setBackground(new Color(240,248,222));
        btnConnect.addActionListener
                (
                        new ActionListener()
                        {
                           @Override
                           public void actionPerformed(ActionEvent e) {
                btnConnectActionPerformed(e);
            }
                        });
        contentPane.add(btnConnect);
        btnConnect.setBounds(50, 200, 150, 40);
        btnConnect.setFont(new Font("",Font.BOLD,15));

        { // compute preferred size
            Dimension preferredSize = new Dimension();
            for(int i = 0; i < contentPane.getComponentCount(); i++) {
                Rectangle bounds = contentPane.getComponent(i).getBounds();
                preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
            }
            Insets insets = contentPane.getInsets();
            preferredSize.width += insets.right;
            preferredSize.height += insets.bottom;
            contentPane.setMinimumSize(preferredSize);
            contentPane.setPreferredSize(preferredSize);
        }
        setSize(600, 450);
        setLocationRelativeTo(getOwner());
    }

}

//-----Class For Extend Socket-----
 class Soketim
{
    String name;
    Socket socket;
    Dialog78 dialog;

    public Soketim(String name, Socket socket, Dialog78 dialog)
    {
        this.name = name;
        this.socket = socket;
        this.dialog = dialog;
    }

    @Override
    public String toString() {
        return name;
    }
}






