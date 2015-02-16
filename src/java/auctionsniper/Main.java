package auctionsniper;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * Entry point of Auction Sniper.
 * 
 * @author Hiroyuki Ito (The Hiro)
 * @see http://www.growing-object-oriented-software.com/
 */
public class Main {

    // FIXME 再チェック
    public static final String SNIPER_STATUS_NAME = "status";
    public static final String STATUS_JOINING     = "Joining";
    public static final String STATUS_LOST        = "Lost";
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


    public class MainWindow extends JFrame {

        public static final String MAIN_WINDOW_NAME = "MAIN_WINDOW_NAME";
        private static final long serialVersionUID = 1;

        public MainWindow() {
            super("Auction Sniper");
            setName(MAIN_WINDOW_NAME);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setVisible(true);
        }
    }
}
