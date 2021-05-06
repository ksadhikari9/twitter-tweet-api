package com.java.twitter.tweet.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.Date;
@Data
@AllArgsConstructor
@Setter@Getter
public class TwitterData implements Comparable {
    Date createdAt;
    long id;
    int sentimentType;
    String html;



    @Override
    public int compareTo(@NotNull Object o) {
        TwitterData a=(TwitterData)o;
        return a.sentimentType-this.sentimentType;
    }
}