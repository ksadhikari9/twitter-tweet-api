package com.java.twitter.tweet.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.twitter.config.TwitterConfig;

import com.java.twitter.tweet.model.TwitterData;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import twitter4j.Query;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class TwitterService {
    final TwitterConfig twitterConfig;

    final SentimentAnalysisService sentimentAnalysisService;
    final String url="https://publish.twitter.com/oembed?url=https://twitter.com/Interior/status/";

    public TwitterService(TwitterConfig twitterConfig, SentimentAnalysisService sentimentAnalysisService) {
        this.twitterConfig = twitterConfig;
        this.sentimentAnalysisService = sentimentAnalysisService;
    }

    public List<TwitterData> covidSearch() throws TwitterException {
       String keyword="(IndiaFightsCorona OR Unite2FightCorona OR PositiveVibe)";
        List<TwitterData> twitterData = this.fetchTweets(keyword, 1000);
        return twitterData.stream().sorted().collect(Collectors.toList());
    }

    public List<TwitterData> fetchTweets(String keyword, int count) throws TwitterException {
        Twitter twitter = this.twitterConfig.twitter(this.twitterConfig.twitterFactory());
        Query query = new Query(keyword.concat("-filter:retweets -filter:replies"));
        query.setCount(count);
        query.setLocale("en");
        query.setLang("en");
        query.setResultType(Query.ResultType.mixed);
        return twitter.search(query).getTweets().stream().map(status -> {
            try {
                return this.cleanTweets(status);
            } catch (URISyntaxException | JsonProcessingException e) {
                e.printStackTrace();
            }
            return null;
        }).sorted().collect(Collectors.toList());
    }


    private TwitterData cleanTweets(Status status) throws URISyntaxException, JsonProcessingException {
        TwitterData twitterData = new TwitterData(
                status.getCreatedAt(),
                status.getId(),
                getHtmlStringByTwitterId(status.getId())
        );
        // Clean up tweets for analysis
        String text = status.getText().trim()
                .replaceAll("http.*?[\\S]+", "")
                .replaceAll("@[\\S]+", "")
                .replaceAll("#", "")
                .replaceAll("[\\s]+", " ");
        twitterData.setSentimentData(sentimentAnalysisService.analyse(text));
        return twitterData;
    }

    private String getHtmlStringByTwitterId(long id)  {
        ObjectMapper mapper = new ObjectMapper();
       try {
            ResponseEntity<String> exchange = new RestTemplate().exchange(
                    new URI(url+id),
                    HttpMethod.GET,
                    getHeaders(id), String.class);
            return mapper.readTree(exchange.getBody()).get("html").asText();
        }catch (JsonProcessingException | URISyntaxException e)
        {
            return "";
        }
    }
    private HttpEntity<?> getHeaders(long id)
    {
        HttpHeaders httpHeaders =new HttpHeaders();
        httpHeaders.add("url",url+id);
        return new HttpEntity<>(httpHeaders);
    }
}
