package com.java.twitter.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

@Component
public class TwitterConfig  {
    private TwitterStream twitterStream;

    @Value("${twitter.consumer.key}")
    private String consumerKey;
    @Value("${twitter.consumer.secret}")
    private String consumerSecret;
    @Value("${twitter.access.token}")
    private String accesstoken;
    @Value("${twitter.access.token.secret}")
    private String accesstokenSecret;

    public TwitterFactory twitterFactory() {
        ConfigurationBuilder cb = this.getTwitterConfigBuilder();
        TwitterFactory tf = new TwitterFactory(cb.build());
        return tf;
    }

    public Twitter twitter(TwitterFactory twitterFactory){
        return twitterFactory.getInstance();
    }

    public ConfigurationBuilder getTwitterConfigBuilder() {
        ConfigurationBuilder cb =  new ConfigurationBuilder();
        cb.setOAuthConsumerKey(consumerKey)
                .setOAuthConsumerSecret(consumerSecret)
                .setOAuthAccessToken(accesstoken)
                .setOAuthAccessTokenSecret(accesstokenSecret);
        return cb;
    }
}