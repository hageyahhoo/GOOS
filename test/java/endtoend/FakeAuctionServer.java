package endtoend;

import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.hamcrest.Matcher;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

/**
 * Auction server for this end-to-end test.
 * 
 * @author Hiroyuki Ito (The Hiro)
 * @see http://www.growing-object-oriented-software.com/
 */
public class FakeAuctionServer {

    public  static final String ITEM_ID_AS_LOGIN = "auction-%s";
    public  static final String AUCTION_RESOURCE = "Auction";
    public  static final String XMPP_HOSTNAME    = "localhost";
    private static final String AUCTION_PASSWORD = "auction";

    private final String itemId;
    private final XMPPConnection connection;
    // FIXME Why do we need to initialize this listner here, not at constructor?
    private final SingleMessageListener messageListener = new SingleMessageListener();
    private Chat currentChat;


    public FakeAuctionServer(String itemId) {
        this.itemId     = itemId;
        this.connection = new XMPPConnection(XMPP_HOSTNAME);
    }


    public void startSellingItem() throws XMPPException {

        connection.connect();

        connection.login(
            String.format(ITEM_ID_AS_LOGIN, itemId),
            AUCTION_PASSWORD,
            AUCTION_RESOURCE);

        connection.getChatManager().addChatListener(
            new ChatManagerListener() {
                public void chatCreated(Chat chat, boolean createdLocally) {
                    currentChat = chat;
                    chat.addMessageListener(messageListener);
                }
            });
    }


    public void reportPrice(int price, int increment, String bidder) throws XMPPException {
        currentChat.sendMessage(
                String.format(
                        "SOLVersion: 1.1; Event: PRICE; CurrentPrice: %d; Increment: %d; Bidder: %s;",
                        price, increment, bidder));
    }


    public void hasReceivedJoinRequestFromSniper() throws InterruptedException {
        messageListener.receivesAMessage(is(anything()));
    }


    public void hasReceivedBid(int bid, String sniperId) throws InterruptedException {
        messageListener.receivesAMessage(
                equalTo(String.format("SOLVersion: 1.1; Command: BID; Price: %d;", bid)));
    }


    public void announceClosed() throws XMPPException {
        currentChat.sendMessage(new Message());
    }


    public void stop() {
        connection.disconnect();
    }


    public String getItemId() {
        return itemId;
    }


    public class SingleMessageListener implements MessageListener {
        private final ArrayBlockingQueue<Message> messages = new ArrayBlockingQueue<Message>(1);

        public void processMessage(Chat chat, Message message) {
            messages.add(message);
        }

        public void receivesAMessage(Matcher<? super String> messageMatcher) throws InterruptedException {
            final Message message = messages.poll(5, TimeUnit.SECONDS);
            assertThat("Message", message, is(notNullValue()));
            assertThat(message.getBody(), messageMatcher);
        }
    }
}
