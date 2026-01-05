package ru.skillfactory.sentiment.analyzer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.skillfactory.sentiment.analyzer.model.SentimentResponse;
import ru.skillfactory.sentiment.analyzer.service.SentimentService;

@RestController
@RequestMapping("/api")
public class SentimentController {

    @Autowired
    private SentimentService sentimentService;

    @PostMapping("/sentiment")
    public SentimentResponse analyzeSentimentPost(@RequestBody String text) {
        return sentimentService.analyze(text);
    }
}
