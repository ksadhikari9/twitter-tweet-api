package com.java.twitter.tweet.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SentimentData {
    Double veryPositive;
    Double positive;
    Double neutral;
    Double negative;
    Double VeryNegative;
    int SentimentScore;
    String sentimentType;

}



