package ru.skillfactory.sentiment.analyzer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SentimentResponse {
    private String text;
    private String sentiment;
    private double confidence;

}