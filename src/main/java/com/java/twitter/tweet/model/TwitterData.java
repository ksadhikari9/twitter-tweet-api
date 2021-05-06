package com.java.twitter.tweet.model;

import lombok.*;

import java.util.Date;
@Data
@RequiredArgsConstructor
@NoArgsConstructor
@Setter@Getter
public class TwitterData implements Comparable {
    @NonNull
    Date createdAt;
    @NonNull
    long id;
    @NonNull
    String html;

    SentimentData sentimentData;

    @Override
    public int compareTo(Object o) {
        TwitterData a=(TwitterData)o;
        return (int) ((a.sentimentData.getVeryPositive() + a.sentimentData.getPositive()) - (this.sentimentData.getVeryPositive() + this.sentimentData.positive));
    }
}