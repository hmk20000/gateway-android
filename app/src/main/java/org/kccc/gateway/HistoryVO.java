package org.kccc.gateway;

import io.realm.RealmObject;

/**
 * Created by ming on 2017. 12. 1..
 */

public class HistoryVO extends RealmObject {
    private int category;
    private int index;
    private long history_Date;

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

    public long getHistory_Date() {
        return history_Date;
    }

    public void setHistory_Date(long history_Date) {
        this.history_Date = history_Date;
    }
}
