package endtoend;

import auctionsniper.Main;
import auctionsniper.ui.MainWindow;

/**
 * Runner of this application.
 * 
 * @author Hiroyuki Ito (The Hiro)
 * @see http://www.growing-object-oriented-software.com/
 */
public class ApplicationRunner {

    // FIXME ここで良い？
    public static final String XMPP_HOSTNAME   = "localhost";
    public static final String SNIPER_ID       = "sniper";
    public static final String SNIPER_PASSWORD = "sniper";
    private AuctionSniperDriver driver;


    public void startBiddingIn(final FakeAuctionServer auction) {

        Thread thread = new Thread("Test Application") {
            @Override
            public void run() {
                try {
                    Main.main(XMPP_HOSTNAME, SNIPER_ID, SNIPER_PASSWORD, auction.getItemId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        thread.setDaemon(true);
        thread.start();
        driver = new AuctionSniperDriver(1000);
        driver.showsSniperStatus(MainWindow.STATUS_JOINING);
    }


    public void showsSniperHasLostAuction() {
        driver.showsSniperStatus(MainWindow.STATUS_LOST);
    }


    public void stop() {
        if (driver != null) {
            driver.dispose();
        }
    }
}
