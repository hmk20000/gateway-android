package org.kccc.gateway;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;


/**
 * Created by LG on 2016-02-24.
 */

public class NextListHolder extends RecyclerView.ViewHolder{
    public TextView Question;
    public GridLayout target1;
    public GridLayout target2;
    public GridLayout target3;
    public GridLayout target4;
    public TextView targetText1;
    public TextView targetText2;
    public TextView targetText3;
    public TextView targetText4;
    public NextListHolder(View itemView) {
        super(itemView);
        Question = (TextView) itemView.findViewById(R.id.question);
        target1 = (GridLayout) itemView.findViewById(R.id.target1);
        target2 = (GridLayout) itemView.findViewById(R.id.target2);
        target3 = (GridLayout) itemView.findViewById(R.id.target3);
        target4 = (GridLayout) itemView.findViewById(R.id.target4);
        targetText1 = (TextView) itemView.findViewById(R.id.targetText1);
        targetText2 = (TextView) itemView.findViewById(R.id.targetText2);
        targetText3 = (TextView) itemView.findViewById(R.id.targetText3);
        targetText4 = (TextView) itemView.findViewById(R.id.targetText4);
    }
}
