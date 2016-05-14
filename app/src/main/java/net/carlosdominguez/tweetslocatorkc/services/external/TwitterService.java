package net.carlosdominguez.tweetslocatorkc.services.external;

import twitter4j.AsyncTwitter;
import twitter4j.AsyncTwitterFactory;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Created by Carlos Domínguez on 13/05/16.
 */
public class TwitterService {

    private AccessToken loadAccessToken() {
        return new AccessToken(TwitterServiceConstants.ACCESS_TOKEN, TwitterServiceConstants.ACCESS_TOKEN_SECRET);
    }

    public AsyncTwitter getAsyncTwitter() {
        AsyncTwitterFactory factory = new AsyncTwitterFactory();
        AsyncTwitter asyncTwitter = factory.getInstance();
        asyncTwitter.setOAuthConsumer(TwitterServiceConstants.CONSUMER_KEY, TwitterServiceConstants.CONSUMER_SECRET);
        asyncTwitter.setOAuthAccessToken(loadAccessToken());
        return asyncTwitter;
    }
}
