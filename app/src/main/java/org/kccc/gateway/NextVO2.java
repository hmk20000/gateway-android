package org.kccc.gateway;

/**
 * Created by LG on 2016-07-27.
 * 현재 영상의 질문을 저장할 클래스
 */
public class NextVO2 {
    private int category;
    private int index;
    private int nextInd;
    private String question;

    public NextVO2() {
    }

    public NextVO2(int category, int index, int nextInd, String question) {
        this.category = category;
        this.index = index;
        this.nextInd = nextInd;
        this.question = question;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getNextInd() {
        return nextInd;
    }

    public void setNextInd(int nextInd) {
        this.nextInd = nextInd;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
