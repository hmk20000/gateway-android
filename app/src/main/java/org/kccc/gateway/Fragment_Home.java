package org.kccc.gateway;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;



/**
 * Created by LG on 2016-06-09.
 * 기본 홈 화면에서 보이는 프래그먼트
 */
public class Fragment_Home extends Fragment{

    private View view;
    private RecyclerView CategoryView;
    private TextView title;

    private CategoryAdapter categoryAdapter;
    private RecyclerView.LayoutManager categoryListLayoutManager;
    private ArrayList<CategoryVO> list;

    public Fragment_Home(){    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // we have no menu item to show in action bar
        setHasOptionsMenu(false);

        list = new ArrayList<>();

        list.add(new CategoryVO(getResources().getDrawable(R.drawable.h_1), "SHORT FILMS"));
        list.add(new CategoryVO(getResources().getDrawable(R.drawable.h_2), "JESUS"));
        list.add(new CategoryVO(getResources().getDrawable(R.drawable.h_3), "MAGDALENA"));
        list.add(new CategoryVO(getResources().getDrawable(R.drawable.h_4), "GOSPEL"));
        list.add(new CategoryVO(getResources().getDrawable(R.drawable.h_5), "HOW TO USE"));

        categoryListLayoutManager = new LinearLayoutManager(view.getContext());
        CategoryView.setLayoutManager(categoryListLayoutManager);
        if(list.size() != 0){
            categoryAdapter = new CategoryAdapter(view.getContext(), list);
            CategoryView.setAdapter(categoryAdapter);
            //CategoryView.setItemAnimator(new MyItemAnimator());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_home, container, false);

        CategoryView = (RecyclerView)view.findViewById(R.id.fragment_category_list);
        title = (TextView)view.findViewById(R.id.title);
        title.setText("GATEWAY");

        return view;
    }
}
