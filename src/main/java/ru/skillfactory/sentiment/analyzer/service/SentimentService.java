package ru.skillfactory.sentiment.analyzer.service;

import org.springframework.stereotype.Service;
import ru.skillfactory.sentiment.analyzer.model.SentimentResponse;

import java.util.*;

@Service
public class SentimentService {

    private final Map<String, Double> positiveWords = Map.of(
            "good", 0.8, "great", 0.9, "excellent", 1.0,
            "happy", 0.7, "love", 0.9, "awesome", 0.9,
            "fantastic", 0.95, "perfect", 1.0, "best", 0.9
    );

    private final Map<String, Double> negativeWords = Map.of(
            "bad", 0.8, "terrible", 0.9, "awful", 0.95,
            "hate", 0.9, "worst", 0.95, "poor", 0.7,
            "disappointing", 0.8, "sad", 0.7, "angry", 0.8
    );

    public SentimentResponse analyze(String text) {
        text = text.toLowerCase();
        String[] words = text.split("\\W+");

        double positiveScore = 0;
        double negativeScore = 0;
        int wordCount = 0;

        for (String word : words) {
            if (positiveWords.containsKey(word)) {
                positiveScore += positiveWords.get(word);
                wordCount++;
            }
            if (negativeWords.containsKey(word)) {
                negativeScore += negativeWords.get(word);
                wordCount++;
            }
        }

        String sentiment;
        double confidence;

        if (wordCount == 0) {
            sentiment = "neutral";
            confidence = 0.5;
        } else {
            double totalScore = positiveScore - negativeScore;
            if (totalScore > 0.3) {
                sentiment = "positive";
                confidence = Math.min(0.3 + totalScore / wordCount, 0.95);
            } else if (totalScore < -0.3) {
                sentiment = "negative";
                confidence = Math.min(0.3 + Math.abs(totalScore) / wordCount, 0.95);
            } else {
                sentiment = "neutral";
                confidence = 0.6;
            }
        }

        return new SentimentResponse(text, sentiment, confidence);
    }
}