package com.ngovangiang.onlineexam.exception;

public class NotEnoughQuestionException extends RuntimeException {

    public NotEnoughQuestionException(String topicName, Integer numOfQuestionsRequired) {
        super(String.format("%s don't have enough %d question!", topicName, numOfQuestionsRequired));
    }
}
