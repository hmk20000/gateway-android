package org.kccc.gateway;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;


/**
 * Created by LG on 2016-06-09.
 * 수정 버튼을 눌렀을 때 나오는 프래그먼트
 */
@SuppressLint("ValidFragment")
public class Fragment_MyVideos_Edit extends Fragment implements View.OnClickListener {
    private View view;
    private Button cancel;
    private RecyclerView editListView;

    private EditListAdapter editListAdapter;
    private RecyclerView.LayoutManager editListLayoutManager;

    private List<ContentsVO> list;
    private int flag;

    public Fragment_MyVideos_Edit(int flag){
        this.flag = flag;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_myvideos_edit, container, false);

        editListView = (RecyclerView)view.findViewById(R.id.fragment_myvideo_list);
        cancel = (Button)view.findViewById(R.id.cancelBtn);

        cancel.setOnClickListener(this);

        initializationData();
        initializationListView();

        return view;
    }

    private void initializationData() {

        List<ContentsVO> dbList = null;

        Realm realm = Realm.getDefaultInstance();
        RealmResults<ContentsVO> realmContentsVO = null;

        switch (flag){
            case 0:
//                dbList = dbHandler.readFavoriteList();
                break;
            case 1:
                realmContentsVO = realm.where(ContentsVO.class)
                        .equalTo("history",1)
                        .findAll();
                dbList = realm.copyFromRealm(realmContentsVO);
                break;
            default:
//                dbList = dbHandler.readDownloadList();
        }

        this.list = dbList;

        realm.close();
    }
    private void initializationListView() {
        editListLayoutManager = new LinearLayoutManager(view.getContext());
        editListView.setLayoutManager(editListLayoutManager);
        if(list.size() != 0){
            editListAdapter = new EditListAdapter(view.getContext(), list, flag);
            editListView.setAdapter(editListAdapter);
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cancelBtn:
                ((MainActivity) view.getContext()).fragmentReplaceWithMyVideo(flag);
                break;
        }
    }
}
