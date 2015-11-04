package Project3;

import javax.swing.*;

/**************************************************************************
 * Main
 * @author Jason Bensel, Braedin Hendrickson
 * @version Project 3
 *************************************************************************/
public class Main{
    public static void main(String[] args) {
        BankGUI g = new BankGUI();
        g.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        g.setVisible(true);
        g.setResizable(false);
        g.pack();
    }
}