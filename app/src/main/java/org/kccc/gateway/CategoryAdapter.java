package org.kccc.gateway;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by LG on 2016-02-16.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryHolder>  {
    private List<CategoryVO> itemList;
    private MainActivity mainActivity;

    public CategoryAdapter(Context context, List<CategoryVO> itemList) {
        this.itemList = itemList;
        mainActivity = (MainActivity) context;
    }

    @Override
    public CategoryHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_category_thumbnail, viewGroup, false);
        return new CategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(final CategoryHolder holder, final int position) {
        holder.Thumbnail.setImageDrawable(itemList.get(position).getIcon());
        holder.Title.setText(itemList.get(position).getTitle());
        holder.Thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.fragmentReplaceWithCategory(position);
            }
        });

        //애니매이션 효과 추가
        new MyItemAnimator().setFadeAnimation(holder.Title);
    }
    @Override
    public int getItemCount() {
        return (itemList.size() > 0) ? itemList.size() : 0;
    }

    /*private void setFadeAnimation(View view){
        AlphaAnimation anim = new AlphaAnimation(0.0f,1.0f);
        anim.setDuration(500);
        view.startAnimation(anim);
    }*/
}
