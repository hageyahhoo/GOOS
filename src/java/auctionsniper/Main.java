package auctionsniper;

import javax.swing.SwingUtilities;

import auctionsniper.ui.MainWindow;

/**
 * Entry point of Auction Sniper.
 * 
 * @author Hiroyuki Ito (The Hiro)
 * @see http://www.growing-object-oriented-software.com/
 */
public class Main {

    private MainWindow ui;


    public Main() throws Exception {
        startUserInterface();
    }


    public static void main(String... args) throws Exception {
        Main main = new Main();
    }


    private void startUserInterface() throws Exception {
        SwingUtilities.invokeAndWait(new Runnable() {
            public void run() {
                ui = new MainWindow();
            }
        });
    }
}
