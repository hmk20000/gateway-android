package org.kccc.gateway;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by LG on 2016-02-24.
 */

public class CategoryHolder extends RecyclerView.ViewHolder{
    public TextView Title;
    public ImageView Thumbnail;
    public FrameLayout layout;
    public CategoryHolder(View itemView) {
        super(itemView);
        Title = (TextView) itemView.findViewById(R.id.name);
        Thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
        layout = (FrameLayout) itemView.findViewById(R.id.contentPanel);
    }
}
