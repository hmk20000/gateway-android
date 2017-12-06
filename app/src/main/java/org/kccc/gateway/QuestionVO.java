package org.kccc.gateway;

import io.realm.RealmObject;

/**
 * Created by ming on 2017. 11. 29..
 */

public class QuestionVO extends RealmObject{
    private int num;
    private String question;
    public QuestionVO(){
    }
    public QuestionVO(int num, String question){
        this.num = num;
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
