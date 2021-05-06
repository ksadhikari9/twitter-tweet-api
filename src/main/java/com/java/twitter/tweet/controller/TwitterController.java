package com.java.twitter.tweet.controller;


import com.java.twitter.tweet.model.SentimentType;
import com.java.twitter.tweet.service.TwitterService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import twitter4j.TwitterException;


@Controller
public class TwitterController {
    final TwitterService twitterService;


    public TwitterController(TwitterService twitterService) {
        this.twitterService = twitterService;
    }

    @GetMapping("/twitter")
    public String covidRelatedTweets(Model model) throws TwitterException {
        model.addAttribute("tweets",twitterService.covidSearch());
        return "index";
    }

    @GetMapping("/twitter/search")
    public String searchTweets(@RequestParam("search")String search, Model model) throws TwitterException {
        model.addAttribute("tweets",twitterService.fetchTweets(search,100));
        return "index";
    }
}

