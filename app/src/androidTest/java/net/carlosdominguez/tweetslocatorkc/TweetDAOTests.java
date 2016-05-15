package net.carlosdominguez.tweetslocatorkc;

import android.test.AndroidTestCase;

import net.carlosdominguez.tweetslocatorkc.model.db.DBHelper;
import net.carlosdominguez.tweetslocatorkc.model.db.Tweet;
import net.carlosdominguez.tweetslocatorkc.model.db.dao.TweetDAO;

import java.util.Date;
import java.util.List;

/**
 * Created by Carlos Domínguez on 13/05/16.
 */
public class TweetDAOTests extends AndroidTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        DBHelper.configure(getContext(), "TestDB.sqlite");
    }

    public void testInsertTweet() {
        Tweet tweet = new Tweet();
        tweet.setText("Hi from tweet");
        tweet.setUsername("atlanticasound");
        tweet.setPublicationDate(new Date());

        TweetDAO dao = new TweetDAO();
        dao.deleteAll();

        long result = dao.insert(tweet);

        assertTrue("New tweet created should have right id", result > 0);

        Tweet tweet1 = dao.query(result);

        assertEquals(tweet.getText(), tweet1.getText());
        assertEquals(tweet.getUsername(), tweet1.getUsername());

        List<Tweet> tweets = dao.query();

        assertEquals(tweets.size(), 1);
    }

    public void testDeleteTweet() {
        DBHelper.configure(getContext(), "TestDB.sqlite");

        Tweet tweet = new Tweet();
        tweet.setText("Hi from tweet");
        tweet.setUsername("atlanticasound");
        tweet.setPublicationDate(new Date());

        TweetDAO dao = new TweetDAO();
        dao.deleteAll();

        long result = dao.insert(tweet);
        dao.delete(result);

        List<Tweet> tweets = dao.query();

        assertTrue("List of tweets should be zero if we delete all tweets", tweets.size() == 0);
    }

}
