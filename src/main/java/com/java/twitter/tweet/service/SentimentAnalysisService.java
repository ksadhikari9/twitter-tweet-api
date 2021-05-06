package com.java.twitter.tweet.service;
import com.java.twitter.tweet.model.SentimentData;
import com.java.twitter.tweet.model.SentimentType;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import org.ejml.simple.SimpleMatrix;
import org.springframework.stereotype.Service;
import java.util.Properties;

@Service
public class SentimentAnalysisService {

    public  SentimentData analyse(String tweet) {
        SentimentData sentimentData = new SentimentData();


        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, parse, sentiment");

        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        Annotation annotation = pipeline.process(tweet);

        for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
            Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
            SimpleMatrix sm = RNNCoreAnnotations.getPredictions(tree);
            sentimentData.setVeryPositive(sm.get(4) * 100d);
            sentimentData.setPositive(sm.get(3) * 100d);
            sentimentData.setNeutral(sm.get(2) * 100d);
            sentimentData.setNegative(sm.get(1) * 100d);
            sentimentData.setVeryNegative(sm.get(0) * 100d);

            sentimentData.setSentimentScore(RNNCoreAnnotations.getPredictedClass(tree));
            sentimentData.setSentimentType(SentimentType.fromValue(sentimentData.getSentimentScore()).toString());
            return sentimentData;
        }
        return sentimentData;
    }
}

