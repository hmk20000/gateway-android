package org.kccc.gateway;

import android.annotation.SuppressLint;
import android.app.Fragment;
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
    private Button edit;
    private Button favorite;
    private Button history;
    private Button download;
    private RecyclerView categoryListView;
    private ImageView line;

    private CategoryListAdapter categoryListAdapter;
    private RecyclerView.LayoutManager categoryListLayoutManager;

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

        edit = (Button)view.findViewById(R.id.editBtn);
        favorite = (Button)view.findViewById(R.id.favoriteBtn);
        history = (Button)view.findViewById(R.id.historyBtn);
        download = (Button)view.findViewById(R.id.downloadBtn);

        if(flag == RealmHelper.FAVORITE)
            line = (ImageView)view.findViewById(R.id.favoriteLine);
        else if(flag == RealmHelper.HISTORY)
            line = (ImageView)view.findViewById(R.id.historyLine);
        else
            line = (ImageView)view.findViewById(R.id.downloadLine);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            line.setBackground(view.getResources().getDrawable(R.drawable.underlinetrue));
        }

        edit.setOnClickListener(this);
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

        categoryListLayoutManager = new LinearLayoutManager(view.getContext());
        categoryListView.setLayoutManager(categoryListLayoutManager);
        if(list!=null){
            categoryListAdapter = new CategoryListAdapter(view.getContext(), list, flag);
            categoryListView.setAdapter(categoryListAdapter);
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.editBtn:
                ((MainActivity) view.getContext()).fragmentReplaceWithMyVideoEdit(flag);
                break;
            case R.id.favoriteBtn:
                ((MainActivity) view.getContext()).fragmentReplaceWithMyVideo(0);
                if(flag!=0)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        line.setBackground(view.getResources().getDrawable(R.drawable.underlinefalse));
                    }
                break;
            case R.id.historyBtn:
                ((MainActivity) view.getContext()).fragmentReplaceWithMyVideo(1);
                if(flag!=1)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        line.setBackground(view.getResources().getDrawable(R.drawable.underlinefalse));
                    }
                break;
            case R.id.downloadBtn:
                ((MainActivity) view.getContext()).fragmentReplaceWithMyVideo(2);
                if(flag!=2)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        line.setBackground(view.getResources().getDrawable(R.drawable.underlinefalse));
                    }
                break;
        }
    }
}
