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

    public TwitterStreamFactory twitterStreamFactory(){
        ConfigurationBuilder cb = this.getTwitterConfigBuilder();
        TwitterStreamFactory tsf = new TwitterStreamFactory(cb.build());
        return tsf;
    }

    public TwitterStream twitterStream(){
        if(this.twitterStream == null)
            this.twitterStream =  this.twitterStreamFactory().getInstance();
        return twitterStream;
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
//    @Override
//    public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer, Environment environment) {
//        connectionFactoryConfigurer.addConnectionFactory(
//                new TwitterConnectionFactory(
//                        environment.getProperty("twitter.consumerKey"),
//                        environment.getProperty("twitter.consumerSecret")
//                )
//        );
//    }


//    @Bean
//    public Twitter getTwitter(){
//        return new TwitterTemplate("FaQIQhhZCtZSvdYPfOCxnbRR9",
//                "rf7lu8E7PyV75EQaGcoeRiUag7iDolt0E7JLe3ZBNczvK3ERxE",
//                "2210667690-H95mmW62lLOh2TJ07Hks8CU70R6fPf7l7AIuqa9",
//                "23S5rLtyIof9pFow1M98FhjiwIvcmvKAg2C8NSQ3BpqSL");
//    }
//    @Override
//    public UserIdSource getUserIdSource() {
//        return null;
//    }
//
//    @Override
//    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
//        return null;
//    }