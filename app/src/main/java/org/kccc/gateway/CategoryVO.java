package org.kccc.gateway;

import android.graphics.drawable.Drawable;

/**
 * Created by LG on 2016-06-09.
 */
public class CategoryVO {
    private Drawable icon;
    private String title;

    public CategoryVO(Drawable icon, String title) {
        this.icon = icon;
        this.title = title;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
