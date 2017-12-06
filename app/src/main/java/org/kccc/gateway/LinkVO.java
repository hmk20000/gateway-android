package org.kccc.gateway;

import io.realm.RealmObject;

/**
 * Created by ming on 2017. 11. 29..
 */
public class LinkVO extends RealmObject{
    private int category;
    private int index_key;

    public LinkVO() {
    }

    public LinkVO(int category, int index_key) {
        this.category = category;
        this.index_key = index_key;
    }

    public int getCategory() { return category; }

    public void setCategory(int category) { this.category = category; }

    public int getIndex() { return index_key; }

    public void setIndex(int index) { this.index_key = index; }
}
