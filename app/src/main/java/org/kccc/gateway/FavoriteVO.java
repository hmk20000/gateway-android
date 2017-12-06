package org.kccc.gateway;

import io.realm.RealmObject;

/**
 * Created by ming on 2017. 12. 1..
 */

public class FavoriteVO extends RealmObject {
    private int category;
    private int index;
    private long favorite_Date;

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

    public long getFavorite_Date() {
        return favorite_Date;
    }

    public void setFavorite_Date(long favorite_Date) {
        this.favorite_Date = favorite_Date;
    }
}
