package com.ksbm.ontu.fundamental.model;

public class WordResultStatus {
    String quizWords; String rightAnswer;

    public WordResultStatus(String quizWords, String rightAnswer) {
        this.quizWords = quizWords;
        this.rightAnswer = rightAnswer;
    }

    public String getQuizWords() {
        return quizWords;
    }

    public void setQuizWords(String quizWords) {
        this.quizWords = quizWords;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }
}
