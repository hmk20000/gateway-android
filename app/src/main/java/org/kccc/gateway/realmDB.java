package org.kccc.gateway;

import io.realm.RealmObject;

/**
 * Created by ming on 2017. 11. 28..
 */

public class realmDB extends RealmObject {
    private String test;
    private int category;

    public String getTest(){
        return test;
    }
    public void setTest(String test){
        this.test = test;
    }

    public int getCategory(int i) {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }
}


