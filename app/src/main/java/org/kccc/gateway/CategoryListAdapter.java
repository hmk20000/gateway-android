package org.kccc.gateway;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by LG on 2016-02-16.
 */
public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListHolder>  {
    private Context context;
    private int flag;
    private List<ContentsVO> itemList;

    private SharedPreferences orientationPref = null;
    private SharedPreferences.Editor orientationEditor = null;

    private View view;
    public CategoryListAdapter(Context context, List<ContentsVO> itemList, int flag) {
        this.context = context;
        this.itemList = itemList;
        this.flag = flag;
    }

    @Override
    public CategoryListHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_category_list_thumbnail, viewGroup, false);
        return new CategoryListHolder(view);
    }

    @Override
    public void onBindViewHolder(final CategoryListHolder holder, final int position) {

//        Drawable tmp = new ThumbnailHandler(context).setThumbnail(
//                itemList.get(position).getCategory(),
//                itemList.get(position).getIndex());

//        Drawable tmp = new ThumbnailHandler(context).setThumbnail(itemList.get(position));
//        holder.Thumbnail.setImageDrawable(tmp);

        int resourceID = new ThumbnailHandler(context).setThumbnail(itemList.get(position));
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceID);
        int height = (bitmap.getHeight() * 256 / bitmap.getWidth());
        Bitmap scale = Bitmap.createScaledBitmap(bitmap, 256, height, true);

        holder.Thumbnail.setImageBitmap(scale);
        holder.Title.setText(itemList.get(position).getTitle());
        holder.subTitle.setText(itemList.get(position).getSubTitle());

        holder.Thumbnail.setScaleType(ImageView.ScaleType.FIT_CENTER);
        holder.Thumbnail.getLayoutParams().height = 270;

        orientationPref = context.getSharedPreferences("orientationPref", context.MODE_PRIVATE);
        orientationEditor = orientationPref.edit();

        holder.Thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("click", "onClick: "+position);
                if (itemList.get(position).getCategory() == 3 && itemList.get(position).getIndex() == 3) {
                    orientationEditor.putInt("flag", 1);
                    orientationEditor.commit();
                    context.startActivity(new Intent(context, ImageSlideActivity.class));
                }
                else if (itemList.get(position).getCategory() == 4) {
                    if (itemList.get(position).getIndex() == 1) {
                        orientationEditor.putInt("flag", 0);
                        orientationEditor.commit();
                        Intent intent = new Intent(context, ImageSlideActivity.class);
                        context.startActivity(intent);
                    }
                    else{

                        SharedPreferences videoPref = view.getContext().getSharedPreferences("videoPos", view.getContext().MODE_PRIVATE);
                        SharedPreferences.Editor videoEditor = videoPref.edit();
                        videoEditor.putInt("category",itemList.get(position).getCategory());
                        videoEditor.putInt("index",itemList.get(position).getIndex());
                        videoEditor.putInt("curPos",0);
                        videoEditor.putBoolean("curPlay",false);
                        videoEditor.commit();

//                        view.getContext().startActivity(new Intent(view.getContext(), VideoFullScreen.class));
                    }
                }
                else {
                    if(flag<4)
                        ((MainActivity)context).fragmentReplaceWithWatch(itemList.get(position).getCategory(), itemList.get(position).getIndex(), false, flag);
                    else
                        ((MainActivity)context).fragmentReplaceWithWatch(itemList.get(position).getCategory(), itemList.get(position).getIndex(), true, flag);
                }
            }
        });
        if(flag<4) {
            holder.Thumbnail.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                    alert.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();     //닫기
                        }
                    });
                    alert.setPositiveButton("네", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ContentsVO contentsVO = new ContentsVO();
                            contentsVO.setCategory(itemList.get(position).getCategory());
                            contentsVO.setIndex(itemList.get(position).getIndex());
                            contentsVO.setFavorite(0);
                            contentsVO.setFavoriteDate(0);
//                            contentsVO.setHistory(0);
//                            contentsVO.setHistoryDate(0);
//                            contentsVO.setDownload(0);
//                            contentsVO.setDownloadDate(0);
                            /*if(flag == 0)
                                DataBaseHandler.getInstance(context).updateFavorite(contentsVO);
                            else if(flag == 1)
                                DataBaseHandler.getInstance(context).updateHistory(contentsVO);
                            else if(flag == 2)
                                DataBaseHandler.getInstance(context).updateDownload(contentsVO);*/
//                            ((MainActivity)context).fragmentReplaceWithMyVideo(flag);
                        }
                    });
                    alert.setMessage("정말로 지우시겠습니까?");
                    alert.show();
                    return false;
                }
            });
        }

        //애니매이션 효과 추가
        new MyItemAnimator().setFadeAnimation(holder.Title);
        new MyItemAnimator().setFadeAnimation(holder.subTitle);
    }

    @Override
    public int getItemCount() {
        return (itemList.size() > 0) ? itemList.size() : 0;
    }
}
