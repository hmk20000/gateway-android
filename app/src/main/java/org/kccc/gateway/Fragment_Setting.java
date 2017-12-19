package org.kccc.gateway;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by ming on 2017. 12. 11..
 */

public class Fragment_Setting extends Fragment implements OnClickListener {
//    private View view;
    static final String[] LIST_MENU = {"LIST1", "LIST2", "LIST3", "LIST3", "LIST3", "LIST3", "LIST3", "LIST3", "LIST3", "LIST3"} ;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
//        super.onCreateView(inflater, container, savedInstanceState);
//        view = inflater.inflate(R.layout.fragment_setting, container, false);
//        return view;

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, null) ;
//        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, LIST_MENU) ;

//        LayoutParams layoutparams;
//        ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, LIST_MENU){
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
//        };

        ListView listview = (ListView) view.findViewById(R.id.listview1) ;

        ArrayList<SettingVO> data=new ArrayList<>();
        SettingVO lion=new SettingVO(R.drawable.arrow_left,"lion");
        SettingVO tiger=new SettingVO(R.drawable.arrow_left,"tiger");
        SettingVO cat=new SettingVO(R.drawable.arrow_left,"cat");
        SettingVO dog=new SettingVO(R.drawable.arrow_left,"dog");

        data.add(lion);
        data.add(tiger);
        data.add(dog);
        data.add(cat);

        SettingListAdapter adapter=new SettingListAdapter(MainActivity.getActivity(),R.layout.item_setting,data);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("click Test", "onItemClick: "+position);
            }
        });

        return view;
    }

    @Override
    public void onClick(View v) {

    }
}