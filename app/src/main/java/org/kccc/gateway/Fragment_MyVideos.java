package org.kccc.gateway;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by LG on 2016-06-09.
 * 마이비디오 탭을 눌렀을 때 보이는 화면
 */
@SuppressLint("ValidFragment")
public class Fragment_MyVideos extends Fragment implements View.OnClickListener {
    private View view;
    private RecyclerView categoryListView;

    private List<ContentsVO> list;
    private int flag;

    public Fragment_MyVideos(int flag){
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

        view = inflater.inflate(R.layout.fragment_myvideos, container, false);

        categoryListView = (RecyclerView)view.findViewById(R.id.fragment_myvideo_list);

        // swipe delete Test

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();

                ContentsVO deleteContents = list.get(position);
                Realm realm = Realm.getDefaultInstance();
                deleteContents = realm.where(ContentsVO.class)
                        .equalTo("category",deleteContents.getCategory())
                        .equalTo("index",deleteContents.getIndex())
                        .findFirst();
                realm.beginTransaction();

                switch (flag){
                    case 0: deleteContents.setFavorite(0); break;
                    case 1: deleteContents.setHistory(0); break;
                    case 2: deleteContents.setDownload(0); break;
                }



                realm.commitTransaction();
                realm.close();

                list.remove(position);

                categoryListView.getAdapter().notifyItemRemoved(position);
                categoryListView.getAdapter().notifyDataSetChanged();
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchHelperCallback);
        itemTouchHelper.attachToRecyclerView(categoryListView);

        //

//        edit = (Button)view.findViewById(R.id.editBtn);
        Button favorite = (Button) view.findViewById(R.id.favoriteBtn);
        Button history = (Button) view.findViewById(R.id.historyBtn);
        Button download = (Button) view.findViewById(R.id.downloadBtn);

        ArrayList<ImageView> line = new ArrayList<ImageView>();
        line.add(0,(ImageView)view.findViewById(R.id.favoriteLine));
        line.add(1,(ImageView)view.findViewById(R.id.historyLine));
        line.add(2,(ImageView)view.findViewById(R.id.downloadLine));

        tabSelect(line,flag);

        favorite.setOnClickListener(this);
        history.setOnClickListener(this);
        download.setOnClickListener(this);

        initializationData();
        initializationListView();

        return view;
    }

    private void initializationData() {
        RealmHelper realmHelper = new RealmHelper();
        this.list = realmHelper.getMyVideos(flag);
    }
    private void initializationListView() {

        RecyclerView.LayoutManager categoryListLayoutManager = new LinearLayoutManager(view.getContext());
        categoryListView.setLayoutManager(categoryListLayoutManager);
        if(list!=null){
            CategoryListAdapter categoryListAdapter = new CategoryListAdapter(view.getContext(), list, flag);
            categoryListView.setAdapter(categoryListAdapter);
        }
    }
    private void tabSelect(ArrayList<ImageView> line, int select){
        Resources resource = MainActivity.getActivity().getResources();
        int pointColor = resource.getColor(R.color.point);
        int backgroundColor = resource.getColor(R.color.darkGray);
        for (int i=0; i<line.size(); i++){
            if(i == select){
                line.get(i).setBackgroundColor(pointColor);
            }else {
                line.get(i).setBackgroundColor(backgroundColor);
            }
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.favoriteBtn:
                ((MainActivity) view.getContext()).fragmentReplaceWithMyVideo(0);
                break;
            case R.id.historyBtn:
                ((MainActivity) view.getContext()).fragmentReplaceWithMyVideo(1);
                break;
            case R.id.downloadBtn:
                ((MainActivity) view.getContext()).fragmentReplaceWithMyVideo(2);
                break;
        }
    }
}
