package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main extends JFrame{
    private CardLayout card=new CardLayout();
    Mail_GUI mail=new Mail_GUI();
    public Main(){
        setLayout(card);
        add("Mail_gui",mail);
        setBounds(300,100,544,620);
        setVisible(true);

        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }
    public static void main(String[] args) {
        new Main();
    }
}