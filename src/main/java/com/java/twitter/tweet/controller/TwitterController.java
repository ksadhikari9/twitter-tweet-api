package com.java.twitter.controller;

import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TwitterController {
    final Twitter twitter;

    public TwitterController(Twitter twitter) {
        this.twitter = twitter;
    }

    @CrossOrigin
    @GetMapping
    public SearchResults search()
    {
        SearchResults results = twitter.searchOperations().search("bed");
        return results;
    }
}
