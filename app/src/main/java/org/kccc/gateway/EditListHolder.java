package org.kccc.gateway;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by LG on 2016-02-24.
 */

public class EditListHolder extends RecyclerView.ViewHolder{
    public TextView Title;
    public TextView subTitle;
    public ImageView Thumbnail;
    public EditListHolder(View itemView) {
        super(itemView);
        Title = (TextView) itemView.findViewById(R.id.name);
        subTitle = (TextView) itemView.findViewById(R.id.subName);
        Thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
    }
}
