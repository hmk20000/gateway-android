package org.kccc.gateway;

import io.realm.RealmObject;

/**
 * Created by ming on 2017. 12. 1..
 */

public class DownloadVO {
    private int category;
    private int index;
    private String donwload_path;
    private long downloadDate;

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

    public long getDownloadDate() {
        return downloadDate;
    }

    public void setDownloadDate(long downloadDate) {
        this.downloadDate = downloadDate;
    }

    public String getDonwload_path() {
        return donwload_path;
    }

    public void setDonwload_path(String donwload_path) {
        this.donwload_path = donwload_path;
    }
}
