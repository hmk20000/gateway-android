package org.kccc.gateway;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by LG on 2016-06-09.
 * 홈화면에서 한 단계 더 들어가서 카테고리 별로 영상를 볼 때 나오는 프래그먼트
 */

@SuppressLint("ValidFragment")
public class Fragment_Category extends Fragment implements View.OnClickListener{

    private View view;
    private RecyclerView categoryListView;
    private TextView title;
    private ImageButton back;

    private CategoryListAdapter categoryListAdapter;
    private RecyclerView.LayoutManager categoryListLayoutManager;
    private List<ContentsVO> list;
    private int category;

    public Fragment_Category(){}
    public Fragment_Category(int category){
        this.category = category;
    }

    private void initializationData() {

        Realm realm = Realm.getDefaultInstance();

        final RealmResults<ContentsVO> tmp = realm.where(ContentsVO.class).equalTo("category",category).findAll();
        this.list = realm.copyFromRealm(tmp);
//        Log.d("realm", String.valueOf(tmp.size()));

        realm.close();
    }
    private void initializationListView() {
        categoryListLayoutManager = new LinearLayoutManager(view.getContext());
        categoryListView.setLayoutManager(categoryListLayoutManager);
        if(list.size() != 0){
            categoryListAdapter = new CategoryListAdapter(view.getContext(), list, 4);
            categoryListView.setAdapter(categoryListAdapter);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_category, container, false);

        categoryListView = (RecyclerView)view.findViewById(R.id.fragment_view_list);
        title = (TextView)view.findViewById(R.id.titleBar);
        back = (ImageButton)view.findViewById(R.id.backBtn);
        back.setOnClickListener(this);

        list = new ArrayList<>();
        initializationData();
        initializationListView();

        if(this.category == 0)
            title.setText("SHORT FILMS");
        else if(this.category == 1)
            title.setText("JESUS");
        else if(this.category == 2)
            title.setText("MAGDALENA");
        else if(this.category == 3)
            title.setText("GOSPEL");
        else if(this.category == 4)
            title.setText("HOW TO USE");

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.backBtn:
                ((MainActivity)view.getContext()).fragmentReplaceWithHome();
                break;
        }
    }
}
