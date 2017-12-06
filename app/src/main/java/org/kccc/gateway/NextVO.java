package org.kccc.gateway;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by ming on 2017. 11. 29..
 */
public class NextVO extends RealmObject{
    private String question;
    private RealmList<LinkVO> link;

    public NextVO() {
    }

    public NextVO(String question, RealmList<LinkVO> link) {

        this.question = question;
        this.link = link;
    }


    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public RealmList<LinkVO> getLink() {
        return link;
    }

    public void setLink(RealmList<LinkVO> link) { this.link = link; }
}
