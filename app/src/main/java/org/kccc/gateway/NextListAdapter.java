package org.kccc.gateway;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;


/**
 * Created by LG on 2016-02-16.
 * watch fragment의 next 버튼에서 각 질문에 대해 다음 영상으로 향하는 버튼을 관리하는 클래스
 * 동적으로 리스트 뷰를 두개 중첩시키면 데이터에 오류가 생겨서
 * 최대 다음 영상 개수를 4개로 고정시키고 작업함
 */
public class NextListAdapter extends RecyclerView.Adapter<NextListHolder> {

    private View view;
    private Context context;
    private List<NextVO> itemList;
    private Dialog dialog;
//    private List<List<TargetVO>> targetVOList;
    public NextListAdapter(Context context, List<NextVO> itemList, Dialog dialog) {
        this.context = context;
        this.itemList = itemList;
//        this.targetVOList = new ArrayList<>();
        this.dialog = dialog;
    }

    @Override
    public NextListHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_next_question, viewGroup, false);
        return new NextListHolder(view);
    }

    @Override
    public void onBindViewHolder(final NextListHolder holder, final int position) {
        holder.Question.setText(itemList.get(position).getQuestion());
//        targetVOList.add(position, DataBaseHandler.getInstance(view.getContext()).readTargetList(itemList.get(position).getCategory(), itemList.get(position).getIndex(), itemList.get(position).getNextInd()));
//        setTarget(holder, targetVOList.get(position));
        setTarget(holder, itemList.get(position).getLink());
    }

    public void setTarget(NextListHolder holder, final List<LinkVO> linkVOS) {

        SharedPreferences orientationPref = context.getSharedPreferences("orientationPref", context.MODE_PRIVATE);
        SharedPreferences.Editor orientationEditor = orientationPref.edit();
        orientationEditor.putInt("flag", 2);
        orientationEditor.commit();
        for (int i = 0; i < 4; i++) {
            if (i < linkVOS.size()) {
                final int index = linkVOS.get(i).getIndex();
                final int category = linkVOS.get(i).getCategory();

                Realm realm = Realm.getDefaultInstance();
                final ContentsVO contentsVO = realm.where(ContentsVO.class)
                        .equalTo("category",category)
                        .equalTo("index",index)
                        .findFirst();
                String title = contentsVO.getTitle();
                realm.close();

                if (i == 0) {

                    holder.targetText1.setText(title);
                    holder.target1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            switch (v.getId()) {
                                case R.id.target1:
                                    if(category == 3 && index==3){
                                        Intent intent = new Intent(context, ImageSlideActivity.class);
                                        context.startActivity(intent);
                                    }
                                    else
                                        ((MainActivity)context).fragmentReplaceWithWatch(category,index,true,4);
                                    break;
                            }
                            dialog.dismiss();
                        }
                    });
                } else if (i == 1) {
                    holder.targetText2.setText(title);
                    holder.target2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            switch (v.getId()) {
                                case R.id.target2:
                                    if(category == 3 && index==3){
                                        Intent intent = new Intent(context, ImageSlideActivity.class);
                                        context.startActivity(intent);
                                    }
                                    else
                                        ((MainActivity)context).fragmentReplaceWithWatch(category,index,true,4);
                                    break;
                            }
                            dialog.dismiss();
                        }

                    });
                } else if (i == 2) {
                    holder.targetText3.setText(title);
                    holder.target3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            switch (v.getId()) {
                                case R.id.target3:
                                    if(category == 3 && index==3){
                                        Intent intent = new Intent(context, ImageSlideActivity.class);
                                        context.startActivity(intent);
                                    }
                                    else
                                        ((MainActivity)context).fragmentReplaceWithWatch(category,index,true,4);
                                    break;

                            }
                            dialog.dismiss();
                        }

                    });
                } else if (i == 3) {
                    holder.targetText4.setText(title);
                    holder.target4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            switch (v.getId()) {
                                case R.id.target4:
                                    if(category == 3 && index==3){
                                        Intent intent = new Intent(context, ImageSlideActivity.class);
                                        context.startActivity(intent);
                                    }
                                    else
                                        ((MainActivity)context).fragmentReplaceWithWatch(category,index,true,4);
                                    break;
                            }
                            dialog.dismiss();
                        }

                    });
                }
            } else {
                if (i == 0)
                    holder.target1.setVisibility(View.GONE);
                else if (i == 1)
                    holder.target2.setVisibility(View.GONE);
                else if (i == 2)
                    holder.target3.setVisibility(View.GONE);
                else if (i == 3)
                    holder.target4.setVisibility(View.GONE);
            }
        }

    }
    @Override
    public int getItemCount() {
        return (itemList.size() > 0) ? itemList.size() : 0;
    }
}
