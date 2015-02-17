package endtoend;

import static org.hamcrest.CoreMatchers.equalTo;

import org.hamcrest.Matcher;

import auctionsniper.Main;
import auctionsniper.Main.MainWindow;

import com.objogate.wl.swing.AWTEventQueueProber;
import com.objogate.wl.swing.driver.JFrameDriver;
import com.objogate.wl.swing.driver.JLabelDriver;
import com.objogate.wl.swing.gesture.GesturePerformer;

/**
 * Driver class for Auction Sniper.
 * 
 * @author Hiroyuki Ito (The Hiro)
 * @see http://www.growing-object-oriented-software.com/
 */
public class AuctionSniperDriver extends JFrameDriver {

    public AuctionSniperDriver(int timeoutMillis) {
        super(
            new GesturePerformer(),
            JFrameDriver.topLevelFrame(
                named(MainWindow.MAIN_WINDOW_NAME),
                showingOnScreen()),
            new AWTEventQueueProber(timeoutMillis, 100));
    }


    public void showsSniperStatus(String statusText) {
        new JLabelDriver(
            this,
            named(Main.SNIPER_STATUS_NAME))
            .hasText((Matcher<String>) equalTo(statusText));
    }
}
