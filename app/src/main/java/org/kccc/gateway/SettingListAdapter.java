package org.kccc.gateway;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ming on 2017. 12. 13..
 */

public class SettingListAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<SettingVO> data;
    private int layout;

    public SettingListAdapter(Context context, int layout, ArrayList<SettingVO> data){
        this.inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data=data;
        this.layout=layout;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position).getName();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView=inflater.inflate(layout,parent,false);
        }
        convertView.getLayoutParams().height = 120;
        SettingVO settingVO =data.get(position);
        ImageView icon=(ImageView)convertView.findViewById(R.id.imageIcon);
        ImageView gotoMenu =(ImageView)convertView.findViewById(R.id.gotoMenu);
        icon.setImageResource(settingVO.getIcon());
        gotoMenu.setImageResource(R.drawable.arrow_right);
        TextView name=(TextView)convertView.findViewById(R.id.textview);
        name.setText(settingVO.getName());
        return convertView;
    }
    //            @Override
//            public View getView(int position, View convertView, ViewGroup parent){
//
//                View view = super.getView(position,convertView,parent);
//                view.getLayoutParams().height = 170;
//
////                layoutparams = view.getLayoutParams();
//
//                //Define your height here.
////                layoutparams.height = 70;
//
////                view.setLayoutParams(layoutparams);
//
//                return view;
//            }
}
